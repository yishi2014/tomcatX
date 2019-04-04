package com.yishi.code.general.x.impl;

import com.yishi.code.general.annotation_.RequestMapping;
import com.yishi.code.general.annotation_.RequestParam;
import com.yishi.code.general.annotation_.ResponseBody;
import com.yishi.code.general.dto.TableMeta;
import com.yishi.code.general.util.RegexUtil;
import com.yishi.code.general.x.*;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ControllerBuilder extends XObjectBuilder {

    public ControllerBuilder(TableMeta tableMeta) {
        super(tableMeta);
    }

    @Override
    public String getPackageName() {
        return tableMeta.getControllerPackage();
    }

    @Override
    public void buildXHead() {
        String tableName=tableMeta.getTableName();
        XHead xHead=new XClassHead(getXClass(tableMeta.getControllerPackage()+'.'+tableMeta.getControllerName()));
        ((XClassHead) xHead).setRemark(tableMeta.getRemarks());
        XAnnotation controllerAn=getXAnnotation(Controller.class);
        XAnnotation rmAn=getXAnnotation(RequestMapping.class);
        rmAn.setAttrs(new ArrayList<XAnnotationAttr>());
        rmAn.getAttrs().add(new XAnnotationAttr("/"+RegexUtil.toCamel1Low(tableName)));
        ((XClassHead) xHead).addAnnotation(controllerAn);
        ((XClassHead) xHead).addAnnotation(rmAn);
        this.addComponent(xHead);
    }

    @Override
    public void buildXFields() {
        XField xField=new CommonXField(getXClass(tableMeta.getServicePackage()+'.'+tableMeta.getServiceName()),RegexUtil.firstToLower(tableMeta.getServiceName()));
        ((CommonXField) xField).addAnnotation(getXAnnotation(Resource.class));
        this.addComponent(xField);
    }

    @Override
    public void buildXMethods() {
        XField xField=new CommonXField(getXClass(tableMeta.getServicePackage()+'.'+tableMeta.getServiceName()),RegexUtil.firstToLower(tableMeta.getServiceName()));

        XAnnotation responseAn=getXAnnotation(ResponseBody.class);
        if(tableMeta.isSelect()){
            XClass byPageParamType=getXClass(Map.class);
            XParam byPageParam=new XParam("pageMap",byPageParamType);
            byPageParam.addAnnotation(new XAnnotation(getXClass(RequestParam.class)));
            XClass paramClass=getXClass(tableMeta.getEntityQualifyName());
            String content= String.format("return %s.query(pageMap);", xField._getName());
            XMethod jMethod=new XClassMethod("query",getXClass(List.class),content);
            ((XClassMethod) jMethod).getReturnType().addParameterizeType(paramClass);
            ((XClassMethod) jMethod).addParam(byPageParam);
//            ((XClassMethod) jMethod).addParam("pageMap",byPageParamType);
            ((XClassMethod) jMethod).addAnnotation(getXAnnotation(RequestMapping.class).addAttr(new XAnnotationAttr("/"+jMethod._getName())));
            ((XClassMethod) jMethod).addAnnotation(responseAn);

            //查询方法
            this.addComponent(jMethod);


//            XClass paramsClassPage=getXClass("com.datanew.dto.unalterable.Page");
//            XClass byPageRerurn=getXClass("com.datanew.dto.unalterable.PageData");
            XClass byPageRerurn=getXClass(Object.class);

            String contentByPage = "return %s.queryByPage(pageMap);\n";
            contentByPage= String.format(contentByPage, xField._getName());
            XMethod jMethodByPage=new XClassMethod("queryByPage",byPageRerurn,contentByPage);
            ((XClassMethod) jMethodByPage).addAnnotation(getXAnnotation(RequestMapping.class).addAttr(new XAnnotationAttr("/"+jMethodByPage._getName())));
//            ((XClassMethod) jMethodByPage).addParam("page",paramsClassPage);
//            ((XClassMethod) jMethodByPage).addParam("t",paramClass);

            ((XClassMethod) jMethodByPage).addParam(byPageParam);
            ((XClassMethod) jMethodByPage).addAnnotation(responseAn);
            this.addComponent(jMethodByPage);



        }
        if(tableMeta.isInsert()){
            XClass paramClass=getXClass(tableMeta.getEntityQualifyName());
            String content= String.format("return %s.save(t);\n\n", xField._getName());
            XMethod jMethod=new XClassMethod("save",getXClass("com.datanew.dto.unalterable.$Result"),content);
            ((XClassMethod) jMethod).addParam("t",paramClass);
            ((XClassMethod) jMethod).addAnnotation(responseAn);
            ((XClassMethod) jMethod).addAnnotation(getXAnnotation(RequestMapping.class).addAttr(new XAnnotationAttr("/"+jMethod._getName())));

            //新增方法
            this.addComponent(jMethod);
        }
        if(tableMeta.isUpdate()){
            XClass paramClass=getXClass(tableMeta.getEntityQualifyName());
            String content= String.format("return %s.update(t);\n\n", xField._getName());
            XMethod jMethod=new XClassMethod("update",getXClass("com.datanew.dto.unalterable.$Result"),content);
            ((XClassMethod) jMethod).addParam("t",paramClass);
            ((XClassMethod) jMethod).addAnnotation(getXAnnotation(RequestMapping.class).addAttr(new XAnnotationAttr("/"+jMethod._getName())));
            ((XClassMethod) jMethod).addAnnotation(responseAn);
            //新增方法
            this.addComponent(jMethod);

        }
        if(tableMeta.isDelete()){
            XClass paramClass=getXClass(tableMeta.getEntityQualifyName());
            String content= String.format("return %s.delete(t);\n\n", xField._getName());
            XMethod jMethod=new XClassMethod("delete",getXClass("com.datanew.dto.unalterable.$Result"),content);
            ((XClassMethod) jMethod).addParam("t",paramClass);
            ((XClassMethod) jMethod).addAnnotation(getXAnnotation(RequestMapping.class).addAttr(new XAnnotationAttr("/"+jMethod._getName())));
            ((XClassMethod) jMethod).addAnnotation(responseAn);
            //新增方法
            this.addComponent(jMethod);
        }
    }

    @Override
    public XObject getProduct() {
        return this.object;
    }
}
