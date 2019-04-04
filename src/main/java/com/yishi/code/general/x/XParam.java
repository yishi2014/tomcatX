package com.yishi.code.general.x;

import java.util.ArrayList;
import java.util.List;

public class XParam implements XComponent{

    private String name;
    private XClass jClass;
    private List<XAnnotation> annotations;


    public XParam(String name, XClass jClass) {
        this.name = name;
        this.jClass = jClass;
    }

    public void addAnnotation(XAnnotation xAnnotation){
        if(this.annotations==null)
            this.annotations=new ArrayList<>();
        this.annotations.add(xAnnotation);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public XClass getjClass() {
        return jClass;
    }

    public void setjClass(XClass jClass) {
        this.jClass = jClass;
    }

    @Override
    public String toString() {
        return jClass.getSimpleName()+' '+name;
    }

    @Override
    public String render() {
        String str="";
        if(this.annotations!=null){
            for(XAnnotation an:this.annotations){
                str+=an.render(false);
            }
            str+=' ';
        }
        str+= jClass.render()+' '+name;
        return str;
    }

    @Override
    public String _getName() {
        return null;
    }

    @Override
    public String _getRemark() {
        return null;
    }
}
