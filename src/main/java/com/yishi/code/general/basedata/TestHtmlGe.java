package com.yishi.code.general.basedata;//package com.datanew.code.general.basedata;
//
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.io.*;
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//import java.lang.reflect.Modifier;
//import java.lang.reflect.Parameter;
//import java.util.Properties;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
///**
// * Created by Lustin on 2017/11/24.
// */
//public class TestHtmlGe {
//    String htmlPath=new File("").getAbsolutePath();
//    private static String liStr = "<li><span class=\"spantitle\" >%s</span><input style=\"width: 550px;\" value=\"%s\"><button class=\"req\">请求</button></li>\n";
//    public static String src = "\\server-admin\\src\\main\\java";
//
//    public static void createHtml(String packageName) throws ClassNotFoundException, UnsupportedEncodingException {
//        String packageReplaceRegex = "\\.|^(?!\\.)|(?<!\\.)$";
//        String path = (new File("").getAbsolutePath() + src + packageName.replaceAll(packageReplaceRegex, "\\\\"));
//        File dir = new File(path);
//        StringBuffer stringBuffer=new StringBuffer();
//        for (File file : dir.listFiles()) {
//            Class clazz = Class.forName(packageName.replaceAll("^\\.|\\.$", "") + "." + file.getName().replaceAll("\\.java", ""));
//            if (clazz.isAnnotationPresent(RequestMapping.class)) {
//                RequestMapping annotation = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
//                String path1 = annotation.value()[0];
//                Method[] methods = clazz.getDeclaredMethods();
//                for (Method method : methods) {
//                    if (method.isAnnotationPresent(RequestMapping.class)) {
//                        RequestMapping methodAnnotation = (RequestMapping) method.getAnnotation(RequestMapping.class);
//                        String path2 = path1 + methodAnnotation.value()[0];
//                        Parameter[] parameters = method.getParameters();
//                        String url = path2 + getQueryStr(parameters);
//                        String li = assemble(url);
//                        stringBuffer.append(li);
//                        System.out.println(li);
//                        //
//
//
//                    }
//                }
//            }
//
//        }
//        try {
//            replaceFile(stringBuffer.toString());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    private static String assemble(String url) throws UnsupportedEncodingException {
//        Properties prop = PropReader.load("method.properties");
//        String regex = "^/(?<l1>\\w+)/(?<l2>\\w+)";
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(url);
//        matcher.find();
//        String l1 = matcher.group("l1");
//        String l2 = matcher.group("l2");
//        String l1Name = prop.getProperty(l1);
//        String l2Name = prop.getProperty(l2);
//
//        String name = new String((l1Name == null ? l1 : l1Name).getBytes("iso8859-1"), "gbk") + new String((l2Name == null ? l2 : l2Name).getBytes("iso8859-1"), "gbk");
//        return String.format(liStr, name, url);
//
//    }
//
//    public static void main(String[] args) throws IOException {
//        String packageName = "com.datanew.serveradmin.controller";
//        try {
//            createHtml(packageName);
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//    }
//    public static void  replaceFile(String content) throws IOException {
//        File file=new File(new File("").getAbsolutePath()+"\\server-admin\\src\\test\\resources\\interface.html");
//       try( BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));){
//           StringBuffer stringBuffer=new StringBuffer();
//           String line=null;
//           while((line=br.readLine())!=null){
//               stringBuffer.append(line).append("\n");
//           }
//           String newContent=stringBuffer.toString().replaceFirst("(?<=\\<\\/li\\>)[\\w|\\W]*?(?=\\<\\/ul\\>)",content);
//           System.out.println(newContent);
//           try(BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"utf-8"))){
//                bw.write(newContent);
//           }
//       }
//
//    }
//
//    public static String getQueryStr(Parameter[] parameters) {
//        StringBuffer stringBuffer = new StringBuffer("?");
//        if (parameters.length > 0) {
//            for (Parameter p : parameters) {
//                Class para = p.getType();
//                if (para.getName().startsWith("com.datanew.")) {
//                    Field[] fields = para.getDeclaredFields();
//                    for (Field field : fields) {
//                        if (!Modifier.isStatic(field.getModifiers())&&!field.getName().matches("(?i)guid|id"))
//                            stringBuffer.append(String.format("%s=&", field.getName()));
//                    }
//
//                } else {
//                    stringBuffer.append(String.format("%s=&", p.getName()));
//                }
//            }
//        }
//
//        return stringBuffer.subSequence(0, stringBuffer.length() - 1).toString();
//    }
//}
