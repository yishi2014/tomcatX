package com.yishi.code.general.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yishi.code.general.x.XParam;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeMeta {

    private static final String DEFAULT_ENTITY_PACKAGE="com.datanew.model";
    private static final String DEFAULT_CONTROLLER_PACKAGE="com.datanew.action";
    private static final String DEFAULT_SERVICE_PACKAGE="com.datanew.service";
    private static final String DEFAULT_SERVICE_IMPL_PACKAGE="com.datanew.service.impl";
    public static final String DEFAULT_DATE_PATTERN="yyyy-MM-dd";
    private static final String DEFAULT_JAVA_DIR_PREFIX="src.main.java";
    private static final String DEFAULT_HTML_DIR_PREFIX="src.main.webapp";

    private  String htmlPathPrefix;
    private  String javaPathPrefix;
    private String remarks;
    private String tableName;
    private String tableSchem;
    @JsonIgnore
    private Map<String,ColumnMeta> columnMetas;
    @JsonIgnore
    private Map<String,TreeMeta> tablemetas;
    private List<ColumnMeta> columnMetaList;
    private String tableType;
    private List<String> pks;
    private String tableVersion;
    private String isCheckbox;
    private String isPagination; //是否分页Y/N
    private String isTree;    //表格内容是否使用树展示
    private String queryMode; //查询模式(single:单表,group:组合)
    private String pkSequence; //主键序列，当主键策略为sequence时生效
    private String pkStrategy;//主键策略(UUID,NATIVE,SEQUENCE)
    private String formCategory; //表单分类
    private String pcTemplate; //PC表单风格
    private String mobileTemplate; //移动表单风格
    private boolean insert;
    private boolean select;
    private boolean update;
    private boolean delete;
    private boolean excel;
    private String encode;
    private String entityPackage;
    private String controllerPackage;
    private String servicePackage;
    private String serviceImplPackage;
    private String entityName;
    private String controllerName;
    private String serviceName;
    private String serviceImplName;
    private String htmlName;

    //----树配置
    private String nodeNameField;  //节点名称
    private String nodeId;     //节点id字段
    private String nodePId;    //节点pid字段
    private String rootNodeValue; //根节点数据
    private String onlyLeaf; //是否只能选择叶子节点
    private String isLeaf;      //叶子节点字段
    private String showRootNode; //是否显示根节点 {是：1}|{否：0}
    private String treeConfigEnable; //是否开启树配置
    private String multiple;  //是否多选



    @JsonIgnore
    private List<XParam> constructParams;



    @JsonIgnore
    private ColumnMeta idColumn;
    @JsonIgnore
    private ColumnMeta pidColumn;
    @JsonIgnore
    private ColumnMeta nameColumn;
    @JsonIgnore
    private ColumnMeta isleafColumn;
    private boolean treeInited;

    public void initTreeConfig(){
        if(treeInited)return ;
        for(ColumnMeta col:columnMetaList){
            if(this.nodeId!=null&&!"".equals(this.nodeId)){
                if(this.nodeId.equalsIgnoreCase(col.getColumnName())){
                    this.idColumn=col;
                }
            }
            if(this.nodePId!=null&&!"".equals(this.nodePId)){
                if(this.nodePId.equalsIgnoreCase(col.getColumnName())){
                    this.pidColumn=col;
                }
            }
            if(this.nodeNameField!=null&&!"".equals(this.nodeNameField)){
                if(this.nodeNameField.equalsIgnoreCase(col.getColumnName())){
                    this.nameColumn=col;
                }
            }
            if(this.isLeaf!=null&&!"".equals(this.isLeaf)){
                if(this.isLeaf.equalsIgnoreCase(col.getColumnName())){
                    this.isleafColumn=col;
                }
            }
        }
        treeInited=true;

    }
    @JsonIgnore
    public Map getFieldNameMap(){
        Map <String,ColumnMeta> map=new HashMap<>();
        for(ColumnMeta c:columnMetaList){
            map.put(c.getFieldName(),c);
        }
        return map;
    }
    public List<XParam> getConstructParams() {
        return constructParams;
    }

    public void setConstructParams(List<XParam> constructParams) {
        this.constructParams = constructParams;
    }

    public ColumnMeta getIdColumn() {
        return idColumn;
    }

    public void setIdColumn(ColumnMeta idColumn) {
        this.idColumn = idColumn;
    }

    public ColumnMeta getPidColumn() {
        return pidColumn;
    }

    public void setPidColumn(ColumnMeta pidColumn) {
        this.pidColumn = pidColumn;
    }

    public ColumnMeta getNameColumn() {
        return nameColumn;
    }

    public void setNameColumn(ColumnMeta nameColumn) {
        this.nameColumn = nameColumn;
    }

    public ColumnMeta getIsleafColumn() {
        return isleafColumn;
    }

    public void setIsleafColumn(ColumnMeta isleafColumn) {
        this.isleafColumn = isleafColumn;
    }



    public String getEncode() {
        return encode;
    }

    public void setEncode(String encode) {
        this.encode = encode;
    }

    public String getHtmlPathPrefix() {
        return htmlPathPrefix;
    }

    public void setHtmlPathPrefix(String htmlPathPrefix) {
        this.htmlPathPrefix = htmlPathPrefix;
    }

    public String getJavaPathPrefix() {
        return javaPathPrefix;
    }

    public void setJavaPathPrefix(String javaPathPrefix) {
        this.javaPathPrefix = javaPathPrefix;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getControllerName() {
        return controllerName;
    }

    public void setControllerName(String controllerName) {
        this.controllerName = controllerName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceImplName() {
        return serviceImplName;
    }

    public void setServiceImplName(String serviceImplName) {
        this.serviceImplName = serviceImplName;
    }

    public String getHtmlName() {
        return htmlName;
    }

    public void setHtmlName(String htmlName) {
        this.htmlName = htmlName;
    }

    public String getEntityPackage() {
        return entityPackage;
    }

    public void setEntityPackage(String entityPackage) {
        this.entityPackage = entityPackage;
    }

    public String getControllerPackage() {
        return controllerPackage;
    }

    public void setControllerPackage(String controllerPackage) {
        this.controllerPackage = controllerPackage;
    }

    public String getServicePackage() {
        return servicePackage;
    }

    public void setServicePackage(String servicePackage) {
        this.servicePackage = servicePackage;
    }

    public String getServiceImplPackage() {
        return serviceImplPackage;
    }

    public void setServiceImplPackage(String serviceImplPackage) {
        this.serviceImplPackage = serviceImplPackage;
    }

    public boolean isInsert() {
        return insert;
    }

    public void setInsert(boolean insert) {
        this.insert = insert;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public boolean isExcel() {
        return excel;
    }

    public void setExcel(boolean excel) {
        this.excel = excel;
    }

    public String getEntityQualifyName() {
        return this.entityPackage + '.' + this.entityName;

    }

    public String getControllerQualifyName() {
        return this.controllerPackage+'.'+this.controllerName;
    }

    public String getServiceQualifyName() {
        return this.servicePackage+'.'+this.serviceName;
    }

    public String getServiceImplQualifyName() {
        return this.serviceImplPackage+'.'+this.serviceImplName;
    }

    public String getPkStrategy() {
        return pkStrategy;
    }

    public void setPkStrategy(String pkStrategy) {
        this.pkStrategy = pkStrategy;
    }



    public TreeMeta(){}

    public TreeMeta(String sql){
//        try{
//            Connection con=null;
//            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
//            ResultSet rset = stmt.executeQuery(sql);
//            ResultSetMetaData rsmd = rset.getMetaData() ;
//            int columnCount = rsmd.getColumnCount();
//            this.columnMetas=new HashMap<>();
//            this.columnMetaList=new ArrayList<>();
//            this.pks=new ArrayList<>();
//            for(int i=0;i<columnCount;i++){
//
//                    ColumnMeta columnMeta = new ColumnMeta();
//                    if(i==0){
//                        this.setTableName(rs.getString("TABLE_NAME"));
//                        this.setTableSchem(rs.getString("TABLE_SCHEM"));
//                        i++;
//                    }
//                    columnMeta.setTableMeta(this);
//                    String columnName = rs.getString("COLUMN_NAME").toUpperCase();
//                    columnMeta.setColumnName(columnName);
//                    columnMeta.setFieldName(RegexUtil.toCamel1Low(columnName));
//                    String typeName = rs.getString("TYPE_NAME");
//                    int decimalDigits = rs.getInt("DECIMAL_DIGITS");
//                    columnMeta.setDecimalDigits(decimalDigits);
//                    String columnType = typeName;//dbTableHandle.getMatchClassTypeByDataType(typeName,decimalDigits);
//
//                    columnMeta.setColunmType(columnType);
//                    int columnSize = rs.getInt("COLUMN_SIZE");
//                    columnMeta.setColumnSize(columnSize);
//
//                    String isNullable = rs.getInt("NULLABLE")==1?"Y":"N";
//                    columnMeta.setIsNullable(isNullable);
//                    String comment = rs.getString("REMARKS");
//                    columnMeta.setComment(comment);
//                    String columnDef = rs.getString("COLUMN_DEF");
//                    String fieldDefault = "";//judgeIsNumber(columnDef)==null?"":judgeIsNumber(columnDef);
//                    columnMeta.setFieldDefault(fieldDefault);
//                    columnMeta.setJavaType(JDBCTypeParser.getJavaType("ORACLE",columnMeta));
//                    columnMeta.setDateInPattern(DEFAULT_DATE_PATTERN);
//                    columnMeta.setDateOutPattern(DEFAULT_DATE_PATTERN);
//                    columnMeta.setFormShow("Y");
//                    columnMeta.setListShow("Y");
//                    columnMeta.setFieldTitle((columnMeta.getComment()==null||columnMeta.getComment().trim().equals(""))?columnMeta.getColumnName():columnMeta.getComment());
//                    columnMeta.setContorlType(JDBCTypeParser.getPageControlType(columnMeta.getJavaType()));
//                    columnMeta.setContorlLength(120);
//                    columnMeta.setAlign("center");
//                    columnMeta.setValign("middle");
//                    columnMeta.setIsSearch("N");
//                    columnMeta.setSearchType("comsearch");
//
//                    this.columnMetas.put(columnName, columnMeta);
//                    this.columnMetaList.add(columnMeta);
//
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
    }
    public TreeMeta(String tableSchem, String tableName, String remarks, String tableType) {
        this.tableSchem=tableSchem;
        this.tableName=tableName;
        this.remarks=remarks;
        this.tableType=tableType;
    }

    public List<ColumnMeta> getColumnMetaList() {
        return columnMetaList;
    }

    public void setColumnMetaList(List<ColumnMeta> columnMetaList) {
        this.columnMetaList = columnMetaList;
    }

    public List<String> getPks() {
        return pks;
    }

    public void setPks(List<String> pks) {
        this.pks = pks;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableSchem() {
        return tableSchem;
    }

    public void setTableSchem(String tableSchem) {
        this.tableSchem = tableSchem;
    }

    public Map<String, ColumnMeta> getColumnMetas() {
        return columnMetas;
    }

    public void setColumnMetas(Map<String, ColumnMeta> columnMetas) {
        this.columnMetas = columnMetas;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    public String getTableVersion() {
        return tableVersion;
    }

    public void setTableVersion(String tableVersion) {
        this.tableVersion = tableVersion;
    }

    public String getIsCheckbox() {
        return isCheckbox;
    }

    public void setIsCheckbox(String isCheckbox) {
        this.isCheckbox = isCheckbox;
    }

    public String getIsPagination() {
        return isPagination;
    }

    public void setIsPagination(String isPagination) {
        this.isPagination = isPagination;
    }

    public String getIsTree() {
        return isTree;
    }

    public void setIsTree(String isTree) {
        this.isTree = isTree;
    }

    public String getQueryMode() {
        return queryMode;
    }

    public void setQueryMode(String queryMode) {
        this.queryMode = queryMode;
    }

    public String getPkSequence() {
        return pkSequence;
    }

    public void setPkSequence(String pkSequence) {
        this.pkSequence = pkSequence;
    }

    public String getFormCategory() {
        return formCategory;
    }

    public void setFormCategory(String formCategory) {
        this.formCategory = formCategory;
    }

    public String getPcTemplate() {
        return pcTemplate;
    }

    public void setPcTemplate(String pcTemplate) {
        this.pcTemplate = pcTemplate;
    }

    public String getMobileTemplate() {
        return mobileTemplate;
    }

    public void setMobileTemplate(String mobileTemplate) {
        this.mobileTemplate = mobileTemplate;
    }

    public String getNodeNameField() {
        return nodeNameField;
    }

    public void setNodeNameField(String nodeNameField) {
        this.nodeNameField = nodeNameField;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodePId() {
        return nodePId;
    }

    public void setNodePId(String nodePId) {
        this.nodePId = nodePId;
    }

    public String getRootNodeValue() {
        return rootNodeValue;
    }

    public void setRootNodeValue(String rootNodeValue) {
        this.rootNodeValue = rootNodeValue;
    }

    public String getOnlyLeaf() {
        return onlyLeaf;
    }

    public void setOnlyLeaf(String onlyLeaf) {
        this.onlyLeaf = onlyLeaf;
    }

    public String getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(String isLeaf) {
        this.isLeaf = isLeaf;
    }

    public String getShowRootNode() {
        return showRootNode;
    }

    public void setShowRootNode(String showRootNode) {
        this.showRootNode = showRootNode;
    }

    public String getTreeConfigEnable() {
        return treeConfigEnable;
    }

    public void setTreeConfigEnable(String treeConfigEnable) {
        this.treeConfigEnable = treeConfigEnable;
    }

    public String getMultiple() {
        return multiple;
    }

    public void setMultiple(String multiple) {
        this.multiple = multiple;
    }

    @JsonIgnore
    public  Map getCloumnMap(){
        Map map=new HashMap();
        for(ColumnMeta c:this.columnMetaList){
            map.put(c.getColumnName(),c);
        }
        return map;
    }

    @Override
    public String toString() {
        return "TableMeta{" +
                "remarks='" + remarks + '\'' +
                ", tableName='" + tableName + '\'' +
                ", tableSchem='" + tableSchem + '\'' +
                ", columnMetas=" + columnMetas +
                ", tableType='" + tableType + '\'' +
                ", pks=" + pks +
                '}';
    }


}
