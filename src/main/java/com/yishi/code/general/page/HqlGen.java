package com.yishi.code.general.page;


import javax.persistence.Column;
import javax.persistence.Entity;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by Lustin on 2018/04/01
 * 此类不可更改
 */
public class HqlGen {

    public static String hql(Object obj,Class cl,List<Object> params,Page page) {
        Class<?> clazz=obj.getClass();
        String hql=null;
        String countSql=null;
        //如果是数据库实体则匹配注解
        if(clazz.equals(cl)){
            String entityName;
            Entity entity=clazz.getAnnotation(Entity.class);
            entityName=entity.name();
            if(isBlank(entityName)){
                entityName=clazz.getSimpleName();
            }
             hql="from "+entityName+" ";
            countSql="select count(*) from "+entityName+' ';
            if(obj!=null){
                int i=0;
                for(Method f:clazz.getDeclaredMethods()){
                    if(f.isAnnotationPresent(Column.class)){
//                        f.setAccessible(true);
                        try {
                            Object reVal;
                            if((reVal=f.invoke(obj))!=null){
                                String field=getFiledByMethodName(f.getName());
                                if(i==0){
                                    hql+=" where "+field+"=?";
                                    countSql+=" where "+field+"=?";
                                }else{
                                    hql+=" and "+field+"=?";
                                    countSql+=" and "+field+"=?";
                                }

                                params.add(reVal);

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                }
            }
        }else {
            //全部匹配

        }
        if(page!=null)
        page.setCountHql(countSql);
        return hql;
    }
//    public static String parseCondition(){
//
//    }
    public static boolean isBlank(String str){
        return str==null||str.trim().length()==0;

    }
    public static String getFiledByMethodName(String methodName){
        if(methodName.startsWith("get"))
        {
            return RegexUtil.firstToLower(methodName.substring(3));

        }else
        {
            return RegexUtil.firstToLower(methodName.substring(2));
        }

    }


//    public static void main(String[] args) {
//        JeeCgformHead jeeCgformHead=new JeeCgformHead();
//        jeeCgformHead.setId("4028b881626fd9d60162708f823b0018");
//        System.out.println(hql(jeeCgformHead,jeeCgformHead.getClass(),new ArrayList<Object>(),new Page()));
//    }

}
