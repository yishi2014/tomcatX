package com.yishi.code.general.example;

import com.yishi.code.general.dto.TableMeta;
import com.yishi.code.general.util.RegexUtil;
import com.yishi.code.general.x.impl.HtmlGenerator;
import org.springframework.core.io.ClassPathResource;

import java.io.*;

public  class LoadFunction implements Runnable{
    private String webAppPath;
    private TableMeta tableMeta;
    private String rootpath;
    private String WEBINF;
    public LoadFunction(TableMeta tableMeta,String rootpath){
        webAppPath=System.getProperty("webapp.root");
        this.tableMeta=tableMeta;
        this.rootpath=rootpath;
//            String jarPath = "/home/yishi/IDEA/code_generator/target/code_generator/WEB-INF/classes";

        this.WEBINF=rootpath.replaceAll("classes/?$","");
    }
    public void writeToExampleSourceDir(String qulifyName,String content) throws IOException {

        String packagePath=qulifyName.replaceAll("\\.[^\\.]+$","");
        String ClassName=qulifyName.replaceAll("^.+\\.([^\\.]+)$","$1");
        String filePath=RegexUtil.formateDir(WEBINF+"/exampleSource"+File.separator+RegexUtil.formateduplicateChar(packagePath,".",File.separator));
        File file=new File(filePath);
        if(!file.exists()){file.mkdirs();}
        File classFile=new File(filePath+File.separator+ClassName+".java");
        try(BufferedWriter out=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(classFile),"utf-8"))){
            out.write(content);
        }

    }
    public void writeOtherClass(String qulifyName) throws IOException {
        String packagePath=qulifyName.replaceAll("\\.[^\\.]+$","");
        String ClassName=qulifyName.replaceAll("^.+\\.([^\\.]+)$","$1");
        String filePath=RegexUtil.formateDir(WEBINF+"/exampleSource"+File.separator+RegexUtil.formateduplicateChar(packagePath,".",File.separator));
        File file=new File(filePath);
        if(!file.exists()){file.mkdirs();}
        File classFile=new File(filePath+File.separator+ClassName+".java");
        try(FileOutputStream out= new FileOutputStream(classFile);
            FileInputStream in=new FileInputStream(new ClassPathResource("code/template/"+ClassName+".template").getFile());

        ){
            int by;
            while((by=in.read())!=-1){
                out.write(by);
            }
        }

    }

    public void writeHtml(String htmlName,String str) throws IOException{
        File html=new File(WEBINF.replaceAll("WEB-INF[\\\\/]?$","")+htmlName);
//System.out.println(html.getAbsolutePath());
        try(BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(html),"utf-8"))){
            bw.write(str);
        }

    }
    public void deleteHtml(String htmlName){
        File html=new File(WEBINF.replaceAll("WEB-INF[\\\\/]?$","")+htmlName);
        if(html.exists()){
            html.delete();
        }
    }
    public void deleteFile(File dir){
            /*if(dir.isDirectory()){
                for(File f:dir.listFiles()){
                    deleteFile(f);
                }
                dir.delete();
            }else{
//System.out.println("delete file "+dir.getAbsolutePath());
                dir.delete();
            }*/

    }
    @Override
    public void run() {
        String prefix=null;
        String htmlPrefix=null;
//            tableMeta.setColumnMetas(tableMeta.getCloumnMap());
//            tableMeta.setControllerPackage("com.example.controller");
//            tableMeta.setEntityPackage("com.example.entity");
//            tableMeta.setServicePackage("com.example.service");
//            tableMeta.setServiceImplPackage("com.example.service.imp");
        EntityContainer.addMapping(tableMeta.getEntityName(),tableMeta);


        try {
//                XObject entity=new XObjectDirector(new EntityBuilder(tableMeta)).construct();
//                writeToExampleSourceDir(tableMeta.getEntityQualifyName(),entity.render());
//
//                XObject controller=new XObjectDirector(new ControllerBuilder(tableMeta)).construct();
//                writeToExampleSourceDir(tableMeta.getControllerQualifyName(),controller.render());
//
//                XObject impl=new XObjectDirector(new ServiceImplBuilder(tableMeta)).construct();
//                writeToExampleSourceDir(tableMeta.getServiceImplQualifyName(),impl.render());
//
//                XObject service=new XObjectDirector(new ServiceBuilder(tableMeta)).construct();
//                writeToExampleSourceDir(tableMeta.getServiceQualifyName(),service.render());
//                writeOtherClass("com.datanew.util.unalterable.HqlGen");
//                writeOtherClass("com.datanew.util.unalterable.RegexUtil");
//                writeOtherClass("com.datanew.dto.unalterable.Page");
//                writeOtherClass("com.datanew.dto.unalterable.PageData");
//                writeOtherClass("com.datanew.dto.unalterable.Result");
            if("Y".equals(tableMeta.getIsTree()))
            writeHtml(RegexUtil.addEndSymbol(tableMeta.getHtmlName(),".html"),new HtmlGenerator().buildExampleTreeHtml(tableMeta));
            else
            writeHtml(RegexUtil.addEndSymbol(tableMeta.getHtmlName(),".html"),new HtmlGenerator().buildExampleHtml(tableMeta));
        } catch (IOException e) {
            e.printStackTrace();
        }

//            try {
//                String filePath = WEBINF+"/exampleSource";
//                String sourceDir = WEBINF+"/exampleSource";
//                String jarPath = WEBINF+"lib";
//                String targetDir = rootpath;
//                File tarDir=new File(targetDir);
//                if(!tarDir.exists()){
//                    tarDir.mkdirs();
//                }
//                DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
//                DynamicCompilerUtil dynamicCompilerUtil = new DynamicCompilerUtil();
//                boolean compilerResult = dynamicCompilerUtil.compiler("UTF-8", dynamicCompilerUtil.getJarFiles(jarPath), filePath, sourceDir, targetDir, diagnostics);
//                if (compilerResult) {
////System.out.println("编译成功");
//                    Class clazz=Class.forName(tableMeta.getEntityQualifyName());
//                    ClassLoader classLoader=clazz.getClassLoader();
//                    Method method=ReflectionUtils.findMethod(ClassLoader.class,"loadClass",String.class,boolean.class);
//                    method.setAccessible(true);
//                    method.invoke(classLoader,clazz.getName(),true);
////                    .loadClass(clazz.getName());
//                    loadEntity(clazz);
//                    load(Class.forName(tableMeta.getServiceImplQualifyName()));
//                    load(Class.forName(tableMeta.getControllerQualifyName()));
//                } else {
////System.out.println("编译失败");
//                    for (Diagnostic diagnostic : diagnostics.getDiagnostics()) {
//                         System.out.format("%s[line %d column %d]-->%s%n", diagnostic.getKind(), diagnostic.getLineNumber(),
//                         diagnostic.getColumnNumber(),
//                         diagnostic.getMessage(null));
//                        System.out.println(diagnostic.getMessage(null));
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }finally {
//                deleteFile(new File(this.WEBINF+"classesome"));
//            }
    }

    public void unload() {
        deleteHtml(RegexUtil.addEndSymbol(tableMeta.getHtmlName(),".html"));
        EntityContainer.removeMapping(tableMeta.getEntityName());

    }
}
