package com.yishi.code.general.dto;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class SchemaMeta {
    private String schemaName;

    public String getSchemaName() {
        return schemaName;
    }

    public List<TableMeta> getTables() {
        return tables;
    }

    public void setTables(List<TableMeta> tables) {
        this.tables = tables;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }
    public SchemaMeta(String schemaName){
        this.schemaName=schemaName;
    }
    private List<TableMeta> tables;
    /***
     * @method  SchemaMetaMeta
     * @authkedou   kedou
     * @version
     * @see * @param dbmd
 * @param catalog
 * @param schemaPattern
 * @param tableNamePattern
 * @param typesn
     * @param types
     * @return
     * @exception
     * @date
     */
    public SchemaMeta(DatabaseMetaData dbmd, String catalog, String schemaPattern, String tableNamePattern,String[] types){
        this.tables=new LinkedList<>();
        this.schemaName=schemaPattern;

        try(
            //获取当前连接中，指定表空间下的表
            ResultSet rs=dbmd.getTables(catalog,schemaPattern,tableNamePattern,types)) {
            while(rs.next()){
                String tableName=rs.getString("TABLE_NAME");
                String tableType=rs.getString("TABLE_TYPE");
                String remarks=rs.getString("REMARKS");
                String tableSchem=rs.getString("TABLE_SCHEM");
                TableMeta tableSimpleMeta=new TableMeta(tableSchem,tableName,remarks,tableType);
                this.tables.add(tableSimpleMeta);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "SchemaMeta{" +
                "schemaName='" + schemaName + '\'' +
                ", tables=" + tables +
                '}';
    }
}
