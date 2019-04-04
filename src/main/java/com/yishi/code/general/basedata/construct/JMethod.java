package com.yishi.code.general.basedata.construct;

import com.yishi.code.general.x.Indent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JMethod implements ImportParser {
    private JClass returnType;
    private String name;
    private List<JParam> params;
    private String Content;
    private List<JClass> extClasses;
    private List<JAnnotation> annotations;

    public List<JClass> getExtClasses() {
        return extClasses;
    }
    public void addParam(String name,JClass jClass){
        if(this.params==null)
            this.params=new ArrayList<>();
        this.params.add(new JParam(name,jClass));
    }
    public void setExtClasses(List<JClass> extClasses) {
        this.extClasses = extClasses;
    }

    public JMethod(String name, JClass returnType, String content){
        this.name=name;
        this.returnType=returnType;
        this.Content=content;

    }
    public JMethod(String name, Class returnType, String content){
        this(name,new JClass(returnType),content);

    }

        public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<JParam> getParams() {
        return params;
    }

    public void setParams(List<JParam> params) {
        this.params = params;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }


    public List<JAnnotation> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(List<JAnnotation> annotations) {
        this.annotations = annotations;
    }
    public void addAnnotation(JAnnotation annotation){
        if(this.annotations==null)
            this.annotations=new ArrayList<>();
        this.annotations.add(annotation);
    }

    public JClass getReturnType() {
        return returnType;
    }
    public JMethod addExtClass(JClass jClass){
        if(this.extClasses==null)
            this.extClasses=new ArrayList<>();
        this.extClasses.add(jClass);
        return this;
    }

    public void setReturnType(JClass returnType) {
        this.returnType = returnType;
    }
    public String invoke(JObject obj, List<JParam> param){
        JField jField= ((JField) obj);
        String str= ((JField) obj).getName()+"."+this.getName();
        str+='(';
        for(int i=0;i<this.params.size();i++){
            JClass expect=this.params.get(i).getjClass();
            JClass given=param.get(i).getjClass();
            if(!expect.equals(given)){
                throw new RuntimeException("参数不匹配");
            }
            str+=param.get(i).getName();
            str+=',';
        }
        str=str.substring(0,str.length()-1);
        str+=");";
        return str;

    }

    @Override
    public Set<JClass> parseImport() {
        Set set = new HashSet<>();
        if(returnType!=null)
        set.add(returnType);
        if(annotations!=null)
        for(JAnnotation j:annotations){
            set.addAll(j.parseImport());
        }
        if(params!=null)
        for(JParam param:params)
        {
            set.add(param.getjClass());
        }
        if(extClasses!=null)
        set.addAll(extClasses);
        return set;
    }

    @Override
    public String toString() {
        String str="";
        if(annotations!=null&&annotations.size()>0){
            for(JAnnotation j:annotations){
                str+=Indent.METHOD+j.toString();
            }
        }
        str+=Indent.METHOD+"public "+(returnType==null?"void":returnType.toString())+' '+name+'(';
        if(params!=null&&params.size()>0){
            for(JParam param:params){

                str+=param;
                str+=',';
            }
            str=str.substring(0,str.length()-1);
        }
        str+="){\n\n";
        str+=Indent.METHODCONTENT+Content;
        str+=Indent.METHOD+"}\n\n";
        return str;
    }

}
