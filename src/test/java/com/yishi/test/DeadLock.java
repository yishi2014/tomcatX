package com.yishi.test;

import java.util.Map;
import java.util.AbstractMap;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

public class DeadLock {
    private String teXst;
    private Date date;
    private int a;

    public String getTeXst() {
        return teXst;
    }

    public void setTeXst(String teXst) {
        this.teXst = teXst;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public static Date getDate1() {
        return date1;
    }

    public static void setDate1(Date date1) {
        DeadLock.date1 = date1;
    }

    private static Date date1=new Date();

    public DeadLock(String teXst, Date date, int a) {
        this.teXst = teXst;
        this.date = date;
        this.a = a;
    }

    public static void main(String[] args)  {
       date1.toString();
        int a=2213;
        Map map=new HashMap<>();
        AbstractMap amap=new AbstractMap() {
            @Override
            public Set<Entry> entrySet() {
                return null;
            }
        };
        test();
//        String sss=123;
        Test1111 t=new Test1111() {
            @Override
            void xxx() {
                System.out.println("1");
            }
        };
        t.xxx();
        new DeadLock().toString();
        while(true){

        }
    }
    public DeadLock(){

    }
    public static void test(){

    }

    @Override
    public String toString() {
        return super.toString();
    }
}
abstract class Test1111{
    abstract void xxx();

}