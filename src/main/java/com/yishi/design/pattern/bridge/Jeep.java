package com.yishi.design.pattern.bridge;

public class Jeep extends AbstractCar {
    @Override
    String getName() {
        return "jeep";
    }

    public Jeep(Tire tire) {
        super(tire);
    }
}
