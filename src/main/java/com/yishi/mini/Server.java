package com.yishi.mini;

/**
 * 即项目本身。项目启动即相当Server启动。添加生命周期
 */
public class Server extends LifeCircle{
    /**
     *  功能模块,项目启动时统一初始化,每个模块提供获取详运行状态及管理生命周期方法
     *  数据库服务
     *  ftp服务
     *  webservice客户端
     */
    private Module[] modules;

    /**
     *单个服务容器,暂无多实例场景，暂时使用单独实例
     */
    private Service service;

    @Override
    public void start() {
        super.start();
        for(Module module:modules){
            module.start();
        }
        service.start();
    }

    @Override
    public void stop() {
        super.stop();
        service.stop();
        for(Module module:modules){
            module.stop();
        }
    }


    public Module[] getModules() {
        return modules;
    }

    public void setModules(Module[] modules) {
        this.modules = modules;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public static void main(String[] args) {
        Server server=new Server();
        server.setModules(new Module[]{new Module("数据库服务"),new Module("ftp服务")});
        server.setService(new Service());
        server.getService().setConnectors(new Connector[]{new Connector("socketConnector"),new Connector("mqConnector")});
        server.getService().setContext(new Context());
        server.getService().getContext().setWrappers(new Wrapper[]{new Wrapper("清算业务"),new Wrapper("结报业务")});
        server.start();
        System.out.println("deal with business.......");
        System.out.println("deal with business.......");
        System.out.println("deal with business.......");
        System.out.println("deal with business.......");

        server.stop();
    }
}
