
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class D {
    private List<? extends String> list=null;
    private List<String> list1=null;

    public void methodAAAAAAAAAAAAAAA(){
        DDDDDDDDDDDDDDDDD d=new DDDDDDDDDDDDDDDDD();
        System.out.println(d);
        int a=1;
        int b=2;
        int c=a+b;
        System.out.println(c);
    }
//    public static void httpProxy() throws IOException {
//        // 代理对象
//        Proxy proxy = new Proxy(Proxy.Type.HTTP,
//                new InetSocketAddress(IP, PORT));
//
//        // 需要访问的地址
//        String urlStr = "http://www.zhyea.com";
//        // 创建连接
//        URL url = new URL(urlStr);
//        URLConnection conn = url.openConnection(proxy);
//        // 输出访问结果
//        try {
//            Scanner scan = new Scanner(conn.getInputStream());
//            StringBuilder builder = new StringBuilder();
//            while (scan.hasNextLine()) {
//                builder.append(scan.nextLine()).append(StringUtils.NEWLINE);
//            }
//            System.out.println(builder.toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    public static void httpProxy() throws IOException {

//        ProxySelector.setDefault(new ProxySelector() {
//
//            @Override
//            public List<Proxy> select(URI uri) {
//                List<Proxy> list = new ArrayList<Proxy>();
//                list.add(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
//                        "10.10.8.84", 8080)));
//                return list;
//            }
//
//            @Override
//            public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
//                System.out.println("连接代理失败！");
//            }
//
//        });

        // 需要访问的地址
        String urlStr = "https://www.baidu.com";
        // 创建连接
        URL url = new URL(urlStr);
        URLConnection conn = url.openConnection();
        // 输出访问结果
        try {
            Scanner scan = new Scanner(conn.getInputStream());
            StringBuilder builder = new StringBuilder();
            while (scan.hasNextLine()) {
                builder.append(scan.nextLine()).append("\r\n");
            }
            System.out.println(builder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws UnsupportedEncodingException {
//        D d=new D();
//        d.methodAAAAAAAAAAAAAAA();
//        System.out.println("end");
//        A a;
//        System.out.println(A.getA());;
//        System.out.println(new String(new byte[]{0x01,0x01},"utf16"));
//        System.out.println(Arrays.toString("ā".getBytes("utf-16be")));
//        System.out.println(new String("ā".getBytes("utf-16be"),"iso8859-1"));
//        System.out.println("\u0001");

        try {
            httpProxy();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
 class DDDDDDDDDDDDDDDDD{

}
class A{

    static int a=1;
    static {
        a=3333;
        System.out.println("a inited");
    }
    public static int getA(){
        return a;
    }

}