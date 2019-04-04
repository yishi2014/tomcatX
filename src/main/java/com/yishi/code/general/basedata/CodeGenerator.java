package com.yishi.code.general.basedata;


import com.yishi.code.general.annotation_.RequestMapping;
import com.yishi.code.general.annotation_.ResponseBody;
import com.yishi.code.general.basedata.construct.*;
import com.yishi.code.general.dao.BaseDao;
import com.yishi.code.general.dto.ColumnMeta;
import com.yishi.code.general.dto.Result;
import com.yishi.code.general.dto.TableMeta;
import com.yishi.code.general.util.RegexUtil;
import com.yishi.code.general.x.Indent;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yishi@2018/04/10
 */
public class CodeGenerator {
    //根目录以下路径
    public static String src = ".src.main.java.".replaceAll("\\.",File.separator);
    public static String home_dir = new File("").getAbsolutePath();
    //java以下路径
    public static String wrapperStr = "com.datanew.code.general.demo.";
    public static String controller = "controller.";
    public static String service = "service.";
    public static String serviceImpl = "service.impl.";
    public static String controllerSuffix = "Controller";
    public static String serviceSuffix = "Service";
    public static String serviceImplSuffix = "ServiceImpl";
    private static  String importStr="import %s;\n";
    private static Properties prop= PropReader.load("classMapping.properties");




    public static String getImport(MethodSignature method){
        StringBuffer stringBuffer=new StringBuffer();
        String returnVal=method.getReturnVal();
        if(!returnVal.trim().equals("void"))
            stringBuffer.append(getImport(returnVal));
        for(Parameter para:method.getParameters()){
            stringBuffer.append(getImport(para.getType()));
        }
        return stringBuffer.toString();
    }
    public static String getImport(String type){
        if(prop.get(type)!=null)
            return String.format(importStr,prop.get(type));
        return "";
    }


    public static void writeFile(Class clazz, String content) throws IOException {
        String path = getPath(clazz);
        try (
                FileWriter fileWriter = new FileWriter(path, true);
        ) {
            fileWriter.write(content);
        }
    }

    public static void writeFile(File file, String content) throws IOException {
        File dir=new File(RegexUtil.getLargestDir(file.getAbsolutePath()));
        if(!dir.exists()){
            dir.mkdirs();
        }
        try (

                FileWriter fileWriter = new FileWriter(file, true);
        ) {

            fileWriter.write(content);
        }

    }

    public static String getPath(Class clazz) {
        String relativePath = getPath(clazz.getName());
        return home_dir + src + relativePath;
    }

    public static String getPath(String packageName) {
        String relativePath = packageName.replaceAll("\\.", File.separator) + ".java";
        return relativePath;
    }

    public static void createFiles(String className, String methodSignature) throws Exception {
        String rawPath = home_dir + src;
        String controllerpackage = (wrapperStr + controller);
        controllerpackage = controllerpackage.substring(0, controllerpackage.length() - 1);
        String servicePackage = wrapperStr + service;
        servicePackage = servicePackage.substring(0, servicePackage.length() - 1);
        String serviceImplPackage = wrapperStr + serviceImpl;
        serviceImplPackage = serviceImplPackage.substring(0, serviceImplPackage.length() - 1);
        MethodSignature method = new MethodSignature(methodSignature);
        @Mark("controller内容")
        String controllerContent = String.format(controllerTemplate, controllerpackage,(String.format(importStr,(servicePackage + "." + className + serviceSuffix)))+getImport(method) , new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()), (className + controllerSuffix),
                (className + serviceSuffix), toLowerCase((className + serviceSuffix)), method.getName(), methodSignature, toLowerCase((className + serviceSuffix)),
                method.getName(), method.getInvokeStr());
        @Mark("service接口")
        String serviceContent = String.format(serviceTemplate, servicePackage,getImport(method), new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()), className + serviceSuffix, methodSignature);
        @Mark("serviceImpl内容")
        String serviceImplContent = String.format(serviceImplTemplate, serviceImplPackage,String.format(importStr,((servicePackage + "." + className + serviceSuffix)))+getImport(method) , new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()), className + serviceImplSuffix, className + serviceSuffix, methodSignature);
        String controllerPath = rawPath + getPath(wrapperStr + controller + className + controllerSuffix);
        String servicePath = rawPath + getPath(wrapperStr + service + className + serviceSuffix);
        String serviceImplPath = rawPath + getPath(wrapperStr + serviceImpl + className + serviceImplSuffix);
        System.out.println(controllerPath);
        System.out.println(servicePath);
        System.out.println(serviceImplPath);
        File file1 = new File(controllerPath);
