package com.yishi.html2pdf;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;

/**
 *
 *  iText5可用
 */
public class ParseHtmlTable7 {
    public static final String DEST = "/home/yishi/html_table.pdf" ;
    public static final String HTML = "table_css.html";

    public static void main(String[] args) throws IOException {
        DefaultFontProvider provider=new DefaultFontProvider();
        provider.addFont("ttf/simsun.ttf");
        ConverterProperties properties=new ConverterProperties();
        properties.setFontProvider(provider);
        File in=new ClassPathResource(HTML).getFile();
        File out=new File(DEST);
        HtmlConverter.convertToPdf(in,out,properties);

    }


}