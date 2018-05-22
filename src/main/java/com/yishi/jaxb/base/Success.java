package com.yishi.jaxb.base;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

/**
 * Created by Lustin on 2017/9/16.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Success {
    public static final String SUCCESS_CODE = "00";
    public static final String SUCCESS_MSG = "成功";
    public static final String FAILER_CODE = "99";
    public static final String FAILER_MSG = "失败";
    public static final String NORMAL_CODE ="01";
    public static final String NORMAL_MSG = "暂无需推送数据";

    public Success(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Success() {

    }

    @XmlAttribute(name = "CODE")
    private String code;
    @XmlValue
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void generateSuccess() {
        this.setCode(SUCCESS_CODE);
        this.setMsg(SUCCESS_MSG);
    }

    public void generateNormal(){
        this.setCode(NORMAL_CODE);
        this.setMsg(NORMAL_MSG);
    }
    public void generateFailer() {
        this.setCode(FAILER_CODE);
        this.setMsg(FAILER_MSG);
    }

    public static Success generalSuccessResult() {
        return new Success(SUCCESS_CODE, SUCCESS_MSG);
    }

    public static Success generalNormalResult(){
        return new Success(NORMAL_CODE,NORMAL_MSG);
    }

    public static Success generalFailerResult() {
        return new Success(FAILER_CODE, FAILER_MSG);
    }
}
