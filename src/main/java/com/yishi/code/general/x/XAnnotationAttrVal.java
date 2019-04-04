package com.yishi.code.general.x;

public class XAnnotationAttrVal implements XComponent{
    public enum TYPE{
        String,val
    }
    private TYPE type;
    private String val;
    public XAnnotationAttrVal(TYPE type, String val){
        this.type=type;
        this.val=val;
    }
    public XAnnotationAttrVal(String val){
        this(TYPE.String,val);
    }

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    @Override
    public String render() {
        switch (type) {
            case String:
                return '"' + val + '"';
            case val:
                return val;
            default:
                return "";
        }
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
