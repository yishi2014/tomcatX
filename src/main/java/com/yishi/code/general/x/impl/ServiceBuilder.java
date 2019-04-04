package com.yishi.code.general.x.impl;

import com.yishi.code.general.dto.TableMeta;
import com.yishi.code.general.x.*;

import java.util.List;
import java.util.Map;

public class ServiceBuilder extends XObjectBuilder {
    public ServiceBuilder(TableMeta tableMeta) {
        super(tableMeta);
    }

    @Override
    public String getPackageName() {
        return tableMeta.getServicePackage();
    }

    @Override
    public void buildXHead() {
        XHead jHead=new XInterfaceHead(getXClass(tableMeta.getServicePackage()+'.'+tableMeta.getServiceName()));
        ((XInterfaceHead) jHead).setRemark(tableMeta.getRemarks());
        this.addComponent(jHead);
    }

    @Override
    public void buildXFields() {

    }

    @Override
    public void buildXMethods() {
        if(tableMeta.isSelect()){
            XClass bypageParam=getXClass(Map.class);
            bypageParam.addParameterizeType(getXClass(String.class));
            bypageParam.addParameterizeType(getXClass(String.class));
            XClass paramClass=getXClass(tableMeta.getEntityQualifyName());
            XMethod jMethod=new XInterfaceMethod("query",getXClass(List.class));
            ((XInterfaceMethod) jMethod).getReturnType().addParameterizeType(paramClass);
            ((XInterfaceMethod) jMethod).addParam("pageMap",bypageParam);

            //查询方法
            this.addComponent(jMethod);

            XClass byPageRerurn=getXClass("com.datanew.dto.unalterable.$Pages");
            byPageRerurn.addParameterizeType(paramClass);
            XMethod jMethodByPage=new XInterfaceMethod("queryByPage",byPageRerurn);
            ((XInterfaceMethod) jMethodByPage).addParam("pageMap",bypageParam);
            this.addComponent(jMethodByPage);

        }
        if(tableMeta.isInsert()){
            XClass paramClass=getXClass(tableMeta.getEntityQualifyName());
            XMethod jMethod=new XInterfaceMethod("save",getXClass("com.datanew.dto.unalterable.$Result"));
            ((XInterfaceMethod) jMethod).addParam("t",paramClass);

            //新增方法
            this.addComponent(jMethod);
        }
        if(tableMeta.isUpdate()){
            XClass paramClass=getXClass(tableMeta.getEntityQualifyName());
            XMethod jMethod=new XInterfaceMethod("update",getXClass("com.datanew.dto.unalterable.$Result"));
            ((XInterfaceMethod) jMethod).addParam("t",paramClass);

            //新增方法
            this.addComponent(jMethod);

        }
        if(tableMeta.isDelete()){
            XClass paramClass=getXClass(tableMeta.getEntityQualifyName());
            XMethod jMethod=new XInterfaceMethod("delete",getXClass("com.datanew.dto.unalterable.$Result"));
            ((XInterfaceMethod) jMethod).addParam("t",paramClass);

            //新增方法
            this.addComponent(jMethod);
        }
    }

    @Override
    public XObject getProduct() {
        return this.object;
    }
}
