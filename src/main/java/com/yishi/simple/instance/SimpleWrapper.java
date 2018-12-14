package com.yishi.simple.instance;

import com.yishi.construct.LifeCircle;
import com.yishi.construct.Wrapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class SimpleWrapper implements Wrapper,LifeCircle{
    private HttpServlet servlet;

    public HttpServlet getServlet() {
        return servlet;
    }

    public void setServlet(HttpServlet servlet) {
        this.servlet = servlet;
    }

    @Override
    public void start() {
        try {
            if(servlet!=null)
            servlet.init();
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        if(servlet!=null)
            servlet.destroy();
    }
}
