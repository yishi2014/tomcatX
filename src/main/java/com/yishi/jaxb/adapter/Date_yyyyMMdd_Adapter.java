package com.yishi.jaxb.adapter;

/**
 * Created by Lustin on 2017/9/16.
 */
public class Date_yyyyMMdd_Adapter extends AbstractDateAdapter {
    private static final String PATTERN="yyyyMMdd";
    @Override
    String getPattern() {
        return PATTERN;
    }
}
