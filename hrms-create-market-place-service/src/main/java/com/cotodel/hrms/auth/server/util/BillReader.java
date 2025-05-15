package com.cotodel.hrms.auth.server.util;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BillReader {
	private static final Logger logger = LoggerFactory.getLogger(BillReader.class);
	public static void main(String[] args) {

        // Load the image file
        File billImage = new File("C:\\Users\\fakhr\\Downloads\\Bill.JPEG");

        // Create a Tesseract instance
        Tesseract tesseract = new Tesseract();

        // Set the path to Tesseract OCR installation folder
        tesseract.setDatapath("C:\\Users\\fakhr\\Downloads");

        // Optionally set the language to English
        tesseract.setLanguage("eng");

        try {
            // Perform OCR and get text
            String result = tesseract.doOCR(billImage);

            // Print the full extracted text
            System.out.println("Extracted Text from Bill Image:\n");
            System.out.println(result);

        } catch (TesseractException e) {
            System.err.println("Error during OCR: " + e.getMessage());
        }
    }
}


