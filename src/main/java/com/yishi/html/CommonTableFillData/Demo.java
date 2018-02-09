package com.yishi.html.CommonTableFillData;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.xpath.internal.WhitespaceStrippingElementMatcher;

import java.io.*;
import java.util.Map;
import java.util.concurrent.TransferQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Demo {
    public static String parseJson(Map map) {

        return null;
    }

    public static void main(String[] args) {
        String regex="(\\<a\\s* class=[\"|']map[\"|'].*?\\>.*?\\</a\\>)";
        String str="<a class='map'  >class1</a><a class='map'  >class2</a><a></a><a class='map'></a>";//
        System.out.println(str.matches(regex));
        Pattern pattern=Pattern.compile(regex);
        Matcher matcher=pattern.matcher(str);
        while (matcher.find()&&!matcher.hitEnd()){
            System.out.println(matcher.group());

        }


//        String json = " [\n" +
//                "  \t{\"value\":\"one\", \"headerText\":\"one\", \"src\":\"samples/sample_data/images/i-icon-1.png\"},\n" +
//                "\t\t{\"value\":\"two\", \"headerText\":\"two\", \"src\":\"samples/sample_data/images/i-icon-2.png\"},\n" +
//                "\t\t{\"value\":\"three\", \"headerText\":\"three\", \"src\":\"samples/sample_data/images/i-icon-3.png\"},\n" +
//                "\t\t{\"value\":\"four\", \"headerText\":\"four\", \"src\":\"samples/sample_data/images/i-icon-4.png\"},\n" +
//                "\t\t{\"value\":\"five\", \"headerText\":\"five\", \"src\":\"samples/sample_data/images/i-icon-5.png\"},\n" +
//                "\t\t{\"value\":\"six\", \"headerText\":\"six\", \"src\":\"samples/sample_data/images/i-icon-6.png\"}\n" +
//                "  ]";
//        String json1="{'id':'1','list':[{'a':'1','b':'2','c':[{'a':1}]},{'a':3,'b':'4'}],'child':{'name':'tom','age':'12'}}";
//        Object jsonObj = JSON.parse(json1);
//        JsonKey root = null;
//        if (jsonObj instanceof JSONArray) {
//            root = parseJsonMap((JSONObject) ((JSONArray) jsonObj).get(0), "[${index}]",true);
//
//        } else if (jsonObj instanceof JSONObject) {
//            root = parseJsonMap((JSONObject) jsonObj, "");
//        }
//
//        root.execute(Demo::print);
    }

    public static void print(JsonKey jsonKey){
        String path=jsonKey.getPath();
        System.out.printf("<a class='map' href='#'>%s</a><br>%n",path);

    }

    public static JsonKey parseJsonMap(JSONObject objMap, String rootName,boolean isArrayElement) {
        Map<String, Object> map = objMap.getInnerMap();
        JsonKey root;
        if(isArrayElement){
            root=new ArrayContainerJsonKey(String.valueOf(rootName));
        }
        else root = new ComplexJsonKey(rootName);
        for (Map.Entry en : map.entrySet()) {
            Object obj = en.getValue();
            Object key = en.getKey();
            if (obj instanceof JSONObject) {
                JsonKey sub;

                 sub = new ComplexJsonKey(String.valueOf(key));
                root.add(sub);
                JSONObject sobj = ((JSONObject) obj);
                JsonKey inkey = parseJsonMap(sobj, "");
                for (JsonKey jKey : inkey.getChild()) {
                    sub.add(jKey);
                }
            } else if (en.getValue() instanceof JSONArray) {
                JsonKey sub ;

                sub= new ComplexJsonKey(String.valueOf(key));
                root.add(sub);
                if (((JSONArray) obj).get(0) != null) {
                    sub.add(parseJsonMap((JSONObject) ((JSONArray) obj).get(0), "[${index}]",true));
                }

            } else {
                JsonKey sub;

                 sub = new SimpleJsonKey(String.valueOf(key));
                root.add(sub);
            }

        }
        return root;
    }
    public static JsonKey parseJsonMap(JSONObject objMap, String rootName){
        return parseJsonMap(objMap,rootName,false);
    }
    public void generateJs(File file) throws IOException {
        BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(file),"gbk"));
        StringBuffer stringBuffer=new StringBuffer("");
        String line=null;
        StringBuffer function=new StringBuffer("function render(data){%s}");

        while((line=br.readLine())!=null){
            stringBuffer.append(line);
        }
        Pattern pattern=Pattern.compile("\\<a.* class=[\"|']map[\"|']\\>\\</a\\>?");


    }
}
