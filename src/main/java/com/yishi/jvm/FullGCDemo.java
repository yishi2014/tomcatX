package com.yishi.jvm;

public class FullGCDemo {
//    private byte[] useless= new byte [1024 * 1024 * 100];;

    /**
     * -XX:+UseSerialGC -Xms200M -Xmx200M -Xmn32m -XX:SurvivorRatio=8 -XX:+PrintGCDetails
     * 创建一个大约Eden space最大值的对象 由于空间不足 将进入old space 而此时old space 空间不足
     */
    public void largeObjOnEden(){
        //老年代空间不足
        //按照上面的参数推算:老年代大小: 200 -32m = 168M
        byte [] MAXOBJ = new byte [1024 * 1024 * 100]; // 100M

        byte [] MAXOBJ2 = new byte [1024 * 1024 * 60]; // 60M

//        byte [] MAXOBJ3 = new byte [1024 * 1024 * 100]; // 60M
    }

    public static void main(String[] args) {
       FullGCDemo f= new FullGCDemo();
       f.largeObjOnEden();
    }
}
