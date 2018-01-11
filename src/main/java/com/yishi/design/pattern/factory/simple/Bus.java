package com.yishi.design.pattern.factory.simple;

public class Bus implements Vehicle {
    @Override
    public void run() {
        System.out.println("bus running");
    }
    protected Bus(){}
}
