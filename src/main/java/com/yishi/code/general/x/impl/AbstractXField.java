package com.yishi.code.general.x.impl;

import com.yishi.code.general.x.Indent;
import com.yishi.code.general.x.XAnnotation;
import com.yishi.code.general.x.XAnnotationable;
import com.yishi.code.general.x.XClass;
import com.yishi.code.general.x.XField;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractXField implements XField,XAnnotationable<XField> {
    private String name;
    private String remark;
    private XClass _type;
    private List<XAnnotation> annotations;
//    public AbstractXField(Class type,String name){
//        this(new XClass(type),name);
//
//    }
//    public AbstractXField(String qualifyName,String name){
//        this(new XClass(qualifyName),name);
//    }

    public AbstractXField(XClass _type, String name){
        this._type=_type;
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<XAnnotation> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(List<XAnnotation> annotations) {
        this.annotations = annotations;
    }

    @Override
    public XField addAnnotation(XAnnotation xAnnotation) {
        if(this.annotations==null)
            this.annotations=new ArrayList<>();
        this.annotations.add(xAnnotation);
        return this;
    }

    @Override
    public String render() {
        String str = "";
        if (annotations != null && annotations.size() > 0) {
            for (XAnnotation j : annotations) {
                str += Indent.FIELD +j.render();
            }
        }
        if (this.remark != null)
            str += Indent.FIELD + "//" + this.remark + '\n';

        str += Indent.FIELD + "private " + _type.render() + ' ' + name + ";\n\n";
        return str;
    }

    @Override
    public String _getName() {
        return this.getName();
    }

    @Override
    public String _getRemark() {
        return this.getRemark();
    }
}
