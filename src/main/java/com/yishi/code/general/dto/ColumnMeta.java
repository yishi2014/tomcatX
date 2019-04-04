package com.yishi.code.general.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.yishi.code.general.x.XField;

import java.sql.ResultSet;

import static org.apache.logging.log4j.util.Strings.isNotEmpty;


public class ColumnMeta {
    private String tableName;
    private String tableSchem;
    private String columnId;
    private String columnName;
    private int columnSize;
    private String colunmType;
    private String comment;//备注
    private String fieldDefault;//默认值
    private int decimalDigits;
    private String isNullable; //Y 是 N 否
    private String pkType;//主键策略
    private String oldColumnName;//原来的字段名
    @JsonIgnore
    private TableMeta tableMeta;
    private boolean isPrimaryKey;
    private String javaType;
    private String fieldName;
    private String dateInPattern;
    private String dateOutPattern;
    private int timeDigits;

    //页面属性
    private String formShow; //表单显示 Y/N
    private String listShow; //列表显示(列是否隐藏)
    private String contorlType; //控件类型//
    private Integer contorlLength; //控件长度
    private String isSearch; //是否查询
    private String searchType; //查询类型(普通查询,范围查询)
    private String align;  //横向对齐
    private String valign; //纵向对齐
    private String fieldTitle; //列头
    private String extendParam; //扩展参数
    private String addRule; //增值规格

    //校验字典
    private String fieldHref; //字段Href
    private String checkRule; //验证规则
    private String isCheck; //校验必填
    private String dictTable; //字典table
//    private String dictCode; //字典code
//    private String dictText; //字典Text
    private String isDictFixed;//fixed/live


    //外键
    private String mainTableName; //主表名
    private String mainTableField; //主表字段

    //索引
    private String indexName; //索引名称
    private String indexField; //索引栏位(多字段逗号分割 name,password)
    private String indexType; //索引类型(normal,unique)
    @JsonIgnore
    private ReferenceMeta referenceMeta;
    @JsonIgnore
    private XField xField;

    public XField getxField() {
        return xField;
    }

    public void setxField(XField xField) {
        this.xField = xField;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setTableSchem(String tableSchem) {
        this.tableSchem = tableSchem;
    }

    public String getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(String isCheck) {
        this.isCheck = isCheck;
    }

    public ReferenceMeta getReferenceMeta() {
        return referenceMeta;
    }

    public void setReferenceMeta(ReferenceMeta referenceMeta) {
        this.referenceMeta = referenceMeta;
    }

    public String getIsDictFixed() {
        return isDictFixed;
    }

    public void setIsDictFixed(String isDictFixed) {
        this.isDictFixed = isDictFixed;
    }

    public int getTimeDigits() {
        return timeDigits;
    }

    public void setTimeDigits(int timeDigits) {
        this.timeDigits = timeDigits;
    }
    @JsonProperty(defaultValue =TableMeta.DEFAULT_DATE_PATTERN)
    @JsonDeserialize
    public String getDateInPattern() {
        return dateInPattern;
    }

    public void setDateInPattern(String dateInPattern) {
        this.dateInPattern = dateInPattern;
    }
    @JsonProperty(defaultValue =TableMeta.DEFAULT_DATE_PATTERN)
    public String getDateOutPattern() {
        return dateOutPattern;
    }

    public void setDateOutPattern(String dateOutPattern) {
        this.dateOutPattern = dateOutPattern;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        isPrimaryKey = primaryKey;
    }

    public ColumnMeta(ResultSet rs) {

    }

    public ColumnMeta() {
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ColumnMeta)) {
            return false;
        }
        ColumnMeta meta = (ColumnMeta) obj;
        //时间类型不比较长度
        if (colunmType.contains("date") || colunmType.contains("blob") || colunmType.contains("text")) {
            return columnName.equals(meta.getColumnName())
                    && isNullable.equals(meta.isNullable)
                    && isEquals(comment, meta.getComment()) && isEquals(fieldDefault, meta.getFieldDefault());
        }
	          /*else if (colunmType.contains("int")) {
				 return columnName.equals(meta.getColumnName())&& colunmType.equals(meta.getColunmType())
		        &&isNullable.equals(meta.isNullable);
			} */

