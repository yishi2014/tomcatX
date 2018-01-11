package com.yishi.design.pattern.builder;


/**
 * 建造接口
 * 定义了创建产品需要的所有方法
 *
 */
public abstract class Builder {
    protected ComputerProduct computer =new ComputerProduct();
    abstract void buidMounse();
    abstract void buildKeyBoard();
    abstract void buildScreen();
    abstract void buildMainEngine();
    abstract ComputerProduct getProduct();
}
