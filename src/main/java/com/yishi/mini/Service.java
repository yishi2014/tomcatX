package com.yishi.mini;

/**
 * 服务
 */
public class Service extends LifeCircle{
    /**
     * 定义接收外部网络请求的方式,如果有多个Context则决定派向哪个Context,Connector上添加日志即可记录所有收到的请求。
     * 1.SocketConnecotr:接收socket连接
     * 2.MQConnector:接收Mq消息
     */
    private Connector[] connectors;
    /**
     *业务系统上下文，保存所有的业务处理逻辑
     */
    private Context context;

    @Override
    public void start() {
        super.start();
        for(Connector connector:connectors){
            connector.start();
        }
        context.start();
    }

    @Override
    public void stop() {
        super.stop();
        context.stop();
        for(Connector connector:connectors){
            connector.stop();
        }
    }

    public Connector[] getConnectors() {
        return connectors;
    }

    public void setConnectors(Connector[] connectors) {
        this.connectors = connectors;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
