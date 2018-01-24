package com.yishi.socket;

import com.alibaba.fastjson.JSON;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Client {
    public static void main(String[] args) throws IOException {
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in,"utf-8"));
        Client client=new Client();
        while(true){
            String string=br.readLine();
            Message message=new Message();
            message.setContent(string);
            message.setType(Message.Type.STRING);
            if(args.length==2){
                message.setIp(args[0]);
                message.setPort(Integer.valueOf(args[1]));
            }else {
                message.setIp("127.0.0.1");
                message.setPort(46686);
            }

            client.send(message,null);

        }

    }
    public void send(Message message, JLabel jLabel) throws IOException {
        try {
            Pattern pattern= Pattern.compile("<html><body>(.*)<body/><html/>");
            Matcher matcher=pattern.matcher(jLabel.getText());
            String content;
            if(matcher.find()){
                content=matcher.group();
            }else {
                content="";
            }
            jLabel.setText("<html><body>"+content+"<br/>"+"you :<br/>"+message.getContent()+"<body/><html/>");
            jLabel.repaint();
            System.out.println("connect to "+message.getIp()+":"+message.getPort());
            Socket s = new Socket(message.getIp(),message.getPort());

            //构建IO
            InputStream is = s.getInputStream();
            OutputStream os = s.getOutputStream();

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os,"utf-8"));
            //向服务器端发送一条消息
            bw.write(message.toString());
            bw.flush();

            //读取服务器返回的消息
            BufferedReader br = new BufferedReader(new InputStreamReader(is,"utf-8"));
            String mess = br.readLine();
            Message receive= JSON.parseObject(mess,Message.class);
            parseMessage(receive);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
