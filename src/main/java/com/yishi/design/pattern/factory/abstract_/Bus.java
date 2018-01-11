package com.yishi.design.pattern.factory.abstract_;

public class Bus implements Vehicle {
    @Override
    public void run() {
        System.out.println("bus running");
    }
    protected Bus(){}
}
