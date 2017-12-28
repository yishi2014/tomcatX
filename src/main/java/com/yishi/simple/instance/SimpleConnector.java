package com.yishi.simple.instance;

import com.yishi.construct.Connector;
import com.yishi.construct.Container;
import com.yishi.construct.LifeCircle;

public class SimpleConnector implements Connector,LifeCircle{
    private Container container;

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

}
