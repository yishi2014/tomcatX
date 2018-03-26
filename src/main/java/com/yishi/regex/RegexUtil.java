package com.yishi.regex;

import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;

/**
 * @author yishi
 * @desc a regex util to process str
 */
public class RegexUtil {

    public static String formateDuplicateFileSeparator(String str,String dupChar){
        return formateduplicateChar(str,dupChar,dupChar);
    }
    public static String formateduplicateChar(String str,String oriChar,String newChar){
        return str.replaceAll("\\"+oriChar+"+","\\"+newChar);
    }
    public static String removeHeadSymbol(String str,String headIfHave){
        return str.replaceAll("^\\"+headIfHave,"");
    }
    public static String removeHeadSymbols(String str,String headsIfHave){
        return str.replaceAll("^\\"+headsIfHave+"+","");
    }
    public static String removeEndSymbol(String str,String endIfHave){
        return str.replaceAll("\\"+endIfHave+"$","");
    }
    public static String removeEndSymbols(String str,String endsIfHave){
        return str.replaceAll("\\"+endsIfHave+"+$","");
    }
    public static String formateDir(String str){
        return str.replaceAll("\\\\|/","\\"+File.separator);

    }
    public static void main(String[] args) throws IOException {
        System.out.println(addHeadSymbol("abc","a"));
        System.out.println(addEndSymbol("bca","a"));
        System.out.println(formateDir("/a\\b/c"));
        System.out.println(formateDir("\\a/b\\c"));

//        System.out.println(new ClassPathResource("/billTemplate/bootstrap.min.css").getFile().getAbsolutePath());
//        String baseUri=new ClassPathResource("billTemplate").getFile().getAbsolutePath();
//        System.out.println(baseUri);

    }
    public static String addHeadSymbol(String str,String headIfNotHave){
//        return str.replaceAll("(^)(?!"+headIfNotHave+")","\\"+headIfNotHave);
        if(str==null)
            return null;
        if(!str.startsWith(headIfNotHave))
            return headIfNotHave+str;
        return str;
    }
    public static String addEndSymbol(String str,String endIfNotHave){
//        return str.replaceAll("(?<!"+endIfNotHave+")($)","\\"+endIfNotHave);
        if(str==null)
            return null;
        if(!str.endsWith(endIfNotHave))
            return str+endIfNotHave;
        return str;
    }
}
