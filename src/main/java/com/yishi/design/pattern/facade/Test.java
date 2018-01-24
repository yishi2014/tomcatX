package com.yishi.design.pattern.facade;

public class Test {
    public static void main(String[] args) {
        SystemFacade systemFacade=new SystemFacade();
        systemFacade.run();
        systemFacade.stop();
    }
}
