package com.yishi.code.general.basedata.construct;


import java.util.*;

public class JObject {

    private JPackage jPackage;
    private JHead jHead;
    private List<JField> jFields;
    private List<JMethod> JMethods;
    private JEnd jEnd;

    public JEnd getjEnd() {
        return jEnd;
    }

    public void setjEnd(JEnd jEnd) {
        this.jEnd = jEnd;
    }

    public JPackage getjPackage() {
        return jPackage;
    }

    public void setjPackage(JPackage jPackage) {
        this.jPackage = jPackage;
    }

    public void addMethod(JMethod method){
        if(this.JMethods==null){
            this.JMethods=new ArrayList<>();
        }
        this.JMethods.add(method);

    }
    public void addField(JField field){
        if(this.jFields==null){
            this.jFields=new ArrayList<>();
        }
        this.jFields.add(field);
    }

    public String getImport(){
        Set<JClass>classes=new HashSet<>();
        classes.addAll(((ImportParser) jPackage).parseImport());
        classes.addAll(((ImportParser) jHead).parseImport());
        classes.addAll(((ImportParser) jEnd).parseImport());
        if(jFields!=null)
        for(JField j: jFields){
            classes.addAll(((ImportParser) j).parseImport());

        }
        if(JMethods!=null)
        for(JMethod j:JMethods){
            classes.addAll(((ImportParser) j).parseImport());

        }
        Set newSet=new HashSet();
        for(JClass j:classes){
            parseImport(newSet,j);
        }
        StringBuffer stringBuffer=new StringBuffer();
        StringBuffer stringBuffer1=new StringBuffer("\n");
        List<Comparable>classList=new ArrayList<>();
        classList.addAll(newSet);
        Collections.sort(classList);
        for(Object cl:classList){
            String  importStr=((JClass) cl).getQualifyName();
                if(!importStr.startsWith("java.lang")&&!importStr.equals(this.jPackage.getPkgName())){
                    if(importStr.matches("(java|javax)\\..+")){
                        stringBuffer1.append("import ").append(importStr).append(";").append('\n');
                    }else{
                        stringBuffer.append("import ").append(importStr).append(";").append('\n');
                    }
                }

        }
        return stringBuffer.toString()+stringBuffer1.toString();

    }


    public JHead getjHead() {
        return jHead;
    }

    public void setjHead(JHead jHead) {
        this.jHead = jHead;
    }

    public List<JField> getjFields() {
        return jFields;
    }

    public void setjFields(List<JField> jFields) {
        this.jFields = jFields;
    }

    public List<JMethod> getJMethods() {
        return JMethods;
    }

    public void setJMethods(List<JMethod> JMethods) {
        this.JMethods = JMethods;
    }

    @Override
    public String toString() {
        StringBuffer str = new StringBuffer();
        str.append(jPackage.toString())
                .append(getImport())
                .append(jHead.toString());
        if(jFields!=null)
        for (JField jField : jFields) {
            str.append(jField.toString());
        }
        if(JMethods!=null)
        for (JMethod jMethod : JMethods) {
            str.append(jMethod.toString());
        }
        str.append(jEnd.toString());
        return str.toString();
    }
    protected void parseImport(Set list,JClass jClass){
        list.add(jClass);
        if(jClass.getParameterizeTypes()!=null){
            for(JClass j:jClass.getParameterizeTypes()){
                parseImport(list, j);
            }
        }
    }


}
