package com.yishi.design.pattern.prototype;

import java.io.IOException;
import java.util.HashMap;

public class Test {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        TestObject a=new TestObject();
//        HashMap m1=new HashMap();
//        m1.put("hello","hello");
//        a.setA("hello");
//        a.setM(m1);
//        TestObject b= (TestObject) a.Clone();
//        b.setA("tom");
//        HashMap m2=new HashMap();
//        m2.put("tom","tom");
//        b.setM(m2);
//
//        System.out.println(a);;
//        System.out.println(b);;


        TestObject1 aa=new TestObject1();
        HashMap m11=new HashMap();
        m11.put("hello","hello");
        aa.setA("hello");
        aa.setM(m11);
        TestObject1 bb= (TestObject1) aa.Clone();
        bb.setA("tom");
        HashMap m22=new HashMap();
        m22.put("tom","tom");
        bb.setM(m22);
        System.out.println(aa);;
        System.out.println(bb);;


        TestObject2 a3=new TestObject2();
        HashMap m13=new HashMap();
        m13.put("hello","hello");
        a3.setA("hello");
        a3.setM(m13);
        TestObject2 b3= (TestObject2) a3.Clone();
        b3.setA("tom");
        HashMap m23=new HashMap();
        m23.put("tom","tom");
        b3.setM(m23);
        System.out.println(aa);;
        System.out.println(bb);;
    }
}
