package com.yishi.simple.instance;

import com.yishi.construct.LifeCircle;
import com.yishi.construct.Server;
import com.yishi.construct.Service;

import java.util.Arrays;

public class SimpleServer implements Server,LifeCircle{

    private Service[] service;

    public Service[] getService() {
        return service;
    }

    public void setService(Service[] service) {
        this.service = service;
    }

    @Override
    public void start() {
        if(service!=null)
        Arrays.stream(service).forEach(servce_->servce_.start());
    }

    @Override
    public void stop() {
        if(service!=null)
            Arrays.stream(service).forEach(service_->service_.stop());
    }
}
