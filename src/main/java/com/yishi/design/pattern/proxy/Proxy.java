package com.yishi.design.pattern.proxy;

public class Proxy implements Sourceable {
    private Sourceable source;

    public Proxy() {
        this.source=new Source();
    }

    @Override
    public void method() {
        System.out.println("before method from Proxy");
        source.method();
        System.out.println("after method from Proxy");
    }
}
