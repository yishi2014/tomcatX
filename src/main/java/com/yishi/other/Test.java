package com.yishi.other;


import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class Test<T> {
    private T obj;
    public void setObj(T obj){
        this.obj=obj;
    }
    public T getObj(){
        return this.obj;
    }
    public void getType(){
        System.out.println("getsuperclass");
        System.out.println(this.getClass().getSuperclass().getName());
        System.out.println("getgenericsuperclass");
        System.out.println(this.getClass().getGenericSuperclass());;
        Type type=this.getClass().getGenericSuperclass();
        System.out.println(type);
        if(ParameterizedType.class.isAssignableFrom(type.getClass())){
            System.out.println("getActualTypeArguments");
            for(Type t1: ((ParameterizedType) type).getActualTypeArguments()){
                System.out.println(t1+".");
            }
        }
    }




    public static void main(String[] args) {
//        Test<String> test=new Test<String>(){};
//        test.getType();
        System.out.println(2&1);
    }
}

