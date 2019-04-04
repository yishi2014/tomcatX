package com.yishi.code.general.x;


/**
 * 规定了如何调用Builder对象，诸如先后顺序之类的流程
 */
public class XObjectDirector {
    private XObjectBuilder XObjectBuilder;
    public XObjectDirector(XObjectBuilder XObjectBuilder){
        this.XObjectBuilder = XObjectBuilder;
    }
    public XObject construct(){
        XObjectBuilder.buildXImport();
        XObjectBuilder.buildXPackage();
        XObjectBuilder.buildXHead();
        XObjectBuilder.buildXFields();
        XObjectBuilder.buildXMethods();
        XObjectBuilder.buildXFoot();
        return XObjectBuilder.getProduct();
    }



}
