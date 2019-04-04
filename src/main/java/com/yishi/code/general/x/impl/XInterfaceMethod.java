package com.yishi.code.general.x.impl;

import com.yishi.code.general.x.Indent;
import com.yishi.code.general.x.XAnnotation;
import com.yishi.code.general.x.XClass;
import com.yishi.code.general.x.XParam;

import java.util.List;

public class XInterfaceMethod extends AbstractXMethod {
    public XInterfaceMethod(String name, XClass returnType, String content) {
        super(name, returnType, content);
    }


    public XInterfaceMethod(String name, XClass returnType) {
        super(name, returnType, null);
    }

    @Override
    public String render() {
        List<XAnnotation> annotations=getAnnotations();
        XClass returnType=getReturnType();
        List<XParam>params=getParams();
        String name=getName();
        String str="";
        if(annotations!=null&&annotations.size()>0){
            for(XAnnotation j:annotations){
                str+=Indent.METHOD+j.render();
            }
        }
        str+=Indent.METHOD+"public "+(returnType==null?"void":returnType.render())+' '+name+'(';
        if(params!=null&&params.size()>0){
            for(XParam param:params){
                str+=param.render();
                str+=',';
            }
            str=str.substring(0,str.length()-1);
        }
        str+=");\n\n";

        return str;
    }
}
