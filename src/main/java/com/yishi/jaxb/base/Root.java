package com.yishi.jaxb.base;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Lustin on 2017/9/17.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="ROOT")
public class Root {

    @XmlElement(name = "IS_SUCCESS")
    private Success success;

    public Success getSuccess() {
        return success;
    }

    public void setSuccess(Success success) {
        this.success = success;
    }
    public Root(){}
    public Root(Success success){
        this.success=success;
    }
    public static Root generalResult(String code, String msg){
        return new Root(new Success(code,msg));
    }
    public static Root generalSuccessResult(){
        return new Root(Success.generalSuccessResult());
    }
    public static Root generalFailerResult(){
        return new Root(Success.generalFailerResult());
    }
}
