package com.yishi.simple.instance;

import com.yishi.construct.Connector;
import com.yishi.construct.Container;
import com.yishi.construct.LifeCircle;
import com.yishi.construct.Service;

public class SimpleService implements Service,LifeCircle {
    private Connector[] connectors;
    private Container container;

    public Connector[] getConnectors() {
        return connectors;
    }

    public void setConnectors(Connector[] connectors) {
        this.connectors = connectors;
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }
}
