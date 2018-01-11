package com.yishi.design.pattern.factory.abstract_;

public class BusFactory implements Factory{
    @Override
    public Vehicle instance() {
        return new Bus();
    }
}
