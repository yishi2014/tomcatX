package com.yishi.code.general.basedata.construct;

public class JAnnotationAttrVal {
    public enum TYPE{
        String,val
    }
    private TYPE type;
    private String val;
    public JAnnotationAttrVal(TYPE type,String val){
        this.type=type;
        this.val=val;
    }
    public JAnnotationAttrVal(String val){
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
    public String toString() {
        switch (type) {
            case String:
                return '"' + val + '"';
            case val:
                return val;
            default:
                return "";
        }
    }
}
