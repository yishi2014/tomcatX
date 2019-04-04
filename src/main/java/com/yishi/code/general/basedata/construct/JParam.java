package com.yishi.code.general.basedata.construct;

public class JParam {
    private String name;
    private JClass jClass;

    public JParam(String name, JClass jClass) {
        this.name = name;
        this.jClass = jClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JClass getjClass() {
        return jClass;
    }

    public void setjClass(JClass jClass) {
        this.jClass = jClass;
    }

    @Override
    public String toString() {
        return jClass.getSimpleName()+' '+name;
    }
}
