package com.yishi.pdf;

import org.apache.pdfbox.contentstream.PDContentStream;
import org.apache.pdfbox.contentstream.operator.Operator;
import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSString;
import org.apache.pdfbox.pdfparser.PDFStreamParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.core.io.ClassPathResource;

import javax.xml.bind.SchemaOutputResolver;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class PdfTest {
    public static void main(String[] args) throws IOException {
//        File file = new ClassPathResource("UFF00.pdf").getFile();
        File file = new ClassPathResource("1001_thaker.pdf").getFile();
        PDDocument doc = PDDocument.load(file);

        //Listing the number of existing pages
        //System.out.print(doc.getNumberOfPages());
        PDPageTree pageTree=doc.getPages();

        // 读文本内容
        PDFTextStripper stripper=new PDFTextStripper();
        // 设置按顺序输出
        stripper.setSortByPosition(true);
        stripper.setStartPage(pageTree.getCount());
        stripper.setEndPage(pageTree.getCount());
        String content = stripper.getText(doc);
        System.out.println(content);
        PDPage page=pageTree.get(pageTree.getCount());

//        PDFStreamParser parser=new PDFStreamParser(page);
//        parser.parse();
//        List tokens = parser.getTokens();
//        for (int j = 0; j < tokens.size(); j++)
//        {
//            Object next =  tokens.get(j);
////            System.out.println(next.getClass());
//            if(next instanceof Operator){
//                if(((Operator) next).getName().equals("Tj")){
//                    COSString previous = (COSString) tokens.get(j - 1);
//                    String string = previous.getString();
//                    System.out.println("Tj "+string);
//                }else if(((Operator) next).getName().equals("TJ")){
//                    COSArray previous = (COSArray) tokens.get(j - 1);
//                    for (int k = 0; k < previous.size(); k++)
//                    {
//                        Object arrElement = previous.getObject(k);
//                        if (arrElement instanceof COSString)
//                        {
//                            COSString cosString = (COSString) arrElement;
//                            String string = cosString.getString();
////                            string = string.replaceFirst("Hello", "Hello World, fish");
//                            System.out.println("TJ "+string);
//                            // Currently this code changes word "Solr" to "Solr123"
////                            cosString.reset();
////                            cosString.append(string.getBytes("ISO-8859-1"));
//                        }
//                    }
//                }
//            }
//        }
//        Iterator<PDStream> streams=page.getContentStreams();
//        while(streams.hasNext()){
//            PDStream stream=streams.next();
//            PDFStreamParser parser = new PDFStreamParser(stream);
//            System.out.println(stream);
//        }


//        Saving the document
//        doc.save("20180420pdfTest.pdf");

//        Closing the document
        doc.close();
//        PDDocument doc = null;
//        PDPage page = null;
//
//        try {
//            doc = new PDDocument();
//            page = new PDPage();
//
//            doc.addPage(page);
//            PDFont font = PDType1Font.HELVETICA_BOLD;
//            PDPageContentStream content = new PDPageContentStream(doc, page);
//            content.beginText();
//            content.setFont(font, 12);
//            content.moveTextPositionByAmount(100, 700);
//            content.drawString("Hello");
//
//            content.endText();
//            content.close();
//            doc.save("20180420pdfTest.pdf");
//            doc.close();
//        } catch (Exception e) {
//            System.out.println(e);
//        }

    }
}
