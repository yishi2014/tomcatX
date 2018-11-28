package com.yishi.jaxb.util;

import com.yishi.jaxb.ElementInfo;
import com.yishi.regex.RegexUtil;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.xml.bind.annotation.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.*;

public class JaxbBeanUtil_ {

    public static void main(String[] args) throws Exception {
//        Map<String,ElementInfo> map=new HashMap<>();
//        Document doc = getDoc("C:\\IDEA\\budgetMaven\\src\\test\\resources\\entityMapping.xml");
//        collect(map,"/",doc.getRootElement());
//        map.forEach((a,b)-> System.out.println(b));

        String pkg = "test.parts.jaxb.special.xwork";
        String path = "C:\\Users\\YiShi\\eclipse-workspace\\test\\src\\";
        String encoding="utf-8";
        String[] entityPath = {
                "C:\\IDEA\\budget_st\\JavaSource\\www\\item-xwork.xml",
                "C:\\IDEA\\budget_st\\JavaSource\\www\\itemPer-xwork.xml",
                "C:\\IDEA\\budget_st\\JavaSource\\www\\itemPerGoal-xwork.xml",
                "C:\\IDEA\\budget_st\\JavaSource\\www\\performance-xwork.xml",
                "C:\\IDEA\\budget_st\\JavaSource\\www\\person-xwork.xml",
                "C:\\IDEA\\budget_st\\JavaSource\\www\\report-xwork.xml",
                "C:\\IDEA\\budget_st\\JavaSource\\www\\ysbz-xwork.xml"
        };
        Map map = batchCollect(entityPath, new HashMap());
//        List<ElementInfo> list = new ArrayList(Arrays.asList(map.values().toArray()));
//        Collections.sort(list, new Comparator<ElementInfo>() {
//            @Override
//            public int compare(ElementInfo o1, ElementInfo o2) {
//                return String.CASE_INSENSITIVE_ORDER.compare(o1.getPath(), o2.getPath());
//            }
//        });
//        list.forEach((b) -> System.out.println(b));
        ElementInfo rootInfo = combine(map);
//        System.out.println(rootInfo);
        render(rootInfo,pkg,path,encoding);
    }

    public static Map<String, ElementInfo> collect(Map<String, ElementInfo> eleMap, String path, Element element) {
        if (eleMap == null) {
            eleMap = new HashMap<>();
        }
        if (path == null) {
            path = "/";
        }
        if (element == null) {
            return eleMap;
        }
        String eleName = element.getName();
        String curPath = path + element.getName() + "/";
        ElementInfo elementInfo = eleMap.get(curPath);
        if (elementInfo == null) {
            elementInfo = new ElementInfo(false, curPath, eleName);
            eleMap.put(curPath, elementInfo);
        }
        if (element.isRootElement()) {
            elementInfo.setRootElement(true);
        }
        for (Object o : element.attributes()) {
            Attribute attribute = ((Attribute) o);
            elementInfo.getAttributes().add(attribute.getName());
        }
        List<Element> children = element.elements();
        if (children.size() == 0) {
            if (!element.getTextTrim().equals("")) {
                elementInfo.setHasVal(true);
            }
        } else {
            Map<String, Integer> childCountMap = countChild(children);
            for (Map.Entry<String, Integer> en : childCountMap.entrySet()) {
                String childPath = curPath + en.getKey() + "/";
                ElementInfo child = eleMap.get(childPath);
                int type = en.getValue() > 1 ? 1 : 0;
                if (child == null) {
                    eleMap.put(childPath, new ElementInfo(en.getKey(), childPath, type, false));
                } else {
                    child.setType(type);
                }
            }
            for (Element ele : children) {
                collect(eleMap, curPath, ele);
            }
        }

        return eleMap;
    }

    public static Map<String, Integer> countChild(List<Element> children) {
        Map<String, Integer> childCountMap = new HashMap();
        for (Element ele : children) {
            String childName = ele.getName();
            if (childCountMap.get(childName) != null) {
                childCountMap.put(childName, childCountMap.get(childName) + 1);
            } else {
                childCountMap.put(childName, 1);
            }
        }
        return childCountMap;
    }

    public static Map<String, ElementInfo> batchCollect(String[] path, Map map) throws Exception {
        for (String str : path) {
            File dir = new File(str);
            if(dir.isFile()){
                if (dir.getName().matches(".+\\.xml$")) {
                    collect(map, "/", getDoc(dir).getRootElement());
                }
            }else{
                for (File f : dir.listFiles()) {
                    if (f.getName().matches(".+\\.xml$")) {
                        collect(map, "/", getDoc(f).getRootElement());
                    }
                }
            }

        }
        return map;
    }

    public static Document getDoc(String str) throws Exception {
        File file = new File(str);
        SAXReader reader = new SAXReader();
        reader.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        Document document = reader.read(file);
        return document;
    }

    public static Document getDoc(File file) throws Exception {
        SAXReader reader = new SAXReader();
        reader.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        Document document = reader.read(file);
        return document;
    }

    public static ElementInfo combine(Map<String, ElementInfo> map) {
        Set<String> rootSet = new HashSet<>();
        map.forEach((key, val) -> {
            rootSet.add(key.replaceAll("^(/[^/]+/).*$", "$1"));
        });
        if (rootSet.size() > 1) {
            throw new RuntimeException("根节点多于一个");
        }
        ElementInfo rootInfo = map.get(new ArrayList<>(rootSet).get(0));
        int i = 1;
        for (Map.Entry<String, ElementInfo> en : map.entrySet()) {
            en.getValue().addChildren(getChildren(en.getValue().getPath(), map));
        }
        return rootInfo;
    }

