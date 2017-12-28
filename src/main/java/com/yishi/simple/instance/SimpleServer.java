package com.yishi.simple.instance;

import com.yishi.construct.LifeCircle;
import com.yishi.construct.Server;
import com.yishi.construct.Service;

public class SimpleServer implements Server,LifeCircle{

    private Service[] service;

    public Service[] getService() {
        return service;
    }

    public void setService(Service[] service) {
        this.service = service;
    }
}
