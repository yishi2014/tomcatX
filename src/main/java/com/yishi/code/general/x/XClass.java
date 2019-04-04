package com.yishi.code.general.x;

import com.yishi.code.general.basedata.construct.JClass;
import com.yishi.code.general.util.RegexUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class XClass implements Comparable<XClass>,XComponent{
    private String qualifyName;
    private String simpleName;
    private Class type;
    private boolean duplicate;

    public boolean isDuplicate() {
        return duplicate;
    }

    public void setDuplicate(boolean duplicate) {
        this.duplicate = duplicate;
    }

    private List<XClass> parameterizeTypes;

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public List<XClass> getParameterizeTypes() {
        return parameterizeTypes;
    }

    public void setParameterizeTypes(List<XClass> parameterizeTypes) {
        this.parameterizeTypes = parameterizeTypes;
    }

    public void addParameterizeType(XClass cl){
        if(this.parameterizeTypes==null)
            this.parameterizeTypes=new ArrayList<>();
        this.parameterizeTypes.add(cl);
    }

     XClass(String qualifyName){
        this.qualifyName=qualifyName;
        this.simpleName=RegexUtil.getSimpleNameFromQualifyName(qualifyName);
    }

     XClass(Class type){
        this.type=type;
        this.qualifyName=type.getName();
        this.simpleName=type.getSimpleName();
    }
    public String getQualifyName() {
        return qualifyName;
    }

    public void setQualifyName(String qualifyName) {
        this.qualifyName = qualifyName;
    }

    public String getSimpleName() {
        return simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }

    @Override
    public String toString() {
        return "XClass{" +
                "qualifyName='" + qualifyName + '\'' +
                ", simpleName='" + simpleName + '\'' +
                ", type=" + type +
                ", parameterizeTypes=" + parameterizeTypes +
                '}';
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        XClass jClass = (XClass) o;
        return Objects.equals(qualifyName, jClass.qualifyName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(qualifyName);
    }

    @Override
    public int compareTo(XClass o) {
        return String.CASE_INSENSITIVE_ORDER.compare(this.getQualifyName(),o.getQualifyName());
    }

    @Override
    public String render() {
        String str="";

         str+=duplicate?qualifyName:simpleName;
        if((this.parameterizeTypes!=null&&this.parameterizeTypes.size()>0)){
            str+='<';
            for(int i=0;i<parameterizeTypes.size();i++){
                XClass jc=parameterizeTypes.get(i);
                str+=jc.render();
                str+=',';
            }
            str=str.substring(0,str.length()-1);
            str+='>';

        }
        return str;
    }

    @Override
    public String _getName() {
        return _getName();
    }

    @Override
    public String _getRemark() {
        return null;
    }
}
