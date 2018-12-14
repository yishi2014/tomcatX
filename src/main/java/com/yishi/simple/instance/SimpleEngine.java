package com.yishi.simple.instance;

import com.yishi.construct.Engine;
import com.yishi.construct.Host;
import com.yishi.construct.LifeCircle;

import java.util.Arrays;

public class SimpleEngine implements Engine,LifeCircle {
    private Host[] hosts;

    public Host[] getHosts() {
        return hosts;
    }

    public void setHosts(Host[] hosts) {
        this.hosts = hosts;
    }

    @Override
    public void start() {
        if(hosts!=null)
        Arrays.stream(hosts).forEach(host_->host_.start());
    }

    @Override
    public void stop() {
        if(hosts!=null)
            Arrays.stream(hosts).forEach(host_->host_.stop());
    }
}
