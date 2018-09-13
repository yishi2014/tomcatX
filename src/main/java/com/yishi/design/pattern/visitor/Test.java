package com.yishi.design.pattern.visitor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntBinaryOperator;

public class Test {
    public static void main(String[] args) {
        List<Element> list = new ArrayList<>();
        list.add(new Element_("e1"));
        list.add(new Element_("e2"));
        list.add(new Element_("e3"));

        for (Element e : list) {
            e.accept_(Test::visit, "a");
        }
        xxx(Test::decrease);
    }

    public static void visit(Element e,String s){
        e.name();
        System.out.println(s);

    }

    static void xxx(IntBinaryOperator o){
        System.out.println(o.applyAsInt(1,2));
    }
    static int decrease(int x,int y){
        return x-y;
    }
}

class Element_ implements Element {
    private String name;

    @Override
    public void name() {
        System.out.println(name);
    }

    public Element_(String name) {
        this.name = name;
    }

    @Override
    public void accept_(Visitor<Element,String> v,String s) {
        v.accept(this,s);
    }

}


