package com.yishi.design.pattern.composite;

import java.util.function.Consumer;

public abstract class AbstractFile implements File {
    private String name;
    private File parent;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File getParent() {
        return parent;
    }

    public void setParent(File parent) {
        this.parent = parent;
    }

    public String getPath() {
        if (this.getParent() == null) {
            return this.getName();
        } else {
            return this.getParent().getPath() + java.io.File.separator + this.getName();
        }
    }
    public void print() {
        System.out.println(this.getPath());
    }
    public void execute(Consumer<File> consumer) {
        consumer.accept(this);
    }
}
