package com.yishi.code.general.x.impl;

import com.yishi.code.general.x.XAnnotation;
import com.yishi.code.general.x.XClass;
import com.yishi.code.general.x.XHead;
import com.yishi.code.general.x.XObject;

import java.util.List;

public class XClassHead extends AbstractXHead{


    @Override
    public int getObjType() {
        return 1;
    }

    public XClassHead(XClass _type) {
        super(_type);
    }
}
