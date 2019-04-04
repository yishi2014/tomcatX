//package com.yishi.code.general.controller;
//
//import com.yishi.code.general.annotation_.RequestMapping;
//import com.yishi.code.general.annotation_.RequestParam;
//import com.yishi.code.general.annotation_.ResponseBody;
//import com.yishi.code.general.dto.ColumnMeta;
//import com.yishi.code.general.dto.Result;
//import com.yishi.code.general.dto.SchemaMeta;
//import com.yishi.code.general.dto.TableMeta;
//import com.yishi.code.general.model.CodeDatasource;
//import com.yishi.code.general.model.CodeForm;
//import com.yishi.code.general.service.CodeDatasourceService;
//import com.yishi.code.general.service.TableInfoService;
//import com.yishi.code.general.util.DBUtil;
//import com.yishi.code.general.x.XObject;
//import com.yishi.code.general.x.XObjectDirector;
//import com.yishi.code.general.x.impl.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.stereotype.Controller;
//import sun.nio.cs.StreamEncoder;
//
//import javax.annotation.Resource;
//import javax.servlet.ServletOutputStream;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.*;
//import java.lang.reflect.Field;
//import java.net.URLEncoder;
//import java.sql.Connection;
//import java.sql.DatabaseMetaData;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.zip.ZipOutputStream;
//
//@Controller
//@RequestMapping("/tableInfo")
//public class TableInfoController {
//    @Autowired
//    private TableInfoService tis;
//
//    @Resource
//    private CodeDatasourceService codeDatasourceService;
//
//    @RequestMapping("getTableInfoAll")
//    @ResponseBody
//    public List getTableInfoAll(@RequestParam(required = false) String formType,
//                                @RequestParam(required = false) String fromStatus,
//                                @RequestParam(required = false) String formTable){
//        return tis.getTableInfoAll(formTable,formType,fromStatus);
//    }
//
//    @RequestMapping("getTableInfoByName")
//    @ResponseBody
//    public CodeForm getTableInfoByName(CodeForm codeForm){
//        return tis.getTableInfoByName(codeForm);
//    }
//
//    @RequestMapping("saveTableInfo")
//    @ResponseBody
//    public Object saveTableInfo(CodeForm codeForm){
//        Result result = new Result();
//        tis.saveTableInfo(result,codeForm);
//        return result;
//    }
//
//    @RequestMapping("updateTableInfo")
//    @ResponseBody
//    public Object updateTableInfo(CodeForm codeForm){
//        Result result = new Result();
//        tis.updateTableInfo(result,codeForm);
//        return result;
//    }
//
//    @RequestMapping("deleteTableInfo")
//    @ResponseBody
//    public Object deleteTableInfo(CodeForm codeForm){
//        Result result = new Result();
//        tis.deleteTableInfo(result,codeForm);
//        return result;
//    }
//
//    @RequestMapping("getTables")
//    @ResponseBody//String schemaPattern,String tableNamePattern,String driverclassname,String url,String username,String password
//    public Object getTables(String tableNamePattern,String id){
//        CodeDatasource cd = codeDatasourceService.queryById(id).get(0);
//        //System.out.println(cd.toString());
//        try(Connection conn= DBUtil.getConnection(cd.getDriverclassname(),cd.getUrl(),cd.getUsername(),cd.getPassword())){
//            DatabaseMetaData dbMetaData = conn.getMetaData();
//            SchemaMeta tableMeta=new SchemaMeta(dbMetaData,null,cd.getUsername().toUpperCase(),null,new String[]{"TABLE","VIEW"});
//            return tableMeta;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    @RequestMapping("getTableMeta")
//    @ResponseBody//String schema,String table,String columnNamePattern,String driverclassname,String url,String username,String password
//    public Object getTableMeta(String schema,String table,String columnNamePattern,String id){
//        CodeDatasource cd = codeDatasourceService.queryById(id).get(0);
//        try(Connection conn=DBUtil.getConnection(cd.getDriverclassname(),cd.getUrl(),cd.getUsername(),cd.getPassword())){
//            DatabaseMetaData dbMetaData = conn.getMetaData();
//            TableMeta tableMeta=new TableMeta(dbMetaData,null,schema,table,columnNamePattern);
//            return tableMeta;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//    //导出实体类
//    @RequestMapping("getEntity")
//    public void getEntity(String schema,String table,String columnNamePattern,String id,String pkgName,String strategy,String encode,HttpServletResponse response){
//        CodeDatasource cd = codeDatasourceService.queryById(id).get(0);
//        try(Connection conn=DBUtil.getConnection(cd.getDriverclassname(),cd.getUrl(),cd.getUsername(),cd.getPassword())){
//            //返回数据连接对象信息
//            DatabaseMetaData dbMetaData = conn.getMetaData();
//            String fileName=table+".zip";
//            OutputStream bOut = new ByteArrayOutputStream();
//            ZipCode zipCode=new ZipCode(bOut,null);
//            String prefix=null;
//            for(String s:table.split(",")){
//                TableMeta t=new TableMeta(dbMetaData,null,null,s,null);
//                t.setEntityPackage(pkgName);
//                t.setPkStrategy(strategy);
//                t.setEncode(encode);
//                t.setColumnMetas(t.getCloumnMap());
//                prefix=t.getJavaPathPrefix();
//                zipCode.setEncode(t.getEncode());
//                XObject entity=new XObjectDirector(new EntityBuilder(t)).construct();
//                zipCode.write(entity.getPath(prefix),entity.render());
//            }
//            zipCode.close();
//            response.setContentType("application/octet-stream");
//            response.setHeader("Content-Disposition","attachment; filename*=UTF-8''" + URLEncoder.encode(fileName,"utf-8"));
//            response.getOutputStream().write(((ByteArrayOutputStream) bOut).toByteArray());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    /**
//     * 导出表结构说明文档
//     * @param schema
//     * @param table
//     * @param columnNamePattern
//     * @param id
//     * @param response
//     */
//    @RequestMapping("exportDBTable")
//    public void exportDBTable(String schema, String table, String columnNamePattern, String id, HttpServletResponse response, HttpServletRequest request){
//        Map resutMap = new HashMap();
//        List resultList = new ArrayList();
//        CodeDatasource cd = codeDatasourceService.queryById(id).get(0);
//        try(Connection conn=DBUtil.getConnection(cd.getDriverclassname(),cd.getUrl(),cd.getUsername(),cd.getPassword())){
//            //准备模板数据
//            DatabaseMetaData dbMetaData = conn.getMetaData();
//            for(String s:table.split(",")){
//                TableMeta t=new TableMeta(dbMetaData,null,null,s,null);
//                List<ColumnMeta> colList = t.getColumnMetaList();
//                SchemaMeta tableMeta=new SchemaMeta(dbMetaData,null,cd.getUsername().toUpperCase(),s,new String[]{"TABLE"});
//                Map<String,Object> map = new HashMap<String,Object>();
//                map.put("tableName",t.getTableName());
//                map.put("tableRemark","");
//                if(tableMeta.getTables()!=null && tableMeta.getTables().size()>0){
//                    map.put("tableRemark",StringUtil.formatNullString(tableMeta.getTables().get(0).getRemarks()));
//                }
//
//                List list = new ArrayList();
//                for (int i = 0; i <colList.size(); i++) {
//                    Map<String,Object> colDetail = new HashMap<String,Object>();
//                    colDetail.put("isPrimary",colList.get(i).isPrimaryKey()?"是":"");
//                    colDetail.put("fieldName", StringUtil.formatNullString(colList.get(i).getColumnName()));
//                    colDetail.put("fieldComment",StringUtil.formatNullString(colList.get(i).getComment()));
//                    colDetail.put("dataType",StringUtil.formatNullString(colList.get(i).getColunmType()));
//                    colDetail.put("dataSize",StringUtil.formatNullString(colList.get(i).getColumnSize()+""));
//                    colDetail.put("nullAble","Y".equalsIgnoreCase(colList.get(i).getIsNullable())?"是":"");
//                    colDetail.put("constraints",""); //todo  约束设置
//                    colDetail.put("defaultValue",StringUtil.formatNullString(colList.get(i).getFieldDefault()));
//                    colDetail.put("fieldRemark","");  //todo  备注
//                    list.add(colDetail);
//                }
//                map.put("detail",list);
//                resultList.add(map);
//            }
//            resutMap.put("dataList",resultList);
//
//            //freemarker模板处理数据
//            Configuration conf = new Configuration(Configuration.VERSION_2_3_23);
//            conf.setDefaultEncoding("utf-8");
//            conf.setServletContextForTemplateLoading(request.getSession().getServletContext(),"/word.template");
//            conf.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
//            Template template = conf.getTemplate("table.ftl","utf-8");
//            Writer out = new OutputStreamWriter(new ByteArrayOutputStream(),"utf-8");
//            template.process(resutMap,out);
//
//            //通过反射将字符Writer流转字节InputStream流
//            OutputStreamWriter osw = (OutputStreamWriter)out;
//            osw.flush();
//
//            Field field = osw.getClass().getDeclaredField("se");
//            field.setAccessible(true);
//            StreamEncoder streamEncoder =  (StreamEncoder)field.get(osw);
//
//            Field field2 = streamEncoder.getClass().getDeclaredField("out");
//            field2.setAccessible(true);
//            ByteArrayOutputStream ops  =(ByteArrayOutputStream)field2.get(streamEncoder);
//            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(ops.toByteArray());
//            //验证license
//            if (!getLicense()) {
//                out.close();
//                osw.close();
//                ops.close();
//                byteArrayInputStream.close();
//                return;
//            }
//            //转word处理
//            OutputStream outputStream = new ByteArrayOutputStream();
//            Document doc = new Document(byteArrayInputStream);
//            doc.save(outputStream, SaveFormat.DOC);
//
//            response.setContentType("application/msword");
//            response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + URLEncoder.encode("表结构说明.doc", "utf-8"));
//            response.getOutputStream().write(((ByteArrayOutputStream)outputStream).toByteArray());
//
//            out.close();
//            osw.close();
//            ops.close();
//            byteArrayInputStream.close();
//            outputStream.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static boolean getLicense() {
//        boolean result = false;
//        try {
//            ClassPathResource is =new ClassPathResource("\\license.xml");
//            License aposeLic = new License();
//            aposeLic.setLicense(is.getInputStream());
//            result = true;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
//
//    @RequestMapping("getImportSequenceS")
//    @ResponseBody//String schemaPattern,String tableNamePattern,String driverclassname,String url,String username,String password
//    public Object getImportSequenceS(String schemaPattern,String tableNamePattern,String id){
//        CodeDatasource cd = codeDatasourceService.queryById(id).get(0);
//        try(Connection conn=DBUtil.getConnection(cd.getDriverclassname(),cd.getUrl(),cd.getUsername(),cd.getPassword())){
//            DatabaseMetaData dbMetaData = conn.getMetaData();
//            SchemaMeta tableMeta=new SchemaMeta(dbMetaData,null,schemaPattern,null,new String[]{"SEQUENCE"});
//            return tableMeta;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    private void passwordDes(Map<String,String> map){
//        try{
//            DesUtil desUtil = new DesUtil("www.datanew.com");
//            map.put("password",map.get("password")==null||map.get("password").equals("")?map.get("password"):desUtil.decrypt(map.get("password")));
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//}
