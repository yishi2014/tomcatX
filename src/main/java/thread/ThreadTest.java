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
}
