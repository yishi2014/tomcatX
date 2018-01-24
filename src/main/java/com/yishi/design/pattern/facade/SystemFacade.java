package com.yishi.design.pattern.facade;

public class SystemFacade {
    private SystemClass systemClass;
    public SystemFacade(){
        this.systemClass=new SystemClass();
    }
    public void run(){
        systemClass.methodA();
        systemClass.methodA();
    }
    public void stop(){
        systemClass.methodC();
        systemClass.methodD();
    }
}
