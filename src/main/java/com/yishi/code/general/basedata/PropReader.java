package com.yishi.code.general.basedata;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Lustin on 2017/11/23.
 */
public class PropReader {

    public static  Properties load(String path){
        Properties prop=new Properties();
        try {
            prop.load(PropReader.class.getClassLoader().getResourceAsStream(path));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }

}
