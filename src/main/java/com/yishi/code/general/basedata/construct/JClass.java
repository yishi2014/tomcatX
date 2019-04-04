package com.yishi.code.general.basedata.construct;

import com.yishi.code.general.util.RegexUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JClass implements Comparable<JClass>{
    private String qualifyName;
    private String simpleName;
    private Class type;
    private List<JClass> parameterizeTypes;

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public List<JClass> getParameterizeTypes() {
        return parameterizeTypes;
    }

    public void setParameterizeTypes(List<JClass> parameterizeTypes) {
        this.parameterizeTypes = parameterizeTypes;
    }

    public void addParameterizeType(JClass cl){
        if(this.parameterizeTypes==null)
            this.parameterizeTypes=new ArrayList<>();
        this.parameterizeTypes.add(cl);
    }
    public JClass(String qualifyName){
        this.qualifyName=qualifyName;
        this.simpleName=RegexUtil.getSimpleNameFromQualifyName(qualifyName);
    }

    public JClass(Class type){
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
        String str=simpleName;
        if((this.parameterizeTypes!=null&&this.parameterizeTypes.size()>0)){
            str+='<';
            for(int i=0;i<parameterizeTypes.size();i++){
                JClass jc=parameterizeTypes.get(i);
                str+=jc.toString();
                str+=',';
            }
            str=str.substring(0,str.length()-1);
            str+='>';

        }
        return str;
    }

    public static void main(String[] args) {
        JClass jClass=new JClass(List.class);
        JClass jClass1=new JClass(List.class);
        jClass1.addParameterizeType(new JClass(String.class));
        jClass1.addParameterizeType(new JClass(String.class));
        jClass.addParameterizeType(jClass1);

        System.out.println(jClass);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JClass jClass = (JClass) o;
        return Objects.equals(qualifyName, jClass.qualifyName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(qualifyName);
    }

    @Override
    public int compareTo(JClass o) {
        return String.CASE_INSENSITIVE_ORDER.compare(this.getQualifyName(),o.getQualifyName());
    }

}
