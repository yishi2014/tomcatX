package com.yishi.code.general.basedata;


public class CopyTest {
    public static void main(String[] args) {
        System.out.println(getStep("1"));

    }
    private static String getStep(String oldStatus) {
        //申请状态（0已申请，10初审成功，11初审失败，20评估成功，21评估失败，30贷款审核成功，31贷款审核失败，40缴纳服务费，41缴纳服务费失败，50完成质押成功，51完成质押失败，90放款失败， 99放款成功）
        switch (oldStatus){
            case "11":
                return "初审";
            case "21":
                return "评估";
            case "31":
                return "贷款审核";
            case "41":
                return "缴纳服务费";
        }
        return null;
    }

}

class A{

    private String a;
    private Integer b;

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public Integer getB() {
        return b;
    }

    public void setB(Integer b) {
        this.b = b;
    }

    @Override
    public String toString() {
        return "A{" +
                "a='" + a + '\'' +
                ", b=" + b +
                '}';
    }
}