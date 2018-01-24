package com.yishi.design.pattern.bridge;

public class Bus extends AbstractCar{
    @Override
    String getName() {
        return "bus";
    }

    public Bus(Tire tire) {
        super(tire);
    }
}
