package com.yishi.design.pattern.prototype;

import com.alibaba.fastjson.JSON;

public class Prototype2 implements Prototype_ {
    @Override
    public Prototype_ Clone() {
        return JSON.parseObject(JSON.toJSONString(this),this.getClass());
    }
}
