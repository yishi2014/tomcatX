package com.yishi.design.pattern.composite;

public class Test {
    public static void main(String[] args) {
        File dir=new Directory("rootdir");
        File subDir=new Directory("subdir");
        File text1=new TextFile("text1.txt");
        File text2=new TextFile("text2.txt");
        File text3=new TextFile("text3.txt");

        dir.add(subDir);
        dir.add(text1);
        subDir.add(text2);
        subDir.add(text3);
        subDir.execute(File::print);
//        System.out.println("--remove text2");
//        subDir.remove(text2);
//        dir.execute(File::print);

    }

}
