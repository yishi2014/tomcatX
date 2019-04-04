//测试
/*
ceshi
 */
package com.yishi.code.general.x.parse;


import com.yishi.code.general.x.XObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XParser {
    //测试
    char a='\\';
    private static String constructReg="\\s*(?<pkg>package\\s*.+?;)\\s*(?<cimp>(?:import\\s*.+?;\\s*)*)?\\s*(?<cqualifier>public)?\\s*(?<ctype>class|interface|enum|@interface)\\s*(?<cname>.+?)\\{(?<cbody>[\\s\\S]*)}";

    public static XObject parse(String objStr){
//        System.out.println(objStr);
        XObject object=new XObject();

        Pattern pattern=Pattern.compile(constructReg);
        Matcher matcher=pattern.matcher(objStr);
        if(matcher.find()){
            String pkg=matcher.group("pkg");
            String cimport=matcher.group("cimp");
            String cqualifier=matcher.group("cqualifier");
            String ctype=matcher.group("ctype");
            String cname=matcher.group("cname");
            String cbody=matcher.group("cbody");
            System.out.println(pkg);
            System.out.println(cimport);
            System.out.println(cqualifier);
            System.out.println(ctype);
            System.out.println(cname);
            System.out.println(cbody);
        }
        return null;


    }
    public static XObject parse(File file,String encode) throws IOException {
        try(BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(file),encode))){
            StringBuffer buffer=new StringBuffer();
            String line=null;
            while((line=br.readLine())!=null){
                buffer.append(line).append("\n");
            }
            return parse(buffer.toString());
        }

    }

    public static void main(String[] args) throws IOException {
        lexicalAnalysis(new File("C:\\IDEA\\tomcatX\\tomcatX\\src\\main\\java\\com\\yishi\\code\\general\\x\\parse\\XParser.java"),"utf-8");

//        System.out.println(Arrays.toString("123".getBytes("gbk")));
    }

    public static List<Token> lexicalAnalysis(File file, String encode) throws IOException {
        List<Token> list=new ArrayList<>();
        try(BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(file),encode))){
            Token token;
            int[] separator=new int[1];
            separator[0]=-1;
            while((token=getnextToken(br,separator))!=null){
                list.add(token);
                System.out.println(token);
            }
            return list;
        }

    }
    public static Token getnextToken(BufferedReader br,int[] separator) throws IOException {
        Token token=new Token();
        int initCapacity=64;
        char[] chars=new char[initCapacity];
        char c;
        int index=0;
        if(separator[0]>-1){
            c= (char) separator[0];
            separator[0]=-1;
        }else{
            while((c= (char) br.read())>-1){
//            System.out.println("current char is:"+c);
                if(!canIgnore(c)){
                    break;
                }
//            System.out.println("will ignore it");
            }
        }
        if(isSeparator(c)){
            token.setType("separator");
            chars[index++]=c;
            separator[0]=-1;
        } else if(c=='/'){
            chars[index++]=c;
            c= (char) br.read();
            if(c!=-1){
                chars[index++]=c;
                if(c=='/'){
                    token.setType("comment");
                    while((c= (char) br.read())>-1&&c!='\n'){
                        if(index==initCapacity){
                            initCapacity<<=1;
                            chars=increase(chars,initCapacity);
                        }
                        chars[index++]=c;
                    }
                }else if(c=='*'){
                    token.setType("comment");

                    while((c= (char) br.read())>-1){
                        if(index==initCapacity){
                            initCapacity<<=1;
                            chars=increase(chars,initCapacity);
                        }
                        chars[index++]=c;
                        if(c=='/'){
                            if(chars[index-2]=='*'){
                                break;
                            }

                        }
                    }
                }
            }
        }else if(isOperator(c)){
            token.setType("operator");
            chars[index++]=c;

        }else if(c=='"'){
            token.setType("String");
            while ((c = (char) br.read()) > -1) {
                if (index == initCapacity) {
                    initCapacity <<= 1;
                    chars = increase(chars, initCapacity);
                }
                if (c == '"') {
                    if (index>2&&chars[index - 2] != '\\') {
                        if (chars[index - 3] != '\\') {
                            break;
                        }
                    }else{
                        break;
                    }
                }
                chars[index++]=c;
            }
        } else if(c=='\''){
            token.setType("char");
            while ((c = (char) br.read()) > -1) {
                if (index == initCapacity) {
                    initCapacity <<= 1;
                    chars = increase(chars, initCapacity);
                }
                if (c == '\'') {
                    if(chars[0]=='\\'){
                        if(index>1)
                            break;
                    }else{
                        break;
                    }
                }
                chars[index++]=c;
            }
        }else if((c>=0x41&&c<=0x5a)||(c>=0x61&&c<=0x7a)||(c>='0'&&c<='9')){
            token.setType("var");
            chars[index++]=c;
            while ((c = (char) br.read()) > -1) {
                if (index == initCapacity) {
                    initCapacity <<= 1;
                    chars = increase(chars, initCapacity);
                }
                if (c == ' ') {
                    break;
                }else if(isSeparator(c)){
                    separator[0]=c;
                    break;
                }else if(isOperator(c)){
                    separator[0]=c;
                    break;
                }
                chars[index++]=c;
            }
        }else if(isOperator(c)){
            token.setType("operator");
            chars[index++]=c;
        }else{
            return null;
        }
        if(index<initCapacity)
        chars=decrease(chars,index);
        token.setContent(chars);
        return token;
    }
    public static  char[] increase(char[] chars,int capacity){
        char[] newchars=new  char[capacity];
        System.arraycopy(chars,0,newchars,0,chars.length);
        return newchars;
    }
    public static char[] decrease(char[] chars,int capacity){
        char[] newchars=new  char[capacity];
        System.arraycopy(chars,0,newchars,0,capacity);
        return newchars;
    }
    public static boolean canIgnore(char c){
        return c==' '||c=='\n'||c=='\r'||c==' '||c==' '||c==' '||c==' '||c==' ';

    }
    public static boolean isSeparator(char c){
        return c==','||c==';'||c==':'||c=='('||c==')'||c=='{'||c=='}'||c=='['||c==']'||c=='.';
    }
    public static boolean isOperator(char c){
        return c=='+'||c=='-'||c=='*'||c=='/'||c=='&'||c=='^'||c=='%'||c=='~'||c=='='||c=='!'||c=='>'||c=='<'||c=='?'||c=='|';
    }
}

class Token{
    public static final String[] keywords={"abstract", "boolean", "break", "byte","case", "catch", "char", "class", "continue", "default", "do","double", "else", "extends", "final", "finally", "float", "for","if", "implements", "import", "instanceof", "int", "interface", "long", "native", "new", "package", "private", "protected", "public", "return", "short", "static", "super", "switch","synchronized", "this", "throw","throws", "transient", "try","void","volatile","while","strictfp","enum","goto","const","assert"};
    private String type;
    private char[] content;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public char[] getContent() {
        return content;
    }

    public void setContent(char[] content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return new String(content);
    }
}