package com.yishi.code.general.dummy;

import com.yishi.code.general.service.TableInfoService;

public interface ApplicationContext {
    <T> T getBean(Class<T> tableInfoServiceClass);
}
