package com.yishi.simple.instance;

import com.yishi.construct.Context;
import com.yishi.construct.Host;
import com.yishi.construct.LifeCircle;

public class SimpleHost implements Host,LifeCircle{
    private Context[] contexts;

    public Context[] getContexts() {
        return contexts;
    }

    public void setContexts(Context[] contexts) {
        this.contexts = contexts;
    }
}
