package com.yishi.code.general.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtil {
    private static Connection conn = null;

    public static Connection getConnection(String driverClassName,String url,String username,String password) {
        try {
            Class.forName(driverClassName);
            Properties props =new Properties();
            //设置remarks，可以读取表注释
            if(driverClassName.contains("oracle")){
                props.put("remarksReporting","true");
            }else if(driverClassName.contains("mysql")){
                props.put("remarks","true");
                props.put("useInformationSchema","true");
            }
            props.put("user", username);
            props.put("password", password);
            conn =DriverManager.getConnection(url,props);
//            conn = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