//        if (file1.exists()) throw new RuntimeException("文件已存在");
        writeFile(file1, controllerContent);
        File file2 = new File(servicePath);
//        if (file2.exists()) throw new RuntimeException("文件已存在");
        writeFile(new File(servicePath), serviceContent);
        File file3 = new File(serviceImplPath);
//        if (file3.exists()) throw new RuntimeException("文件已存在");
        writeFile(new File(serviceImplPath), serviceImplContent);
    }

    public static String controllerTemplate() {
        return null;
    }




    public static String serviceTemplate() {
        return null;

    }

    public static String serviceImplTemplate() {
        return null;

    }

    public static String toLowerCase(String str) {
        char[] c = str.toCharArray();
        c[0]^=32;
        return new String(c);
    }

    public static String controllerTemplate = "package %s;\n" +//1
            "\n" +
            "%s\n" +//2
            "import org.springframework.web.bind.annotation.RequestMapping;\n" +
            "import org.springframework.web.bind.annotation.RestController;\n" +
//            "import org.springframework.web.bind.annotation.CrossOrigin;\n"+
            "import org.springframework.web.bind.annotation.ResponseBody;\n"+
            "\n" +
            "import javax.annotation.Resource;\n" +
            "\n" +
            "/**\n" +
            " *  @%s by CodeGenerator\n" +//3
            " */\n" +
            "@RestController\n" +//4
