package com.yishi.html.CommonTableFillData;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ArrayContainerJsonKey extends AbstractJsonKey{
    private List<JsonKey> list=new ArrayList<>();
    public ArrayContainerJsonKey(String name){
        setName(name);
    }

    @Override
    public void remove(JsonKey file) {
        this.list.remove(file);
    }

    @Override
    public void add(JsonKey file) {
        this.getChild().add(file);
        file.setParent(this);
    }

    @Override
    public List<JsonKey> getChild() {
        return this.list;
    }
    @Override
    public void execute(Consumer<JsonKey> consumer) {
//        consumer.accept(this);
        this.getChild().stream().forEach(e->e.execute(consumer));
    }

    public String getPath() {
        if (this.getParent() == null) {
            return this.getName();
        } else {
            return this.getParent().getPath()  + this.getName();
        }
    }
}
