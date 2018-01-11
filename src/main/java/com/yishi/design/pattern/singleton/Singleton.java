package com.yishi.design.pattern.singleton;

/**
 * @author yishi
 * @warn 在JDK1.5之后，双重检查锁定才能够正常达到单例效果
 *
 */
public class Singleton {
    private volatile static Singleton singleton;
    private Singleton (){}
    public static Singleton getSingleton() {
        if (singleton == null) {
            synchronized (Singleton.class) {
                if (singleton == null) {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }
}

