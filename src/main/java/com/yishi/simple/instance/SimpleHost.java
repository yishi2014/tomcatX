package com.yishi.simple.instance;

import com.yishi.construct.Context;
import com.yishi.construct.Host;
import com.yishi.construct.LifeCircle;

import java.util.Arrays;

public class SimpleHost implements Host,LifeCircle{
    private Context[] contexts;

    public Context[] getContexts() {
        return contexts;
    }

    public void setContexts(Context[] contexts) {
        this.contexts = contexts;
    }

    @Override
    public void start() {
        if(contexts!=null)
        Arrays.stream(contexts).forEach(context -> context.start());
    }

    @Override
    public void stop() {
        if(contexts!=null)
            Arrays.stream(contexts).forEach(context -> context.stop());

    }
}
