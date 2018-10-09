package com.yishi.recurse;


import java.io.File;
import java.util.function.Consumer;

public class RecureFileCall {

    public static void foreach(File file, Consumer<File> consumer){
        if(file==null)
            return;
        if(file.isDirectory()){
            for(File f:file.listFiles()){
                foreach (f,consumer);
            }
        }else {
            consumer.accept(file);
        }

    }
}
