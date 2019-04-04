package com.yishi.code.general.x.impl;

import com.yishi.code.general.x.XClass;

public class CommonConstructor extends XClassMethod {

    public CommonConstructor(String name, XClass returnType, String content) {
        super(name, returnType, content);
    }

    @Override
    public String render() {
        return super.render(false);
    }
}
