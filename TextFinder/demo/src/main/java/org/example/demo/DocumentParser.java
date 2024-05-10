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
    private AVLTree tree;

    public DocumentParser() {
        this.tree = new AVLTree();
    }

    public String parseDocument(File file) throws IOException {
        String fileName = file.getName();
        String fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();

        switch (fileExtension) {
            case "txt":
                return parseTextFile(file);
            case "pdf":
                return parsePdfFile(file);
            case "docx":
                return parseDocxFile(file);
            default:
                throw new IllegalArgumentException("Unsupported file type: " + fileExtension);
        }
    }

    private String parseTextFile(File file) throws IOException {
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
                    tree.insert(word.toLowerCase(), new Document(file.getAbsolutePath(), type, file.getName(), date, size, ""), position++);
                    System.out.println("TxT: "+ word);
                }
            }
        }
        return content.toString();
    }

    private String parsePdfFile(File file) throws IOException {
        try (PDDocument document = PDDocument.load(file)) {
            PDFTextStripper stripper = new PDFTextStripper();
            String content = stripper.getText(document);

            // Obtener la fecha y el tamaño del archivo
            Long date = file.lastModified(); // Obtener la fecha de última modificación del archivo
            Long size = file.length(); // Obtener el tamaño del archivo en bytes

            // Supongamos que el tipo de documento es PDF
            DocumentType type = DocumentType.PDF;

            String[] words = content.split("\\s+"); // Dividir el contenido en palabras
            int position = 0;
            for (String word : words) {
                tree.insert(word.toLowerCase(), new Document(file.getAbsolutePath(), type, file.getName(), date, size, ""), position++);
                System.out.println("Se AgregoPDF: " + word);
            }

            return content;
        }
    }

    private String parseDocxFile(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file);
             XWPFDocument document = new XWPFDocument(fis);
             XWPFWordExtractor extractor = new XWPFWordExtractor(document)) {
            String content = extractor.getText();

            // Obtener la fecha y el tamaño del archivo
            Long date = file.lastModified(); // Obtener la fecha de última modificación del archivo
            Long size = file.length(); // Obtener el tamaño del archivo en bytes

            // Supongamos que el tipo de documento es DOCX
            DocumentType type = DocumentType.DOCX;

            String[] words = content.split("\\s+"); // Dividir el contenido en palabras
            int position = 0;
            for (String word : words) {
                tree.insert(word.toLowerCase(), new Document(file.getAbsolutePath(), type, file.getName(), date, size, ""), position++);
                System.out.println("Se AgregoDocx: " + word);
            }

            return content;
        }
    }

    public void parseDocumentsInFolder(File folder) throws IOException {
        if (!folder.isDirectory()) {
            throw new IllegalArgumentException("El argumento no es una carpeta válida.");
        }

        File[] files = folder.listFiles();
        if (files == null) {
            throw new IOException("Error al listar archivos en la carpeta.");
        }

        for (File file : files) {
            if (file.isFile()) {
                String fileName = file.getName();
                String fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();

                switch (fileExtension) {
                    case "txt":
                        parseTextFile(file);
                        break;
                    case "pdf":
                        parsePdfFile(file);
                        break;
                    case "docx":
                        parseDocxFile(file);
                        break;
                    default:
                        // Ignorar archivos con extensiones no compatibles
                        break;
                }
            }
        }
    }


}
