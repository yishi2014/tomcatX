package com.yishi.socket;

import com.alibaba.fastjson.JSON;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        if(args.length==1)
        new Server().startUp(Integer.parseInt(args[0]));
        else
        new Server().startUp(null);

    }
    public void startUp(Integer port) throws IOException {
        int porT;
        if(port!=null)
            porT=port;
        else
        porT=46686;
        System.out.println("using port:"+porT);
        ServerSocket server=new ServerSocket(porT);
        while(true){
            Socket socket=server.accept();
            BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));
            Message receive= JSON.parseObject(br.readLine(),Message.class);
            parseMessage(receive);
            BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"utf-8"));
            Message message=new Message(Message.Type.ACK);
            bw.write(message.toString());
            bw.flush();


        }

    }
    public  void parseMessage(Message message){
        switch (message.getType()){
            case ACK:
                break;
            case STRING:
//                System.out.println(socket.getInetAddress()+":"+socket.getPort()+"says");
                System.out.println(message.getContent());
                break;
            case IMG:
                break;
            case FILE:
                break;
        }
    }


}
