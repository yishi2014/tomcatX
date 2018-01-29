package com.yishi.design.pattern.interpreter;

import java.util.function.Consumer;

public abstract class AbstractFile implements Expression {
    private String name;
    private Expression parent;
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Expression getParent() {
        return parent;
    }

    public void setParent(Expression parent) {
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
    public void execute(Consumer<Expression> consumer) {
        consumer.accept(this);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
