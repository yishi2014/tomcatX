package com.yishi.code.general.x.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONAware;
import com.alibaba.fastjson.JSONObject;
import com.yishi.code.general.dto.$Dictionary;
import com.yishi.code.general.dto.ColumnMeta;
import com.yishi.code.general.dto.TableMeta;
import com.yishi.code.general.dummy.$BaseDictionaryServiceImpl;
import com.yishi.code.general.dummy.ContextLoader;
import com.yishi.code.general.util.RegexUtil;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;

public class HtmlGenerator {
    private static Map controlTypeMapping;
    private static Map<Character, Character> datePatternMapping;

//    private BaseDao baseDao;

//    private $BaseDictionaryServiceImpl baseDictionaryService;

    private JSONArray searchbarInput = new JSONArray();
    private JSONArray formInput;
    private Map<String, String> selectFormater = new HashMap<>();


    static {
        controlTypeMapping = new HashMap();
        controlTypeMapping.put("select", "comboBox");
        controlTypeMapping.put("comboTree", "comboTree");
        controlTypeMapping.put("searchTree", "searchTree");
        controlTypeMapping.put("password", "textBox");
        controlTypeMapping.put("text", "textBox");
        controlTypeMapping.put("textarea", "textBox");
        controlTypeMapping.put("file", "webupload");
        controlTypeMapping.put("date", "dateBox");
        controlTypeMapping.put("editor", "textEditor");



        datePatternMapping = new HashMap<>();
        datePatternMapping.put('a', 'P');
        datePatternMapping.put('s', 's');
        datePatternMapping.put('h', 'H');
        datePatternMapping.put('H', 'h');
        datePatternMapping.put('D', 'd');
        datePatternMapping.put('d', 'd');
        datePatternMapping.put('M', 'm');
        datePatternMapping.put('m', 'i');
        datePatternMapping.put('y', 'y');


    }

    public String buildTreeJson(TableMeta tableMeta, int i) {//0测试
//        JSONObject treeJson=new JSONObject();
//        JSONObject callBack=new JSONObject();
//        treeJson.put("dtype","tree");
//        treeJson.put("id","treeDemo");
//        treeJson.put("outerHeight",new ValObj(" $(window).height() - 60"));
//        treeJson.put("checkType","nocheck");
//        treeJson.put("callback",callBack);
//        callBack.put("clickNode",new ValObj("function(e, treeId, treeNode, clickFlag){\n" +
//                "                                    $(\"#formDemo\").dform(\"clear\");\n" +
//                "                                    $(\"#formDemo\").dform(\"load\", treeNode);\n" +
//                "                                    $(\".bootstrap-table-edit, .bootstrap-table-delete\").attr(\"disabled\", false);\n" +
//                "                                    $(\".bootstrap-table-save, .bootstrap-table-cancel\").attr(\"disabled\", true);\n" +
//                "                                }"));
//        treeJson.put("onLoaded",new ValObj("function(ztree){ztree.expandAll(true);}"));
        String url;
        if (i == 0)
            url = "$.getRootPath()+\"/" + example_tree_prefix + tableMeta.getEntityName() + "\"";
        else
            url = "$.getRootPath()+\"/" + RegexUtil.toCamel1Low(tableMeta.getTableName()) + "/query.do\"";
//        treeJson.put("url",new ValObj(url));

        return url;
    }

