package com.yishi.code.general.x;

import com.yishi.code.general.util.RegexUtil;

import java.util.*;

public class XImport implements XComponent  {
    private XPackage xPackage;

    public XPackage getxPackage() {
        return xPackage;
    }

    public void setxPackage(XPackage xPackage) {
        this.xPackage = xPackage;
    }

    private Set<XClass> classes;

    public Set<XClass> getClasses() {
        return classes;
    }

    public XImport(){
        this.classes=new HashSet<>();
    }
    public void setClasses(Set<XClass> classes) {
        this.classes = classes;
    }



    @Override
    public String render() {
        Set newSet=new HashSet();
        for(XClass j:classes){
            parseImport(newSet,j);
        }
        StringBuffer stringBuffer=new StringBuffer();
        StringBuffer stringBuffer1=new StringBuffer("\n");
        List<Comparable> classList=new ArrayList<>();
        classList.addAll(newSet);
        Collections.sort(classList);
        for(Object cl:classList){
            String  importStr=((XClass) cl).getQualifyName();
            String pkgNameStr=importStr.replaceAll("\\.[^\\.]+$","");
            if(!importStr.startsWith("java.lang")&&!(RegexUtil.largestPackage(importStr)).equals(xPackage.getPkgName())&&!importStr.matches("(boolean|int|long|char|short|byte|float|double)")){
                if(importStr.matches("(java|javax)\\..+")){
                    stringBuffer1.append("import ").append(importStr).append(";").append('\n');
                }else{
                    stringBuffer.append("import ").append(importStr).append(";").append('\n');
                }
            }

        }
        return stringBuffer.toString()+stringBuffer1.toString();

    }
    protected void parseImport(Set list,XClass jClass){
        list.add(jClass);
        if(jClass.getParameterizeTypes()!=null){
            for(XClass j:jClass.getParameterizeTypes()){
                parseImport(list, j);
            }
        }
    }
    @Override
    public String _getName() {
        return null;
    }

    @Override
    public String _getRemark() {
        return null;
    }
}
