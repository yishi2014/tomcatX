package com.yishi.simple.instance;

import com.yishi.construct.LifeCircle;
import com.yishi.construct.Wrapper;

import javax.servlet.http.HttpServlet;

public class SimpleWrapper implements Wrapper,LifeCircle{
    private HttpServlet servlet;

    public HttpServlet getServlet() {
        return servlet;
    }

    public void setServlet(HttpServlet servlet) {
        this.servlet = servlet;
    }

    public static void main(String[] args) {
        System.out.println(1);
    }
}
