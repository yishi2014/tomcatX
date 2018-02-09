package com.yishi.test;

import java.io.UnsupportedEncodingException;

public class BitCalculate {
    static int lowbit(int x)
    {
        return x&-x;
        //0000 0000 0000 0000 0000 0000 0000 0001
        //1111 1111 1111 1111 1111 1111 1111 1111




    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String s=("你好；；");
        System.out.println(s.length());
        System.out.println(s.getBytes("gbk").length);
        int length=120-s.getBytes("gbk").length+s.length();
        String newstr=String.format("%"+length+"s",s);
        System.out.println(newstr);
        System.out.println(newstr.getBytes("gbk").length);
//        System.out.println((1>2)-(1<2));


//        System.out.println(String.format("%h",16));;
//        System.out.println(String.format("%1$s", 32, "Hello"));


//        System.out.println(String.format("|%-20.9s|", "Hello World"));; // 输出：|               Hello|
//        for (int i = 0; i < 100; i++) {
//            int j=-100;
//            System.out.println((j+i)+":"+lowbit(j+i));
//
//        }
    }
}
