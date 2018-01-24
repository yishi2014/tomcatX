package com.yishi.design.pattern.composite;

import java.util.List;
import java.util.function.Consumer;

public class TextFile extends AbstractFile{
    public TextFile(String name){
        setName(name);
    }


    @Override
    public void remove(File file) {
        throw new RuntimeException();
    }

    @Override
    public void add(File file) {
        throw new RuntimeException();

    }

    @Override
    public List<File> getChild() {
        return null;
    }

}
