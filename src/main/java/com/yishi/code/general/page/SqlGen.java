package com.yishi.code.general.page;

import com.yishi.code.general.basedata.PropReader;
import com.yishi.code.general.basedata.construct.JDBCTypeParser;
import com.yishi.code.general.dto.ColumnMeta;
import com.yishi.code.general.dto.TableMeta;
import com.yishi.code.general.dto.Value_Type;
import org.hibernate.type.BasicType;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SqlGen {
    private static Properties properties=PropReader.load("config.properties");

    public static boolean isBlank(String str){
        return str==null||str.trim().length()==0;

    }
    public static String getFiledByMethodName(String methodName){
        if(methodName.startsWith("get"))
        {
            return RegexUtil.firstToLower(methodName.substring(3));

        }else
        {
            return RegexUtil.firstToLower(methodName.substring(2));
        }

    }
    public static Object[] deleteSql(TableMeta obj,Map map){
        map.remove("unique___Name");
        String sql="delete from %s where %s";
        List<ColumnMeta> columnMetaList=obj.getColumnMetaList();
        String tableName=obj.getTableName();
        List<String> columns=new ArrayList<>(columnMetaList.size());
        List<Object> columnVals=new ArrayList<>(columns.size());
        List<Object> whereVals=new ArrayList<>();
        String whereStr="";

        for(ColumnMeta columnMeta:columnMetaList){
            String fieldName = columnMeta.getFieldName();
            String columnName = columnMeta.getColumnName();
            Object val = map.get(fieldName);
            Object valObj = typeReverse(columnMeta, val);

            if(!columnMeta.isPrimaryKey()){
                continue;
            }
            if(valObj==null)
                continue;
            columns.add(columnName);
            columnVals.add(valObj);
        }
        String columnStr="";
        if(columns.size()==0)throw  new RuntimeException();
        for(int i=0;i<columns.size();i++){
            if(i==0){
                columnStr+=columns.get(i)+"=? ";
            }else{
                columnStr+=" and "+columns.get(i)+"=?";
            }
        }
        sql=String.format(sql,tableName,columnStr);
        Object[] result={sql,columnVals};
        return result;

    }

    public static Object[] updateSql(TableMeta obj, Map map){
        map.remove("unique___Name");
        String sql="update %s set %s where %s";
        if("oracle.jdbc.driver.OracleDriver".equals(properties.get("jdbc.driverClassName"))){
            List<ColumnMeta> columnMetaList=obj.getColumnMetaList();
            String tableName=obj.getTableName();
            List<String> columns=new ArrayList<>(columnMetaList.size());
            List<Object> columnVals=new ArrayList<>(columns.size());
            List<Object> whereVals=new ArrayList<>();
            List<String> whereColumns=new ArrayList<>();
            String whereStr="";

            for (ColumnMeta columnMeta : columnMetaList) {
                String fieldName = columnMeta.getFieldName();
                String columnName = columnMeta.getColumnName();
                Object val = map.get(fieldName);
                Object valObj = typeReverse(columnMeta, val);

                if(columnMeta.isPrimaryKey()){
                    if(valObj==null)
                        throw new RuntimeException("主键不能为空");
                    whereVals.add(typeReverse(columnMeta,val));
                    whereColumns.add(columnName);
                }
                else{

                    columns.add(columnName);
                    columnVals.add(valObj);

                }
            }
            String columnStr="";
            for(int i=0;i<columns.size();i++){
                if(i==0){
                    columnStr+=columns.get(i)+"=? ";
                }else{
                    columnStr+=","+columns.get(i)+"=? ";
                }
            }
            for(int i=0;i<whereVals.size();i++){
                if(i==0){
                    whereStr+=whereColumns.get(i)+"=?";
                }else {
                    whereStr+=" and "+whereColumns.get(i)+"=?";
                }
            }
            sql=String.format(sql,tableName,columnStr,whereStr);
            columnVals.addAll(whereVals);
            Object[] result={sql,columnVals};
            return result;
        }
        return null;

    }
    public static Object[] updateSqlnew(TableMeta obj, Map map){
        map.remove("unique___Name");
        String sql="update %s set %s where %s";
//        if("oracle.jdbc.driver.OracleDriver".equals(properties.get("jdbc.driverClassName"))){
            List<ColumnMeta> columnMetaList=obj.getColumnMetaList();
            String tableName=obj.getTableName();
            List<String> columns=new ArrayList<>(columnMetaList.size());
            List<Object> columnVals=new ArrayList<>(columns.size());
            List<Object> whereVals=new ArrayList<>();
            List<String> whereColumns=new ArrayList<>();
            String whereStr="";

            for (ColumnMeta columnMeta : columnMetaList) {
                String fieldName = columnMeta.getFieldName();
                String columnName = columnMeta.getColumnName();
                Object val = map.get(fieldName);
                Object valObj = typeReverse(columnMeta, val);

                if(columnMeta.isPrimaryKey()){
                    if(valObj==null)
                        throw new RuntimeException("主键不能为空");
                    whereVals.add(typeReverse(columnMeta,val));
                    whereColumns.add(columnName);
                }
                else{

                    columns.add(columnName);
                    if (valObj == null) {
                        Type type=getHibernateType(columnMeta);
                        if(type==null){
                            columnVals.add(valObj);
                        }else{
                            columnVals.add(new Value_Type(valObj,type));

                        }
                    }else{
                        columnVals.add(valObj);
                    }

                }
            }
            String columnStr="";
            for(int i=0;i<columns.size();i++){
                if(i==0){
                    columnStr+=columns.get(i)+"=? ";
                }else{
                    columnStr+=","+columns.get(i)+"=? ";
                }
            }
            for(int i=0;i<whereVals.size();i++){
                if(i==0){
                    whereStr+=whereColumns.get(i)+"=?";
                }else {
                    whereStr+=" and "+whereColumns.get(i)+"=?";
                }
            }
            sql=String.format(sql,tableName,columnStr,whereStr);
            columnVals.addAll(whereVals);
            Object[] result={sql,columnVals};
            return result;
//        }
//        return null;

    }

    private static Type getHibernateType(ColumnMeta columnMeta) {
        if(Integer.class==JDBCTypeParser.getJavaTypeClass(columnMeta.getJavaType())){
            return StandardBasicTypes.INTEGER;
        }
        if (Long.class == JDBCTypeParser.getJavaTypeClass(columnMeta.getJavaType())) {
            return StandardBasicTypes.LONG;
        }
        if (Date.class == JDBCTypeParser.getJavaTypeClass(columnMeta.getJavaType())) {
            return StandardBasicTypes.DATE;
        }
        if (BigDecimal.class == JDBCTypeParser.getJavaTypeClass(columnMeta.getJavaType())) {
            return StandardBasicTypes.BIG_DECIMAL;
        }
       return null;
    }

    public static Object[] saveSql(TableMeta obj, Map map){
        map.remove("unique___Name");
        obj.initTreeConfig();
//        if(map.get("PID")!=null){
//            if(obj.getPidColumn()!=null)
//            map.put(obj.getPidColumn().getFieldName(),map.get("PID"));
//        }

        String sql="insert into %s (%s)values(%s)";
//        if("oracle.jdbc.driver.OracleDriver".equals(properties.get("jdbc.driverClassName"))){
            List<ColumnMeta> columnMetaList=obj.getColumnMetaList();
            String tableName=obj.getTableName();
            List<String> columns=new ArrayList<>(columnMetaList.size());
            List<Object> columnVals=new ArrayList<>(columns.size());
            List<ColumnLiterals> literals=new ArrayList<>();
            String columnStr="";
            String columnValStr="";
            for (ColumnMeta columnMeta : columnMetaList) {
                String fieldName = columnMeta.getFieldName();
                String columnName = columnMeta.getColumnName();
                Object val = map.get(fieldName);
                Object valObj = typeReverse(columnMeta, val);
                if(columnMeta.isPrimaryKey()){
                    if("SEQUENCE".equals(obj.getPkStrategy()) && "oracle.jdbc.driver.OracleDriver".equals(properties.get("jdbc.driverClassName"))){
                        columns.add(columnName);
                        ColumnLiterals literalsVal=new ColumnLiterals(obj.getPkSequence()+".nextval");
                        columnVals.add(literalsVal);
                        literals.add(literalsVal);

                    }else if("ASSIGNED".equals(obj.getPkStrategy())){
                        columns.add(columnName);
                        columnVals.add(valObj);
                    }else if("NATIVE".equals(obj.getPkStrategy()) && "com.mysql.jdbc.Driver".equals(properties.get("jdbc.driverClassName"))){
                        System.out.println("-------IDENTITY-------");
                    }else if("GUID".equals(obj.getPkStrategy())){
                        columns.add(columnName);
                        columnVals.add(UUID.randomUUID().toString().replaceAll("\\-",""));
                    }else{
                        columns.add(columnName);
                        columnVals.add(UUID.randomUUID().toString().replaceAll("\\-",""));
                    }

                }else if (valObj == null) {
                    continue;
                }else{
                    columns.add(columnName);
                    columnVals.add(valObj);
                }

            }

            for(int i=0;i<columns.size();i++){
                if(i==0){
                    columnStr+=columns.get(i);
                    if(columnVals.get(i) instanceof ColumnLiterals){
                        columnValStr+=((ColumnLiterals) columnVals.get(i)).string();
                    }else {
                        columnValStr+="?";
                    }
                }else{
                    columnStr+=","+columns.get(i);
                    if(columnVals.get(i) instanceof ColumnLiterals){
                        columnValStr+=','+((ColumnLiterals) columnVals.get(i)).string();

                    }else{
                        columnValStr+=",?";
                    }
                }
            }
            for(ColumnLiterals c:literals){
                columnVals.remove(c);
            }
            sql=String.format(sql,tableName,columnStr,columnValStr);
            Object[] result={sql,columnVals};
            return result;
//        }
//        return null;
    }
    static class ColumnLiterals{
        private boolean isVal;
        private String val;

        public ColumnLiterals(String val) {
            this(false,val);

        }

        public ColumnLiterals(boolean isVal, String val) {
            this.isVal = isVal;
            this.val = val;
        }

        public boolean isVal() {
            return isVal;
        }

        public void setVal(boolean val) {
            isVal = val;
        }

        public String getVal() {
            return val;
        }

        public void setVal(String val) {
            this.val = val;
        }
        public String string(){
            if(isVal)
                return val;
            return '\''+val+'\'';
        }
    }
    public static Object [] querySql(TableMeta obj, Map map){
        map.remove("unique___Name");

        String sql="select * from %s";
        String sqlWhere=" where %s";
        String tableName=obj.getTableName();
        sql=String.format(sql,tableName);
        List<Object> columnVals=new ArrayList<>();


        if(map!=null&&map.size()>0){
            if("oracle.jdbc.driver.OracleDriver".equals(properties.get("jdbc.driverClassName"))){
                List<ColumnMeta> columnMetaList=obj.getColumnMetaList();
                List<String> columns=new ArrayList<>(columnMetaList.size());
                for (ColumnMeta columnMeta : columnMetaList) {
                    String fieldName = columnMeta.getFieldName();
                    String columnName = columnMeta.getColumnName();
                    if("Y".equals(columnMeta.getIsSearch())){
                        if("rangesearch".equals(columnMeta.getSearchType())){
                            Object sval=map.get(fieldName+"__start");
                            Object svalObj=typeReverse(columnMeta, sval);
                            if (svalObj != null) {
                                columns.add(columnName+">");
                                columnVals.add(svalObj);
                            }

                            Object eval=map.get(fieldName+"__start");
                            Object evalObj=typeReverse(columnMeta, eval);
                            if (evalObj != null) {
                                columns.add(columnName+"<");
                                columnVals.add(evalObj);
                            }

                        }else{
                            Object val = map.get(fieldName);
                            Object valObj = typeReverse(columnMeta, val);

                            if (valObj == null) {
                                continue;
                            }
                            columns.add(columnName);
                            columnVals.add(valObj);
                        }

                    }else
                        continue;
                }
                String columnStr="";
                for(int i=0;i<columns.size();i++){
                    if(i==0){
                        columnStr+=columns.get(i)+"=? ";
                    }else{
                        columnStr+=" and "+columns.get(i)+"=?";
                    }
                }
                if(columnStr.trim().length()>0)
                sql+=String.format(sqlWhere,columnStr);


            }
        }
        Object[] result={sql,columnVals};
        return result;


    }

    public static Object typeReverse(ColumnMeta columnMeta,Object val){
        String strVal=String.valueOf(val);

        String javaType=columnMeta.getJavaType();
        if(val==null||strVal.trim().equals("")){
            return null;
        }
        if("String".equals(javaType)){
            return strVal;
        }

        if("Date".equals(javaType)){
            try {
                return new SimpleDateFormat(columnMeta.getDateInPattern()).parse(strVal);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if("Integer".equals(javaType)){
            return Integer.valueOf(strVal);
        }
        if("Decimal".equals(javaType)){
            return new BigDecimal(strVal);
        }
        if("Long".equals(javaType)){
            return Long.valueOf(strVal);
        }
        return null;
    }
    public static String typeFormat(ColumnMeta columnMeta,Object val){
        if(val==null)
            return null;
        String strVal=String.valueOf(val);
        String javaType=columnMeta.getJavaType();
        if("String".equals(javaType)){
            return strVal;
        }
        if("Date".equals(javaType)){
            return new SimpleDateFormat(columnMeta.getDateOutPattern()).format(val);
        }
        if("Decimal".equals(javaType)){
            return String.valueOf(val);
        }
        if("Integer".equals(javaType)){
            return String.valueOf(val);
        }
        if("Long".equals(javaType)){
            return String.valueOf(val);
        }
        return null;
    }

    public static List<Map> convertMap(List<Map> rawList, TableMeta mapping) {
        if("1".equals(mapping.getTreeConfigEnable()))
        mapping.initTreeConfig();
        Map<String,ColumnMeta> rawMapping=new HashMap<>();
        for(ColumnMeta col:mapping.getColumnMetaList()){
            rawMapping.put(col.getColumnName().toUpperCase(),col);
        }

        List<Map> processedList=new ArrayList<>();
        for(Map<String,Object> m:rawList){
            Map<String,Object> pMapElement=new HashMap();
            for(Map.Entry<String,Object> en:m.entrySet()){
                String key=en.getKey().toUpperCase();
                Object val=en.getValue();
                ColumnMeta meta=rawMapping.get(key);
                if(meta!=null)
                pMapElement.put(meta.getFieldName(),typeFormat(meta,val));
            }
            if("1".equals(mapping.getTreeConfigEnable())){
                if(mapping.getIdColumn()!=null){
                    pMapElement.put("ID",String.valueOf(pMapElement.get(mapping.getIdColumn().getFieldName())));
                }
                if(mapping.getPidColumn()!=null&&pMapElement.get(mapping.getPidColumn().getFieldName())!=null){
                    pMapElement.put("PID",String.valueOf(pMapElement.get(mapping.getPidColumn().getFieldName())));
                }else{
                    pMapElement.put("PID",mapping.getRootNodeValue()==null?"0":mapping.getRootNodeValue());

                }
                if(mapping.getNameColumn()!=null){
                    pMapElement.put("NAME",String.valueOf(pMapElement.get(mapping.getNameColumn().getFieldName())));
                }else{
                    pMapElement.put("NAME","空文本");

                }
                if(mapping.getIsleafColumn()!=null){
                    pMapElement.put("ISLEAF",String.valueOf(pMapElement.get(mapping.getIsleafColumn().getFieldName())));
                }
            }
            processedList.add(pMapElement);
        }
        return processedList;

    }

    /**
     * CREATE TABLE aaaaa
     * (
     *     id int PRIMARY KEY,
     *     text varchar2(50)
     * );
     * CREATE UNIQUE INDEX aaaaa_text_uindex ON aaaaa (text);
     * @param obj
     * @return
     */

    public static String createTableSql(TableMeta obj){
        String sql="create table %s(" +//tableName
                "%s" +//columns
                ")" +
                "%s";//index,constraint

        return null;

    }
}
