package com.yishi.test;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;

public interface ITest {
    public static void main(String[] args) throws UnsupportedEncodingException {
        byte[] bt="你好啊".getBytes("gbk");
        System.out.println(URLEncoder.encode("你好啊","gbk"));
        for(byte b:bt){
            System.out.print("%"+Integer.toHexString((b&0xff)).toUpperCase());
        }


    }
    //1000 0000
    //0111 1111
    //1000 0000
    //1110 0100
    //1110 0011/-1
    //0001 1100
    //16   12

}
