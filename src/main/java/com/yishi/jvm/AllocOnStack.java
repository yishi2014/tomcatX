package com.yishi.jvm;

public class AllocOnStack {
    public static void alloc(String name) {
        User user = new User(5,"hello");
        user.setName(name);

    }

    /**
     * -server -Xmx10m -Xms10m -XX:+DoEscapeAnalysis -XX:+PrintGC -XX:-UseTLAB -XX:+EliminateAllocations
     * @param args
     */

    public static void main(String[]args) {

        long begin = System.currentTimeMillis();



        for (int i = 0;i < 100000000;i++) {

            alloc("hello");

        }
        long end = System.currentTimeMillis();

        System.out.println(end-begin);

    }
}
class User {
    public User(int id,String name){
        this.name=name;
        this.id=id;
    }
    private String name;
    private int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}