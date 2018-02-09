//package com.yishi.test;
//
//import com.itextpdf.text.*;
//import com.itextpdf.text.pdf.BaseFont;
//import com.itextpdf.text.pdf.PdfWriter;
//import com.itextpdf.tool.xml.XMLWorkerFontProvider;
//import com.itextpdf.tool.xml.XMLWorkerHelper;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.nio.charset.Charset;
//
///**
// *
// * @author iText
// */
//public class ParseHtmlTable {
//    public static final String DEST = "/home/yishi/html_table.pdf" ;
//    public static final String HTML = "/home/yishi/IDEA/tomcatX/src/test/resource/table_css.html";
//
//    public static void main(String[] args) throws IOException, DocumentException {
//        File file = new File( DEST);
//        file.getParentFile().mkdirs();
//        new ParseHtmlTable().createPdf(DEST );
//    }
//
//    /**
//     * Creates a PDF with the words "Hello World"
//     * @param file
//     * @throws IOException
//     * @throws DocumentException
//     */
//    public void createPdf(String file) throws IOException, DocumentException {
//        XMLWorkerFontProvider fontProvider=new XMLWorkerFontProvider();
////        fontProvider.register("/ttf/MS Mincho.TTF");
//        fontProvider.register("/ttf/simsun.ttf");
//        // step 1
//        Document document = new Document();
//        // step 2
//        PdfWriter writer = PdfWriter. getInstance(document, new FileOutputStream(file));
//        // step 3
//        document.open();
//        // step 4
//        XMLWorkerHelper. getInstance().parseXHtml(writer, document,
//                new FileInputStream( HTML), Charset.forName("utf-8"),fontProvider);
//        // step 5
//        document.close();
//    }
//}