package com.yishi.test;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.zip.DeflaterInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Test {
    public static void main(String[] args) throws IOException {
//        Test test=new Test();
//        Optional<String>name=Optional.of("hola");
//        name.map(test::firstLetterToUpCase).ifPresent(System.out::println);
//        String x="0A 78 9C 5D 4F 41 6A C3 30 10 BC EB 15 7B 4C 0F 41 4E CE C6 50 52 02 3E 34 29 75 F3 00 45 1A 1B 41 BD 12 6B F9 E0 DF 57 52 43 0A 3D EC C2 EC EC CC EC EA 53 FF D6 B3 4F A4 3F 24 D8 01 89 46 CF 4E B0 84 55 2C E8 8E C9 B3 3A 1C C9 79 9B 1E A8 76 3B 9B A8 74 16 0F DB 92 30 F7 3C 06 D5 B6 44 FA 33 B3 4B 92 8D 76 AF 2E DC F1 A2 F4 55 1C C4 F3 44 BB DB 69 C8 78 58 63 FC C6 0C 4E D4 A8 AE 23 87 31 3B BD 9B 78 31 33 48 57 D9 BE 77 99 F7 69 DB 67 CD DF C6 D7 16 41 C7 8A 0F BF D7 D8 E0 B0 44 63 21 86 27 A8 B6 69 3A 6A CF E7 4E 81 DD 3F AE 4C CA D5 CF 28 BB 8A E4 94 FA 5A B5 2F C6 9E F1 FC 3E 86 58 54 A5 7E 00 C8 C6 63 3D 0A";
//        System.out.println(x.replaceAll("^|\\s",",(byte)0x"));
        DeflaterInputStream Zin=new DeflaterInputStream(new ByteArrayInputStream(new byte[]{(byte)0x0A,(byte)0x78,(byte)0x9C,(byte)0x5D,(byte)0x4F,(byte)0x41,(byte)0x6A,(byte)0xC3,(byte)0x30,(byte)0x10,(byte)0xBC,(byte)0xEB,(byte)0x15,(byte)0x7B,(byte)0x4C,(byte)0x0F,(byte)0x41,(byte)0x4E,(byte)0xCE,(byte)0xC6,(byte)0x50,(byte)0x52,(byte)0x02,(byte)0x3E,(byte)0x34,(byte)0x29,(byte)0x75,(byte)0xF3,(byte)0x00,(byte)0x45,(byte)0x1A,(byte)0x1B,(byte)0x41,(byte)0xBD,(byte)0x12,(byte)0x6B,(byte)0xF9,(byte)0xE0,(byte)0xDF,(byte)0x57,(byte)0x52,(byte)0x43,(byte)0x0A,(byte)0x3D,(byte)0xEC,(byte)0xC2,(byte)0xEC,(byte)0xEC,(byte)0xCC,(byte)0xEC,(byte)0xEA,(byte)0x53,(byte)0xFF,(byte)0xD6,(byte)0xB3,(byte)0x4F,(byte)0xA4,(byte)0x3F,(byte)0x24,(byte)0xD8,(byte)0x01,(byte)0x89,(byte)0x46,(byte)0xCF,(byte)0x4E,(byte)0xB0,(byte)0x84,(byte)0x55,(byte)0x2C,(byte)0xE8,(byte)0x8E,(byte)0xC9,(byte)0xB3,(byte)0x3A,(byte)0x1C,(byte)0xC9,(byte)0x79,(byte)0x9B,(byte)0x1E,(byte)0xA8,(byte)0x76,(byte)0x3B,(byte)0x9B,(byte)0xA8,(byte)0x74,(byte)0x16,(byte)0x0F,(byte)0xDB,(byte)0x92,(byte)0x30,(byte)0xF7,(byte)0x3C,(byte)0x06,(byte)0xD5,(byte)0xB6,(byte)0x44,(byte)0xFA,(byte)0x33,(byte)0xB3,(byte)0x4B,(byte)0x92,(byte)0x8D,(byte)0x76,(byte)0xAF,(byte)0x2E,(byte)0xDC,(byte)0xF1,(byte)0xA2,(byte)0xF4,(byte)0x55,(byte)0x1C,(byte)0xC4,(byte)0xF3,(byte)0x44,(byte)0xBB,(byte)0xDB,(byte)0x69,(byte)0xC8,(byte)0x78,(byte)0x58,(byte)0x63,(byte)0xFC,(byte)0xC6,(byte)0x0C,(byte)0x4E,(byte)0xD4,(byte)0xA8,(byte)0xAE,(byte)0x23,(byte)0x87,(byte)0x31,(byte)0x3B,(byte)0xBD,(byte)0x9B,(byte)0x78,(byte)0x31,(byte)0x33,(byte)0x48,(byte)0x57,(byte)0xD9,(byte)0xBE,(byte)0x77,(byte)0x99,(byte)0xF7,(byte)0x69,(byte)0xDB,(byte)0x67,(byte)0xCD,(byte)0xDF,(byte)0xC6,(byte)0xD7,(byte)0x16,(byte)0x41,(byte)0xC7,(byte)0x8A,(byte)0x0F,(byte)0xBF,(byte)0xD7,(byte)0xD8,(byte)0xE0,(byte)0xB0,(byte)0x44,(byte)0x63,(byte)0x21,(byte)0x86,(byte)0x27,(byte)0xA8,(byte)0xB6,(byte)0x69,(byte)0x3A,(byte)0x6A,(byte)0xCF,(byte)0xE7,(byte)0x4E,(byte)0x81,(byte)0xDD,(byte)0x3F,(byte)0xAE,(byte)0x4C,(byte)0xCA,(byte)0xD5,(byte)0xCF,(byte)0x28,(byte)0xBB,(byte)0x8A,(byte)0xE4,(byte)0x94,(byte)0xFA,(byte)0x5A,(byte)0xB5,(byte)0x2F,(byte)0xC6,(byte)0x9E,(byte)0xF1,(byte)0xFC,(byte)0x3E,(byte)0x86,(byte)0x58,(byte)0x54,(byte)0xA5,(byte)0x7E,(byte)0x00,(byte)0xC8,(byte)0xC6,(byte)0x63,(byte)0x3D,(byte)0x0A}));//输入源zip路径


//        BufferedInputStream Bin=new BufferedInputStream(Zin);
//        String Parent="C:\\Users\\HAN\\Desktop"; //输出路径（文件夹目录）
//        File Fout=null;
        ZipEntry entry;

            Zin.close();

    }

    public String firstLetterToUpCase(String s){
        if(s==null)return s;
        if(s.length()>0){
            char first=s.charAt(0);
            if((first>=0x41&&first<=0x5a)||(first>0x61&&first<=0x7a)){
                char[] chars=s.toLowerCase().toCharArray();
                chars[0]= (char) (chars[0]^0x20);
                return String.valueOf(chars);
            }
            else
                return s.toLowerCase();
        }else
            return s.toLowerCase();
    }

}
