package com.yishi.html.CommonTableFillData;

import java.util.List;

public class SimpleJsonKey extends AbstractJsonKey {
    public SimpleJsonKey(String name){
        setName(name);
    }
    @Override
    public void remove(JsonKey file) {
        throw new RuntimeException();
    }

    @Override
    public void add(JsonKey file) {
        throw new RuntimeException();
    }

    @Override
    public List<JsonKey> getChild() {
        return null;
    }
}
