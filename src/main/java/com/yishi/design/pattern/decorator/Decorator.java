package com.yishi.design.pattern.decorator;

public class Decorator implements Sourceable {
    private Sourceable source;

    public Decorator(Source source) {
        this.source=source;
    }

    @Override
    public void method() {
        System.out.println("before method from Decorator");
        source.method();
        System.out.println("after method from Decorator");
    }
}
