package com.spring.ocr.service.impl;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
public class OcrServiceTesseract {
        public static void main(String[] args) {
            String pdfFilePath = "path/to/your/input.pdf"; // Replace with the actual path to your PDF file
            String outputFolder = "path/to/your/output"; // Replace with the desired output folder

            convertPDFToImages(pdfFilePath, outputFolder);
            performOCR(outputFolder);
        }

        private static void convertPDFToImages(String pdfFilePath, String outputFolder) {
            try {
                PDDocument document = PDDocument.load(new File(pdfFilePath));
                PDFRenderer pdfRenderer = new PDFRenderer(document);

                for (int pageIndex = 0; pageIndex < document.getNumberOfPages(); pageIndex++) {
                    BufferedImage image = pdfRenderer.renderImage(pageIndex);
                    ImageIO.write(image, "png", new File(outputFolder, "page_" + (pageIndex + 1) + ".png"));
                }

                document.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private static void performOCR(String inputFolder) {
            ITesseract tesseract = new Tesseract();
            tesseract.setDatapath("path/to/tessdata"); // Replace with the actual path to tessdata folder

            File folder = new File(inputFolder);
            File[] imageFiles = folder.listFiles((dir, name) -> name.endsWith(".png"));

            if (imageFiles != null) {
                for (File imageFile : imageFiles) {
                    try {
                        String result = tesseract.doOCR(imageFile);
                        System.out.println("Text extracted from " + imageFile.getName() + ":\n" + result);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

