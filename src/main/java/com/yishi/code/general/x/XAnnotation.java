package com.yishi.code.general.x;

import java.util.ArrayList;
import java.util.List;

public class XAnnotation  implements XComponent {
    private XClass annotationClass;
    private List<XAnnotationAttr> attrs;
    public XAnnotation(XClass xClass){
        this.annotationClass=xClass;
    }

    public XAnnotation addAttr(XAnnotationAttr attr){
        if (this.attrs == null)
            this.attrs = new ArrayList<>();
        this.attrs.add(attr);
        return this;
    }
    public XClass getAnnotationClass() {
        return annotationClass;
    }

    public void setAnnotationClass(XClass annotationClass) {
        this.annotationClass = annotationClass;
    }

    public List<XAnnotationAttr> getAttrs() {
        return attrs;
    }

    public void setAttrs(List<XAnnotationAttr> attrs) {
        this.attrs = attrs;
    }

    public String render(boolean changLine) {
        String str='@'+ annotationClass.getSimpleName();
        if(attrs!=null&&attrs.size()>0){
            if(attrs.size()==1&&attrs.get(0).getName()==null){
                str+='(';
                str+=attrs.get(0).getVal().render();
                str+=')';
            }else{
                str+='(';
                for(XAnnotationAttr attr:attrs){
                    str+=attr.render();
                    str+=',';
                }
                str=str.substring(0,str.length()-1);
                str+=')';
            }
        }
        if(changLine)
        str+='\n';
        return str;
    }
    @Override
    public String render(){
        return render(true);
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
