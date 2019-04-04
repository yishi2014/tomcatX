package com.yishi.code.general.basedata.construct;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JAnnotation implements ImportParser {
    private JClass annotationClass;
    private List<JAnnotationAttr> attrs;
    public JAnnotation(Class<? extends Annotation> clazz){
        this.annotationClass=new JClass(clazz);
    }

    public List<JAnnotationAttr> getAttrs() {
        return attrs;
    }

    public void setAttrs(List<JAnnotationAttr> attrs) {
        this.attrs = attrs;
    }
    public JAnnotation addAttr(JAnnotationAttr attr){
        if (this.attrs == null)
            this.attrs = new ArrayList<>();
        this.attrs.add(attr);
        return this;
    }

    public JClass getAnnotationClass() {
        return annotationClass;
    }

    public void setAnnotationClass(JClass annotationClass) {
        this.annotationClass = annotationClass;
    }

    @Override
    public String toString() {
        String str='@'+ annotationClass.getSimpleName();
        if(attrs!=null&&attrs.size()>0){
           if(attrs.size()==1&&attrs.get(0).getName()==null){
               str+='(';
               str+=attrs.get(0).getVal().toString();
               str+=')';
           }else{
               str+='(';
               for(JAnnotationAttr attr:attrs){
                   str+=attr;
                   str+=',';
               }
               str=str.substring(0,str.length()-1);
               str+=')';
           }
        }
        str+='\n';
        return str;
    }

    public static void main(String[] args) {
//        JAnnotation jAnnotation=new JAnnotation();
//        jAnnotation.setAnnotationClass(Entity.class);
//        jAnnotation.setAttrs(new ArrayList<JAnnotationAttr>());
//        System.out.println(jAnnotation);
    }

    @Override
    public Set<JClass> parseImport() {
        Set set= new HashSet<>();
        set.add(annotationClass);
        return set;
    }
}
