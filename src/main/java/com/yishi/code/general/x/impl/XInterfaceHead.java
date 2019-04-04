package com.yishi.code.general.x.impl;

import com.yishi.code.general.x.XClass;

public class XInterfaceHead extends AbstractXHead {


    @Override
    public int getObjType() {
        return 2;
    }

    public XInterfaceHead(XClass _type) {
        super(_type);
    }
}
