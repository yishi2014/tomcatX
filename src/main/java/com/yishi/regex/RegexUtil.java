package com.yishi.regex;

import java.io.File;
import java.io.IOException;

/**
 * Created by Lustin on 2017/11/21.
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
//        System.out.println(addHeadSymbol("abc","a"));
//        System.out.println(addEndSymbol("bca","a"));
//        System.out.println(formateDir("/a\\b/c"));
//        System.out.println(formateDir("\\a/b\\c"));
//        System.out.println(getLargestDir("/home/yishi/IDEA/code_generator/src/main/java/com/datanew/code/general/demo/controller/AuthorizeApplyController.java"));
//        System.out.println(new ClassPathResource("/billTemplate/bootstrap.min.css").getFile().getAbsolutePath());
//        String baseUri=new ClassPathResource("billTemplate").getFile().getAbsolutePath();
        System.out.println(toCamel1Low("get_"+"FILL_RULE_CODE"));
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
    public static String getLargestDir(String str){
        return str.replaceAll("[^/\\\\]+\\.[^/\\\\]+$","");
    }
    public static String toCamel(String str){
        str=str.toLowerCase();
        String[] strs=str.split("_");
        String returnStr="";
        for(String s:strs){
            char[] chars=s.toCharArray();
            if(chars.length>0)
            chars[0]^=32;
            returnStr+=new String(chars);
        }

        return new String(returnStr);

    }
    public static String toCamel1Low(String str){

        String str_=toCamel(str);
        char[]charArr=str_.toCharArray();
        charArr[0]^=32;
        return new String(charArr);

    }
    public static String getSimpleNameFromQualifyName(String qualifyName){
        return qualifyName.replaceAll("^(.+\\.)(.+)$","$2");
    }
    public static String firstToLower(String str){
        char[] chars=str.toCharArray();
        chars[0]^=32;
        return new String(chars);
    }

}
