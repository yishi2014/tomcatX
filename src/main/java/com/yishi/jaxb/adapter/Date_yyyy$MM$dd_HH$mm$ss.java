package com.yishi.jaxb.adapter;

/**
 * Created by Lustin on 2017/9/18.
 */
public class Date_yyyy$MM$dd_HH$mm$ss extends AbstractDateAdapter{
    private static final String PATTERN="yyyy-MM-dd HH:mm:ss";
    @Override
    String getPattern() {
        return PATTERN;
    }
}
