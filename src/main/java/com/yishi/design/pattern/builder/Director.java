package com.yishi.design.pattern.builder;


/**
 * 规定了如何调用Builder对象，诸如先后顺序之类的流程
 */
public class Director {
    private Builder builder;
    public Director(Builder builder){
        this.builder=builder;
    }
    public ComputerProduct construct(){
        builder.buidMounse();
        builder.buildKeyBoard();
        builder.buildScreen();
        builder.buildMainEngine();
        return builder.getProduct();
    }

    public static void main(String[] args) {
        System.out.println(new Director(new DellConcreteBuilder()).construct());
        System.out.println(new Director(new HPConcreteBuilder()).construct());

    }

}
