//package com.yishi.test;
//
//import com.itextpdf.text.BaseColor;
//import com.itextpdf.text.Font;
//import com.itextpdf.text.FontFactoryImp;
//import com.itextpdf.text.FontProvider;
//import com.itextpdf.text.pdf.BaseFont;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class FontProvider_ extends FontFactoryImp {
////    private BaseColor bc;
////    private String fontname;
////    private String encoding;
////    private boolean embedded;
////    private boolean cached;
////    private float size;
////    private int style;
//    private static Map<String,BaseFont> baseFonts=new HashMap<>();
//
//    public FontProvider_(){}
//
//
//
//
//
//
//
//    public FontProvider_(BaseFont baseFont) {
//        super();
////        this.bc = bc;
////        this.fontname = fontname;
////        this.encoding = encoding;
////        this.embedded = embedded;
////        this.cached = cached;
////        this.size = size;
////        this.style = style;
//        this.baseFonts.put(baseFont.getPostscriptFontName(),baseFont);
////        this.registerFamily("stsong-light","stsong-light","/home/yishi/IDEA/tomcatX/src/test/resource/ttf/simsun.ttf");
//
//    }
//
//
//    public Font getFont(String arg0, String arg1, boolean arg2, float arg3,
//                        int arg4, BaseColor arg5) {
//
//
//            Font font;
//            if ((font = (super.getFont(arg0, arg1, arg2, arg3, arg4, arg5))) == null||font.getFamilyname().equals("unknown")) {
//                font = new Font(baseFonts.get(arg0));
//                font.setFamily(arg0);
//                font.setColor(arg5);
//                font.setSize(arg3);
//                font.setStyle(arg4);
//                return font;
//            }
//            return font;
//
//
//    }
//
//
//    public boolean isRegistered(String arg0) {
//// TODO Auto-generated method stub
//        return true;
//    }
//}