package com.yishi.design.pattern.proxy.jproxy;

public class PersonImpl implements Person{
    @Override
    public void doWork() {
        System.out.println("impl do work");
    }
}
