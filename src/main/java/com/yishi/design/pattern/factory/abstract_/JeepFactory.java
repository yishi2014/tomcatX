package com.yishi.design.pattern.factory.abstract_;

public class JeepFactory implements Factory{
    @Override
    public Vehicle instance() {
        return new Jeep();
    }
}
