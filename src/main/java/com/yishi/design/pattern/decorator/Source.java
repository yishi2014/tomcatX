package com.yishi.design.pattern.decorator;

public class Source implements Sourceable{
    @Override
    public void method() {
        System.out.println("origin method from Source");
    }
}
