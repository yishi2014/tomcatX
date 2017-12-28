package com.yishi.simple.instance;

import com.yishi.construct.Context;
import com.yishi.construct.LifeCircle;
import com.yishi.construct.Wrapper;

public class SimpleContext implements Context,LifeCircle{
    private Wrapper[] wrappers;

    public Wrapper[] getWrappers() {
        return wrappers;
    }

    public void setWrappers(Wrapper[] wrappers) {
        this.wrappers = wrappers;
    }
}
