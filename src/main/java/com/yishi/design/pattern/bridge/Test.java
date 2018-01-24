package com.yishi.design.pattern.bridge;

public class Test {
    public static void main(String[] args) {
        new Jeep(new SandyTire()).drive();
        new Jeep(new RainyTire()).drive();
        new Bus(new SandyTire()).drive();
        new Bus(new RainyTire()).drive();
    }
}
