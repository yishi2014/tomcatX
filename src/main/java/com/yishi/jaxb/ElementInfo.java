package com.yishi.jaxb;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ElementInfo{


    private String name;
    private String path;
    private int type;//0唯一节点，1集合节点
    private Set<String> attributes;
    private boolean isRootElement;
    private boolean hasVal;
    private Set<ElementInfo> children;


    public ElementInfo(String name, String path, int type, boolean isRootElement, boolean hasVal) {
        this.name = name;
        this.path = path;
        this.type = type;
        this.attributes = new HashSet<>();
        this.isRootElement = isRootElement;
        this.hasVal = hasVal;
        this.children =  new HashSet<>();
    }
    public ElementInfo(boolean isRootElement,String path) {
        this(null,path, 0,false,false);
        this.name=path.replaceAll(".+/([^/]+)/$","$1");
    }
    public ElementInfo(boolean isRootElement, String path, String name) {
        this(name,path,0,isRootElement,false);
    }
    public ElementInfo(String name, String path, int type, boolean isRootElement) {
       this(name,path,type,isRootElement,false);
    }

    public void addChild(ElementInfo child) {
        if (this.children == null)
            this.children = new HashSet<>();
        this.children.add(child);
    }
    public void addChildren(List<ElementInfo> list){
        this.children.addAll(list);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public boolean isHasVal() {
        return hasVal;
    }

    public void setHasVal(boolean hasVal) {
        this.hasVal = hasVal;
    }

    public boolean isRootElement() {
        return isRootElement;
    }

    public void setRootElement(boolean rootElement) {
        isRootElement = rootElement;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Set<String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<String> attributes) {
        this.attributes = attributes;
    }

    public Set<ElementInfo> getChildren() {
        return children;
    }

    public void setChildren(Set<ElementInfo> children) {
        this.children = children;
    }


    @Override
    public String toString() {
        return "ElementInfo{" +
                "  type=" + type +
                ", path='" + path + '\'' +
                ", isRootElement=" + isRootElement +
                ", hasVal=" + hasVal +
                ", name='" + name + '\'' +
                ", attributes=" + attributes +
                ", children=" + children +
                '}';
    }
}
