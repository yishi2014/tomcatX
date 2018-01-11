package com.yishi.design.pattern.factory.simple;

public class SimpleFactoryTest {
    public static void main(String[] args) {
        VehicleSimpleFactory.instanceBus().run();
        VehicleSimpleFactory.instanceJeep().run();
    }
}