    public String buildTableJson(TableMeta tableMeta, int i) {//0测试
        String dataUrl = "$.getRootPath()+\"/" + RegexUtil.toCamel1Low(tableMeta.getTableName()) + "/queryByPage.do\"";
        String example_dataUrl = "$.getRootPath()+\"/example/queryByPage.do?unique___Name=" + tableMeta.getEntityName() + "\"";


        String uniqueId = null;
        JSONArray columns = new JSONArray();
        JSONArray toolbar = new JSONArray();
        JSONObject searchbar = new JSONObject();
        searchbar.put("rownum", new ValObj("3"));
        searchbar.put("labelwidth", "100px");
        searchbar.put("inputs", searchbarInput);


        JSONObject radio = new JSONObject();
        radio.put("field", "checkType");
        radio.put("radio", true);

        columns.add(radio);
        for (ColumnMeta col : tableMeta.getColumnMetaList()) {
            JSONObject colObj = new JSONObject();
            columns.add(colObj);

            colObj.put("field", col.getFieldName());
            //todo 赋值 表格头
            colObj.put("title", col.getFieldTitle());
            //todo 赋值 是否可见
            if (!"N".equals(col.getListShow())) {
                colObj.put("visible", true);
            } else {
                colObj.put("visible", false);

            }
            if (col.getContorlLength() != null) {
                colObj.put("width", col.getContorlLength());
            }
            //判断是否有align
            if (col.getAlign() != null) {
                colObj.put("align", col.getAlign());
            }
            //判断是否有align
            if (col.getValign() != null) {
                colObj.put("valign", col.getValign());
            }

            if (col.getContorlType().matches("(comboTree|searchTree)") && i != 0) {
                if (!"N".equals(col.getListShow())) {
                    colObj.put("visible", false);
                    JSONObject colNameObj = new JSONObject();
                    columns.add(colNameObj);

                    colNameObj.put("field", col.getFieldName() + "_name");
                    //todo 赋值 表格头
                    colNameObj.put("title", col.getFieldTitle());
                    colNameObj.put("visible", true);
                    if (col.getContorlLength() != null) {
                        colNameObj.put("width", col.getContorlLength());
                    }
                    //判断是否有align
                    if (col.getAlign() != null) {
                        colNameObj.put("align", col.getAlign());
                    }
                    //判断是否有Valign
                    if (col.getValign() != null) {
                        colNameObj.put("valign", col.getValign());
                    }
                }
            }
            //todo 纠正控件类型
            if ("select".equals(col.getContorlType())) {
                //todo 判断是否有字典表
                if (selectFormater.get(col.getFieldName() + "__format") != null) {
                    colObj.put("formatter", new ValObj(selectFormater.get(col.getFieldName() + "__format")));
                }


            } else {


            }

        }

        if (tableMeta.isInsert()) {
            JSONObject isInert = new JSONObject();
            isInert.put("name", "新增");
            isInert.put("classes", "btn bootstrap-table-add");
            isInert.put("type", "button");
            isInert.put("onclick", "addRow()");
            toolbar.add(isInert);
        }
        if (tableMeta.isUpdate()) {
            JSONObject isUpdate = new JSONObject();
            isUpdate.put("name", "编辑");
            isUpdate.put("classes", "btn bootstrap-table-edit");
            isUpdate.put("type", "button");
            isUpdate.put("onclick", "editRow()");
            toolbar.add(isUpdate);
        }
        if (tableMeta.isDelete()) {
            JSONObject isDelete = new JSONObject();
            isDelete.put("name", "删除");
            isDelete.put("classes", "btn bootstrap-table-delete");
            isDelete.put("type", "button");
            isDelete.put("onclick", "removeRow()");
            toolbar.add(isDelete);
        }

        if (tableMeta.isView()) {
            JSONObject isView = new JSONObject();
            isView.put("name", "查看");
            isView.put("classes", "btn bootstrap-table-review");
            isView.put("type", "button");
            isView.put("onclick", "viewRow()");
            toolbar.add(isView);
        }


        JSONArray mainArr = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        //外层plugs数组
        JSONArray outerPlugs = new JSONArray();
        //第二层plug 第一层plug数组的元素
        JSONObject level2 = new JSONObject();
        JSONArray level2Arr = new JSONArray();
        level2.put("plug", level2Arr);
        level2.put("dtype", "row");


        JSONObject level3 = new JSONObject();
        JSONArray level3Arr = new JSONArray();
        level3.put("plug", level3Arr);
        level3.put("dtype", "column");
        level3.put("colspan", "12");

        JSONObject internalObj = new JSONObject();

        internalObj.put("dtype", "dtable");
        internalObj.put("id", "tableDemo");
        internalObj.put("height", new ValObj("$(window).height() - 80"));
        internalObj.put("url", new ValObj(i == 0 ? example_dataUrl : dataUrl));
        internalObj.put("pagination", new ValObj("true"));
        internalObj.put("clickToSelect", new ValObj("true"));

        internalObj.put("sidePagination", "server");
        internalObj.put("pageNumber", new ValObj("1"));
        internalObj.put("pageSize", new ValObj("30"));


        internalObj.put("uniqueId", uniqueId);
        internalObj.put("columns", columns);
        internalObj.put("toolbar", toolbar);
        internalObj.put("searchbar", searchbar);
        if(tableMeta.isExcel()){
            JSONObject excel = new JSONObject();
            excel.put("fileName","export.xls");
            excel.put("fileEncode","utf-8");
            excel.put("tableStyle","");
            internalObj.put("exportExcel",excel);
        }

        level3Arr.add(internalObj);


        level2Arr.add(level3);


        outerPlugs.add(level2);


        jsonObject.put("plug", outerPlugs);
        jsonObject.put("dtype", "body");
        mainArr.add(jsonObject);
        return mainArr.toJSONString();
    }

