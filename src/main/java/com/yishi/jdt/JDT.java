package com.yishi.jdt;

import org.eclipse.jdt.core.dom.*;

import java.io.*;
import java.util.List;

public class JDT {
    public static String read(String path, String encode) {
        return read(new File(path), encode);
    }

    private static String read(File file, String encode) {
        try {
            return read(new FileInputStream(file), encode);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return "";

    }

//    public static void main(String[] args) {
//        xx();
//    }

    private static String read(FileInputStream fileInputStream, String encode) {
        StringBuffer stringBuffer = new StringBuffer();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream, encode))) {
            String line;
            while ((line = br.readLine()) != null) {
                stringBuffer.append(line).append(System.getProperty("line.separator"));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }

    static void xx(String path,String encoding) {

//        String content = read("/home/yishi/IDEA/budget_st/JavaSource/www/com/datanew/performance/dao/impl/PerEnterStatusDao.java","gbk");
        String content = read(path,encoding);

        //创建解析器
        ASTParser parsert = ASTParser.newParser(AST.JLS8);
        //设定解析器的源代码字符
        parsert.setSource(content.toCharArray());
        //使用解析器进行解析并返回AST上下文结果(CompilationUnit为根节点)
        CompilationUnit result = (CompilationUnit) parsert.createAST(null);


        //获取类型
        List types = result.types();
        //取得类型声明
        TypeDeclaration typeDec = (TypeDeclaration) types.get(0);
        //##############获取源代码结构信息#################
        //引用import
        List importList = result.imports();
        //取得包名
        PackageDeclaration packetDec = result.getPackage();
        //取得类名
        String className = typeDec.getName().toString();
        //取得函数(Method)声明列表
        MethodDeclaration methodDec[] = typeDec.getMethods();
        //取得函数(Field)声明列表
        FieldDeclaration fieldDec[] = typeDec.getFields();
        //输出包名
        System.out.println("包:");
        System.out.println(packetDec.getName());
        //输出引用import
        System.out.println("引用import:");
        for (Object obj : importList) {
            ImportDeclaration importDec = (ImportDeclaration) obj;
            System.out.println(importDec.getName());
        }
        //输出类名
        System.out.println("类:");
        System.out.println(className);
        //循环输出函数名称
        System.out.println("函数:");
        for (MethodDeclaration method : methodDec) {
            System.out.println(method.getName()+":");
            System.out.println(method);
        }
        //循环输出变量
        System.out.println("变量:");
        for (FieldDeclaration fieldDecEle : fieldDec) {
        //public static
            for (Object modifiObj : fieldDecEle.modifiers()) {
                Modifier modify = (Modifier) modifiObj;
                System.out.print(modify + "-");
            }
            System.out.println(fieldDecEle.getType());
            for (Object obj : fieldDecEle.fragments()) {
                VariableDeclarationFragment frag = (VariableDeclarationFragment) obj;
                System.out.println("[FIELD_NAME:]" + frag.getName());
            }
        }
        System.out.println("是否接口："+(((TypeDeclaration) result.types().get(0)).isInterface()?"是":"否"));
        System.out.println("父类");result.getParent();
        System.out.println(((TypeDeclaration) result.types().get(0)).getSuperclassType());
        System.out.println(((TypeDeclaration) result.types().get(0)).superInterfaceTypes());
    }

    private static String read(String s) {
        return read(s, "utf-8");
    }
}
