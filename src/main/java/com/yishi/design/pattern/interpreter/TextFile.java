package com.yishi.design.pattern.interpreter;

import java.util.List;

public class TextFile extends AbstractFile {
    public TextFile(String name){
        setName(name);
    }


    @Override
    public void remove(Expression file) {
        throw new RuntimeException();
    }

    @Override
    public void add(Expression file) {
        throw new RuntimeException();

    }

    @Override
    public List<Expression> getChild() {
        return null;
    }

    @Override
    public int getVal() {
        return Integer.valueOf(getVal());
    }
}