    String example_tree_prefix = "example/query.do?unique___Name=";

    public String buildFormJson(TableMeta tableMeta, int i) {//0测试1正式
        int rownum=2;
        JSONObject jsonObject = new JSONObject();
        JSONArray inputs = new JSONArray();
        formInput = inputs;
        jsonObject.put("id", "newform");
        jsonObject.put("dtype", "dform");
        jsonObject.put("rownum", rownum);
        jsonObject.put("labelwidth", "150px");
        jsonObject.put("inputs", inputs);

        for (ColumnMeta col : tableMeta.getColumnMetaList()) {
            JSONObject input = new JSONObject();
            JSONObject searchInput = new JSONObject();
            input.put("title", col.getFieldTitle());
            searchInput.put("title", col.getFieldTitle());
            input.put("name", col.getFieldName());
            searchInput.put("name", col.getFieldName());


            if (!"N".equals(col.getFormShow()) || "Y".equals(col.getIsSearch())) {
                String contrlType = col.getContorlType();
                if ("select".equals(contrlType)) {
                    input.put("type", controlTypeMapping.get(contrlType));
                    searchInput.put("type", controlTypeMapping.get(contrlType));
                    String codetableId = col.getDictTable();
                    if (true) {
                        $BaseDictionaryServiceImpl baseDictionaryService = ContextLoader.getCurrentWebApplicationContext().getBean($BaseDictionaryServiceImpl.class);
                        List<$Dictionary> dicts = baseDictionaryService.getDictionary(codetableId);
                        dicts.add(new $Dictionary("", ""));
                        String format = "function (value, row, index) {";
                        String format_sub = "if(value==\"%s\") return \"%s\";";
                        for ($Dictionary d : dicts) {
                            format += String.format(format_sub, d.getId(), d.getText());
                        }
                        format += "return value;";
                        selectFormater.put(col.getFieldName() + "__format", format + "}");
                        String localData = JSON.toJSONString(dicts);
                        input.put("localdata", new ValObj(localData));
                        searchInput.put("localdata", new ValObj(localData));
//                            if(dicts!=null&&dicts.size()>0){
//                                input.put("selected",dicts.get(0).getId());searchInput.put("selected",dicts.get(0).getId());searchInput.put("selected",dicts.get(0).getId());searchInput.put("selected",dicts.get(0).getId());
//                            }else{
//
//
//                            }
                        input.put("selected", "");
                        searchInput.put("selected", "");
//                            input.put("searchOption",new ValObj("false"));searchInput.put("searchOption",new ValObj("false"));searchInput.put("searchOption",new ValObj("false"));searchInput.put("searchOption",new ValObj("false"));
                    } else {
                        String url = "$base/$selectDict.do?groupID=" + codetableId;
                        input.put("url", url);
                        searchInput.put("url", url);
                        input.put("selected", "1");
                        searchInput.put("selected", "1");
                    }
                } else if (contrlType.matches("(comboTree|searchTree)")) {
                    input.put("type", controlTypeMapping.get(contrlType));
                    searchInput.put("type", controlTypeMapping.get(contrlType));

                    String codetableId = col.getDictTable();
                    if (false) {
//                            $BaseDictionaryServiceImpl baseDictionaryService=ContextLoader.getCurrentWebApplicationContext().getBean($BaseDictionaryServiceImpl.class);
//                            List<$Dictionary> dicts=baseDictionaryService.getDictionary(codetableId);
//                            String localData=JSON.toJSONString(dicts);
//                            input.put("localdata",new ValObj(localData));searchInput.put("localdata",new ValObj(localData));
//                            if(dicts!=null&&dicts.size()>0){
//                                input.put("selected",dicts.get(0).getId());searchInput.put("selected",dicts.get(0).getId());
//                            }else{
//                                input.put("selected","1");searchInput.put("selected","1");
//
//                            }
                    } else {
                        TableMeta tab = col.getReferenceMeta().getTableMeta();
                        if (tab != null) {
                            String url = null;
                            if (i == 0)
                                url = "$.getRootPath()+\"/" + example_tree_prefix + tab.getEntityName() + "\"";
                            else
                                url = "$.getRootPath()+\"/" + RegexUtil.toCamel1Low(tab.getTableName()) + "/query.do\"";

                            input.put("url", new ValObj(url));
                            searchInput.put("url", new ValObj(url));
                            String multipl = tab.getMultiple();
                            if ("0".equals(multipl)) {
                                input.put("checkType", "radio");
                                searchInput.put("checkType", "radio");
                            } else {
                                input.put("checkType", "checkbox");
                                searchInput.put("checkType", "checkbox");
                            }
                            String isleaf = tab.getIsLeaf();
                            if ("0".equals(isleaf)) {
                                input.put("ISLEAF", new ValObj("false"));
                                searchInput.put("ISLEAF", new ValObj("false"));
                            } else {
                                input.put("ISLEAF", new ValObj("true"));
                                searchInput.put("ISLEAF", new ValObj("true"));
                            }
                            String rootElement = tab.getShowRootNode();
                            if ("0".equals(rootElement)) {
                                input.put("rootElement", new ValObj("false"));
                                searchInput.put("rootElement", new ValObj("false"));
                            } else {
                                input.put("rootElement", new ValObj("true"));
                                searchInput.put("rootElement", new ValObj("true"));
                            }
                            JSONObject treeData = new JSONObject();
                            JSONObject keyData = new JSONObject();
                            JSONObject simpleData = new JSONObject();
                            keyData.put("name", "NAME");
                            simpleData.put("idKey", "ID");
                            simpleData.put("pIdKey", "PID");
                            simpleData.put("rootPId", tab.getRootNodeValue());
                            treeData.put("key", keyData);
                            treeData.put("simpleData", simpleData);
                            input.put("data", treeData);
                            searchInput.put("data", treeData);
                        }

                    }

                } else if ("date".equals(contrlType)) {
                    input.put("type", controlTypeMapping.get(contrlType));
                    searchInput.put("type", controlTypeMapping.get(contrlType));
                    input.put("pickerPosition", "bottom-left");
                    searchInput.put("pickerPosition", "bottom-left");
                    String pattern = replacePattern(col.getDateInPattern());
                    input.put("format", pattern);
                    searchInput.put("format", pattern);
                    //todo 解析minview 和startview
                    if (pattern.contains("y")) {
                        input.put("startView", "decade");
                        searchInput.put("startView", "decade");
                    } else if (pattern.contains("M")) {
                        input.put("startView", "year");
                        searchInput.put("startView", "year");
                    } else if (pattern.contains("d")) {
                        input.put("startView", "month");
                        searchInput.put("startView", "month");
                    } else if (pattern.contains("H") || pattern.contains("h")) {
                        input.put("startView", "day");
                        searchInput.put("startView", "day");
                    } else if (pattern.contains("m")) {
                        input.put("startView", "hour");
                        searchInput.put("startView", "hour");
                    }
                    if (pattern.contains("m")) {
                        input.put("minView", "hour");
                        searchInput.put("minView", "hour");
                    } else if (pattern.contains("H") || pattern.contains("h")) {
                        input.put("minView", "day");
                        searchInput.put("minView", "day");
                    } else if (pattern.contains("d")) {
                        input.put("minView", "month");
                        searchInput.put("minView", "month");
                    } else if (pattern.contains("M")) {
                        input.put("minView", "year");
                        searchInput.put("minView", "year");
                    } else if (pattern.contains("y")) {
                        input.put("minView", "decade");
                        searchInput.put("minView", "decade");
                    }

                } else if("file".equals(contrlType)){
                    input.put("type",controlTypeMapping.get(contrlType));
                    input.put("colspan",rownum);
                    input.put("server","upload.do");
                    input.put("url","fileInfo.do");
                    input.put("delUrl","delete.do");
                    input.put("downloadUrl","download.do");
                    input.put("fileIdKey","id");
                    input.put("isDel",new ValObj("true"));
                    input.put("isDownload",new ValObj("true"));



                }else if("password".equals(contrlType)){
                    input.put("type", controlTypeMapping.get(contrlType));
                    input.put("isPassword",true);
                }else if("editor".equals(contrlType)){
                    input.put("colspan",rownum);
                    input.put("type", controlTypeMapping.get(contrlType));
                }else {
                    input.put("type", controlTypeMapping.get(contrlType));
                    if("textarea".equals(contrlType)){
                        input.put("multiline",new ValObj("true"));
                        input.put("colspan",rownum);
                    }
                    searchInput.put("type", controlTypeMapping.get(contrlType));
                }


                //是否必填


                if (!"N".equals(col.getFormShow())) {
                    if("Y".equals(col.getIsCheck())){
                        input.put("required", new ValObj("true"));
                    }
                } else {
                    input.put("type", "hidden");
                }
                if ("Y".equals(col.getIsSearch())) {
                    if ("text".equals(contrlType) && "rangesearch".equals(col.getSearchType())) {
                        JSONObject searchStart = JSON.parseObject(input.toJSONString());
                        JSONObject searchEnd = JSON.parseObject(input.toJSONString());
                        String title = String.valueOf(input.get("title"));
                        String name = String.valueOf(input.get("name"));
                        searchStart.put("title", title + "起始");
                        searchStart.put("name", name + "__start");
                        searchEnd.put("title", title + "结束");
                        searchEnd.put("name", name + "__end");
                        searchbarInput.add(searchStart);
                        searchbarInput.add(searchEnd);


                    } else {
//                        JSONObject searchInput = JSON.parseObject(input.toJSONString());
                        searchbarInput.add(searchInput);
                    }
                }
            } else {
                input.put("type", "hidden");


            }
            inputs.add(input);

//                input.put("localdata","");searchInput.put("localdata","");
//                input.put("selected","");searchInput.put("selected","");
        }


        return jsonObject.toJSONString();
    }

