package com.yishi.code.general.basedata.construct;

public class JAnnotationAttr {
    private String name;
    private JAnnotationAttrVal val;
    public JAnnotationAttr(String name,JAnnotationAttrVal jAnnotationAttrVal){
        this.name=name;
        this.val=jAnnotationAttrVal;
    }
    public JAnnotationAttr(String name,String val){
        this(name,new JAnnotationAttrVal(val));
    }

    public JAnnotationAttr(JAnnotationAttrVal jAnnotationAttrVal) {
        this(null,jAnnotationAttrVal);
    }
    public JAnnotationAttr(String val){
        this(null,new JAnnotationAttrVal(val));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JAnnotationAttrVal getVal() {
        return val;
    }

    public void setVal(JAnnotationAttrVal val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return name+'='+val;
    }
}
