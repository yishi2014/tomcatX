package com.yishi.code.general.basedata.construct;

public class JInterface extends JObject {


    public JInterface(JClass jClass){
        this.setjHead(new JInterfaceHead(jClass));
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