    public String buildHtml(TableMeta tableMeta) {
        StringBuffer stringBuffer = new StringBuffer();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new ClassPathResource(RegexUtil.formateDir("code/template/html.html")).getInputStream(), "utf-8"))) {
            String line = null;
            while ((line = br.readLine()) != null) {
                stringBuffer.append(line).append("\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        String content = stringBuffer.toString();
        content = content.replace("${FORMJSON}", this.buildFormJson(tableMeta, 1));
        content = content.replace("${TABLEJSON}", this.buildTableJson(tableMeta, 1));
        content = content.replaceAll("\\$\\{ENTITY\\}", RegexUtil.firstToLower(tableMeta.getEntityName()));
//        for (int i = 0; i < tableMeta.getColumnMetaList().size(); i++) {
//            if(tableMeta.getColumnMetaList().get(i).isPrimaryKey()){
//                content=content.replaceAll("\\$\\{ID\\}",tableMeta.getColumnMetaList().get(i).getFieldName());
//                break;
//
//            }
//
//        }

        return content;
    }

    public String buildTreeHtml(TableMeta tableMeta) {
        StringBuffer stringBuffer = new StringBuffer();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new ClassPathResource(RegexUtil.formateDir("code/template/layout-demo.html")).getInputStream(), "utf-8"))) {
            String line = null;
            while ((line = br.readLine()) != null) {
                stringBuffer.append(line).append("\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        String content = stringBuffer.toString();
        this.buildFormJson(tableMeta, 1);
        content = content.replace("${FORMJSON}", this.formInput.toJSONString());
        content = content.replace("${TREEURL}", this.buildTreeJson(tableMeta, 1));
        content = content.replace("${ROOTPID}", tableMeta.getRootNodeValue());
//        content = content.replace("${IDREF}", tableMeta.getIdColumn() == null ? "null" : tableMeta.getIdColumn().getFieldName());
        content = content.replace("${NODEIDREF}", tableMeta.getIdColumn() == null ? "null" : tableMeta.getIdColumn().getFieldName());
        content = content.replace("${IDREF}", findPrimaryField(tableMeta.getColumnMetaList()) == null ? "null" : findPrimaryField(tableMeta.getColumnMetaList()));
        content = content.replace("${PIDREF}", tableMeta.getPidColumn() == null ? "null" : tableMeta.getPidColumn().getFieldName());
        content = content.replace("${ENTITY}", RegexUtil.firstToLower(tableMeta.getEntityName()));
        String [] buttonStr={"<button id=\"tool-add\" class=\"bootstrap-table-add\" type=\"button\" onclick=\"add()\">新增</button>","<button id=\"tool-edit\" class=\"bootstrap-table-edit\" type=\"button\" onclick=\"edit()\">修改</button>","<button id=\"tool-save\" class=\"bootstrap-table-save\" type=\"button\" onclick=\"save()\">保存</button>","<button id=\"tool-cancel\" class=\"bootstrap-table-cancel\" type=\"button\" onclick=\"cancel()\">取消</button>","<button id=\"tool-delete\" class=\"bootstrap-table-delete\" type=\"button\" onclick=\"del()\">删除</button>"};
        Set<Integer> buttons=new HashSet<>();
        if(tableMeta.isInsert()){
            //1 3 4
            buttons.add(0);
            buttons.add(2);
            buttons.add(3);
        }
        if(tableMeta.isUpdate()){
            buttons.add(1);
            buttons.add(2);
            buttons.add(3);
        }

        if(tableMeta.isDelete()){
            buttons.add(4);
        }
        List<Integer> sortList=new ArrayList(buttons);
        Collections.sort(sortList);
        if(sortList.isEmpty()){
            content = content.replace("${BUTTONS}", "");
        }else{
            String head="<div class=\"toolbar\" id=\"toolbar\">";
            for(int i:sortList){
                head+=buttonStr[i];
            }
            head+="</div>";
            content = content.replace("${BUTTONS}", head);

        }


//        for (int i = 0; i < tableMeta.getColumnMetaList().size(); i++) {
//            if(tableMeta.getColumnMetaList().get(i).isPrimaryKey()){
//                content=content.replaceAll("\\$\\{ID\\}",tableMeta.getColumnMetaList().get(i).getFieldName());
//                break;
//
//            }
//
//        }

        return content;
    }

    public String buildExampleTreeHtml(TableMeta tableMeta) {
        tableMeta.initTreeConfig();
        StringBuffer stringBuffer = new StringBuffer();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new ClassPathResource(RegexUtil.formateDir("code/template/layout-demo-example.html")).getInputStream(), "utf-8"))) {
            String line = null;
            while ((line = br.readLine()) != null) {
                stringBuffer.append(line).append("\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        String content = stringBuffer.toString();
        this.buildFormJson(tableMeta, 0);
        content = content.replace("${FORMJSON}", this.formInput.toJSONString());
        content = content.replace("${PIDREF}", tableMeta.getPidColumn() == null ? "null" : tableMeta.getPidColumn().getFieldName());
        content = content.replace("${NODEIDREF}", tableMeta.getIdColumn() == null ? "null" : tableMeta.getIdColumn().getFieldName());
        content = content.replace("${IDREF}", findPrimaryField(tableMeta.getColumnMetaList()) == null ? "null" : findPrimaryField(tableMeta.getColumnMetaList()));
        content = content.replace("${TREEURL}", this.buildTreeJson(tableMeta, 0));
        content = content.replace("${ROOTPID}", tableMeta.getRootNodeValue());
        content = content.replace("${ENTITY}", tableMeta.getEntityName());

//        for (int i = 0; i < tableMeta.getColumnMetaList().size(); i++) {
//            if(tableMeta.getColumnMetaList().get(i).isPrimaryKey()){
//                content=content.replaceAll("\\$\\{ID\\}",tableMeta.getColumnMetaList().get(i).getFieldName());
//                break;
//
//            }
//
//        }

        return content;
    }

    public String buildExampleHtml(TableMeta tableMeta) {
        StringBuffer stringBuffer = new StringBuffer();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new ClassPathResource(RegexUtil.formateDir("code/template/example.html")).getInputStream(), "utf-8"))) {
            String line = null;
            while ((line = br.readLine()) != null) {
                stringBuffer.append(line).append("\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        String content = stringBuffer.toString();
        content = content.replace("${FORMJSON}", this.buildFormJson(tableMeta, 0));
        content = content.replace("${TABLEJSON}", this.buildTableJson(tableMeta, 0));
        content = content.replaceAll("\\$\\{ENTITY\\}", tableMeta.getEntityName());
//        for (int i = 0; i < tableMeta.getColumnMetaList().size(); i++) {
//            if(tableMeta.getColumnMetaList().get(i).isPrimaryKey()){
//                content=content.replaceAll("\\$\\{ID\\}",tableMeta.getColumnMetaList().get(i).getFieldName());
//                break;
//
//            }
//
//        }

        return content;
    }

    public String replacePattern(String pattern) {
        if (pattern == null)
            pattern = TableMeta.DEFAULT_DATE_PATTERN;
        char[] chars = pattern.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if ((chars[i] >= 0x41 && chars[i] <= 0x5a) || (chars[i] >= 0x61 && chars[i] <= 0x7a)) {
                chars[i] = datePatternMapping.get(chars[i]);
            }
        }
        return new String(chars);
    }

    private String findPrimaryField(List<ColumnMeta> columnMetaList){
        String field = null;
        for (int i = 0; i < columnMetaList.size(); i++) {
            if(columnMetaList.get(i).isPrimaryKey()){
                field = columnMetaList.get(i).getFieldName();
                break;
            }
        }
        return field;
    }

    public static void main(String[] args) {
//        System.out.println(new HtmlGenerator().buildTableJson(null));;
        System.out.println((char) 0x41);
        System.out.println((char) 0x5a);
        System.out.println((char) 0x61);
        System.out.println((char) 0x7a);
    }
}

class ValObj implements JSONAware {
    private String val;

    public ValObj(String val) {
        this.val = val;
    }

    public ValObj() {

    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    @Override
    public String toJSONString() {
        return this.val;
    }
}
