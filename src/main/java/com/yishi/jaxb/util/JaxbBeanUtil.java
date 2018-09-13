package com.yishi.jaxb.util;

import com.yishi.regex.RegexUtil;
import org.dom4j.*;

import java.io.*;
import java.util.*;

public class JaxbBeanUtil {
    private static String pkg = "com.yishi.jaxb.special.project.config";
    private static String path = "/home/yishi/IDEA/budgetX/src/test/";
    private static String filerootPath = path + com.yishi.regex.RegexUtil.formateduplicateChar(pkg, ".", "/")+"/";

    public static void main(String[] args) throws DocumentException, IOException {
        Document doc = DocumentHelper.parseText(xml);
        Element root = doc.getRootElement();
//        System.out.println(root.asXML());
        String name = "ProjectConfig";
        parse(root, name);


    }

    public static void parse(Element element, String name) throws IOException {


        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("package " + pkg + ";\n");
        stringBuilder.append("import javax.xml.bind.annotation.XmlAccessType;\n" +
                "import javax.xml.bind.annotation.XmlAccessorType;\n" +
                "import javax.xml.bind.annotation.XmlElement;\n" +
                "import javax.xml.bind.annotation.XmlRootElement;\n");
        if (element.isRootElement())
            stringBuilder.append("@XmlRootElement(name=\"" + element.getName() + "\")").append("\n");
        stringBuilder.append("@XmlAccessorType(XmlAccessType.FIELD)").append("\n");
        stringBuilder.append("public class " + name + "{").append("\n");
        List<Attribute> eAttrs = element.attributes();
        for (Attribute a : eAttrs) {
            stringBuilder.append("    @XmlAttribute(name = \"" + a.getName() + "\")\n");
            stringBuilder.append("    private String " + getField(a.getName()) + ";\n");
        }
        List<Element> elements = element.elements();
        if (elements.size() > 0) {
            Map<String, ProcessObj> countMap = count(elements);
            for (Element e : elements) {
                String elementName = e.getName();
                ProcessObj pobj = countMap.get(elementName);
                if (pobj.isProcessed()) continue;
                stringBuilder.append("    @XmlElement(name=\"" + elementName + "\")").append("\n");
                String typeName = name + getType(elementName);
                stringBuilder.append(typeParse(pobj, typeName));
                if (!pobj.isProcessed() && (pobj.isHasChildren() || pobj.getAttrs().size() > 0)) {
                    parse(e, typeName);
                }
                pobj.setProcessed(true);
            }
        } else {
            stringBuilder.append("    @XmlValue\n");
            stringBuilder.append("    private String val;\n");
        }


        stringBuilder.append("}");
        System.out.println("--------------------------------------------------");
        try (
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(filerootPath + name+".java")), "utf-8"))
        ) {
            bw.write(stringBuilder.toString());
        }
        System.out.println(stringBuilder.toString());
    }

    public static String typeParse(ProcessObj pobj, String typeName) {
        String elementName = getField(pobj.getName());
        if (pobj.getCount() == 1 && pobj.getAttrs().size() == 0) {
            if (pobj.isHasChildren()) {
                return "    private " + typeName + " " + RegexUtil.firstToLower(typeName) + ";\n";
            } else {

                return "    private String " + elementName + ";\n";
            }

        } else if (pobj.getCount() == 1) {
            return "    private " + typeName + " " + RegexUtil.firstToLower(typeName) + ";\n";
        } else if (pobj.getAttrs().size() == 0) {
            if (pobj.isHasChildren()) {
                return "    private List<" + typeName + "> " + elementName + "List;\n";
            } else {
                return "    private List<String> " + elementName + "List;\n";
            }
        } else {
            return "    private List<" + typeName + "> " + elementName + "List;\n";
        }
    }

    private static Map count(List<Element> elements) {
        Map<String, ProcessObj> map = new HashMap<>(elements.size());
        for (Element e : elements) {
            String name = e.getName();
            if (map.containsKey(name)) {
                ProcessObj obj = map.get(name);
                obj.add();

            } else {
                ProcessObj obj = new ProcessObj();
                obj.setCount(1);
                obj.setName(name);
                obj.setAttrs(new HashSet<>());
                obj.setHasChildren(e.elements().size() > 0);
                map.put(name, obj);

            }
            ProcessObj pobj = map.get(name);
            List<Attribute> attrs = e.attributes();
            for (Attribute attr : attrs) {
                pobj.addAttr(attr.getName());
            }
        }
        return map;
    }

    public static String getType(String eleName) {
        return RegexUtil.firstToLower(RegexUtil.toCamel1Low(eleName.toLowerCase()));
    }

    public static String getField(String eleName) {
        return RegexUtil.toCamel1Low(eleName.toLowerCase());
    }

    static class ProcessObj {
        private String name;
        private Integer count;
        private boolean processed;
        private Set<String> attrs;
        private boolean hasChildren;

        public boolean isHasChildren() {
            return hasChildren;
        }

        public void setHasChildren(boolean hasChildren) {
            this.hasChildren = hasChildren;
        }

        public void addAttr(String attr) {
            if (attrs == null)
                attrs = new HashSet<>();
            attrs.add(attr);
        }

        public Set<String> getAttrs() {
            return attrs;
        }

        public void setAttrs(Set<String> attrs) {
            this.attrs = attrs;
        }

        public void add() {
            count++;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public boolean isProcessed() {
            return processed;
        }

        public void setProcessed(boolean processed) {
            this.processed = processed;
        }
    }


    static String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<module type=\"JAVA_MODULE\" version=\"4\">\n" +
            "  <component name=\"NewModuleRootManager\" LANGUAGE_LEVEL=\"JDK_1_5\">\n" +
            "    <output url=\"file://$MODULE_DIR$/WebContent/WEB-INF/classes\" />\n" +
            "    <output-test url=\"file:///test/budget_st\" />\n" +
            "    <exclude-output />\n" +
            "    <content url=\"file://$MODULE_DIR$\">\n" +
            "      <sourceFolder url=\"file://$MODULE_DIR$/JavaSource/budgetManage/resources\" type=\"java-resource\" />\n" +
            "      <sourceFolder url=\"file://$MODULE_DIR$/JavaSource/src\" isTestSource=\"false\" />\n" +
            "      <sourceFolder url=\"file://$MODULE_DIR$/JavaSource/www\" isTestSource=\"false\" />\n" +
            "      <sourceFolder url=\"file://$MODULE_DIR$/JavaSource/budgetManage\" isTestSource=\"false\" />\n" +
            "      <sourceFolder url=\"file://$MODULE_DIR$/JavaSource/config\" type=\"java-resource\" />\n" +
            "      <sourceFolder url=\"file://$MODULE_DIR$/JavaSource/generated\" type=\"java-resource\" />\n" +
            "    </content>\n" +
            "    <orderEntry type=\"inheritedJdk\" />\n" +
            "    <orderEntry type=\"sourceFolder\" forTests=\"false\" />\n" +
            "    <orderEntry type=\"module-library\">\n" +
            "      <library>\n" +
            "        <CLASSES>\n" +
            "          <root url=\"file://$MODULE_DIR$/WebContent/WEB-INF/lib\" />\n" +
            "        </CLASSES>\n" +
            "        <JAVADOC />\n" +
            "        <SOURCES>\n" +
            "          <root url=\"file://$MODULE_DIR$/WebContent/WEB-INF/lib\" />\n" +
            "        </SOURCES>\n" +
            "        <jarDirectory url=\"file://$MODULE_DIR$/WebContent/WEB-INF/lib\" recursive=\"false\" />\n" +
            "        <jarDirectory url=\"file://$MODULE_DIR$/WebContent/WEB-INF/lib\" recursive=\"false\" type=\"SOURCES\" />\n" +
            "      </library>\n" +
            "    </orderEntry>\n" +
            "    <orderEntry type=\"module-library\">\n" +
            "      <library>\n" +
            "        <CLASSES>\n" +
            "          <root url=\"file://$MODULE_DIR$/WebContent/WEB-INF/lib/axis2\" />\n" +
            "        </CLASSES>\n" +
            "        <JAVADOC />\n" +
            "        <SOURCES />\n" +
            "        <jarDirectory url=\"file://$MODULE_DIR$/WebContent/WEB-INF/lib/axis2\" recursive=\"false\" />\n" +
            "      </library>\n" +
            "    </orderEntry>\n" +
            "    <orderEntry type=\"module-library\">\n" +
            "      <library>\n" +
            "        <CLASSES>\n" +
            "          <root url=\"file://$MODULE_DIR$/WebContent/WEB-INF/lib/jodconverter\" />\n" +
            "        </CLASSES>\n" +
            "        <JAVADOC />\n" +
            "        <SOURCES />\n" +
            "        <jarDirectory url=\"file://$MODULE_DIR$/WebContent/WEB-INF/lib/jodconverter\" recursive=\"false\" />\n" +
            "      </library>\n" +
            "    </orderEntry>\n" +
            "    <orderEntry type=\"module-library\">\n" +
            "      <library>\n" +
            "        <CLASSES>\n" +
            "          <root url=\"file://$MODULE_DIR$/WebContent/WEB-INF/lib/xdoclet-1.2\" />\n" +
            "        </CLASSES>\n" +
            "        <JAVADOC />\n" +
            "        <SOURCES />\n" +
            "        <jarDirectory url=\"file://$MODULE_DIR$/WebContent/WEB-INF/lib/xdoclet-1.2\" recursive=\"false\" />\n" +
            "      </library>\n" +
            "    </orderEntry>\n" +
            "  </component>\n" +
            "</module>";
}
