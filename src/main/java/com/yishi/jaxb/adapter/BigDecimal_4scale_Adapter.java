package com.yishi.jaxb.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by Lustin on 2017/9/16.
 */
public class BigDecimal_4scale_Adapter extends XmlAdapter<String, BigDecimal> {
    @Override
    public BigDecimal unmarshal(String v) throws Exception {
        return new BigDecimal(v).setScale(4, RoundingMode.HALF_UP);
    }

    @Override
    public String marshal(BigDecimal v) throws Exception {
        return v.setScale(4, RoundingMode.HALF_UP).toString();
    }
}
