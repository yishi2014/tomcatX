package com.yishi.design.pattern.observer;

import java.util.Observable;
import java.util.Observer;

public class Observer_ implements Observer{
    private String name;

    public String getName() {
        return name;
    }

    public Observer_(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println(getName()+" 收到消息，将执行相应变更方法");
    }
}
