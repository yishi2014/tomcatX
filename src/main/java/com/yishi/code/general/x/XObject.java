package com.yishi.code.general.x;

import com.yishi.code.general.util.RegexUtil;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class XObject  {
    private XPackage xPackage;
    private XImport xImport;
    private XHead xHead;
    private List<XField> fields;
    private List<XMethod> methods;
    private XFoot xFoot;



    public XPackage getxPackage() {
        return xPackage;
    }

    public void setxPackage(XPackage xPackage) {
        this.xPackage = xPackage;
    }


    public XHead getxHead() {
        return xHead;
    }

    public XImport getxImport() {
        return xImport;
    }

    public void setxImport(XImport xImport) {
        this.xImport = xImport;
    }

    public void setxHead(XHead xHead) {
        this.xHead = xHead;
    }

    public List<XField> getFields() {
        return fields;
    }

    public void setFields(List<XField> fields) {
        this.fields = fields;
    }

    public List<XMethod> getMethods() {
        return methods;
    }

    public void setMethods(List<XMethod> methods) {
        this.methods = methods;
    }

    public XFoot getxFoot() {
        return xFoot;
    }

    public void setxFoot(XFoot xFoot) {
        this.xFoot = xFoot;
    }

    public String render() {
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append(xPackage.render()).append("\n");
        stringBuffer.append(xImport.render());
        stringBuffer.append(xHead.render());
        if(fields!=null)
            for(XField x:fields){
                stringBuffer.append(x.render());
            }
        if(methods!=null)
        for(XMethod x:methods){
            stringBuffer.append(x.render());
        }
        stringBuffer.append(xFoot.render());
        return stringBuffer.toString();
    }

    public String _getName() {
        return this.xHead._getName();
    }

    public String _getRemark() {
        return this.xHead._getRemark();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        XObject xObject = (XObject) o;
        return Objects.equals(xPackage, xObject.xPackage) &&
                Objects.equals(xHead, xObject.xHead);
    }

    @Override
    public int hashCode() {

        return Objects.hash(xPackage, xHead);
    }
    public String getPath(String prefix){
        return RegexUtil.formateduplicateChar(prefix+'.'+xPackage.getPkgName()+'.'+this._getName(),".",File.separator)+".java";
    }
}
