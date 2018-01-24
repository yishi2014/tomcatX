package com.yishi.design.pattern.decorator;

import com.sun.org.apache.xpath.internal.SourceTree;

public class Test {
    public static void main(String[] args) {
        new Decorator(new Source()).method();
    }
}
