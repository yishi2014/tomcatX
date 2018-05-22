package com.yishi.pdf2Img;

import org.icepdf.core.exceptions.PDFException;
import org.icepdf.core.exceptions.PDFSecurityException;
import org.icepdf.core.io.SeekableByteArrayInputStream;
import org.icepdf.core.pobjects.Document;
import org.icepdf.core.pobjects.Page;
import org.icepdf.core.pobjects.graphics.text.LineText;
import org.icepdf.core.pobjects.graphics.text.PageText;
import org.icepdf.core.util.GraphicsRenderingHints;
import org.springframework.core.io.ClassPathResource;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * pdf文件转图片(icepdf技术)
 * @author songjinzhou
 * @day 2016-11-03
 */
public class Test2Pdf {
    public static void main(String[] args) throws InterruptedException {
        String filePath = "UFF00.pdf";
        Document document = new Document();

        try {
//            document.setFile(filePath);
            document.setUrl(new ClassPathResource(filePath).getURL());
            float scale = 2f;// 缩放比例（大图）
            // float scale = 0.2f;// 缩放比例（小图）
            float rotation = 0f;// 旋转角度
//            for (int i = 0; i < document.getNumberOfPages(); i++) {
//                BufferedImage image = (BufferedImage) document.getPageImage(i,
//                        GraphicsRenderingHints.SCREEN,
//                        org.icepdf.core.pobjects.Page.BOUNDARY_CROPBOX,
//                        rotation, scale);
//                RenderedImage rendImage = image;
//                try {
//                    File file = new File("test" + i + ".png");
//                    // 这里png作用是：格式是jpg但有png清晰度
//                    ImageIO.write(rendImage, "png", file);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                image.flush();
//            }
            Page page=document.getPageTree().getPage(document.getNumberOfPages()-1);
            PageText pageText=page.getText();
            System.out.println(pageText.getPageLines());
            ArrayList<LineText> lines=pageText.getPageLines();

            ArrayList<LineText> newLines=new ArrayList<>();

            for(int i=lines.size()-4;i<lines.size();i++){
                newLines.add(lines.get(i));
            }
            for(LineText lt:newLines){
                lines.remove(lt);
            }
            document.dispose();
        } catch (PDFException e1) {
            e1.printStackTrace();
        } catch (PDFSecurityException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        System.out.println("======================完成============================");
    }
}