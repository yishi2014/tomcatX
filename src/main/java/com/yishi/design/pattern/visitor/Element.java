package com.yishi.design.pattern.visitor;

public interface  Element {
    void accept_(Visitor<Element,String> v,String s);
     void name();
}
