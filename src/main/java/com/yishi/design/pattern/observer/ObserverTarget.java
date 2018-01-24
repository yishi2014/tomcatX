package com.yishi.design.pattern.observer;

import java.util.Observable;

public class ObserverTarget extends Observable{
    @Override
    public synchronized void setChanged() {
        super.setChanged();
    }
}
