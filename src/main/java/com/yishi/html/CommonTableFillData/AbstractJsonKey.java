package com.yishi.html.CommonTableFillData;

import java.util.function.Consumer;

public abstract class AbstractJsonKey implements JsonKey {
    private String name;
    private JsonKey parent;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JsonKey getParent() {
        return parent;
    }

    public void setParent(JsonKey parent) {
        this.parent = parent;
    }

    public String getPath() {
        if (this.getParent() == null) {
            return this.getName();
        } else {
            return this.getParent().getPath() + "." + this.getName();
        }
    }
    public void print() {
        System.out.println(this.getPath());
    }
    public void execute(Consumer<JsonKey> consumer) {
        consumer.accept(this);
    }

}
