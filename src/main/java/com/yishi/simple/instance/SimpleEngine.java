package com.yishi.simple.instance;

import com.yishi.construct.Engine;
import com.yishi.construct.Host;
import com.yishi.construct.LifeCircle;

public class SimpleEngine implements Engine,LifeCircle {
    private Host[] hosts;

    public Host[] getHosts() {
        return hosts;
    }

    public void setHosts(Host[] hosts) {
        this.hosts = hosts;
    }
}
