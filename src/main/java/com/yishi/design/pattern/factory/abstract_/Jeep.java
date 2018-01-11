package com.yishi.design.pattern.factory.abstract_;

public class Jeep implements Vehicle {
    @Override
    public void run() {
        System.out.println("jeep running");
    }
    protected Jeep(){}
}
