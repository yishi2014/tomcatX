package com.yishi.code.general.x.impl;

import com.yishi.code.general.x.Indent;
import com.yishi.code.general.x.*;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractXMethod implements XMethod,XAnnotationable<XMethod> {
    private String remark;
    private String qualifier;
    private XClass returnType;
    private String name;
    private List<XParam> params;
    private String Content;
    private List<XClass> extClasses;
    private List<XAnnotation> annotations;

    public List<XClass> getExtClasses() {
        return extClasses;
    }
    public void addParam(String name,XClass jClass){
        if(this.params==null)
            this.params=new ArrayList<>();
        this.params.add(new XParam(name,jClass));
    }
    public void addParam(XParam xParam){
        if(this.params==null)
            this.params=new ArrayList<>();
        this.params.add(xParam);
    }
    public void setExtClasses(List<XClass> extClasses) {
        this.extClasses = extClasses;
    }

    public AbstractXMethod(String name, XClass returnType, String content){
        this.name=name;
        this.returnType=returnType;
        this.Content=content;
        this.qualifier="public";

    }
//    public AbstractXMethod(String name, Class returnType, String content){
//        this(name,new XClass(returnType),content);
//
//    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getQualifier() {
        return qualifier;
    }

    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }

    public XClass getReturnType() {
        return returnType;
    }

    public void setReturnType(XClass returnType) {
        this.returnType = returnType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<XParam> getParams() {
        return params;
    }

    public void setParams(List<XParam> params) {
        this.params = params;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public List<XAnnotation> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(List<XAnnotation> annotations) {
        this.annotations = annotations;
    }

    @Override
    public XMethod addAnnotation(XAnnotation xAnnotation) {
        if (this.annotations==null)
            this.annotations=new ArrayList<>();
        this.annotations.add(xAnnotation);
        return this;

    }

    public String render(boolean hasReturnType) {
        String str="";
        if(annotations!=null&&annotations.size()>0){
            for(XAnnotation j:annotations){
                str+=Indent.METHOD+j.render();
            }
        }
        str+=Indent.METHOD+getQualifier()+' ';
        if(hasReturnType){
            str+=(returnType==null?"void":returnType.render())+' ';
        }
        str+=name+'(';
        if(params!=null&&params.size()>0){
            for(XParam param:params){

                str+=param.render();
                str+=',';
            }
            str=str.substring(0,str.length()-1);
        }
        str+="){\n\n";
        str+=Indent.METHODCONTENT+Content;
        str+=Indent.METHOD+"}\n\n";
        return str;
    }
    public String render(){
        return render(true);
    }

    @Override
    public String _getName() {
        return getName();
    }

    @Override
    public String _getRemark() {
        return getRemark();
    }
}
