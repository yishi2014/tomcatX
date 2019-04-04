package com.yishi.code.general.x.impl;

import com.yishi.code.general.basedata.construct.JDBCTypeParser;
import com.yishi.code.general.dto.ColumnMeta;
import com.yishi.code.general.dto.ReferenceMeta;
import com.yishi.code.general.dto.TableMeta;
import com.yishi.code.general.util.RegexUtil;
import com.yishi.code.general.x.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.*;

public class EntityBuilder extends XObjectBuilder {


    public EntityBuilder(TableMeta tableMeta) {
        super(tableMeta);
    }
    private Map<String,Integer> referenceObjIndex=new HashMap<>();
    /**
     * 当表中某字段依赖其他表做文本显示时（当前表的某个字段xx_id需要显示另一个表的XX_name）此map组织数据
     * 结构：
     * “fieldName”：{"[0]该field的文本字段，现在是在后面直接加_name",}
     *
     */
    private Map<String,Object[]> referenceDataMap=new HashMap<>();

    @Override
    public String getPackageName() {
        return tableMeta.getEntityPackage();
    }

    @Override
    public void buildXHead() {
        String tableName=tableMeta.getTableName();
        String tableSchem=tableMeta.getTableSchem();
        //init JHead
        XHead xHead=new XClassHead(getXClass(tableMeta.getEntityPackage()+'.'+RegexUtil.toCamel(tableName)));
//        ((XClassHead) xHead).addInterface(new XInterfaceHead(getXClass("com.datanew.dto.unalterable.Tree")));
        ((XClassHead) xHead).setRemark(tableMeta.getRemarks());
        XAnnotation entityAn=new XAnnotation(getXClass(Entity.class));
        XAnnotation tableAn=new XAnnotation(getXClass(Table.class));
        (tableAn).setAttrs(new ArrayList<XAnnotationAttr>());
        tableAn.getAttrs().add(new XAnnotationAttr("name",new XAnnotationAttrVal(tableName)));
        if(tableSchem!=null )
        tableAn.getAttrs().add(new XAnnotationAttr("schema",new XAnnotationAttrVal(tableSchem)));
        ((XClassHead) xHead).addAnnotation(entityAn);
        ((XClassHead) xHead).addAnnotation(tableAn);
        this.addComponent(xHead);
    }

    @Override
    public void buildXFields() {
        Map<String,ColumnMeta> columnMetas=tableMeta.getColumnMetas();
        for(Map.Entry<String,ColumnMeta> en:columnMetas.entrySet()){
            ColumnMeta columnMeta=en.getValue();
            XField xField =new CommonXField(getXClass(JDBCTypeParser.getJavaTypeClass(columnMeta.getJavaType())),columnMeta.getFieldName());
            ((CommonXField) xField).setRemark(columnMeta.getComment());
            this.addComponent(xField);
            columnMeta.setxField(xField);

            if(columnMeta.getContorlType().matches("(comboTree|searchTree)")){
                ReferenceMeta ref=columnMeta.getReferenceMeta();

                XField xFieldName =new CommonXField(getXClass(String.class),columnMeta.getFieldName()+"_name");
                ((CommonXField) xFieldName).setRemark(columnMeta.getFieldName()+"的文本显示");
                this.addComponent(xFieldName);
                ref.setTextXfield(xFieldName);
                TableMeta treeTableMeta= ref.getTableMeta();


                if(treeTableMeta!=null){
                    treeTableMeta.initTreeConfig();
                    String referenceObjQName=treeTableMeta.getEntityQualifyName();
                    String referenceObjName=treeTableMeta.getEntityName();
                    ref.setTableMeta(treeTableMeta);
                    String index;
                    if(referenceObjIndex.containsKey(referenceObjName)){
                        index=(referenceObjIndex.get(referenceObjName)+1)+"";
                    }else{
                        referenceObjIndex.put(referenceObjName,0);
                        index="";
                    }
                    XField XReferenceField =new CommonXField(getXClass(referenceObjQName),columnMeta.getFieldName()+index+"_Obj");
                    this.addComponent(XReferenceField);
                    ref.setReferenceObj(XReferenceField);

                }

            }
        }

    }

