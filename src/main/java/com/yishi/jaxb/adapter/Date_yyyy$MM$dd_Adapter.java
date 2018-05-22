package com.yishi.jaxb.adapter;

/**
 * Created by Lustin on 2017/9/16.
 */
public class Date_yyyy$MM$dd_Adapter extends AbstractDateAdapter {
    private static final String PATTERN="yyyy-MM-dd";
    @Override
    String getPattern() {
        return PATTERN;
    }
}
