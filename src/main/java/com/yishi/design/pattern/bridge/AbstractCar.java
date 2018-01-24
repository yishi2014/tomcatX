package com.yishi.design.pattern.bridge;

public abstract class AbstractCar {
    //变化因素 轮胎
    private Tire tire;
    void drive() {
        System.out.println("drive " + getName() + " with " + getTire().getName());
    }

    public AbstractCar(Tire tire) {
        this.tire = tire;
    }

    public Tire getTire() {
        return tire;
    }

    public void setTire(Tire tire) {
        this.tire = tire;
    }
    //变化因素 名称
    abstract String getName();
}
