package com.yishi.code.general.x.impl;

import com.yishi.code.general.basedata.construct.JDBCTypeParser;
import com.yishi.code.general.basedata.construct.JField;
import com.yishi.code.general.dao.BaseDao;
import com.yishi.code.general.dto.ColumnMeta;
import com.yishi.code.general.dto.TableMeta;
import com.yishi.code.general.x.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.From;
import java.util.*;

public class ServiceImplBuilder extends XObjectBuilder {

    public ServiceImplBuilder(TableMeta tableMeta) {
        super(tableMeta);
    }

    @Override
    public String getPackageName() {
        return tableMeta.getServiceImplPackage();
    }

    @Override
    public void buildXHead() {
        XHead jHead=new XClassHead(getXClass(tableMeta.getServiceImplPackage()+'.'+tableMeta.getServiceImplName()));
        ((XClassHead) jHead).addInterface(new XInterfaceHead(getXClass(tableMeta.getServiceQualifyName())));
        ((XClassHead) jHead).setRemark(tableMeta.getRemarks());
        XAnnotation implAn=getXAnnotation(Service.class);
        ((XClassHead) jHead).addAnnotation(implAn);
        this.addComponent(jHead);
    }

    @Override
    public void buildXFields() {
        XField jField=new CommonXField(getXClass(BaseDao.class),"baseDao");
        ((CommonXField) jField).addAnnotation(getXAnnotation(Resource.class));
        this.addComponent(jField);
    }

