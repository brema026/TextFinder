package org.example.demo;

import dataStructures.SinglyLinkedList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.zip.ZipFile;

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
        if (file.isDirectory()) {
            throw new Exception("Invalid file type: Directory.");
        } else if (file.getName().toLowerCase().endsWith(".zip")) {
            addFilesFromZip(file);
        } else {
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
     * Read the given file and returns its content.
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

    public void addFilesFromZip(File zipFile) {
        try (ZipFile zf = new ZipFile(zipFile)) {
            zf.stream()
                    .filter(entry -> !entry.isDirectory())
                    .forEach(entry -> {
                        String name = entry.getName().toLowerCase();
                        if (name.endsWith(".txt") || name.endsWith(".pdf") || name.endsWith(".docx")) {
                            try {
                                File tempFile = File.createTempFile("temp", ".tmp");
                                tempFile.deleteOnExit();
                                zf.getInputStream(entry).transferTo(java.nio.file.Files.newOutputStream(tempFile.toPath()));
                                addFile(tempFile);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteDocument(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= documents.getSize()) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        documents.remove(getDocumentAtIndex(index));
    }

    private Document getDocumentAtIndex(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= documents.getSize()) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        return documents.get(index);
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
