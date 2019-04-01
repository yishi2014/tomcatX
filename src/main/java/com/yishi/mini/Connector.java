package com.yishi.mini;

public class Connector extends LifeCircle{
    @Override
    public void start() {
        System.out.println(this.getClass().getName()+":"+this.name+" start");
    }

    @Override
    public void stop() {
        System.out.println(this.getClass().getName()+":"+this.name+" stop");
    }

    private String name;

    public Connector(String name) {
        this.name = name;
    }
}
