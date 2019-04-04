package com.yishi.code.general.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

//@Entity
//@Table(name = "CODE_FORM")
public class CodeForm {
    private String guid;
    private String createBy;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updateDate;
    private String createName;
    private String formType;
    private String formTable; //表名
    private String formTablename; //表描述
    private String updateBy;
    private String updateName;
    private int formVersion;
    private String formJson;
    private String fromStatus;
    private String formLoad;//是否加载
    private String isTree; //是否将表配置为树
    private String entityName;
    private String datasourceJson;  //数据源

    public String getDatasourceJson() {
        return datasourceJson;
    }

    public void setDatasourceJson(String datasourceJson) {
        this.datasourceJson = datasourceJson;
    }

    public String getFormLoad() {
        return formLoad;
    }

    public void setFormLoad(String formLoad) {
        this.formLoad = formLoad;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getIsTree() {
        return isTree;
    }

    public void setIsTree(String isTree) {
        this.isTree = isTree;
    }

    //    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO,generator="cf")
//    @SequenceGenerator(name = "cf",sequenceName="CODE_FORM_GUID_SEQ",allocationSize=1)
//    @Column(name = "GUID")
    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

//    @Basic
//    @Column(name = "CREATE_BY")
    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

//    @Basic
//    @Column(name = "CREATE_DATE")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

//    @Basic
//    @Column(name = "CREATE_NAME")
    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

//    @Basic
//    @Column(name = "FORM_TYPE")
    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

//    @Basic
//    @Column(name = "FORM_TABLE")
    public String getFormTable() {
        return formTable;
    }

    public void setFormTable(String formTable) {
        this.formTable = formTable;
    }

//    @Basic
//    @Column(name = "FORM_TABLENAME")
    public String getFormTablename() {
        return formTablename;
    }

    public void setFormTablename(String formTablename) {
        this.formTablename = formTablename;
    }

//    @Basic
//    @Column(name = "UPDATE_BY")
    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

//    @Basic
//    @Column(name = "UPDATE_DATE")
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

//    @Basic
//    @Column(name = "UPDATE_NAME")
    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

//    @Basic
//    @Column(name = "FORM_VERSION")
    public int getFormVersion() {
        return formVersion;
    }

    public void setFormVersion(int formVersion) {
        this.formVersion = formVersion;
    }

//    @Basic
//    @Column(name = "FORM_JSON")
    public String getFormJson() {
        return formJson;
    }

    public void setFormJson(String formJson) {
        this.formJson = formJson;
    }

//    @Basic
//    @Column(name = "FROM_STATUS")
    public String getFromStatus() {
        return fromStatus;
    }

    public void setFromStatus(String fromStatus) {
        this.fromStatus = fromStatus;
    }


    @Override
    public String toString() {
        return "CodeForm{" +
                "guid='" + guid + '\'' +
                ", createBy='" + createBy + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", createName='" + createName + '\'' +
                ", formType='" + formType + '\'' +
                ", formTable='" + formTable + '\'' +
                ", formTablename='" + formTablename + '\'' +
                ", updateBy='" + updateBy + '\'' +
                ", updateName='" + updateName + '\'' +
                ", formVersion=" + formVersion +
                ", formJson='" + formJson + '\'' +
                ", fromStatus='" + fromStatus + '\'' +
                ", formLoad='" + formLoad + '\'' +
                ", isTree='" + isTree + '\'' +
                ", entityName='" + entityName + '\'' +
                '}';
    }
}
