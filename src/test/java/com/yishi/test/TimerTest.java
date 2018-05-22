package com.yishi.test;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimerTest {
    private Timer timer ;
    private Timer timer1;

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public Timer getTimer1() {
        return timer1;
    }

    public void setTimer1(Timer timer1) {
        this.timer1 = timer1;
    }

    public TimerTest(Timer timer, Timer timer1) {
        this.timer = timer;
        this.timer1 = timer1;

    }

    public TimerTest() {
        this(new Timer(),new Timer());
    }

    public static void main(String[] args) {


        TimerTest test=new TimerTest();
        test.getTimer().schedule(new TimerTask() {
            private int t=0;
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getId()+"run0000000000000000000000000000             "+(++t));
            }
        },new Date(new Date().getTime()+5000l),1000l*5);

        test.getTimer1().schedule(new TimerTask() {
            private int t=0;
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getId()+"run11111111111111111111111111            "+(++t));
                if(t>10)
                    throw new RuntimeException();
            }
        },new Date(new Date().getTime()+5000l),1000l*5);

    }
}
