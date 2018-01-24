package com.yishi.design.pattern.composite;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Directory extends AbstractFile {
    public Directory(String name){
        setName(name);
    }
    private List<File> list=new ArrayList<>();
    @Override
    public void remove(File file) {
        this.list.remove(file);
    }

    @Override
    public void add(File file) {
        this.getChild().add(file);
        file.setParent(this);
    }

    @Override
    public List<File> getChild() {
        return this.list;
    }



    @Override
    public void execute(Consumer consumer) {
        consumer.accept(this);
        this.getChild().stream().forEach(e->e.execute(consumer));
    }

}
