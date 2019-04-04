package com.yishi.code.general.basedata.construct;

public class JInterfaceHead extends JHead {

    public JInterfaceHead(String name) {
        super(name);
    }

    public JInterfaceHead(JClass _type) {
        super(_type);
    }

    @Override
    protected int getObjType() {
        return 2;
    }
}
