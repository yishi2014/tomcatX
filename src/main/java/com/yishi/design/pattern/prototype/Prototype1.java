package com.yishi.design.pattern.prototype;

import com.rits.cloning.Cloner;

import java.io.IOException;

public class Prototype1 implements Prototype_ {
    @Override
    public Prototype_ Clone()  {
        Cloner cloner = new Cloner();
        Prototype1 cloned = cloner.deepClone(this);
        return cloned;
    }
}
