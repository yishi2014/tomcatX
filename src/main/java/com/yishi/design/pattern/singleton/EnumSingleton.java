package com.yishi.design.pattern.singleton;

/**
 * @author yishi
 * @desc 枚举类型可以防止反序列化创造对象
 */
public enum  EnumSingleton {
    INSTANCE;
    public void A() {
        System.out.println("A");
    }
    public void B(){
        System.out.println("B");
    }

    public static void main(String[] args) {
        EnumSingleton.values();
    }
}