    @Override
    public void buildXMethods() {
        JField jField=new JField(BaseDao.class,"baseDao");

        if(tableMeta.isSelect()){
            XClass byPageParamType=getXClass(Map.class);
            byPageParamType.addParameterizeType(getXClass(String.class));
            byPageParamType.addParameterizeType(getXClass(String.class));
            Map<String,String >entityNameAndalias=new HashMap<>();
            XClass paramClass=getXClass(tableMeta.getEntityQualifyName());
            String fromOther="";
//            String whereOther="";


            String content=Indent.METHODCONTENT;
            content+="String condition=\"\";\n";
            int entityindex=0;

            for(ColumnMeta col:tableMeta.getColumnMetaList()){
                if("Y".equals(col.getIsSearch())){
                    addXClass("com.datanew.util.unalterable.HqlGen");

                    if("rangesearch".equals(col.getSearchType())&&("text".equals(col.getContorlType()))){
                        String fieldName=col.getFieldName();
                        String startKey=fieldName+"__start";
                        String endKey=col.getFieldName()+"__end";
                        content+=Indent.METHODCONTENT;
                        content+="String "+startKey+"Str=pageMap.get(\""+startKey+"\");\n";
                        content+=Indent.METHODCONTENT;
                        content+="String "+endKey+"Str=pageMap.get(\""+endKey+"\");\n";
                        content+=Indent.METHODCONTENT;


                        XClass startType=getXClass(JDBCTypeParser.getJavaTypeClass(col.getJavaType()));
                        if(startType.getQualifyName().equals("java.util.Date")){
                            content+="Object "+startKey+"Obj=HqlGen.typeReverse("+startType.render()+".class"+","+startKey+"Str"+",\""+col.getDateInPattern()+"\");\n";
                            content+=Indent.METHODCONTENT;
                            content+="Object "+endKey+"Obj=HqlGen.typeReverse("+startType.render()+".class"+","+endKey+"Str"+",\""+col.getDateInPattern()+"\");\n";
                        }else{
                            content+="Object "+startKey+"Obj=HqlGen.typeReverse("+startType.render()+".class"+","+startKey+"Str"+",null"+");\n";
                            content+=Indent.METHODCONTENT;
                            content+="Object "+endKey+"Obj=HqlGen.typeReverse("+startType.render()+".class"+","+endKey+"Str"+",null"+");\n";
                        }

                        content+=Indent.METHODCONTENT;
                        content+="if("+startKey+"Obj!=null){\n" ;
                        content+=Indent.METHODCONTENT;

                        content+="  condition+=\" and t."+fieldName+">=? \";\n" ;
                        content+=Indent.METHODCONTENT;

                        content+="  param.add("+startKey+"Obj"+");\n" ;
                        content+=Indent.METHODCONTENT;
                        content+="}\n";
                        content+=Indent.METHODCONTENT;
                        content+="if("+endKey+"Obj!=null){\n" ;
                        content+=Indent.METHODCONTENT;

                        content+="  condition+=\" and t."+fieldName+"<=? \";\n" ;
                        content+=Indent.METHODCONTENT;

                        content+="  param.add("+endKey+"Obj"+");\n" ;


                        content+=Indent.METHODCONTENT;
                        content+="}\n";


                    }else{
                        String key=col.getFieldName();
                        content+=Indent.METHODCONTENT;
                        content+="String "+key+"Str=pageMap.get(\""+key+"\");\n";
                        content+=Indent.METHODCONTENT;
                        XClass startType=getXClass(JDBCTypeParser.getJavaTypeClass(col.getJavaType()));
                        if(startType.getQualifyName().equals("java.util.Date")){
                            content+="Object "+key+"Obj=HqlGen.typeReverse("+startType.render()+".class"+","+key+"Str"+",\""+col.getDateInPattern()+"\");\n";
                        }else{
                            content+="Object "+key+"Obj=HqlGen.typeReverse("+startType.render()+".class"+","+key+"Str"+",null"+");\n";
                        }
                        content+=Indent.METHODCONTENT;
                        content+="if("+key+"Obj!=null){\n" ;
                        content+=Indent.METHODCONTENT;

                        content+="  condition+=\" and t."+key+"=? \";\n" ;
                        content+=Indent.METHODCONTENT;

                        content+="  param.add("+key+"Obj"+");\n" ;
                        content+=Indent.METHODCONTENT;
                        content+="}\n";

                    }
                }


                if(col.getContorlType().matches("(comboTree|searchTree)")){
                    String metaid=col.getDictTable();
                    TableMeta entity= col.getReferenceMeta().getTableMeta();
                    if(entity!=null){
                        entity.initTreeConfig();
                        if(entity.getIdColumn()!=null){
                            fromOther+=" left join "+"t."+col.getReferenceMeta().getReferenceObj()._getName()+ ' '+"t"+(++entityindex);
                            if(entity.getNameColumn()==null){
                                entityNameAndalias.put(col.getFieldName()+"_name","t"+entityindex+"."+(entity.getIdColumn().getFieldName()+"+\"\""));

                            }else{
                                entityNameAndalias.put(col.getFieldName()+"_name","t"+entityindex+"."+(entity.getNameColumn().getFieldName()));

                            }
//                            whereOther+=" and t."+col.getFieldName()+"=t"+entityindex+"."+entity.getIdColumn().getFieldName();
                        }
                    }
                }

            }
            String frontContent= "List param=new ArrayList();\n";
            frontContent+=Indent.METHODCONTENT;
            content+=Indent.METHODCONTENT;

            StringBuffer headStr = new StringBuffer();
            if("Y".equals(tableMeta.getIsTree())){
                headStr.append("select new map( ");
                for (Map.Entry<String,ColumnMeta> entry:tableMeta.getColumnMetas().entrySet()) {
                    if(tableMeta.getNodeId().equalsIgnoreCase(entry.getValue().getFieldName())){
                        headStr.append("t.");
                        headStr.append(entry.getValue().getFieldName());
                        headStr.append(" as ID,");
                    }
                    if(tableMeta.getNodePId().equalsIgnoreCase(entry.getValue().getFieldName())){
                        headStr.append("t.");
                        headStr.append(entry.getValue().getFieldName());
                        headStr.append(" as PID,");
                    }
                    if(tableMeta.getNodeNameField().equalsIgnoreCase(entry.getValue().getFieldName())){
                        headStr.append("t.");
                        headStr.append(entry.getValue().getFieldName());
                        headStr.append(" as NAME,");
                    }

                    headStr.append("t.");
                    headStr.append(entry.getValue().getFieldName());
                    headStr.append(" as ");
                    headStr.append(entry.getValue().getFieldName());
                    headStr.append(",");
                }
                headStr.deleteCharAt(headStr.lastIndexOf(","));
                headStr.append(" ) ");
            }

            if(!entityNameAndalias.isEmpty()){
//                String headHql="select new "+paramClass.render()+"(";
                String headHql="select new map(";
                if("Y".equals(tableMeta.getIsTree())){
                    for (Map.Entry<String,ColumnMeta> entry:tableMeta.getColumnMetas().entrySet()) {
                        if(tableMeta.getNodeId().equalsIgnoreCase(entry.getValue().getFieldName())){
                            headHql += "t."+entry.getValue().getFieldName()+" as ID,";
                        }
                        if(tableMeta.getNodePId().equalsIgnoreCase(entry.getValue().getFieldName())){
                            headHql += "t."+entry.getValue().getFieldName()+" as PID,";
                        }
                        if(tableMeta.getNodeNameField().equalsIgnoreCase(entry.getValue().getFieldName())){
                            headHql += "t."+entry.getValue().getFieldName()+" as NAME,";
                        }
                    }
                }

                Map<String,ColumnMeta> columnMetaMap=tableMeta.getFieldNameMap();
                for(XParam xParam:tableMeta.getConstructParams()){
                    if(columnMetaMap.containsKey(xParam.getName())){
                        headHql+="t."+xParam.getName()+" as "+xParam.getName()+",";
                    }else if(entityNameAndalias.containsKey(xParam.getName())){
                        headHql+=entityNameAndalias.get(xParam.getName())+" as "+xParam.getName()+",";
                    }else{
                        headHql+="null,";
                    }
                }
//                for(XParam xParam:tableMeta.getConstructParams()){
//                    if(columnMetaMap.containsKey(xParam.getName())){
//                        headHql+="t."+xParam.getName()+",";
//                    }else if(entityNameAndalias.containsKey(xParam.getName())){
//                        headHql+=entityNameAndalias.get(xParam.getName())+",";
//                    }else{
//                        headHql+="null,";
//                    }
//                }
                headHql=headHql.replaceAll("(.*),$","$1");
                headHql+=") ";
                frontContent+="String hql=\""+headHql+" from "+paramClass.render()+" t"+fromOther+"\";\n";
            }else{


                frontContent+="String hql=\""+headStr.toString()+" from "+paramClass.getQualifyName()+" t \";\n";
            }

            content+="condition=condition.replaceAll(\"^\\\\s*and\",\" where\");\n";

            content+=Indent.METHODCONTENT;
            content+="hql+=condition;\n";
            content=frontContent+content;





            XMethod jMethod=new XClassMethod("query",getXClass(List.class),null);
            ((XClassMethod) jMethod).getReturnType().addParameterizeType(paramClass);
            ((XClassMethod) jMethod).addParam("pageMap",byPageParamType);
            ((XClassMethod) jMethod).setContent(content+Indent.METHODCONTENT+String.format("return %s.selectByHql(hql,param);\n",jField.getName()));

            //查询方法
            this.addComponent(jMethod);



//          XClass paramsClassPage=getXClass("com.datanew.dto.unalterable.Page");
            XClass byPageRerurn=getXClass("com.datanew.dto.unalterable.$Pages");
            byPageRerurn.addParameterizeType(paramClass);
            String contentByPage = content +
                    Indent.METHODCONTENT+
                    "Object offsetObj=pageMap.get(\"offset\");\n" +
                    Indent.METHODCONTENT+
                    "Object limitObj=pageMap.get(\"limit\");\n" +
                    Indent.METHODCONTENT+
                    byPageRerurn.render()+" page=new "+(byPageRerurn.render().replaceAll("\\<.*\\>","<>"))+"(Integer.parseInt(limitObj==null?\"30\":String.valueOf(limitObj)),Integer.parseInt(offsetObj==null?\"1\":String.valueOf(offsetObj)));\n"+
                    Indent.METHODCONTENT+
                    String.format("List list = %s.selectByHql(hql, param,page.getOffset(),page.getLimit());\n",jField.getName(),paramClass.getSimpleName() ) +
                    Indent.METHODCONTENT+
                    String.format("Long count=%s.getCountByHQL(\"select count(*) from "+paramClass.render()+" t \"+condition,param);\n", jField.getName()) +
                    Indent.METHODCONTENT+
                    "page.setTotal(count.intValue());\n" +
                    Indent.METHODCONTENT+
                    "page.setRows(list);\n"+
                    Indent.METHODCONTENT+
                    "return page;\n";
            contentByPage= String.format(contentByPage, jField.getName());
            XMethod jMethodByPage=new XClassMethod("queryByPage",byPageRerurn,contentByPage);
            ((XClassMethod) jMethodByPage).addParam("pageMap",byPageParamType);

//            addXClass(List.class);
            addXClass(ArrayList.class);

            this.addComponent(jMethodByPage);

        }
        if(tableMeta.isInsert()){
            XClass paramClass=getXClass(tableMeta.getEntityQualifyName());
            String content="try{\n" +
                    "             %s.save(t);\n" +
                    "         }catch (Exception e){\n" +
                    "             e.printStackTrace();\n" +
                    "             return $Result.fail();\n" +
                    "         }\n" +
                    "         return $Result.success();\n";
            content= String.format(content, jField.getName());
            XMethod jMethod=new XClassMethod("save",getXClass("com.datanew.dto.unalterable.$Result"),content);
            ((XClassMethod) jMethod).addParam("t",paramClass);

            //新增方法
            this.addComponent(jMethod);
        }
        if(tableMeta.isUpdate()){
            String content="try{\n" +
                    "            baseDao.update(t);\n" +
                    "        }catch (Exception e){\n" +
                    "            e.printStackTrace();\n" +
                    "            return $Result.fail();\n" +
                    "        }\n" +
                    "        return $Result.success();\n";
            XClass paramClass=getXClass(tableMeta.getEntityQualifyName());
            content= String.format(content, jField.getName());
            XMethod jMethod=new XClassMethod("update",getXClass("com.datanew.dto.unalterable.$Result"),content);
            ((XClassMethod) jMethod).addParam("t",paramClass);

            //新增方法
            this.addComponent(jMethod);

        }
        if(tableMeta.isDelete()){
            String content="try{\n" +
                    "            baseDao.delete(t);\n" +
                    "        }catch (Exception e){\n" +
                    "            e.printStackTrace();\n" +
                    "            return $Result.fail();\n" +
                    "        }\n" +
                    "        return $Result.success();\n";
            XClass paramClass=getXClass(tableMeta.getEntityQualifyName());
            content= String.format(content, jField.getName());
            XMethod jMethod=new XClassMethod("delete",getXClass("com.datanew.dto.unalterable.$Result"),content);
            ((XClassMethod) jMethod).addParam("t",paramClass);

            //新增方法
            this.addComponent(jMethod);
        }
    }

    @Override
    public XObject getProduct() {
        return this.object;
    }
}
