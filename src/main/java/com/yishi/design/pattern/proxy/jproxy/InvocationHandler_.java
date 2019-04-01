package com.yishi.design.pattern.proxy.jproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class InvocationHandler_ implements InvocationHandler {
    private Object object;

    public InvocationHandler_(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("proxy: " + proxy.getClass().getName());
        System.out.println("-----before");
        method.invoke(object, args);
        System.out.println("-----after");
        return null;
    }

    public static void main(String[] args) {
        Person proxy = (Person) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{Person.class}, new InvocationHandler_(new PersonImpl()));
        proxy.doWork();

    }
}
