package com.yishi.code.general.util;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateUtil {

    /**
     *
     * @param rawObject 查询数据库得到的对象
     * @param newObject 参数对象
     * @return
     * @throws Exception
     */
    public static boolean updateNotNullField(Object rawObject, Object newObject) throws Exception {
        //如果对象不一致,不进行更新字段值的操作
        if (!newObject.getClass().getName().equals(rawObject.getClass().getName())) {
            return false;
        }
        //获取原始对象中的所有public方法
        Method[] methods = rawObject.getClass().getDeclaredMethods();
        //用于提取不包含指定关键词的方法

        String regExpression = "^(get)(?!Id|Guid)(\\w+)";
        Pattern pattern = Pattern.compile(regExpression);
        Matcher m;
        for (Method method : methods) {
            m = pattern.matcher(method.getName());
            //正则匹配以get开头，后面不能匹配Id、CreateTime这两个单词的方法
            if (m.find()) {
                //执行参数对象的get方法,获取值
                Object o = method.invoke(newObject);
                //忽略值为空的字段
                if (o == null || "".equals(o)) {
                    continue;
                }
                //取出get方法名后面的字段名
                String fieldName = m.group(2);
                //找到该字段名的set方法
                Method rawMethod = rawObject.getClass().getMethod("set" + fieldName, method.getReturnType());
                //调用实体对象的set方法更新字段值
                rawMethod.invoke(rawObject, o);
            }
        }
        return true;
    }
}
