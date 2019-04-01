package com.yishi.mini;

/**
 * 为业务服务的功能
 */
public class Module extends LifeCircle{

    @Override
    public void start() {
        System.out.println(this.getClass().getName()+":"+this.name+" start");
    }

    @Override
    public void stop() {
        System.out.println(this.getClass().getName()+":"+this.name+" stop");
    }

    private String name;

    public Module(String name) {
        this.name = name;
    }
}
