package com.yishi.socket;

import com.alibaba.fastjson.JSON;

public class Message {
    private String ip;
    private int port;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public  enum Type{
        STRING,FILE,IMG,ACK;
    }
    private Type type;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this)+"\r\n";
    }

    public Message(Type type) {
        this.type = type;
    }

    public Message() {
    }
}