//            "@CrossOrigin\n"+
            "@RequestMapping(\"/\")\n"+
            "public class %s {\n" +//5
            "    @Resource\n" +
            "    private %s %s;\n" +//6,7
            "    @ResponseBody\n"+
            "    @RequestMapping(\"%s\")\n" +//8
            "    public %s {\n" +//9
            "        return %s.%s(%s);\n" +//10
            "    }\n" +
            "}\n";
    public static String serviceTemplate = "package  %s;\n" +
            "%s"+
            "\n" +
            "/**\n" +
            " *  @%s by CodeGenerator\n" +
            " */\n" +
            "public interface %s {\n" +
            "    %s;\n" +
            "\n" +
            "}\n";
    public static String serviceImplTemplate = "package %s;\n" +
            "\n" +
            "import com.datanew.dao.BaseDao;\n" +
            "import org.springframework.stereotype.Service;\n"+
            "import org.springframework.transaction.annotation.Transactional;\n"+
            "%s\n" +
            "\n" +
            "import javax.annotation.Resource;\n" +
            "\n" +
            "/**\n" +
            " *  @%s by CodeGenerator\n" +
            " */\n" +
            "@Service\n"+
            "@Transactional\n"+
            "public class %s implements %s{\n" +
            "    @Resource\n" +
            "    private BaseDao baseDao;\n" +
            "    @Override\n" +
            "    public %s {\n" +
            "        return null;\n" +
            "    }\n" +
            "}\n";

    public static String  controllerMethodTemplate="    @RequestMapping(\"%s\")\n" +
            "    @ResponseBody\n"+
            "    public %s {\n" +
            "        return %s.%s(%s);\n" +
            "    }";
    public static String serviceImplMethodTemplate="    @Override\n" +
            "    public %s {\n" +
            "        return null;\n" +
            "    }";
    public static String interfaceTemplate="    %s;";
    public static String generalCode(String methodSignature, Class clazz) throws IOException {


        String rawPath = home_dir + src;
        String controllerpackage = (wrapperStr + controller);
        controllerpackage = controllerpackage.substring(0, controllerpackage.length() - 1);
        String servicePackage = wrapperStr + service;
        servicePackage = servicePackage.substring(0, servicePackage.length() - 1);
        String serviceImplPackage = wrapperStr + serviceImpl;
        serviceImplPackage = serviceImplPackage.substring(0, serviceImplPackage.length() - 1);
        MethodSignature method = new MethodSignature(methodSignature);
        Set<String> container=new HashSet<>();
        container.add(controllerSuffix);
        container.add(serviceSuffix);
        container.add(serviceImplSuffix );
        String className=clazz.getName();
        for(String s:container){
            if(className.endsWith(s)){
                className=className.substring(className.lastIndexOf(".")+1,className.lastIndexOf(s));
                @Mark("controller内容")
                String controllerContent = String.format(controllerMethodTemplate,method.getName(), methodSignature, toLowerCase((className + serviceSuffix)),
                        method.getName(), method.getInvokeStr());
                @Mark("service接口")
                String serviceContent = String.format(interfaceTemplate, methodSignature);
                @Mark("serviceImpl内容")
                String serviceImplContent = String.format(serviceImplMethodTemplate,methodSignature);
                String controllerPath = rawPath + getPath(wrapperStr + controller + className + controllerSuffix);
                String servicePath = rawPath + getPath(wrapperStr + service + className + serviceSuffix);
                String serviceImplPath = rawPath + getPath(wrapperStr + serviceImpl + className + serviceImplSuffix);

                File file1 = new File(controllerPath);
                appendClassContent(file1, controllerContent);
                File file2 = new File(servicePath);
                appendClassContent(new File(servicePath), serviceContent);
                File file3 = new File(serviceImplPath);
                appendClassContent(new File(serviceImplPath), serviceImplContent);
                break;
            }

        }

        return null;
    }

    public static void appendClassContent(File file,String content) throws IOException {
         try(FileInputStream in=new FileInputStream(file);

         ){
             byte[] bytes=new byte[in.available()];
             in.read(bytes);
             for(int i=bytes.length-1;i>0;i--){
                if(( bytes[i])==125){
                    bytes[i]='\n';
                    break;
                }

             }
             byte[] byteappend=content.getBytes("utf-8");
             byte[] finalcontent=new byte[bytes.length+byteappend.length+2];
             finalcontent[finalcontent.length-2]=10;
             finalcontent[finalcontent.length-1]=125;
             System.arraycopy(bytes,0,finalcontent,0,bytes.length);
             System.arraycopy(byteappend,0,finalcontent,bytes.length,byteappend.length);
//             System.out.println(new String(finalcontent,"utf-8"));

             try(FileOutputStream out=new FileOutputStream(file);){
                  out.write(finalcontent);
             }

         }



    }

    public static String readFile(String path,String encode) throws IOException {

        try (
                InputStream in = new FileInputStream(path);
                BufferedReader br = new BufferedReader(new InputStreamReader(in, encode))) {
            String line;
            StringBuffer stringBuffer = new StringBuffer();
            while ((line = br.readLine()) != null) {
                stringBuffer.append(line).append("\n");
            }
            return stringBuffer.toString();
        }

    }
    public static String getterMethodName(JField jField, String columnName){
        if(jField.getType().getSimpleName().equalsIgnoreCase("boolean")||jField.getType()==Boolean.class){
            return RegexUtil.toCamel1Low("is_"+columnName);
        }else{
            return RegexUtil.toCamel1Low("get_"+columnName);
        }

    }
    public static String setterMethodName(JField jField, String columnName){
        return RegexUtil.toCamel1Low("set_"+columnName);
    }
    public static void main(String[] args) throws Exception {
//        if(args[0].equals("c")){
//            createFiles(args[1], args[2]);//"String test(String test,String test1)"
//
//        }
//        generalCode("String admin10(String test)", AdminController.class);
//        String path="D:\\lustin\\IdeaWorkSpace\\ygt-microservices\\server-admin\\src\\main\\java\\com\\datanew\\serveradmin\\controller\\AdminController.java";
//        FileInputStream in=new FileInputStream(path);
//        System.out.println(in.available());
//        System.out.println("BASE_POST".replaceAll("(.)(_)(.)","\\x32$1"));
//        System.out.println(getTableName("BASEPOST"));
        TableMeta tableMeta=new TableMeta();
        tableMeta.setTableName("Test");
        tableMeta.setTableSchem("BASE");
        tableMeta.setRemarks("测试表");

        Map<String,ColumnMeta> columnMetas=new HashMap<>();
        ColumnMeta columnMeta=new ColumnMeta();
        columnMeta.setPrimaryKey(true);
        columnMeta.setColumnName("NAME");
        columnMeta.setFieldName("name");
        columnMeta.setComment("名称");

        ColumnMeta columnMeta1=new ColumnMeta();
        columnMeta1.setColumnName("AGE");
        columnMeta1.setFieldName("age");
        columnMeta1.setComment("年龄");
        columnMetas.put("NAME",columnMeta);
        columnMetas.put("AGE",columnMeta1);

        tableMeta.setColumnMetas(columnMetas);
        String entityPackageName="com.datanew.entity";
//        generateEntity(tableMeta,entityPackageName);
    }

    public static JObject generateEntity(TableMeta tableMeta){
        String tableName=tableMeta.getTableName();
        String tableSchem=tableMeta.getTableSchem();
        Map<String,ColumnMeta> columnMetas=tableMeta.getColumnMetas();

        //init JOjbect
        JObject jObject=new JObject();
        //init JPackage
        JPackage jPackage=new JPackage(tableMeta.getEntityPackage());
        jObject.setjPackage(jPackage);
        //init JHead
        JHead jHead=new JHead(tableMeta.getEntityPackage()+'.'+RegexUtil.toCamel(tableName));
        jHead.setRemark(tableMeta.getRemarks());
        JAnnotation entityAn=new JAnnotation(Entity.class);
        JAnnotation tableAn=new JAnnotation(Table.class);
        tableAn.setAttrs(new ArrayList<JAnnotationAttr>());
        tableAn.getAttrs().add(new JAnnotationAttr("name",new JAnnotationAttrVal(tableName)));
        tableAn.getAttrs().add(new JAnnotationAttr("schema",new JAnnotationAttrVal(tableSchem)));
        jHead.addAnnotation(entityAn);
        jHead.addAnnotation(tableAn);

        jObject.setjHead(jHead);
        //init JFieldList
        List fieldList=new ArrayList<JField>();
        //init JMethodList
        List methodList=new ArrayList<JMethod>();
        jObject.setjFields(fieldList);
        jObject.setJMethods(methodList);
        for(Map.Entry<String,ColumnMeta> en:columnMetas.entrySet()){
            ColumnMeta columnMeta=en.getValue();
            String columnName=columnMeta.getColumnName();

            JField jField =new JField(JDBCTypeParser.getJavaTypeClass(columnMeta.getJavaType()),columnMeta.getFieldName());
            jField.setRemark(columnMeta.getComment());
            fieldList.add(jField);

            List<JParam> paramList=new ArrayList<>();;
            paramList.add(new JParam(jField.getName(),new JClass(JDBCTypeParser.getJavaTypeClass(columnMeta.getJavaType()))));
            JMethod jMethodSetter=new JMethod(setterMethodName(jField,columnName),(JClass) null,"this."+jField.getName()+'='+jField.getName()+";\n");
            jMethodSetter.setParams(paramList);
            methodList.add(jMethodSetter);

//            Map getterParamMap=new HashMap();
//            getterParamMap.put(getterMethodName(jField),columnMeta.getJavaTypeClass());
            JMethod jMethodGetter=new JMethod(getterMethodName(jField,columnName),jField.getType(),"return this."+jField.getName()+";\n");
            if(columnMeta.isPrimaryKey()){
                //@GeneratedValue(generator = "uuid")
                //@GenericGenerator(name = "uuid", strategy = "uuid")
                JAnnotation IdAn=new JAnnotation(Id.class);

                JAnnotation generalAn=new JAnnotation(GeneratedValue.class);
                generalAn.addAttr(new JAnnotationAttr("strategy",new JAnnotationAttrVal(JAnnotationAttrVal.TYPE.val,"GenerationType.AUTO")));
                jMethodGetter.addAnnotation(IdAn);
                jMethodGetter.addAnnotation(generalAn);
                jMethodGetter.addExtClass(new JClass(GenerationType.class));
            }
            JAnnotation basicAn=new JAnnotation(Basic.class);
            jMethodGetter.addAnnotation(basicAn);
            JAnnotation columnAn=new JAnnotation(Column.class);
            columnAn.addAttr(new JAnnotationAttr("name",columnMeta.getColumnName()));
            jMethodGetter.addAnnotation(columnAn);

            if(jField.getType()==Date.class){

                if(columnMeta.getDateInPattern()!=null){
                    JAnnotation dateInAn=new JAnnotation(DateTimeFormat.class);
                    dateInAn.addAttr(new JAnnotationAttr("pattern",columnMeta.getDateInPattern()));
                    jMethodGetter.addAnnotation(dateInAn);
                }
                if(columnMeta.getDateOutPattern()!=null){
                    JAnnotation dateOutAn=new JAnnotation(JsonFormat.class);
                    dateOutAn.addAttr(new JAnnotationAttr("pattern",columnMeta.getDateInPattern()));
                    dateOutAn.addAttr(new JAnnotationAttr("locale","zh"));
                    dateOutAn.addAttr(new JAnnotationAttr("timezone","GMT+8"));
                    jMethodGetter.addAnnotation(dateOutAn);
                }


            }

            //todo 加length
            methodList.add(jMethodGetter);

        }
        JEnd jEnd=new JEnd();
        jObject.setjEnd(jEnd);
        return jObject;
    }

    public static JObject generateController(TableMeta tableMeta){


        String tableName=tableMeta.getTableName();
        Map<String,ColumnMeta> columnMetas=tableMeta.getColumnMetas();

        //init JOjbect
        JObject jObject=new JObject();
        //init JPackage
        JPackage jPackage=new JPackage(tableMeta.getControllerPackage());
        jObject.setjPackage(jPackage);
        //init JHead
        JHead jHead=new JHead(tableMeta.getControllerPackage()+'.'+tableMeta.getControllerName());
        jHead.setRemark(tableMeta.getRemarks());
        JAnnotation controllerAn=new JAnnotation(Controller.class);
        JAnnotation rmAn=new JAnnotation(RequestMapping.class);
        rmAn.setAttrs(new ArrayList<JAnnotationAttr>());
        rmAn.getAttrs().add(new JAnnotationAttr("/"+RegexUtil.toCamel1Low(tableName)));
        jHead.addAnnotation(controllerAn);
        jHead.addAnnotation(rmAn);

        jObject.setjHead(jHead);
        //init JFieldList
        List fieldList=new ArrayList<JField>();
        //init JMethodList
        List methodList=new ArrayList<JMethod>();
        jObject.setjFields(fieldList);
        jObject.setJMethods(methodList);

        JField jField=new JField(tableMeta.getServicePackage()+'.'+tableMeta.getServiceName(),RegexUtil.firstToLower(tableMeta.getServiceName()));
        jField.addAnnotation(new JAnnotation(Resource.class));
        jObject.addField(jField);


        JAnnotation responseAn=new JAnnotation(ResponseBody.class);
        if(tableMeta.isSelect()){
            JClass paramClass=new JClass(tableMeta.getEntityQualifyName());
            String content= String.format("return %s.query(t);", jField.getName());
            JMethod jMethod=new JMethod("query",List.class,content);
            jMethod.getReturnType().addParameterizeType(paramClass);
            jMethod.addParam("t",paramClass);
            jMethod.addAnnotation(new JAnnotation(RequestMapping.class).addAttr(new JAnnotationAttr("/"+jMethod.getName())));
            jMethod.addAnnotation(responseAn);

            //查询方法
            jObject.addMethod(jMethod);


            JClass paramsClassPage=new JClass("com.datanew.dto.unalterable.Page");
            JClass byPageRerurn=new JClass("com.datanew.dto.unalterable.PageData");

            String contentByPage = "if(page==null)page=new Page();\n" +
                    Indent.METHODCONTENT +
                    "return %s.queryByPage(page,t);\n";
            contentByPage= String.format(contentByPage, jField.getName());
            JMethod jMethodByPage=new JMethod("queryByPage",byPageRerurn,contentByPage);
            jMethodByPage.addAnnotation(new JAnnotation(RequestMapping.class).addAttr(new JAnnotationAttr("/"+jMethodByPage.getName())));
            jMethodByPage.addParam("page",paramsClassPage);
            jMethodByPage.addParam("t",paramClass);
            jMethodByPage.addAnnotation(responseAn);
            jObject.addMethod(jMethodByPage);



        }
        if(tableMeta.isInsert()){
            JClass paramClass=new JClass(tableMeta.getEntityQualifyName());
            String content= String.format("return %s.save(t);\n\n", jField.getName());
            JMethod jMethod=new JMethod("save",Result.class,content);
            jMethod.addParam("t",paramClass);
            jMethod.addAnnotation(new JAnnotation(RequestMapping.class).addAttr(new JAnnotationAttr("/"+jMethod.getName())));

            //新增方法
            jObject.addMethod(jMethod);
        }
        if(tableMeta.isUpdate()){
            JClass paramClass=new JClass(tableMeta.getEntityQualifyName());
            String content= String.format("return %s.update(t);\n\n", jField.getName());
            JMethod jMethod=new JMethod("update",Result.class,content);
            jMethod.addParam("t",paramClass);
            jMethod.addAnnotation(new JAnnotation(RequestMapping.class).addAttr(new JAnnotationAttr("/"+jMethod.getName())));

            //新增方法
            jObject.addMethod(jMethod);

        }
        if(tableMeta.isDelete()){
            JClass paramClass=new JClass(Object.class);
            String content= String.format("return %s.delete(t);\n\n", jField.getName());
            JMethod jMethod=new JMethod("delete",Result.class,content);
            jMethod.addParam("t",paramClass);
            jMethod.addAnnotation(new JAnnotation(RequestMapping.class).addAttr(new JAnnotationAttr("/"+jMethod.getName())));

            //新增方法
            jObject.addMethod(jMethod);
        }

        JEnd jEnd=new JEnd();
        jObject.setjEnd(jEnd);
        return jObject;

    }
    public static JObject generateServiceImpl(TableMeta tableMeta){

        //init JOjbect
        JObject jObject=new JObject();
        //init JPackage
        JPackage jPackage=new JPackage(tableMeta.getServiceImplPackage());
        jObject.setjPackage(jPackage);
        //init JHead
        JHead jHead=new JHead(tableMeta.getServiceImplPackage()+'.'+tableMeta.getServiceImplName());
        jHead.addInterface(new JInterface(new JClass(tableMeta.getServiceQualifyName())));
        jHead.setRemark(tableMeta.getRemarks());
        JAnnotation implAn=new JAnnotation(Service.class);

        jHead.addAnnotation(implAn);

        jObject.setjHead(jHead);
        //init JFieldList
        List fieldList=new ArrayList<JField>();
        //init JMethodList
        List methodList=new ArrayList<JMethod>();
        jObject.setjFields(fieldList);
        jObject.setJMethods(methodList);

        JField jField=new JField(BaseDao.class,"baseDao");
        jField.addAnnotation(new JAnnotation(Resource.class));
        jObject.addField(jField);



        if(tableMeta.isSelect()){
            JClass paramClass=new JClass(tableMeta.getEntityQualifyName());

            String content= "List param=new ArrayList();\n";
            content+=Indent.METHODCONTENT;
            content+="return %s.selectByHql(HqlGen.hql(%s,%s.class,param,null),param);\n";

            JMethod jMethod=new JMethod("query",List.class,null);
            jMethod.getReturnType().addParameterizeType(paramClass);
            jMethod.addParam("t",paramClass);
            content= String.format(content,jField.getName(), "t",paramClass.getSimpleName());
            jMethod.setContent(content);

            //查询方法
            jObject.addMethod(jMethod);


            JClass paramsClassPage=new JClass("com.datanew.dto.unalterable.Page");
            JClass byPageRerurn=new JClass("com.datanew.dto.unalterable.PageData");
            byPageRerurn.addParameterizeType(paramClass);

            String contentByPage = "List param = new ArrayList();\n" +
                    String.format("        List list = %s.selectByHql(HqlGen.hql(t, %s.class, param,page), param,((page.getCurrent()-1)*page.getPageSize()),page.getPageSize());\n",jField.getName(),paramClass.getSimpleName() ) +
                    String.format("        long count=%s.getCountByHQL(page.getCountHql(),param);\n", jField.getName()) +
                    "        page.setTotal(count);\n" +
                    "        return new PageData<>(page,list);\n";
            contentByPage= String.format(contentByPage, jField.getName());
            JMethod jMethodByPage=new JMethod("queryByPage",byPageRerurn,contentByPage);
            jMethodByPage.addParam("page",paramsClassPage);
            jMethodByPage.addParam("t",paramClass);

            jMethodByPage.addExtClass(new JClass(List.class));
            jMethodByPage.addExtClass(new JClass(ArrayList.class));

            jMethodByPage.addExtClass(new JClass(tableMeta.getEntityPackage()+".HqlGen"));
            jObject.addMethod(jMethodByPage);

        }
        if(tableMeta.isInsert()){
            JClass paramClass=new JClass(tableMeta.getEntityQualifyName());
            String content="try{\n" +
                    "             %s.save(t);\n" +
                    "         }catch (Exception e){\n" +
                    "             e.printStackTrace();\n" +
                    "             return Result.fail();\n" +
                    "         }\n" +
                    "         return Result.success();\n";
            content= String.format(content, jField.getName());
            JMethod jMethod=new JMethod("save",Result.class,content);
            jMethod.addParam("t",paramClass);

            //新增方法
            jObject.addMethod(jMethod);
        }
        if(tableMeta.isUpdate()){
            String content="try{\n" +
                    "            baseDao.update(t);\n" +
                    "        }catch (Exception e){\n" +
                    "            e.printStackTrace();\n" +
                    "            return Result.fail();\n" +
                    "        }\n" +
                    "        return Result.success();\n";
            JClass paramClass=new JClass(tableMeta.getEntityQualifyName());
            content= String.format(content, jField.getName());
            JMethod jMethod=new JMethod("update",Result.class,content);
            jMethod.addParam("t",paramClass);

            //新增方法
            jObject.addMethod(jMethod);

        }
        if(tableMeta.isDelete()){
            String content="try{\n" +
                    "            baseDao.delete(t);\n" +
                    "        }catch (Exception e){\n" +
                    "            e.printStackTrace();\n" +
                    "            return Result.fail();\n" +
                    "        }\n" +
                    "        return Result.success();\n";
            JClass paramClass=new JClass(Object.class);
            content= String.format(content, jField.getName());
            JMethod jMethod=new JMethod("delete",Result.class,content);
            jMethod.addParam("t",paramClass);

            //新增方法
            jObject.addMethod(jMethod);
        }

        JEnd jEnd=new JEnd();
        jObject.setjEnd(jEnd);
        return jObject;

    }


    public static JObject generateService(TableMeta tableMeta){

        //init JOjbect
        JObject jObject=new JObject();
        //init JPackage
        JPackage jPackage=new JPackage(tableMeta.getServicePackage());
        jObject.setjPackage(jPackage);
        //init JHead
        JHead jHead=new JInterfaceHead(tableMeta.getServicePackage()+'.'+tableMeta.getServiceName());
        jHead.setRemark(tableMeta.getRemarks());


        jObject.setjHead(jHead);

        if(tableMeta.isSelect()){
            JClass paramClass=new JClass(tableMeta.getEntityQualifyName());
            JMethod jMethod=new JInterfaceMehtod("query",List.class);
            jMethod.getReturnType().addParameterizeType(paramClass);
            jMethod.addParam("t",paramClass);

            //查询方法
            jObject.addMethod(jMethod);

            JClass paramsClassPage=new JClass("com.datanew.dto.unalterable.Page");
            JClass byPageRerurn=new JClass("com.datanew.dto.unalterable.PageData");
            byPageRerurn.addParameterizeType(paramClass);

            JMethod jMethodByPage=new JInterfaceMehtod("queryByPage",byPageRerurn);
            jMethodByPage.addParam("page",paramsClassPage);
            jMethodByPage.addParam("t",paramClass);
            jObject.addMethod(jMethodByPage);

        }
        if(tableMeta.isInsert()){
            JClass paramClass=new JClass(tableMeta.getEntityQualifyName());
            JMethod jMethod=new JInterfaceMehtod("save",Result.class);
            jMethod.addParam("t",paramClass);

            //新增方法
            jObject.addMethod(jMethod);
        }
        if(tableMeta.isUpdate()){
            JClass paramClass=new JClass(tableMeta.getEntityQualifyName());
            JMethod jMethod=new JInterfaceMehtod("update",Result.class);
            jMethod.addParam("t",paramClass);

            //新增方法
            jObject.addMethod(jMethod);

        }
        if(tableMeta.isDelete()){
            JClass paramClass=new JClass(Object.class);
            JMethod jMethod=new JInterfaceMehtod("delete",Result.class);
            jMethod.addParam("t",paramClass);

            //新增方法
            jObject.addMethod(jMethod);
        }

        JEnd jEnd=new JEnd();
        jObject.setjEnd(jEnd);
        return jObject;

    }


}


