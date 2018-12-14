package com.yishi.simple.instance;

import com.yishi.construct.Connector;
import com.yishi.construct.Container;
import com.yishi.construct.LifeCircle;
import com.yishi.construct.Service;

import java.util.Arrays;

public class SimpleService implements Service, LifeCircle {
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

    @Override
    public void start() {
        if (container != null)
            container.start();
        if (connectors != null)
            Arrays.stream(connectors).forEach(connector -> connector.start());
    }

    @Override
    public void stop() {
        if(container!=null)
        container.stop();
        if (connectors != null)
            Arrays.stream(connectors).forEach(connector -> connector.stop());
    }
}
