package com.example.utilsdemo.pdf;

import com.sun.javafx.geom.Rectangle;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import java.io.File;
import java.io.IOException;

/**
 * 功能 PDF读写类
 * @CreateTime 2011-4-14 下午02:44:11
 */
public class PDFUtil {

    public static void main(String[] args) {

        try (PDDocument document = PDDocument.load(new File("D:/1/22.pdf"))) {

            document.getClass();

            if(!document.isEncrypted()) {
                PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                stripper.setSortByPosition(true);
                PDFTextStripper tStripper = new PDFTextStripper();

                String pdfFileInText = tStripper.getText(document);

                String[] lines = pdfFileInText.split("\\r?\\n");
                for(String line : lines) {
                    System.out.println(line);
                }

            }

        } catch (InvalidPasswordException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }

    }

}