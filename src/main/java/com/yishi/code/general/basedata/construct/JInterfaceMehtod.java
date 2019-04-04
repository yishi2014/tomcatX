package com.yishi.code.general.basedata.construct;

import com.yishi.code.general.x.Indent;

import java.util.List;

public class JInterfaceMehtod extends JMethod {


    public JInterfaceMehtod(String name, JClass returnType, String content) {
        super(name, returnType, null);
    }

    public JInterfaceMehtod(String name, Class returnType, String content) {
        super(name, returnType, null);
    }
    public JInterfaceMehtod(String name,Class returnType){
        super(name, returnType, null);
    }
    public JInterfaceMehtod(String name,JClass returnType){
        super(name, returnType, null);
    }

    @Override
    public String toString() {
        List<JAnnotation> annotations=getAnnotations();
        JClass returnType=getReturnType();
        List<JParam>params=getParams();
        String name=getName();
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
            str+=");\n\n";

            return str;
    }
}