class MethodSignature {
    private static String paramRegex="\\s*(?<pType>\\w+)\\s+(?<pVal>\\w+)\\,*";
    private static  String methodRegex="(?<qualifier>public|private|protected)*\\s*(?<static>static)*\\s*(?<returnType>\\w+)\\s+(?<methodName>\\w+)\\s*\\((?<param>.*)\\)";
    private String qualifier;
    private String name;
    private String returnVal;
    private Parameter[] parameters;
    private boolean isStatic;

    public MethodSignature(String signature) {
        Pattern p = Pattern.compile(methodRegex);
        Matcher m = p.matcher(signature);
        if (!m.hitEnd()&&m.find()) {
            System.out.println(m.group("qualifier"));
            System.out.println(m.group("static"));
            System.out.println(m.group("returnType"));
            System.out.println(m.group("methodName"));
            this.qualifier=m.group("qualifier");
            this.isStatic=m.group("static")!=null;
            this.name=(m.group("methodName"));
            this.returnVal=(m.group("returnType"));
            this.parameters=(getParams(m.group("param")));

        }
    }

    public String getQualifier() {
        return qualifier;
    }

    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public void setStatic(boolean aStatic) {
        isStatic = aStatic;
    }

    public static Parameter[] getParams(String paramStr){
        Pattern p = Pattern.compile(paramRegex);
        Matcher m = p.matcher(paramStr);
        List<Parameter> params=new ArrayList<Parameter>();
        int i=0;
        while (!m.hitEnd()&&m.find()) {
            System.out.println(m.group("pType"));
            System.out.println(m.group("pVal"));
            params.add(new Parameter(m.group("pType"),m.group("pVal")));
        }
        Parameter[]parameters=new Parameter[params.size()];
        params.toArray(parameters);
        return parameters;
    }
    public String getInvokeStr() {
        StringBuffer stringBuffer = new StringBuffer();
        if(this.parameters==null||this.parameters.length==0)return "";
        for (Parameter parameter : this.parameters) {
            stringBuffer.append(parameter.getName());
            stringBuffer.append(",");
        }
        return stringBuffer.toString().substring(0, stringBuffer.length() - 1);
    }

    public String[] splitBySpace(String str) throws Exception {
        int index = str.indexOf(" ");
        if (index < 0) return new String[]{};
        String[] r = new String[2];
        r[0] = str.substring(0, index);
        r[1] = str.substring(index).trim();
        return r;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReturnVal() {
        return returnVal;
    }

    public void setReturnVal(String returnVal) {
        this.returnVal = returnVal;
    }

    public Parameter[] getParameters() {
        return parameters;
    }

    public void setParameters(Parameter[] parameters) {
        this.parameters = parameters;
    }

}

class Parameter {
    private String type;
    private String name;

    public Parameter(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
