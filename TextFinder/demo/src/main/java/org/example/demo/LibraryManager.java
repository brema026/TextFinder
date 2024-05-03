package org.example.demo;

import dataStructures.SinglyLinkedList;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LibraryManager {
    private final SinglyLinkedList<Document> documents;
    private static LibraryManager instance;

    public LibraryManager() {
        documents = new SinglyLinkedList<>();
    }

    public static LibraryManager getInstance() {
        if (instance == null) {
            instance = new LibraryManager();
        }
        return instance;
    }

    /**
     * Takes a given file and adds it to the document list.
     *
     * @param file File to add in the list.
     * @throws Exception If the given file is not valid or doesn't exist.
     */
    public void addFile(File file) throws Exception {
        String filePath = file.getPath();
        String fileName = file.getName();

        // Checks file extension
        String fileExtension = getFileExtension(filePath);
        DocumentType documentType = switch (fileExtension) {
            case "txt" -> DocumentType.TXT;
            case "pdf" -> DocumentType.PDF;
            case "docx" -> DocumentType.DOCX;
            default -> throw new Exception("The given file is not a valid file.");
        };

        // Reads the file
        String fileContent = readFile(file);

        // Creates the document object
        Document document = new Document(filePath, documentType, fileName,
                file.lastModified(), file.length(), fileContent);

        // Add the object to the list
        documents.add(document);
    }

    /**
     * Gets the file extension from the file path.
     *
     * @param filePath The file path.
     * @return The file extension.
     * @throws Exception If the given file path is not a valid path (Doesn't contain the format '.' + extension).
     */
    private String getFileExtension(String filePath) throws Exception {
        int dotIndex = filePath.lastIndexOf('.');
        if (dotIndex > 0) {
            return filePath.substring(dotIndex + 1);

        } else {
            throw new Exception("Invalid file path.");
        }
    }

    /**
     * Read the given file and returns it's content.
     *
     * @param file File to read.
     * @return The file content.
     * @throws FileNotFoundException If the file doesn't exist.
     */
    private String readFile(File file) throws FileNotFoundException {
        Scanner fileReader = new Scanner(file);
        StringBuilder content = new StringBuilder();

        while (fileReader.hasNextLine()) {
            content.append(fileReader.nextLine());
            content.append("\n");
        }

        return content.toString().strip();
    }

    /**
     * Gets the library documents list.
     *
     * @return The library documents list.
     */
    public SinglyLinkedList<Document> getDocuments() {
        return documents;
    }
}
