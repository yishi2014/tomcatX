package com.yishi.code.general.x.impl;

import com.yishi.code.general.basedata.construct.*;
import com.yishi.code.general.x.XAnnotation;
import com.yishi.code.general.x.XAnnotationable;
import com.yishi.code.general.x.XClass;
import com.yishi.code.general.x.XHead;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractXHead implements XHead,XAnnotationable<XHead> {
    private String name;
    private String qualifier;
    private String remark;
    private XClass _type;
    private List<XHead> interfaces;
    private List<XAnnotation> annotations;
    private JObject superClass;

    private static String[] objTypestrArr={""," class "," interface "};
    private static String[] objTypeInterfaceQualifyArr={""," implements "," extends "};
    private String getObjTypeStr(){
        return this.objTypestrArr[getObjType()];
    }
    private String getObjImplementQualifyStr(){
        return this.objTypeInterfaceQualifyArr[getObjType()];
    }
    public abstract int getObjType();

    public XHead addInterface(XHead jInterface){
        if(this.interfaces==null)
            this.interfaces=new ArrayList<>();
        this.interfaces.add(jInterface);
        return  this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQualifier() {
        return qualifier;
    }

    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public XClass get_type() {
        return _type;
    }

    public void set_type(XClass _type) {
        this._type = _type;
    }

    public List<XHead> getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(List<XHead> interfaces) {
        this.interfaces = interfaces;
    }

    public List<XAnnotation> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(List<XAnnotation> annotations) {
        this.annotations = annotations;
    }

    public JObject getSuperClass() {
        return superClass;
    }

    public void setSuperClass(JObject superClass) {
        this.superClass = superClass;
    }

//    public AbstractXHead(String name){
//        this(new XClass(name));
//    }
    public AbstractXHead(XClass _type){
        this._type=_type;
        this.qualifier="public";
    }
    @Override
    public XHead addAnnotation(XAnnotation xAnnotation) {
        if(this.annotations==null)
            this.annotations=new ArrayList<>();
        this.annotations.add(xAnnotation);
        return null;
    }

    @Override
    public String render() {
        String str = "\n";
        if (annotations != null) {
            for (XAnnotation j : annotations) {
                str += j.render();
            }
        }
        if (this.remark != null)
            str += "//" + this.remark + '\n';
        str += getQualifier() + ' ' + getObjTypeStr() + ' ' + _type.render();

        if(this.superClass!=null){
            if (getObjType() == 2 )
                throw new RuntimeException("接口不能有父类");
            str +=" extend "+getQualifier()+' ';
        }
        if (this.interfaces != null) {
            str += getObjImplementQualifyStr();
            for (XHead j : interfaces) {
                str += j.get_type().render();
                str += ',';
            }
            str = str.substring(0, str.length() - 1);
        }


        str += " {\n\n";
        return str;
    }

    @Override
    public String _getName() {
        return _type.getSimpleName();
    }

    @Override
    public String _getRemark() {
        return getRemark();
    }
}