    @Override
    public void buildXMethods() {
        List<XParam> constructorParam=new ArrayList<>();
        Map<String,ColumnMeta> columnMetas=tableMeta.getColumnMetas();
        for(Map.Entry<String,ColumnMeta> en:columnMetas.entrySet()){
            ColumnMeta columnMeta=en.getValue();
            String columnName=columnMeta.getColumnName();

            XField xField =columnMeta.getxField();
            ((CommonXField) xField).setRemark(columnMeta.getComment());

            List<XParam> paramList=new ArrayList<>();
            XParam param=new XParam(xField._getName(),getXClass(JDBCTypeParser.getJavaTypeClass(columnMeta.getJavaType())));
            paramList.add(param);
            constructorParam.add(param);
            XMethod jMethodSetter=new XClassMethod(setterMethodName((AbstractXField) xField,columnName),(XClass) null,"this."+xField._getName()+'='+xField._getName()+";\n");
            ((XClassMethod) jMethodSetter).setParams(paramList);
            this.addComponent(jMethodSetter);

            XMethod jMethodGetter=new XClassMethod(getterMethodName((AbstractXField) xField,columnName),((CommonXField) xField).get_type(),"return this."+xField._getName()+";\n");
            if(columnMeta.isPrimaryKey()){
                //@GeneratedValue(generator = "uuid")
                //@GenericGenerator(name = "uuid", strategy = "uuid")
                XAnnotation IdAn=new XAnnotation(getXClass(Id.class));

                XAnnotation generalAn=new XAnnotation(getXClass(GeneratedValue.class));
                if("SEQUENCE".equals(tableMeta.getPkStrategy())){
//                    generalAn.addAttr(new XAnnotationAttr("strategy",new XAnnotationAttrVal(XAnnotationAttrVal.TYPE.val,"GenerationType.AUTO")));
//                    this.addXClass(GenerationType.class);
//                    ((XClassMethod) jMethodGetter).addAnnotation(IdAn);
//                    ((XClassMethod) jMethodGetter).addAnnotation(generalAn);
                    generalAn.addAttr(new XAnnotationAttr("generator","sequence" ));
                    XAnnotation generator=new XAnnotation(getXClass(SequenceGenerator.class));
                    generator.addAttr(new XAnnotationAttr("name","sequence"));
                    generator.addAttr(new XAnnotationAttr("sequenceName",tableMeta.getPkSequence()));
                    ((XClassMethod) jMethodGetter).addAnnotation(IdAn);
                    ((XClassMethod) jMethodGetter).addAnnotation(generalAn);
                    ((XClassMethod) jMethodGetter).addAnnotation(generator);

                }else if("SELFDEFINED".equals(tableMeta.getPkStrategy())){
                    generalAn.addAttr(new XAnnotationAttr("generator","assigned" ));
                    XAnnotation generator=new XAnnotation(getXClass(GenericGenerator.class));
                    generator.addAttr(new XAnnotationAttr("name","assigned"));
                    generator.addAttr(new XAnnotationAttr("strategy","assigned"));
                    ((XClassMethod) jMethodGetter).addAnnotation(IdAn);
                    ((XClassMethod) jMethodGetter).addAnnotation(generalAn);
                    ((XClassMethod) jMethodGetter).addAnnotation(generator);

                }else if("NATIVE".equals(tableMeta.getPkStrategy())){
                    generalAn.addAttr(new XAnnotationAttr("generator","identity" ));
                    XAnnotation generator=new XAnnotation(getXClass(GenericGenerator.class));
                    generator.addAttr(new XAnnotationAttr("name","identity"));
                    generator.addAttr(new XAnnotationAttr("strategy","identity"));
                    ((XClassMethod) jMethodGetter).addAnnotation(IdAn);
                    ((XClassMethod) jMethodGetter).addAnnotation(generalAn);
                    ((XClassMethod) jMethodGetter).addAnnotation(generator);

                }else if("GUID".equals(tableMeta.getPkStrategy())){
                    generalAn.addAttr(new XAnnotationAttr("generator","guid" ));
                    XAnnotation generator=new XAnnotation(getXClass(GenericGenerator.class));
                    generator.addAttr(new XAnnotationAttr("name","guid"));
                    generator.addAttr(new XAnnotationAttr("strategy","guid"));
                    ((XClassMethod) jMethodGetter).addAnnotation(IdAn);
                    ((XClassMethod) jMethodGetter).addAnnotation(generalAn);
                    ((XClassMethod) jMethodGetter).addAnnotation(generator);
                }else{
                    generalAn.addAttr(new XAnnotationAttr("generator","uuid" ));
                    XAnnotation generator=new XAnnotation(getXClass(GenericGenerator.class));
                    generator.addAttr(new XAnnotationAttr("name","uuid"));
                    generator.addAttr(new XAnnotationAttr("strategy","uuid"));
                    ((XClassMethod) jMethodGetter).addAnnotation(IdAn);
                    ((XClassMethod) jMethodGetter).addAnnotation(generalAn);
                    ((XClassMethod) jMethodGetter).addAnnotation(generator);
                }


            }
            XAnnotation basicAn=new XAnnotation(getXClass(Basic.class));
            ((XClassMethod) jMethodGetter).addAnnotation(basicAn);
            XAnnotation columnAn=new XAnnotation(getXClass(Column.class));
            columnAn.addAttr(new XAnnotationAttr("name",columnMeta.getColumnName()));
            if(columnMeta.getColumnSize()>0){
                if("String".equals(columnMeta.getJavaType()))
                columnAn.addAttr(new XAnnotationAttr("length",new XAnnotationAttrVal(XAnnotationAttrVal.TYPE.val,columnMeta.getColumnSize()+"")));
            }
            ((XClassMethod) jMethodGetter).addAnnotation(columnAn);

            if(((CommonXField) xField).get_type().getType()==Date.class){
                if(columnMeta.getDateInPattern()==null){
                    columnMeta.setDateInPattern(TableMeta.DEFAULT_DATE_PATTERN);
                }
                if(columnMeta.getDateOutPattern()==null){
                    columnMeta.setDateOutPattern(TableMeta.DEFAULT_DATE_PATTERN);
                }
                if(columnMeta.getDateInPattern()!=null){
                    XAnnotation dateInAn=new XAnnotation(getXClass(DateTimeFormat.class));
                    dateInAn.addAttr(new XAnnotationAttr("pattern",columnMeta.getDateInPattern()));
                    ((XClassMethod) jMethodGetter).addAnnotation(dateInAn);
                }
                if(columnMeta.getDateOutPattern()!=null){
                    XAnnotation dateOutAn=new XAnnotation(getXClass(JsonFormat.class));
                    dateOutAn.addAttr(new XAnnotationAttr("pattern",columnMeta.getDateOutPattern()));
                    dateOutAn.addAttr(new XAnnotationAttr("locale","zh"));
                    dateOutAn.addAttr(new XAnnotationAttr("timezone","GMT+8"));
                    ((XClassMethod) jMethodGetter).addAnnotation(dateOutAn);
                }


            }
            this.addComponent(jMethodGetter);


            //todo 加length
            if(columnMeta.getContorlType().matches("(comboTree|searchTree)")){
                XField xFieldName =columnMeta.getReferenceMeta().getTextXfield();
//                ((CommonXField) xFieldName).setRemark(columnMeta.getFieldName()+"的文本显示");
//                this.addComponent(xFieldName);

                List<XParam> nameParamList=new ArrayList<>();;
                XParam param1=new XParam(xFieldName._getName(), ((AbstractXField) xFieldName).get_type());
                nameParamList.add(param1);
                constructorParam.add(param1);
                XMethod jMethodNameSetter=new XClassMethod(setterMethodName((AbstractXField) xFieldName,columnName)+"_name",(XClass) null,"this."+xFieldName._getName()+'='+xFieldName._getName()+";\n");
                ((XClassMethod) jMethodNameSetter).setParams(nameParamList);
                this.addComponent(jMethodNameSetter);

                XMethod jMethodNameGetter=new XClassMethod(getterMethodName((AbstractXField) xFieldName,columnName)+"_name",((CommonXField) xFieldName).get_type(),"return this."+xFieldName._getName()+";\n");
                XAnnotation transientName=new XAnnotation(getXClass(Transient.class));
                ((XClassMethod) jMethodNameGetter).addAnnotation(transientName);
                this.addComponent(jMethodNameGetter);

                XField refObjXField=columnMeta.getReferenceMeta().getReferenceObj();
                List<XParam> refObjParamList=new ArrayList<>();
                refObjParamList.add(new XParam(refObjXField._getName(), ((AbstractXField) refObjXField).get_type()));
                XMethod refObjSetter=new XClassMethod("set"+RegexUtil.firstToLower(refObjXField._getName()),(XClass) null,"this."+refObjXField._getName()+'='+refObjXField._getName()+";\n");
                ((XClassMethod) refObjSetter).setParams(refObjParamList);
                this.addComponent(refObjSetter);

                XMethod refObjGetter=new XClassMethod("get"+RegexUtil.firstToLower(refObjXField._getName()),((AbstractXField) refObjXField).get_type(),"return this."+refObjXField._getName()+";\n");

                XAnnotation oneToOne=new XAnnotation(getXClass(OneToOne.class));
                XAnnotation jsonIgnore=new XAnnotation(getXClass(JsonIgnore.class));
                XAnnotation joinColumn=new XAnnotation(getXClass(JoinColumn.class));
                joinColumn.addAttr(new XAnnotationAttr("name",columnMeta.getColumnName()));
                joinColumn.addAttr(new XAnnotationAttr("referencedColumnName",columnMeta.getReferenceMeta().getTableMeta().getIdColumn().getColumnName()));
                joinColumn.addAttr(new XAnnotationAttr("insertable",new XAnnotationAttrVal(XAnnotationAttrVal.TYPE.val,"false")));
                joinColumn.addAttr(new XAnnotationAttr("updatable",new XAnnotationAttrVal(XAnnotationAttrVal.TYPE.val,"false")));
                ((XClassMethod) refObjGetter).addAnnotation(oneToOne);
                ((XClassMethod) refObjGetter).addAnnotation(jsonIgnore);
                ((XClassMethod) refObjGetter).addAnnotation(joinColumn);
                this.addComponent(refObjGetter);



            }

        }
        CommonConstructor commonConstructor=new CommonConstructor(tableMeta.getEntityName(),null,null);
        commonConstructor.setParams(constructorParam);
        String constructorContent="\n";
        for(XParam xParam:constructorParam){
            constructorContent+=Indent.METHODCONTENT+"this."+xParam.getName()+"="+xParam.getName()+";\n";
        }
        commonConstructor.setContent(constructorContent);
        this.addComponent(commonConstructor);
        tableMeta.setConstructParams(constructorParam);
        CommonConstructor defaultConstructor=new CommonConstructor(tableMeta.getEntityName(),null,"");
        this.addComponent(defaultConstructor);


        if("1".equals(tableMeta.getTreeConfigEnable())){
            tableMeta.initTreeConfig();

//            if(true){
//                XMethod getter=new XClassMethod("GETID",getXClass(String.class),null);
//                XAnnotation tran=new XAnnotation(getXClass(Transient.class));
//                XAnnotation jsonProperty=new XAnnotation(getXClass(JsonProperty.class));
//                jsonProperty.addAttr(new XAnnotationAttr("ID"));
//                ((XClassMethod) getter).addAnnotation(tran);
//                ((XClassMethod) getter).addAnnotation(jsonProperty);
//
//                if(tableMeta.getNodeId()!=null){
//                    String fieldName=getTreeFieldName(columnMetas.entrySet(),tableMeta.getNodeId());
//                    if(fieldName!=null){
//                        ((XClassMethod) getter).setContent("return this."+fieldName+"+\"\";\n");
//                    }else {
//                        ((XClassMethod) getter).setContent("return null;\n");
//                    }
//                }
//                this.addComponent(getter);
//
//            }


//            if(true){
//                XMethod getter=new XClassMethod("GETPID",getXClass(String.class),null);
//                XAnnotation tran=new XAnnotation(getXClass(Transient.class));
//                XAnnotation jsonProperty=new XAnnotation(getXClass(JsonProperty.class));
//                jsonProperty.addAttr(new XAnnotationAttr("PID"));
//                ((XClassMethod) getter).addAnnotation(tran);
//                ((XClassMethod) getter).addAnnotation(jsonProperty);
//                if(tableMeta.getNodePId()!=null){
//                    String fieldName=getTreeFieldName(columnMetas.entrySet(),tableMeta.getNodePId());
//                    if(fieldName!=null){
//                        ((XClassMethod) getter).setContent("return this."+fieldName+"+\"\";\n");
//                    }else {
//                        ((XClassMethod) getter).setContent("return null;\n");
//                    }
//                }
//                this.addComponent(getter);
//            }
//            if(true){
//                XMethod getter=new XClassMethod("GETISLEAF",getXClass(String.class),null);
//                XAnnotation tran=new XAnnotation(getXClass(Transient.class));
//                XAnnotation jsonProperty=new XAnnotation(getXClass(JsonProperty.class));
//                jsonProperty.addAttr(new XAnnotationAttr("ISLEAF"));
//                ((XClassMethod) getter).addAnnotation(tran);
//                ((XClassMethod) getter).addAnnotation(jsonProperty);
//                if(tableMeta.getNodePId()!=null){
//                    String fieldName=getTreeFieldName(columnMetas.entrySet(),tableMeta.getIsLeaf());
//                    if(fieldName!=null){
//                        ((XClassMethod) getter).setContent("return this."+fieldName+";\n");
//                    }else {
//                        ((XClassMethod) getter).setContent("return null;\n");
//                    }
//                }
//                this.addComponent(getter);
//            }
//            if(true){
//                XMethod getter=new XClassMethod("GETNAME",getXClass(String.class),null);
//                XAnnotation tran=new XAnnotation(getXClass(Transient.class));
//                XAnnotation jsonProperty=new XAnnotation(getXClass(JsonProperty.class));
//                jsonProperty.addAttr(new XAnnotationAttr("NAME"));
//                ((XClassMethod) getter).addAnnotation(tran);
//                ((XClassMethod) getter).addAnnotation(jsonProperty);
//                if(tableMeta.getNodePId()!=null){
//                    String fieldName=getTreeFieldName(columnMetas.entrySet(),tableMeta.getNodeNameField());
//                    if(fieldName!=null){
//                        ((XClassMethod) getter).setContent("return this."+fieldName+";\n");
//                    }else {
//                        ((XClassMethod) getter).setContent("return \"未设置显示值\";\n");
//                    }
//                }
//                this.addComponent(getter);
//            }
//            if(true){
//                XMethod getter=new XClassMethod("GETOPEN",getXClass(boolean.class),null);
//                XAnnotation tran=new XAnnotation(getXClass(Transient.class));
//                XAnnotation jsonProperty=new XAnnotation(getXClass(JsonProperty.class));
//                jsonProperty.addAttr(new XAnnotationAttr("OPEN"));
//                ((XClassMethod) getter).addAnnotation(tran);
//                ((XClassMethod) getter).addAnnotation(jsonProperty);
//                ((XClassMethod) getter).setContent("return false;\n");
//                this.addComponent(getter);
//            }
//            if(true){
//                XMethod getter=new XClassMethod("GETPIDREF",getXClass(String.class),null);
//                XAnnotation tran=new XAnnotation(getXClass(Transient.class));
//                XAnnotation jsonProperty=new XAnnotation(getXClass(JsonProperty.class));
//                jsonProperty.addAttr(new XAnnotationAttr("PIDREF"));
//                ((XClassMethod) getter).addAnnotation(tran);
//                ((XClassMethod) getter).addAnnotation(jsonProperty);
//                if (tableMeta.getPidColumn() != null)
//                    ((XClassMethod) getter).setContent("return \"" + tableMeta.getPidColumn().getFieldName() + "\";\n");
//                else
//                    ((XClassMethod) getter).setContent("return null;\n");
//
//                this.addComponent(getter);
//            }


        }

    }

    private String getTreeFieldName(Set<Map.Entry<String,ColumnMeta>> cols, String columnName){

        for(Map.Entry<String,ColumnMeta> en:cols){
            if(en.getValue().getColumnName().equalsIgnoreCase(columnName)){
                return en.getValue().getFieldName();
            }
        }
        return null;

    }

    @Override
    public XObject getProduct() {
        return this.object;
    }
}
