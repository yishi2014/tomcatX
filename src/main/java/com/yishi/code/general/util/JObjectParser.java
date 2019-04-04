package com.yishi.code.general.util;

import com.yishi.code.general.basedata.construct.JObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JObjectParser {
    private static final String regex="^\\s*package\\s+(?<package>.+);\\s*(?<import>(?:import\\s*.+;\\s*)*)(?<cAn>(?:@.+\\s*)*)(?<cqualify>public)?\\s+(?<cType>class|interface)\\s+(?<cName>.+?)(?:\\s*(?:extends\\s+(?<superC>.+?))?(?:implements\\s*(?<superI>.+))?)\\{(?<body>[\\w\\W]*)\\}\\s*$";
    private String packageName;
    private String classQualifier;
    private String classType;
    private String className;
    private String superClass;
    private String classAnnotations;
    private List<String> superInterfaces;
    public JObjectParser addInterface(String str){
        if(this.superInterfaces==null)
            this.superInterfaces=new ArrayList<>();
        this.superInterfaces.add(str);
        return this;
    }
    public JObjectParser(String content){
        parse(content);
    }
    private void parse(String content){
        Pattern pattern=Pattern.compile(regex);
        Matcher matcher=pattern.matcher(content);
        if(matcher.find()){
            this.packageName=matcher.group("package");
            this.className=matcher.group("cName");
            this.classQualifier=matcher.group("cqualify");
            this.classType=matcher.group("cType");
            this.classAnnotations=matcher.group("cAn");
            if("class".equals(this.classType)){
                this.superClass=matcher.group("superC");
                this.addInterface(matcher.group("superI"));
            }else if("interface".equals(this.classType)){
                this.addInterface(matcher.group("superC"));
            }

        }
    }

    public static void main(String[] args) {
        String s=("package  com.datanew.model;\n" +
                "\n" +
                "import com.fasterxml.jackson.annotation.JsonFormat;\n" +
                "import org.springframework.format.annotation.DateTimeFormat;\n" +
                "\n" +
                "import java.util.Date;\n" +
                "import javax.persistence.Basic;\n" +
                "import javax.persistence.Column;\n" +
                "import javax.persistence.Entity;\n" +
                "import javax.persistence.GeneratedValue;\n" +
                "import javax.persistence.GenerationType;\n" +
                "import javax.persistence.Id;\n" +
                "import javax.persistence.Table;\n" +
                "\n" +
//                "@Entity\n" +
//                "@Table(name=\"JEE_CGFORM_HEAD\",schema=\"BASE\")\n" +
                "public  class  JeeCgformHead<T,Q> extends Object implements A,B,C {\n" +
                "\n" +
                "    //表名\n" +
                "    private String tableName;\n" +
                "\n" +
                "    //物理表id(配置表用)\n" +
                "    private String physiceId;\n" +
                "\n" +
                "    //修改时间\n" +
                "    private Date updateDate;\n" +
                "\n" +
                "    //创建人名称\n" +
                "    private String createName;\n" +
                "\n" +
                "    //同步数据库状态\n" +
                "    private String isDbsynch;\n" +
                "\n" +
                "    private Long relationType;\n" +
                "\n" +
                "    //主键类型\n" +
                "    private String jformPkType;\n" +
                "\n" +
                "    //PC表单模板\n" +
                "    private String formTemplate;\n" +
                "\n" +
                "    //是否带checkbox\n" +
                "    private String isCheckbox;\n" +
                "\n" +
                "    //树开表单列字段\n" +
                "    private String treeFieldname;\n" +
                "\n" +
                "    //是否分页\n" +
                "    private String isPagination;\n" +
                "\n" +
                "    //主键ID\n" +
                "    private String id;\n" +
                "\n" +
                "    //表描述\n" +
                "    private String content;\n" +
                "\n" +
                "    //主键生成序列\n" +
                "    private String jformPkSequence;\n" +
                "\n" +
                "    //修改人名字\n" +
                "    private String updateName;\n" +
                "\n" +
                "    //修改人\n" +
                "    private String updateBy;\n" +
                "\n" +
                "    //树形表单父id\n" +
                "    private String treeParentidFieldname;\n" +
                "\n" +
                "    //表单分类\n" +
                "    private String jformCategory;\n" +
                "\n" +
                "    //''0''为物理表，‘1’为配置表\n" +
                "    private String tableType;\n" +
                "\n" +
                "    //创建时间\n" +
                "    private Date createDate;\n" +
                "\n" +
                "    //子表\n" +
                "    private String subTableStr;\n" +
                "\n" +
                "    //树表主键字段\n" +
                "    private String treeIdFieldname;\n" +
                "\n" +
                "    //表单模板样式(移动端)\n" +
                "    private String formTemplateMobile;\n" +
                "\n" +
                "    //表单版本号\n" +
                "    private String jformVersion;\n" +
                "\n" +
                "    //创建人\n" +
                "    private String createBy;\n" +
                "\n" +
                "    //是否是树\n" +
                "    private String isTree;\n" +
                "\n" +
                "    //表版本\n" +
                "    private Long tableVersion;\n" +
                "\n" +
                "    //查询模式\n" +
                "    private String querymode;\n" +
                "\n" +
                "    //附表排序序号\n" +
                "    private Long tabOrder;\n" +
                "\n" +
                "    //表类型:1.单表、2.主表、3.附表\n" +
                "    private Long jformType;\n" +
                "\n" +
                "    public void setTableName(String tableName){\n" +
                "\n" +
                "        this.tableName=tableName;\n" +
                "    }\n" +
                "\n" +
                "    @Basic\n" +
                "    @Column(name=\"TABLE_NAME\")\n" +
                "    public String getTableName(){\n" +
                "\n" +
                "        return this.tableName;\n" +
                "    }\n" +
                "\n" +
                "    public void setPhysiceId(String physiceId){\n" +
                "\n" +
                "        this.physiceId=physiceId;\n" +
                "    }\n" +
                "\n" +
                "    @Basic\n" +
                "    @Column(name=\"PHYSICE_ID\")\n" +
                "    public String getPhysiceId(){\n" +
                "\n" +
                "        return this.physiceId;\n" +
                "    }\n" +
                "\n" +
                "    public void setUpdateDate(Date updateDate){\n" +
                "\n" +
                "        this.updateDate=updateDate;\n" +
                "    }\n" +
                "\n" +
                "    @Basic\n" +
                "    @Column(name=\"UPDATE_DATE\")\n" +
                "    @DateTimeFormat(pattern=\"yyyy-MM-dd\")\n" +
                "    @JsonFormat(pattern=\"yyyy-MM-dd\",locale=\"zh\",timezone=\"GMT+8\")\n" +
                "    public Date getUpdateDate(){\n" +
                "\n" +
                "        return this.updateDate;\n" +
                "    }\n" +
                "\n" +
                "    public void setCreateName(String createName){\n" +
                "\n" +
                "        this.createName=createName;\n" +
                "    }\n" +
                "\n" +
                "    @Basic\n" +
                "    @Column(name=\"CREATE_NAME\")\n" +
                "    public String getCreateName(){\n" +
                "\n" +
                "        return this.createName;\n" +
                "    }\n" +
                "\n" +
                "    public void setIsDbsynch(String isDbsynch){\n" +
                "\n" +
                "        this.isDbsynch=isDbsynch;\n" +
                "    }\n" +
                "\n" +
                "    @Basic\n" +
                "    @Column(name=\"IS_DBSYNCH\")\n" +
                "    public String getIsDbsynch(){\n" +
                "\n" +
                "        return this.isDbsynch;\n" +
                "    }\n" +
                "\n" +
                "    public void setRelationType(Long relationType){\n" +
                "\n" +
                "        this.relationType=relationType;\n" +
                "    }\n" +
                "\n" +
                "    @Basic\n" +
                "    @Column(name=\"RELATION_TYPE\")\n" +
                "    public Long getRelationType(){\n" +
                "\n" +
                "        return this.relationType;\n" +
                "    }\n" +
                "\n" +
                "    public void setJformPkType(String jformPkType){\n" +
                "\n" +
                "        this.jformPkType=jformPkType;\n" +
                "    }\n" +
                "\n" +
                "    @Basic\n" +
                "    @Column(name=\"JFORM_PK_TYPE\")\n" +
                "    public String getJformPkType(){\n" +
                "\n" +
                "        return this.jformPkType;\n" +
                "    }\n" +
                "\n" +
                "    public void setFormTemplate(String formTemplate){\n" +
                "\n" +
                "        this.formTemplate=formTemplate;\n" +
                "    }\n" +
                "\n" +
                "    @Basic\n" +
                "    @Column(name=\"FORM_TEMPLATE\")\n" +
                "    public String getFormTemplate(){\n" +
                "\n" +
                "        return this.formTemplate;\n" +
                "    }\n" +
                "\n" +
                "    public void setIsCheckbox(String isCheckbox){\n" +
                "\n" +
                "        this.isCheckbox=isCheckbox;\n" +
                "    }\n" +
                "\n" +
                "    @Basic\n" +
                "    @Column(name=\"IS_CHECKBOX\")\n" +
                "    public String getIsCheckbox(){\n" +
                "\n" +
                "        return this.isCheckbox;\n" +
                "    }\n" +
                "\n" +
                "    public void setTreeFieldname(String treeFieldname){\n" +
                "\n" +
                "        this.treeFieldname=treeFieldname;\n" +
                "    }\n" +
                "\n" +
                "    @Basic\n" +
                "    @Column(name=\"TREE_FIELDNAME\")\n" +
                "    public String getTreeFieldname(){\n" +
                "\n" +
                "        return this.treeFieldname;\n" +
                "    }\n" +
                "\n" +
                "    public void setIsPagination(String isPagination){\n" +
                "\n" +
                "        this.isPagination=isPagination;\n" +
                "    }\n" +
                "\n" +
                "    @Basic\n" +
                "    @Column(name=\"IS_PAGINATION\")\n" +
                "    public String getIsPagination(){\n" +
                "\n" +
                "        return this.isPagination;\n" +
                "    }\n" +
                "\n" +
                "    public void setId(String id){\n" +
                "\n" +
                "        this.id=id;\n" +
                "    }\n" +
                "\n" +
                "    @Id\n" +
                "    @GeneratedValue(strategy=GenerationType.AUTO)\n" +
                "    @Basic\n" +
                "    @Column(name=\"ID\")\n" +
                "    public String getId(){\n" +
                "\n" +
                "        return this.id;\n" +
                "    }\n" +
                "\n" +
                "    public void setContent(String content){\n" +
                "\n" +
                "        this.content=content;\n" +
                "    }\n" +
                "\n" +
                "    @Basic\n" +
                "    @Column(name=\"CONTENT\")\n" +
                "    public String getContent(){\n" +
                "\n" +
                "        return this.content;\n" +
                "    }\n" +
                "\n" +
                "    public void setJformPkSequence(String jformPkSequence){\n" +
                "\n" +
                "        this.jformPkSequence=jformPkSequence;\n" +
                "    }\n" +
                "\n" +
                "    @Basic\n" +
                "    @Column(name=\"JFORM_PK_SEQUENCE\")\n" +
                "    public String getJformPkSequence(){\n" +
                "\n" +
                "        return this.jformPkSequence;\n" +
                "    }\n" +
                "\n" +
                "    public void setUpdateName(String updateName){\n" +
                "\n" +
                "        this.updateName=updateName;\n" +
                "    }\n" +
                "\n" +
                "    @Basic\n" +
                "    @Column(name=\"UPDATE_NAME\")\n" +
                "    public String getUpdateName(){\n" +
                "\n" +
                "        return this.updateName;\n" +
                "    }\n" +
                "\n" +
                "    public void setUpdateBy(String updateBy){\n" +
                "\n" +
                "        this.updateBy=updateBy;\n" +
                "    }\n" +
                "\n" +
                "    @Basic\n" +
                "    @Column(name=\"UPDATE_BY\")\n" +
                "    public String getUpdateBy(){\n" +
                "\n" +
                "        return this.updateBy;\n" +
                "    }\n" +
                "\n" +
                "    public void setTreeParentidFieldname(String treeParentidFieldname){\n" +
                "\n" +
                "        this.treeParentidFieldname=treeParentidFieldname;\n" +
                "    }\n" +
                "\n" +
                "    @Basic\n" +
                "    @Column(name=\"TREE_PARENTID_FIELDNAME\")\n" +
                "    public String getTreeParentidFieldname(){\n" +
                "\n" +
                "        return this.treeParentidFieldname;\n" +
                "    }\n" +
                "\n" +
                "    public void setJformCategory(String jformCategory){\n" +
                "\n" +
                "        this.jformCategory=jformCategory;\n" +
                "    }\n" +
                "\n" +
                "    @Basic\n" +
                "    @Column(name=\"JFORM_CATEGORY\")\n" +
                "    public String getJformCategory(){\n" +
                "\n" +
                "        return this.jformCategory;\n" +
                "    }\n" +
                "\n" +
                "    public void setTableType(String tableType){\n" +
                "\n" +
                "        this.tableType=tableType;\n" +
                "    }\n" +
                "\n" +
                "    @Basic\n" +
                "    @Column(name=\"TABLE_TYPE\")\n" +
                "    public String getTableType(){\n" +
                "\n" +
                "        return this.tableType;\n" +
                "    }\n" +
                "\n" +
                "    public void setCreateDate(Date createDate){\n" +
                "\n" +
                "        this.createDate=createDate;\n" +
                "    }\n" +
                "\n" +
                "    @Basic\n" +
                "    @Column(name=\"CREATE_DATE\")\n" +
                "    @DateTimeFormat(pattern=\"yyyy-MM-dd\")\n" +
                "    @JsonFormat(pattern=\"yyyy-MM-dd\",locale=\"zh\",timezone=\"GMT+8\")\n" +
                "    public Date getCreateDate(){\n" +
                "\n" +
                "        return this.createDate;\n" +
                "    }\n" +
                "\n" +
                "    public void setSubTableStr(String subTableStr){\n" +
                "\n" +
                "        this.subTableStr=subTableStr;\n" +
                "    }\n" +
                "\n" +
                "    @Basic\n" +
                "    @Column(name=\"SUB_TABLE_STR\")\n" +
                "    public String getSubTableStr(){\n" +
                "\n" +
                "        return this.subTableStr;\n" +
                "    }\n" +
                "\n" +
                "    public void setTreeIdFieldname(String treeIdFieldname){\n" +
                "\n" +
                "        this.treeIdFieldname=treeIdFieldname;\n" +
                "    }\n" +
                "\n" +
                "    @Basic\n" +
                "    @Column(name=\"TREE_ID_FIELDNAME\")\n" +
                "    public String getTreeIdFieldname(){\n" +
                "\n" +
                "        return this.treeIdFieldname;\n" +
                "    }\n" +
                "\n" +
                "    public void setFormTemplateMobile(String formTemplateMobile){\n" +
                "\n" +
                "        this.formTemplateMobile=formTemplateMobile;\n" +
                "    }\n" +
                "\n" +
                "    @Basic\n" +
                "    @Column(name=\"FORM_TEMPLATE_MOBILE\")\n" +
                "    public String getFormTemplateMobile(){\n" +
                "\n" +
                "        return this.formTemplateMobile;\n" +
                "    }\n" +
                "\n" +
                "    public void setJformVersion(String jformVersion){\n" +
                "\n" +
                "        this.jformVersion=jformVersion;\n" +
                "    }\n" +
                "\n" +
                "    @Basic\n" +
                "    @Column(name=\"JFORM_VERSION\")\n" +
                "    public String getJformVersion(){\n" +
                "\n" +
                "        return this.jformVersion;\n" +
                "    }\n" +
                "\n" +
                "    public void setCreateBy(String createBy){\n" +
                "\n" +
                "        this.createBy=createBy;\n" +
                "    }\n" +
                "\n" +
                "    @Basic\n" +
                "    @Column(name=\"CREATE_BY\")\n" +
                "    public String getCreateBy(){\n" +
                "\n" +
                "        return this.createBy;\n" +
                "    }\n" +
                "\n" +
                "    public void setIsTree(String isTree){\n" +
                "\n" +
                "        this.isTree=isTree;\n" +
                "    }\n" +
                "\n" +
                "    @Basic\n" +
                "    @Column(name=\"IS_TREE\")\n" +
                "    public String getIsTree(){\n" +
                "\n" +
                "        return this.isTree;\n" +
                "    }\n" +
                "\n" +
                "    public void setTableVersion(Long tableVersion){\n" +
                "\n" +
                "        this.tableVersion=tableVersion;\n" +
                "    }\n" +
                "\n" +
                "    @Basic\n" +
                "    @Column(name=\"TABLE_VERSION\")\n" +
                "    public Long getTableVersion(){\n" +
                "\n" +
                "        return this.tableVersion;\n" +
                "    }\n" +
                "\n" +
                "    public void setQuerymode(String querymode){\n" +
                "\n" +
                "        this.querymode=querymode;\n" +
                "    }\n" +
                "\n" +
                "    @Basic\n" +
                "    @Column(name=\"QUERYMODE\")\n" +
                "    public String getQuerymode(){\n" +
                "\n" +
                "        return this.querymode;\n" +
                "    }\n" +
                "\n" +
                "    public void setTabOrder(Long tabOrder){\n" +
                "\n" +
                "        this.tabOrder=tabOrder;\n" +
                "    }\n" +
                "\n" +
                "    @Basic\n" +
                "    @Column(name=\"TAB_ORDER\")\n" +
                "    public Long getTabOrder(){\n" +
                "\n" +
                "        return this.tabOrder;\n" +
                "    }\n" +
                "\n" +
                "    public void setJformType(Long jformType){\n" +
                "\n" +
                "        this.jformType=jformType;\n" +
                "    }\n" +
                "\n" +
                "    @Basic\n" +
                "    @Column(name=\"JFORM_TYPE\")\n" +
                "    public Long getJformType(){\n" +
                "\n" +
                "        return this.jformType;\n" +
                "    }\n" +
                "\n" +
                "\n" +
                "}");
        System.out.println(new JObjectParser(s).toString());
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getClassQualifier() {
        return classQualifier;
    }

    public void setClassQualifier(String classQualifier) {
        this.classQualifier = classQualifier;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSuperClass() {
        return superClass;
    }

    public void setSuperClass(String superClass) {
        this.superClass = superClass;
    }

    public String getClassAnnotations() {
        return classAnnotations;
    }

    public void setClassAnnotations(String classAnnotations) {
        this.classAnnotations = classAnnotations;
    }

    public List<String> getSuperInterfaces() {
        return superInterfaces;
    }

    public void setSuperInterfaces(List<String> superInterfaces) {
        this.superInterfaces = superInterfaces;
    }

    @Override
    public String toString() {
        return "JObjectParser{" +
                "packageName='" + packageName + '\'' +
                ", classQualifier='" + classQualifier + '\'' +
                ", classType='" + classType + '\'' +
                ", className='" + className + '\'' +
                ", superClass='" + superClass + '\'' +
                ", classAnnotations='" + classAnnotations + '\'' +
                ", superInterfaces=" + superInterfaces +
                '}';
    }
}