        else {
            return colunmType.equals(meta.getColunmType())
                    && isNullable.equals(meta.isNullable) && columnSize == meta.getColumnSize()
                    && isEquals(comment, meta.getComment()) && isEquals(fieldDefault, meta.getFieldDefault());
        }
    }

    /**
     * 新增对比方法： 针对Sqlserver2008数据库，不对比字段备注和默认值
     *
     * @param obj      对象
     * @param dataType 数据库类型
     * @return
     */
    public boolean equalsByDataType(Object obj, String dataType) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ColumnMeta)) {
            return false;
        }
        ColumnMeta meta = (ColumnMeta) obj;
        if ("SQLSERVER".equals(dataType)) {
            //时间类型不比较长度
            if (colunmType.contains("date") || colunmType.contains("blob") || colunmType.contains("text")) {
                return columnName.equals(meta.getColumnName()) && isNullable.equals(meta.isNullable);
            } else {
                return colunmType.equals(meta.getColunmType()) && isNullable.equals(meta.isNullable) && columnSize == meta.getColumnSize();
            }
        } else {
            if (colunmType.contains("date") || colunmType.contains("blob") || colunmType.contains("text")) {
                return columnName.equals(meta.getColumnName())
                        && isNullable.equals(meta.isNullable)
                        && isEquals(comment, meta.getComment()) && isEquals(fieldDefault, meta.getFieldDefault());
            } else {
                return colunmType.equals(meta.getColunmType())
                        && isNullable.equals(meta.isNullable) && columnSize == meta.getColumnSize()
                        && isEquals(comment, meta.getComment()) && isEquals(fieldDefault, meta.getFieldDefault());
            }
        }
    }


    public boolean equalsDefault(ColumnMeta meta) {
        if (meta == this) {
            return true;
        }
        return isEquals(comment, meta.getComment());
    }

    public boolean equalsComment(ColumnMeta meta) {
        if (meta == this) {
            return true;
        }
        return isEquals(comment, meta.getComment());
    }

    private boolean isEquals(String newS, String oldS) {
        boolean booN = isNotEmpty(newS);
        boolean booO = isNotEmpty(oldS);
        if (booN != booO) {
            return false;
        }
        if (booN) {
            return newS.equals(oldS);
        }
        return true;
    }

    public String getColumnName() {
        return columnName;
    }

    public int getColumnSize() {
        return columnSize;
    }

    public String getColunmType() {
        return colunmType;
    }

    public String getComment() {
        return comment;
    }

    public int getDecimalDigits() {
        return decimalDigits;
    }

    public String getIsNullable() {
        return isNullable;
    }

    public String getOldColumnName() {
        return oldColumnName;
    }

    public int hashCode() {
        return columnSize + colunmType.hashCode() * columnName.hashCode();
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public void setColumnSize(int columnSize) {
        this.columnSize = columnSize;
    }

    public void setColunmType(String colunmType) {
        this.colunmType = colunmType;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setDecimalDigits(int decimalDigits) {
        this.decimalDigits = decimalDigits;
    }

    public void setIsNullable(String isNullable) {
        this.isNullable = isNullable;
    }

    public void setOldColumnName(String oldColumnName) {
        this.oldColumnName = oldColumnName;
    }

    public String getColumnId() {
        return columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    public String getTableName() {
        return this.tableMeta.getTableName();
    }

    public String getTableSchem() {
        return this.tableMeta.getTableSchem();
    }


    //	public void setTableName(String tableName) {
//		this.tableName = tableName;
//	}
    public String getFieldDefault() {
        return fieldDefault;
    }

    public void setFieldDefault(String fieldDefault) {
        this.fieldDefault = fieldDefault;
    }

    public String getPkType() {
        return pkType;
    }

    public void setPkType(String pkType) {
        this.pkType = pkType;
    }


    public TableMeta getTableMeta() {
        return tableMeta;
    }

    public void setTableMeta(TableMeta tableMeta) {
        this.tableMeta = tableMeta;
    }


    public String getFormShow() {
        return formShow;
    }

    public void setFormShow(String formShow) {
        this.formShow = formShow;
    }

    public String getListShow() {
        return listShow;
    }

    public void setListShow(String listShow) {
        this.listShow = listShow;
    }

    public String getContorlType() {
        return contorlType;
    }

    public void setContorlType(String contorlType) {
        this.contorlType = contorlType;
    }

    public Integer getContorlLength() {
        return contorlLength;
    }

    public void setContorlLength(Integer contorlLength) {
        this.contorlLength = contorlLength;
    }

    public String getIsSearch() {
        return isSearch;
    }

    public void setIsSearch(String search) {
        isSearch = search;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getAlign() {
        return align;
    }

    public void setAlign(String align) {
        this.align = align;
    }

    public String getValign() {
        return valign;
    }

    public void setValign(String valign) {
        this.valign = valign;
    }

    public String getFieldTitle() {
        return fieldTitle;
    }

    public void setFieldTitle(String fieldTitle) {
        this.fieldTitle = fieldTitle;
    }

    public String getExtendParam() {
        return extendParam;
    }

    public void setExtendParam(String extendParam) {
        this.extendParam = extendParam;
    }

    public String getAddRule() {
        return addRule;
    }

    public void setAddRule(String addRule) {
        this.addRule = addRule;
    }

    public String getFieldHref() {
        return fieldHref;
    }

    public void setFieldHref(String fieldHref) {
        this.fieldHref = fieldHref;
    }

    public String getCheckRule() {
        return checkRule;
    }

    public void setCheckRule(String checkRule) {
        this.checkRule = checkRule;
    }

    public String getCheck() {
        return isCheck;
    }

    public void setCheck(String check) {
        isCheck = check;
    }

    public String getDictTable() {
        return dictTable;
    }

    public void setDictTable(String dictTable) {
        this.dictTable = dictTable;
    }

    public String getMainTableName() {
        return mainTableName;
    }

    public void setMainTableName(String mainTableName) {
        this.mainTableName = mainTableName;
    }

    public String getMainTableField() {
        return mainTableField;
    }

    public void setMainTableField(String mainTableField) {
        this.mainTableField = mainTableField;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getIndexField() {
        return indexField;
    }

    public void setIndexField(String indexField) {
        this.indexField = indexField;
    }

    public String getIndexType() {
        return indexType;
    }

    public void setIndexType(String indexType) {
        this.indexType = indexType;
    }

    @Override
    public String toString() {
        return "ColumnMeta{" +
                "tableName='" + tableName + '\'' +
                ", tableSchem='" + tableSchem + '\'' +
                ", columnId='" + columnId + '\'' +
                ", columnName='" + columnName + '\'' +
                ", columnSize=" + columnSize +
                ", colunmType='" + colunmType + '\'' +
                ", comment='" + comment + '\'' +
                ", fieldDefault='" + fieldDefault + '\'' +
                ", decimalDigits=" + decimalDigits +
                ", isNullable='" + isNullable + '\'' +
                ", pkType='" + pkType + '\'' +
                ", oldColumnName='" + oldColumnName + '\'' +
                ", isPrimaryKey=" + isPrimaryKey +
                '}';
    }


}