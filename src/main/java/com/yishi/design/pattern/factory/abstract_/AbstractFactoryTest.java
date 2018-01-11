package com.yishi.design.pattern.factory.abstract_;

public class AbstractFactoryTest {
    public static void main(String[] args) {
        new BusFactory().instance().run();
        new JeepFactory().instance().run();
    }
}
