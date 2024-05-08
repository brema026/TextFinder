package org.example.demo;


import dataStructures.AVLTree;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.example.demo.Document;
import org.example.demo.Occurrence;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class DocumentParser {

    public static String parseDocument(File file, AVLTree tree) throws IOException {
        String fileName = file.getName();
        String fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();

        switch (fileExtension) {
            case "txt":
                return parseTextFile(file, tree);
            case "pdf":
                return parsePdfFile(file, tree);
            case "docx":
                return parseDocxFile(file, tree);
            default:
                throw new IllegalArgumentException("Unsupported file type: " + fileExtension);
        }
    }

    private static String parseTextFile(File file, AVLTree index) throws IOException {
        StringBuilder content = new StringBuilder();
        try (java.util.Scanner scanner = new java.util.Scanner(file)) {
            int position = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                content.append(line).append("\n");
                String[] words = line.split("\\s+");
                for (String word : words) {
                    // Obtener la fecha y el tamaño del archivo
                    Long date = file.lastModified(); // Obtener la fecha de última modificación del archivo
                    Long size = file.length(); // Obtener el tamaño del archivo en bytes

                    // Supongamos que el tipo de documento es TXT
                    DocumentType type = DocumentType.TXT;

                    // Creamos el objeto Document con los parámetros obtenidos
                    index.insert(word.toLowerCase(), new Document(file.getAbsolutePath(), type, file.getName(), date, size, ""), position++);
                }
            }
        }
        return content.toString();
    }


    private static String parsePdfFile(File file, AVLTree index) throws IOException {
        try (PDDocument document = PDDocument.load(file)) {
            PDFTextStripper stripper = new PDFTextStripper();
            String content = stripper.getText(document);

            // Obtener la fecha y el tamaño del archivo
            Long date = file.lastModified(); // Obtener la fecha de última modificación del archivo
            Long size = file.length(); // Obtener el tamaño del archivo en bytes

            // Supongamos que el tipo de documento es PDF
            DocumentType type = DocumentType.PDF;

            // Insertar el contenido del PDF en el árbol AVL
            index.insert(content.toLowerCase(), new Document(file.getAbsolutePath(), type, file.getName(), date, size, content), -1);

            return content;
        }
    }

    private static String parseDocxFile(File file, AVLTree index) throws IOException {
        try (FileInputStream fis = new FileInputStream(file);
             XWPFDocument document = new XWPFDocument(fis);
             XWPFWordExtractor extractor = new XWPFWordExtractor(document)) {
            String content = extractor.getText();

            // Obtener la fecha y el tamaño del archivo
            Long date = file.lastModified(); // Obtener la fecha de última modificación del archivo
            Long size = file.length(); // Obtener el tamaño del archivo en bytes

            // Supongamos que el tipo de documento es DOCX
            DocumentType type = DocumentType.DOCX;

            // Insertar el contenido del DOCX en el árbol AVL
            index.insert(content.toLowerCase(), new Document(file.getAbsolutePath(), type, file.getName(), date, size, content), -1);

            return content;
        }
    }

}
