package com.yishi.design.pattern.interpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Directory extends AbstractFile {
    public Directory(String name){
        setName(name);
    }
    private List<Expression> list=new ArrayList<>();
    @Override
    public void remove(Expression file) {
        this.list.remove(file);
    }

    @Override
    public void add(Expression file) {
        this.getChild().add(file);
        file.setParent(this);
    }

    @Override
    public List<Expression> getChild() {
        return this.list;
    }



    @Override
    public void execute(Consumer consumer) {
        consumer.accept(this);
        this.getChild().stream().forEach(e->e.execute(consumer));
    }

    @Override
    public int getVal() {
        return 0;
    }
}
