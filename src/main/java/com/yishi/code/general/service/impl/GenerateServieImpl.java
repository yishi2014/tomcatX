package com.yishi.code.general.service.impl;

import com.alibaba.fastjson.JSON;
import com.yishi.code.general.dao.BaseDao;
import com.yishi.code.general.dto.ColumnMeta;
import com.yishi.code.general.dto.ReferenceMeta;
import com.yishi.code.general.dto.TableMeta;
import com.yishi.code.general.dummy.ContextLoader;
import com.yishi.code.general.model.CodeForm;
import com.yishi.code.general.service.TableInfoService;
import com.yishi.code.general.util.RegexUtil;
import com.yishi.code.general.x.XObject;
import com.yishi.code.general.x.XObjectDirector;
import com.yishi.code.general.x.impl.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GenerateServieImpl {

    @Resource
    private BaseDao baseDao;

    public List getDictSelectData(String controlType, String targetTableName){
        if("select".equals(controlType)){
            String hql="select new com.yishi.code.general.dto.$Dictionary(id||'',fieldname) from "+targetTableName+" where   parentcode=(select id||'' from BaseBasicdataselect where classcode='SDectionary' and fieldcode='SDectionary' )";
            return baseDao.selectByHql(hql);

        }
        if(controlType.matches("(comboTree|searchTree)")){
            String hql="select new com.yishi.code.general.dto.$Dictionary(guid,entityName) from "+targetTableName+ " where isTree='Y'";
            return baseDao.selectByHql(hql);
        }

        return null;
    }

    public void process(  String content, HttpServletResponse response) throws IOException {
        OutputStream bOut = new ByteArrayOutputStream();
        ZipCode zipCode = new ZipCode(bOut, null);
        //1,开始解析配置数据
        List<TableMeta> tableMetas = JSON.parseArray(content, TableMeta.class);
        TableInfoService tableInfoService = ContextLoader.getCurrentWebApplicationContext().getBean(TableInfoService.class);

        //String rootPath = new ClassPathResource("").getFile().getAbsolutePath();
        String fileName = tableMetas.get(0).getEntityName() + ".zip";
        String prefix = null;
        String htmlPrefix = null;


        Map<String, TableMeta> referencemap = new HashMap<>();
        for (TableMeta t : tableMetas) {
            parseTableMetas(referencemap, t, tableInfoService);
        }
        tableMetas.clear();
        for (Map.Entry<String, TableMeta> en : referencemap.entrySet()) {
            tableMetas.add(en.getValue());
        }
        //2，开始按表单生成对应的代码
        List<String> processedList = new ArrayList<>();
        for (TableMeta t : tableMetas) {
            if (processedList.contains(t.getEntityQualifyName())) {//过滤重复tablemeta
                continue;
            }
            t.setColumnMetas(t.getCloumnMap());
            prefix = t.getJavaPathPrefix();
            htmlPrefix = t.getHtmlPathPrefix();
            zipCode.setEncode(t.getEncode());
            XObject entity = new XObjectDirector(new EntityBuilder(t)).construct();
            XObject controller = new XObjectDirector(new ControllerBuilder(t)).construct();
            XObject impl = new XObjectDirector(new ServiceImplBuilder(t)).construct();
            XObject service = new XObjectDirector(new ServiceBuilder(t)).construct();
            zipCode.write(entity.getPath(prefix), entity.render());
            zipCode.write(controller.getPath(prefix), controller.render());
            zipCode.write(impl.getPath(prefix), impl.render());
            zipCode.write(service.getPath(prefix), service.render());

            String html = RegexUtil.formateduplicateChar(htmlPrefix + ".", ".", File.separator) + RegexUtil.addEndSymbol(t.getHtmlName(), ".html");

            if ("Y".equals(t.getIsTree()))
                zipCode.write(html, new HtmlGenerator().buildTreeHtml(t));
            else
                zipCode.write(html, new HtmlGenerator().buildHtml(t));


//            this.fixedThreadPool.submit(new LoadFunction(t,rootPath));
            processedList.add(t.getEntityQualifyName());
        }

        //3，开始生成工具类
        String hqlGenPath = RegexUtil.formateduplicateChar(prefix + ".com.datanew.util.unalterable.HqlGen", ".", File.separator) + ".java";
        String pagePath = RegexUtil.formateduplicateChar(prefix + ".com.datanew.dto.unalterable.$Pages", ".", File.separator) + ".java";
        String pageDataPath = RegexUtil.formateduplicateChar(prefix + ".com.datanew.dto.unalterable.PageData", ".", File.separator) + ".java";
        String regexUtil = RegexUtil.formateduplicateChar(prefix + ".com.datanew.util.unalterable.RegexUtil", ".", File.separator) + ".java";
        String result = RegexUtil.formateduplicateChar(prefix + ".com.datanew.dto.unalterable.$Result", ".", File.separator) + ".java";

        zipCode.write(hqlGenPath, new ClassPathResource(RegexUtil.formateDir("code/template/HqlGen.template")).getInputStream());
        zipCode.write(pagePath, new ClassPathResource(RegexUtil.formateDir("code/template/Pages.template")).getInputStream());
//            zipCode.write(pageDataPath,new ClassPathResource(RegexUtil.formateDir("code/template/PageData.template")).getInputStream());
        zipCode.write(regexUtil, new ClassPathResource(RegexUtil.formateDir("code/template/RegexUtil.template")).getInputStream());
        zipCode.write(result, new ClassPathResource(RegexUtil.formateDir("code/template/Result.template")).getInputStream());

        zipCode.close();
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + URLEncoder.encode(fileName, "utf-8"));
        response.getOutputStream().write(((ByteArrayOutputStream) bOut).toByteArray());

    }



    private void  processTable( ZipCode zipCode,String type, String content, HttpServletResponse response) throws IOException {


    }
    private void processTree(ZipCode zipCode, String type, String content, HttpServletResponse response) {

    }

    public void parseTableMetas(Map<String, TableMeta> refMap, TableMeta source, TableInfoService tableInfoService) {
        if (!refMap.containsKey(source.getEntityQualifyName())) {
            refMap.put(source.getEntityQualifyName(), source);
            for (ColumnMeta col : source.getColumnMetaList()) {
                if (col.getContorlType().matches("(comboTree|searchTree)")) {
                    String tableinfoId = col.getDictTable();
                    if (tableinfoId != null && !"".equals(tableinfoId.trim())) {
                        CodeForm codeForm = tableInfoService.getTableInfoById(tableinfoId);
                        if (codeForm != null) {
                            TableMeta localTable = JSON.parseObject(codeForm.getFormJson(), TableMeta.class);
                            ReferenceMeta ref = new ReferenceMeta(col);
                            ref.setTableMeta(localTable);
                            col.setReferenceMeta(ref);
                            if (!refMap.containsKey(localTable.getEntityQualifyName())) {
                                parseTableMetas(refMap, localTable, tableInfoService);
                            }

                        }
                    }
                }
            }
        }


    }
}
