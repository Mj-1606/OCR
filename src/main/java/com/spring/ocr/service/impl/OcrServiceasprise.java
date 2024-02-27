package com.spring.ocr.service.impl;
import com.asprise.ocr.Ocr;

import java.io.File;

public class OcrServiceasprise {

    public static void main(String[] args) {
        String imagePath = "\"C:\\Users\\garvi\\OneDrive\\Pictures\\Screenshots\\Screenshot (53).png\""; // Replace with the actual path to your image file
        String licenseKey = "your_license_key"; // Replace with your Asprise OCR SDK license key

        try {
            setUpAspriseOCR(licenseKey); // Set up the OCR engine with the license key

            String result = performOCR(imagePath);
            System.out.println("Text extracted from the image:\n" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void setUpAspriseOCR(String licenseKey) {
        Ocr.setUp(); // Set up the OCR engine

        // Set the Asprise OCR license key
//        Ocr.setLicenseKey(licenseKey);

    }

    private static String performOCR(String imagePath) {
        Ocr ocr = new Ocr(); // Create an OCR object

        try {
            ocr.startEngine("eng", Ocr.SPEED_FASTEST); // Specify language and OCR speed

            String result = ocr.recognize(new File[]{new File(imagePath)},
                    Ocr.RECOGNIZE_TYPE_TEXT, Ocr.OUTPUT_FORMAT_PLAINTEXT);

            return result.trim(); // Trim to remove leading/trailing whitespace
        } catch (Exception e) {
            e.printStackTrace();
            return "Error during OCR: " + e.getMessage();
        } finally {
            ocr.stopEngine(); // Stop the OCR engine to release resources
        }
    }
}
