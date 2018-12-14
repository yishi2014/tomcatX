package com.yishi.simple.instance;

import com.yishi.construct.Context;
import com.yishi.construct.LifeCircle;
import com.yishi.construct.Wrapper;

import java.util.Arrays;

public class SimpleContext implements Context,LifeCircle{
    private Wrapper[] wrappers;

    public Wrapper[] getWrappers() {
        return wrappers;
    }

    public void setWrappers(Wrapper[] wrappers) {
        this.wrappers = wrappers;
    }

    @Override
    public void start() {
        if(wrappers!=null)
        Arrays.stream(wrappers).forEach(wrapper_->wrapper_.start());
    }

    @Override
    public void stop() {
        if(wrappers!=null)
            Arrays.stream(wrappers).forEach(wrapper_->wrapper_.stop());
    }
}
