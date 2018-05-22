package com.yishi.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipTest {
    public static void main(String[] args) throws IOException {
        zip("hello\n","utf-8");
    }
    public static void zip(String content,String encode)throws IOException{
        try(
                OutputStream out=new FileOutputStream("test.zip");
                ZipOutputStream zipOutputStream=new ZipOutputStream(out);){
            zipOutputStream.putNextEntry(new ZipEntry("src/a.txt"));
            zipOutputStream.write(content.getBytes(encode));
            zipOutputStream.closeEntry();
//            File dir=new File("testDir");
//            if(!dir.exists())
//                dir.mkdir();

        }
    }
}
