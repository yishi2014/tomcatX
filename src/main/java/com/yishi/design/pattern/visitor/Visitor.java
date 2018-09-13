package com.yishi.design.pattern.visitor;

@FunctionalInterface
public  interface Visitor<T,E>  {
    void accept(T t,E e);
}
