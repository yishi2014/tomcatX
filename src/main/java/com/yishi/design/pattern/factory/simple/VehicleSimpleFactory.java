package com.yishi.design.pattern.factory.simple;

/**
 * @author yishi
 * @desc 由工厂对类进行实例化,使用者只想获得对象而不关心具体实例化细节。所以即便对象的实例化过程异常复杂，也只需要传一个字符串。
 * @when 通常用到工厂方法时是因为作者写的代码要给别人调用。而对象实例化的过程可能比较复杂，比如参数过多或这对象实例化先后顺序有要求。而通过预先写好实例化逻辑，可以避免调用者自己进行实例化操作。
 * @more 若有需要可以将类构造函数置为protected，这样调用者只能从工厂获得实例化对象
 * @warn 简单工厂模式违反开闭原则
 */
public class VehicleSimpleFactory {
    private static Vehicle instance(String vehicle){
       if("jeep".equals(vehicle))
           return new Jeep();
       if("bus".equals(vehicle))
           return new Bus();
       throw new RuntimeException("无对应车型");
    }

    /**
     * 对所支持的实例化的类型进行方法枚举
     * @return
     */
    public static Vehicle instanceJeep(){
        return instance("jeep");
    }
    public static Vehicle instanceBus(){
        return instance("bus");
    }
}
