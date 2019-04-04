package com.yishi.code.general.basedata.construct;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JHead implements ImportParser {
//    private String name;
    private String qualifier;
    private String remark;
    private JClass _type;
    private List<JInterface> interfaces;
    private List<JAnnotation> annotations;
    private JObject superClass;

    private static String[] objTypestrArr={""," class "," interface "};
    private static String[] objTypeInterfaceQualifyArr={""," implements "," extends "};

    public JObject getSuperClass() {
        return superClass;
    }

    public void setSuperClass(JObject superClass) {
        this.superClass = superClass;
    }

    public List<JInterface> getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(List<JInterface> interfaces) {
        this.interfaces = interfaces;
    }

    public JHead addInterface(JInterface jInterface){
        if(this.interfaces==null)
            this.interfaces=new ArrayList<>();
        this.interfaces.add(jInterface);
        return  this;
    }

    public JClass get_type() {
        return _type;
    }

    public void set_type(JClass _type) {
        this._type = _type;
    }
    public JHead(String name){
        this(new JClass(name));
    }
    public JHead(JClass _type){
        this._type=_type;
        this.qualifier="public";
    }
    public String getName() {
        return this._type.getSimpleName();
    }

    public String getQualifier() {
        return qualifier;
    }

    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }

    public List<JAnnotation> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(List<JAnnotation> annotations) {
        this.annotations = annotations;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public Set<JClass> parseImport() {
        Set set = new HashSet<>();
        if(this._type.getParameterizeTypes()!=null)
        set.addAll(this._type.getParameterizeTypes());
        if(annotations!=null)
        for(JAnnotation j:annotations){
            set.addAll(j.parseImport());
        }
        if(interfaces!=null){
            for(JInterface j:interfaces){
                set.add(j.getjHead().get_type());
            }
        }
        return set;
    }

    private static final String CONTENT="";
    protected int getObjType(){
        //1:class
        //2 interface
        return 1;
    }

    private String getObjTypeStr(){
        return this.objTypestrArr[getObjType()];
    }
    private String getObjImplementQualifyStr(){
        return this.objTypeInterfaceQualifyArr[getObjType()];
    }
    @Override
    public String toString() {
       
        String str = "\n";
        if (annotations != null) {
            for (JAnnotation j : annotations) {
                str += j.toString();
            }
        }
        if (this.remark != null)
            str += "//" + this.remark + '\n';
        str += getQualifier() + ' ' + getObjTypeStr() + ' ' + _type.toString();
        
        if(this.superClass!=null){
            if (getObjType() == 2 )
                throw new RuntimeException("接口不能有父类");
            str +=" extend "+getQualifier()+' ';
        }
        if (this.interfaces != null) {
            str += getObjImplementQualifyStr();
            for (JInterface j : interfaces) {
                str += j.getjHead().get_type();
                str += ',';
            }
            str = str.substring(0, str.length() - 1);
        }


        str += " {\n\n";
        return str;
    }

    public void addAnnotation(JAnnotation annotation) {
        if(annotations==null)
            annotations=new ArrayList<>();
        annotations.add(annotation);
    }
}
