package com.yishi.code.general.dto;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class DataBaseMeta {
    private List<SchemaMeta> schemas;
    public DataBaseMeta(DatabaseMetaData dbmd, String catalog, String schemaPattern){
        if(catalog!=null)
        catalog=catalog.toUpperCase();
        if(schemaPattern!=null)
        schemaPattern=schemaPattern.toUpperCase();
        this.schemas=new LinkedList<>();
        try(ResultSet rs=dbmd.getSchemas(catalog,schemaPattern);) {
            while(rs.next()){
                String tableSchem=rs.getString("TABLE_SCHEM");
                SchemaMeta schemaMeta=new SchemaMeta(tableSchem);
                this.schemas.add(schemaMeta);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<SchemaMeta> getSchemas() {
        return schemas;
    }

    public void setSchemas(List<SchemaMeta> schemas) {
        this.schemas = schemas;
    }

    @Override
    public String toString() {
        return "DataBaseMeta{" +
                "schemas=" + schemas +
                '}';
    }
}
