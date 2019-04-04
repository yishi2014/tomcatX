package com.yishi.code.general.x;


import com.yishi.code.general.dto.TableMeta;
import com.yishi.code.general.util.RegexUtil;
import com.yishi.code.general.x.impl.AbstractXField;
import com.yishi.code.general.x.impl.CommonXFoot;
import com.yishi.code.general.x.impl.CommonXPackage;

import java.util.ArrayList;

/**
 * 建造接口
 * 定义了创建产品需要的所有方法
 *
 */
public abstract class XObjectBuilder {
    protected TableMeta tableMeta;

    public XObjectBuilder(TableMeta tableMeta) {
        this.tableMeta = tableMeta;
    }
    protected XObject object =new XObject();

    public abstract String getPackageName();
    public void buildXPackage() {
        XPackage jPackage=new CommonXPackage(getPackageName());
        this.addComponent(jPackage);
        this.object.getxImport().setxPackage(jPackage);
    }
    public abstract void buildXHead();
    public abstract void buildXFields();
    public abstract void buildXMethods();
    public void buildXImport() {
        this.addComponent(new XImport());
    }
    public  void buildXFoot(){
        addComponent(new CommonXFoot());
    }
    public abstract XObject getProduct();
    public XObjectBuilder addComponent(XComponent component){
        if(component instanceof XPackage){
            this.object.setxPackage((XPackage) component);
        }else
        if(component instanceof XImport){
           this.object.setxImport((XImport) component);
        }else
        if(component instanceof XHead){
            this.object.setxHead((XHead) component);
        }else
        if(component instanceof  XField){
            if(this.object.getFields()==null)
                this.object.setFields(new ArrayList<XField>());
            this.object.getFields().add((XField) component);
        }else
        if(component instanceof XMethod){
            if(this.object.getMethods()==null){
                this.object.setMethods(new ArrayList<XMethod>());
            }
            this.object.getMethods().add((XMethod) component);
        }else
        if(component instanceof XFoot){
            this.object.setxFoot((XFoot) component);
        }
        return this;
    }
    public static String getterMethodName(AbstractXField jField, String columnName){
        if(jField.get_type().getSimpleName().equalsIgnoreCase("boolean")||jField.get_type()==new XClass(Boolean.class)){
            return RegexUtil.toCamel1Low("is_"+columnName);
        }else{
            return RegexUtil.toCamel1Low("get_"+columnName);
        }

    }
    public static String setterMethodName(AbstractXField jField, String columnName){
        return RegexUtil.toCamel1Low("set_"+columnName);
    }
    public XClass getXClass(Class clazz){
        XClass xClass=new XClass(clazz);
        if(!this.object.getxImport().getClasses().contains(xClass)){
            this.object.getxImport().getClasses().add(xClass);
        }
        for(XClass x:this.object.getxImport().getClasses()){
            if(x.getSimpleName().equals(xClass.getSimpleName())&&!x.equals(xClass)){
                x.setDuplicate(true);
                xClass.setDuplicate(true);
            }
        }
        return xClass;
    }
    public XClass getXClass(String className){
        XClass xClass=new XClass(className);
        if(!this.object.getxImport().getClasses().contains(xClass)){
            this.object.getxImport().getClasses().add(xClass);
        }
        return xClass;
    }
    public XObjectBuilder addXClass(Class clazz){
        getXClass(clazz);
        return this;

    }
    public XObjectBuilder addXClass(String className){
       getXClass(className);
       return this;
    }
    public XAnnotation getXAnnotation(Class clazz){
        return new XAnnotation(getXClass(clazz));
    }
}
