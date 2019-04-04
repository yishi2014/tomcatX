package com.yishi.code.general.basedata.construct;


import com.yishi.code.general.basedata.PropReader;
import com.yishi.code.general.dto.ColumnMeta;

import java.util.Properties;

public class JDBCTypeParser {
    private static final String FILE_NAME="data_type_mapping.properties";
    private static final String DEFAULT_TYPE="String";
    private static final String DEFAULT_CLASS_STR="java.lang.String";
    private static final String DECIMAL="NUMBER(,1)";
    private static final String INTEGER="NUMBER(9)";
    private static final String LONG="NUMBER(10)";
    private static final String TIMESTAMP="TIMESTAMP";


    private static Properties prop= PropReader.load(FILE_NAME);

    public static String getJavaType(String databaseType, ColumnMeta meta){
        String type;
        if(meta.getColunmType().equalsIgnoreCase("NUMBER")){
            if(meta.getDecimalDigits()>0){
                type=DECIMAL;
            }else {
                if(meta.getColumnSize()<=9){
                    type=INTEGER;

                }else{
                    type=LONG;
                }
            }
        }else if(meta.getColunmType().startsWith("TIMESTAMP")){
            type=TIMESTAMP;
            //String timeDigitsStr=meta.getColunmType().replaceAll("(TIMESTAMP\\()(\\d+)(\\))","$2");
            //meta.setTimeDigits(Integer.valueOf(timeDigitsStr));
        } else{
            type=meta.getColunmType();
        }
        return prop.getProperty(databaseType+'.'+type,DEFAULT_TYPE);
    }
    public static String getPageControlType(String javaType){
        return prop.getProperty("PAGE."+javaType);
    }

    public static Class getJavaTypeClass(String javaType) {

        try {
            return Class.forName(prop.getProperty(javaType,DEFAULT_CLASS_STR));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return String.class;
        }
    }


}
