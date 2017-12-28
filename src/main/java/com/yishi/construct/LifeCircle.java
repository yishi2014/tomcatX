package com.yishi.construct;


public interface LifeCircle {
    default void start() {
        System.out.println("starting");
    }
    default void stop(){
        System.out.println("stopping");
    }
}
