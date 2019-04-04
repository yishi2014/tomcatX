package com.yishi.code.general.basedata.construct;

import com.yishi.code.general.x.Indent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JField extends JObject implements ImportParser {
    private String name;
    private String remark;
    private JClass _type;
    private List<JAnnotation> annotations;
    public JField(Class type,String name){
        this(new JClass(type),name);

    }
    public JField(String qualifyName,String name){
      this(new JClass(qualifyName),name);
    }

    public JField(JClass _type, String name){
        this._type=_type;
        this.name=name;
    }
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Class getType() {
        return this._type.getType();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<JAnnotation> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(List<JAnnotation> annotations) {
        this.annotations = annotations;
    }

    public JClass get_type() {
        return _type;
    }

    public void set_type(JClass _type) {
        this._type = _type;
    }
    public void addAnnotation(JAnnotation annotation){
        if(this.annotations==null)
            this.annotations=new ArrayList<>();
        this.annotations.add(annotation);
    }

    @Override
    public Set<JClass> parseImport() {
        Set set = new HashSet<>();

        set.add(_type);
        if (annotations != null)
            for (JAnnotation j : annotations) {
                set.addAll(j.parseImport());
            }
        return set;
    }

    @Override
    public String toString() {
        String str = "";
        if (annotations != null && annotations.size() > 0) {
            for (JAnnotation j : annotations) {
                str += Indent.FIELD +j.toString();
            }
        }
        if (this.remark != null)
            str += Indent.FIELD + "//" + this.remark + '\n';

            str += Indent.FIELD + "private " + _type.toString() + ' ' + name + ";\n\n";
        return str;
    }
}
