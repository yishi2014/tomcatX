package com.yishi.code.general.page;

import com.yishi.code.general.dto.Result;

import java.util.List;

public interface Service<T> {
    List<T> query(T t);
    PageData<T> query(Page page, T xEntity);
    Result update(T t);
    Result delete(Object t);
    Result insert(T t);
}
