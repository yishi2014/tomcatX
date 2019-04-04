package com.yishi.code.general.dto;

import org.hibernate.type.Type;

public class Value_Type {
    public Object val;
    public Type type;

    public Value_Type(Object val, Type type) {
        this.val = val;
        this.type = type;
    }
}
