package com.yishi.design.pattern.observer;

public class Test {
    public static void main(String[] args) {
        ObserverTarget target=new ObserverTarget();
        for (int i = 0; i < 10; i++) {
            target.addObserver(new Observer_("观察者"+i));
        }

        target.setChanged();
        target.notifyObservers();
        target.notifyObservers();
    }
}
