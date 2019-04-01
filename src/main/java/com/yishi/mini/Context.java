package com.yishi.mini;

public class Context extends LifeCircle{
    /**
     *业务类包装类
     */
    private Wrapper[] wrappers;

    @Override
    public void start() {
        super.start();
        for(Wrapper wrapper:wrappers){
            wrapper.start();
        }
    }

    @Override
    public void stop() {
        for(Wrapper wrapper:wrappers){
            wrapper.stop();
        }
    }

    public Wrapper[] getWrappers() {
        return wrappers;
    }

    public void setWrappers(Wrapper[] wrappers) {
        this.wrappers = wrappers;
    }
}