    private static List<ElementInfo> getChildren(String path, Map<String, ElementInfo> map) {
        List<ElementInfo> list = new ArrayList();
//        String nextpath=String.format("%0"+i+"d",0).replaceAll("0","[^/]+/");
        map.forEach((key, val) -> {
            if (key.matches(path + "[^/]+/")) {
                list.add(val);
            }
        });
        return list;
    }
    static Class xmlAccessType= XmlAccessType.class;
    static Class xmlAccessorType= XmlAccessorType.class;
    static Class xmlElement= XmlElement.class;
    static Class xmlRootElement= XmlRootElement.class;
    static Class xmlvalue= XmlValue.class;
    static Class listClass=List.class;
    static Class xmlAttribute=XmlAttribute.class;
    public static void render(ElementInfo info,String pkg,String path,String encoding){
        String filerootPath = path + com.yishi.regex.RegexUtil.formateduplicateChar(pkg, ".", "/") + "/";

        StringBuffer importBuffer=new StringBuffer();
        StringBuffer classAnnotation=new StringBuffer();
        StringBuffer fieldBuffer=new StringBuffer();
        StringBuffer methodBuffer=new StringBuffer();
        String name=getClassName(info.getPath());
        Set<Class> importSet=new HashSet<>();


        if (info.isRootElement())
        {
            classAnnotation.append(String.format("@XmlRootElement(name=\"%s\")\n",info.getName()));
            importSet.add(xmlRootElement);
        }
        importSet.add(xmlAccessType);
        importSet.add(xmlAccessorType);

        classAnnotation.append("@XmlAccessorType(XmlAccessType.FIELD)\n");
        if(info.isHasVal()){
            fieldBuffer.append("    @XmlValue\n");
            fieldBuffer.append("    private String val;\n\n");

            methodBuffer.append(getter("val","String"));
            methodBuffer.append(setter("val","String"));

            importSet.add(xmlvalue);
        }
        Set<String> eAttrs = info.getAttributes();
        if(info.getAttributes().size()>0) importSet.add(xmlAttribute);
        for (String attr : eAttrs) {
            String fieldName=getFieldName(attr);
            fieldBuffer.append("    @XmlAttribute(name = \"" + attr + "\")\n");
            fieldBuffer.append("    private String " + fieldName + ";\n\n");

            methodBuffer.append(getter(fieldName,"String"));
            methodBuffer.append(setter(fieldName,"String"));
        }
        if(info.getChildren().size()>0) importSet.add(xmlElement);
        for(ElementInfo child:info.getChildren()){
            String fieldType=getFieldType(child,importSet);
            String fieldName=getFieldName(child.getPath(),fieldType);
            fieldBuffer.append(String.format("    @XmlElement(name = \"%s\")\n",child.getName()));
            fieldBuffer.append(String.format("    private %s %s;\n\n",fieldType,fieldName));

            methodBuffer.append(getter(fieldName,fieldType));
            methodBuffer.append(setter(fieldName,fieldType));
            render(child,pkg,path,encoding);
        }
        String classStr="package %s;\n\n" +//package
                "%s\n"+//import
                "%s"+//class Annotation
                "public class %s{\n\n" +//class name
                "%s\n"+//field
                "%s\n"+//method
                "}";
        for(Class clazz:importSet){
            importBuffer.append(String.format("import %s;\n",clazz.getName()));
        }
        String classContent=String.format(classStr,pkg,importBuffer.toString(),classAnnotation.toString(),name,fieldBuffer.toString(),methodBuffer.toString());
        System.out.println(classContent);
        writeFile(classContent,filerootPath+name + ".java",encoding);
        System.out.println("--------------------------------------------");
    }

    private static String getFieldType(ElementInfo child,Set set) {

        String type1=null;
        String type2="";
        if(child.getType()==1){
            type1="List";
            if(set!=null)
                set.add(listClass);
        }
        if(child.getAttributes().size()==0&&child.getChildren().size()==0&&!child.isHasVal()){
            type2="String";
        }else{
            type2=getClassName(child.getPath());
        }

        if(type1==null)
            return type2;
        return String.format("%s<%s>",type1,type2);
    }

    public static String getFieldName(String name){
        String name_= RegexUtil.toCamel1Low(name.replaceAll("-","_").replaceAll("/","_").toLowerCase());
        return filterKeyWord(name_);

    }
    public static String getFieldName(String name,String type){
        if(type.startsWith("List")){
            String name_=RegexUtil.toCamel1Low(name.replaceAll("-","_").replaceAll(".+/([^/]+)/$", "$1"));
            return name_+"List";
        }else {
            String name_= RegexUtil.toCamel1Low(name.replaceAll("-","_").replaceAll("/","_").toLowerCase());
            return filterKeyWord(name_);
        }

    }

    private static String filterKeyWord(String name_) {
        List list=Arrays.asList(keywords);
        if(list.contains(name_))
            return name_+"_";
        return name_;
    }
    static String[] keywords={"class","package","public","private","protected","for","while","switch","case","new","extends","implement"};

    public static String getClassName(String path){
        String name= RegexUtil.toCamel(path.replaceAll("-","_").replaceAll("/","_").toLowerCase());
        return filterKeyWord(name);
    }

    public static void writeFile(String content,String path,String encoding){
        System.out.println(path);
        try(BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path),encoding))){
            bw.write(content);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static String getter(String str,String type){
        String getStr="    public %s get%s(){\n      return this.%s;\n    }\n\n";

        return String.format(getStr,type,RegexUtil.firstToLower(str),str);
    }
    public static String setter(String str,String type){
        String setStr="    public void set%1$s(%3$s %2$s){\n      this.%2$s=%2$s;\n    }\n\n";

        return String.format(setStr,RegexUtil.firstToLower(str),str,type);

    }
}
