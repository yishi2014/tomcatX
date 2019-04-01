package com.yishi.design.pattern.proxy.cglibproxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class Interceptor_ implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println(methodProxy.getSignature());
        System.out.println("----befor");
        Object re=methodProxy.invokeSuper(o, objects);
        System.out.println("----after");
        return re;
    }

    public static void main(String[] args) {
        Enhancer enhancer=new Enhancer();
        enhancer.setSuperclass(Hello.class);
        enhancer.setCallback(new Interceptor_());
        Hello hello= (Hello) enhancer.create();
        hello.sayHello();
    }
}
