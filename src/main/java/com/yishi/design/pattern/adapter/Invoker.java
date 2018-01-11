package com.yishi.design.pattern.adapter;

public class Invoker {
    void invoke(BusinessInterface businessInterface){
        businessInterface.methodA();
    }
}
