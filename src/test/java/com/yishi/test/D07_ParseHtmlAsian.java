//package com.yishi.test;
//
//import com.itextpdf.text.Document;
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.pdf.BaseFont;
//import com.itextpdf.text.pdf.PdfWriter;
//import com.itextpdf.tool.xml.XMLWorkerHelper;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.nio.charset.Charset;
//
//public class D07_ParseHtmlAsian {
//
//    public static final String DEST = "/home/yishi/html_table.pdf";
//    public static final String HTML = "/home/yishi/IDEA/tomcatX/src/test/resource/table_css.html";
//
//    /**
//     * Creates a PDF with the words "Hello World"
//     *
//     * @param file
//     * @throws IOException
//     * @throws DocumentException
//     */
//    public void createPdf(String file) throws IOException, DocumentException {
//        BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
//
//        // step 1
//        Document document = new Document();
//        // step 2
//        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
//        // step 3
//        document.open();
//        // step 4
//        XMLWorkerHelper.getInstance().parseXHtml(writer, document,
//                new FileInputStream(HTML), Charset.forName("UTF-8"));
//        // step 5
//        document.close();
//    }
//
//    /**
//     * Main method
//     */
//    public static void main(String[] args) throws IOException, DocumentException {
//        File file = new File(DEST);
//        file.getParentFile().mkdirs();
//        new D07_ParseHtmlAsian().createPdf(DEST);
//    }
//}
