package com.yishi.jaxb.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Lustin on 2017/9/16.
 */
public abstract class AbstractDateAdapter extends XmlAdapter<String,Date> {
    @Override
    public Date unmarshal(String v) throws Exception {
        SimpleDateFormat dateFormat=new SimpleDateFormat(getPattern());
        return dateFormat.parse(v);
    }
    @Override
    public String marshal(Date v) throws Exception {
        SimpleDateFormat dateFormat=new SimpleDateFormat(getPattern());
        return dateFormat.format(v);
    }
    abstract String getPattern();
}
