package com.yishi.simple.instance;


import com.yishi.construct.Connector;
import com.yishi.construct.Service;
import com.yishi.simple.instance.SimpleConnector;
import com.yishi.simple.instance.SimpleServer;
import com.yishi.simple.instance.SimpleService;

public class MainClass {
    public static void main(String[] args) {
        SimpleServer server= new SimpleServer();
        SimpleConnector connector=new SimpleConnector();
        SimpleService service=new SimpleService();
        service.setConnectors(new Connector[]{connector});
        server.setService(new Service[]{service});
        server.start();
    }


}
