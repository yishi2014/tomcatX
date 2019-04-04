package com.yishi.code.general.x;

public class XAnnotationAttr implements XComponent{
    private String name;
    private XAnnotationAttrVal val;
    public XAnnotationAttr(String name,XAnnotationAttrVal xAnnotationAttrVal){
        this.name=name;
        this.val=xAnnotationAttrVal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public XAnnotationAttrVal getVal() {
        return val;
    }

    public void setVal(XAnnotationAttrVal val) {
        this.val = val;
    }

    public XAnnotationAttr(String name, String val){
        this(name,new XAnnotationAttrVal(val));
    }

    public XAnnotationAttr(XAnnotationAttrVal xAnnotationAttrVal) {
        this(null,xAnnotationAttrVal);
    }
    public XAnnotationAttr(String val){
        this(null,new XAnnotationAttrVal(val));
    }
    @Override
    public String render() {
        return name+'='+val.render();
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
