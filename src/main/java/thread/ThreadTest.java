package thread;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadTest {
    public static void main(String[] args) {
//        System.getProperties().forEach((a,b)-> System.out.format("%s:%s\n", a.toString(),b.toString()));
//        System.out.println(Runtime.getRuntime().availableProcessors());
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                System.out.println("1");
            }
        });
        char [] xx=new char[Integer.MAX_VALUE/256];
        int in=new Scanner(System.in).nextInt();
        System.out.println(xx.length);
        System.out.println("hello1");
        throw new RuntimeException("throw exception");
    }
    public void a(){
        int i=Runtime.getRuntime().availableProcessors();
        if(i>1){
            ExecutorService pool= Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        }
    }

    void test(){
        ExecutorService pool=Executors.newFixedThreadPool(100);
        for(int i=0;i<100;i++){
            pool.submit(new Invoker());
        }

    }

    class Invoker implements Runnable{
        @Override
        public void run() {
            //************************方法getPayinfoResult准备执行************************
            //接受参数为:
            //[<?xml version="1.0" encoding="UTF-8"?>
            //<ROOT>
            //<REQUEST>
            //<PARAM NAME="CHANNELNO">330101005</PARAM>
            //<PARAM NAME="ORIGINALNOTICENO">365260</PARAM>
            //<PARAM NAME="YWCODE">
            //</PARAM>
            //</REQUEST>
            //</ROOT>]
            //执行结果:
            //<?xml version="1.0" encoding="GBK" standalone="yes"?><ROOT><IS_SUCCESS CODE="00">成功</IS_SUCCESS><ERRMSG></ERRMSG><REQUEST><PARAM NAME="NOTICENO">330101005365260</PARAM><PARAM NAME="CHANNELNO">330101005</PARAM><PARAM NAME="ORIGINALNOTICENO">365260</PARAM><PARAM NAME="YWCODE"></PARAM></REQUEST><RESPONSE><RESULT><DETAIL><NOTICENO>330101005365260</NOTICENO><PAYLISTNO>001201811130027558</PAYLISTNO><PAYNO>0012018111300027582</PAYNO><CERTIFICATENO>00120181113028263</CERTIFICATENO><YWCODE></YWCODE><CHECKCODE>fe8fc</CHECKCODE><TRADECODE>98</TRADECODE><TRADENAME>支付宝</TRADENAME><WAYCODE>32</WAYCODE><WAYNAME>移动支付（支付机构）</WAYNAME><TRADENO>2018111322001406501012919445</TRADENO><TRADEDATE>20181113</TRADEDATE><PAYMONEY>1027.4</PAYMONEY><PAYDATE>20181113</PAYDATE><PAYTIME>175029</PAYTIME></DETAIL></RESULT></RESPONSE></ROOT>
            //
            //************************方法getPayinfoResult执行结束************************
        }
    }
}
