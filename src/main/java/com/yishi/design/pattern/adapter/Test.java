package com.yishi.design.pattern.adapter;

public class Test {
    public static void main(String[] args) {
        new Invoker().invoke(new BusinessAdapter());
    }
}
