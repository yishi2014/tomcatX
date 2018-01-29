package com.yishi.design.pattern.interpreter;

public class Test {
    public static void main(String[] args) {
        Expression dir=new Directory("rootdir");
        Expression subDir=new Directory("subdir");
        Expression text1=new TextFile("text1.txt");
        Expression text2=new TextFile("text2.txt");
        Expression text3=new TextFile("text3.txt");

        dir.add(subDir);
        dir.add(text1);
        subDir.add(text2);
        subDir.add(text3);
        subDir.execute(Expression::print);
//        System.out.println("--remove text2");
//        subDir.remove(text2);
//        dir.execute(File::print);

    }

}
