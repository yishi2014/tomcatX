package com.yishi.design.pattern.prototype;

import java.util.Map;

public class TestObject extends Prototype{
    private String a;
    private Map m;

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public Map getM() {
        return m;
    }

    public void setM(Map m) {
        this.m = m;
    }

    @Override
    public String toString() {
        return "TestObject{" +
                "a='" + a + '\'' +
                ", m=" + m +
                '}';
    }
}
