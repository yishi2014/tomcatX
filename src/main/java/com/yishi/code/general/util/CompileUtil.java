
package com.yishi.code.general.util;

import org.springframework.core.io.ClassPathResource;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

public class CompileUtil {
    public static Map<String, byte[]> compile(String javaName, String javaSrc) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager stdManager = compiler.getStandardFileManager(null, null, null);
        try (
                MemoryJavaFileManager manager = new MemoryJavaFileManager(stdManager)) {
            JavaFileObject javaFileObject = manager.makeStringSource(javaName, javaSrc);

//            Iterable<String> options = Arrays.asList("-encoding", encoding, "-classpath", jars, "-d", targetDir, "-sourcepath", sourceDir);

            JavaCompiler.CompilationTask task = compiler.getTask(null, manager, null, null, null, Arrays.asList(javaFileObject));
            if (task.call())
                return manager.getClassBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void compileWrite(String javaName, String javaSrc) throws IOException {
        Map<String, byte[]> compileResult=compile(javaName,javaSrc);
        for(Map.Entry<String,byte[]> en:compileResult.entrySet()){
//            new ClassPathResource("com/datanew/XX.class").getFile().getAbsolutePath();
            String rootpath=new ClassPathResource("").getFile().getAbsolutePath();
            String packagePath=en.getKey().replaceAll("\\.[^\\.]+$","");
            String ClassName=en.getKey().replaceAll("^.+\\.([^\\.]+)$","$1");
            System.out.println(ClassName);
            String filePath=RegexUtil.formateDir(rootpath+'/'+packagePath.replaceAll("\\.",File.separator));
            File file=new File(filePath);
            if(!file.exists()){file.mkdirs();}
            File classFile=new File(filePath+File.separator+ClassName+".class");
            try(FileOutputStream out=new FileOutputStream(classFile)){
                out.write(en.getValue());
            }

        }
    }


    /**
     * 查找该目录下的所有的jar文件
     *
     * @param jarPath
     * @throws Exception
     */
    private String getJarFiles(String jarPath) throws Exception {
        File sourceFile = new File(jarPath);
        final String[] jars = {""};
        if (sourceFile.exists()) {// 文件或者目录必须存在
            if (sourceFile.isDirectory()) {// 若file对象为目录
                // 得到该目录下以.java结尾的文件或者目录
                File[] childrenFiles = sourceFile.listFiles(new FileFilter() {
                    public boolean accept(File pathname) {
                        if (pathname.isDirectory()) {
                            return true;
                        } else {
                            String name = pathname.getName();
                            if (name.endsWith(".jar") ? true : false) {
                                jars[0] +=  pathname.getPath() + ";";
                                return true;
                            }
                            return false;
                        }
                    }
                });
            }
        }
        return jars[0];
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        System.out.println(new ClassPathResource("com/example").getFile().getAbsolutePath());
        compileWrite("com.example.controller.Test.java","package com.example.controller;public class Test{}");
        System.out.println(Class.forName("com.example.controller.Test"));
    }
}