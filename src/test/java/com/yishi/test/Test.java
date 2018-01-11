package com.yishi.test;

import java.util.Optional;

public class Test {
    public static void main(String[] args) {
        Test test=new Test();
        Optional<String>name=Optional.of("hola");
        name.map(test::firstLetterToUpCase).ifPresent(System.out::println);
    }

    public String firstLetterToUpCase(String s){
        if(s==null)return s;
        if(s.length()>0){
            char first=s.charAt(0);
            if((first>=0x41&&first<=0x5a)||(first>0x61&&first<=0x7a)){
                char[] chars=s.toLowerCase().toCharArray();
                chars[0]= (char) (chars[0]^0x20);
                return String.valueOf(chars);
            }
            else
                return s.toLowerCase();
        }else
            return s.toLowerCase();
    }

}
