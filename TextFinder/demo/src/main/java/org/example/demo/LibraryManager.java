package org.example.demo;

import dataStructures.SinglyLinkedList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.zip.ZipFile;

public class LibraryManager {
    private final SinglyLinkedList<Document> documents;
    private static LibraryManager instance;

    public LibraryManager() {
        documents = new SinglyLinkedList<>();
        String folderPath = "documents";
        loadDocumentsFromFolder(folderPath);
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

    public void deleteDocument(int index) throws IndexOutOfBoundsException {
        try {
            documents.remove(getDocumentAtIndex(index));
        } catch (NoSuchElementException e) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
    }

    public Document getDocumentAtIndex(int index) throws IndexOutOfBoundsException {
        try {
            return documents.get(index);
        } catch (NoSuchElementException e) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
    }

    private void loadDocumentsFromFolder(String folderPath) {
        File folder = new File(folderPath);
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    Document document = new Document(file.getPath(), null, file.getName(), null, null, null);
                    documents.add(document);
                }
            }
        }
    }

    /**
     * Gets the library documents list.
     *
     * @return The library documents list.
     */

    public void quicksort() {
        quicksort(documents, 0, documents.getSize() - 1);
    }

    private void quicksort(SinglyLinkedList<Document> documents, int low, int high) {
        if (low < high) {
            int pi = partition(documents, low, high);
            quicksort(documents, low, pi - 1);
            quicksort(documents, pi + 1, high);
        }
    }

    private int partition(SinglyLinkedList<Document> documents, int low, int high) {
        Document pivot = documents.get(high);
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (documents.get(j).getFileName().compareTo(pivot.getFileName()) < 0) {
                i++;
                Document temp = documents.get(i);
                documents.set(i, documents.get(j));
                documents.set(j, temp);
            }
        }
        Document temp = documents.get(i + 1);
        documents.set(i + 1, documents.get(high));
        documents.set(high, temp);
        return i + 1;
    }

    public void bubblesort() {
        int n = documents.getSize();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                if (documents.get(j).getDate() > documents.get(j + 1).getDate()) {
                    Document temp = documents.get(j);
                    documents.set(j, documents.get(j + 1));
                    documents.set(j + 1, temp);
                }
            }
        }
    }

    public void radixsort() {
        int max = getMaxSize();
        for (int exp = 1; max / exp > 0; exp *= 10) {
            countSortBySize(documents, exp);
        }
    }

    private int getMaxSize() {
        int n = documents.getSize();
        long max = documents.get(0).getSize();
        for (int i = 1; i < n; i++) {
            if (documents.get(i).getSize() > max) {
                max = documents.get(i).getSize();
            }
        }
        return (int) max;
    }

    private void countSortBySize(SinglyLinkedList<Document> documents, int exp) {
        int n = documents.getSize();
        Document[] output = new Document[n];
        int[] count = new int[10];

        for (int i = 0; i < n; i++) {
            count[(int) ((documents.get(i).getSize() / exp) % 10)]++;
        }

        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }

        for (int i = n - 1; i >= 0; i--) {
            output[count[(int) ((documents.get(i).getSize() / exp) % 10)] - 1] = documents.get(i);
            count[(int) ((documents.get(i).getSize() / exp) % 10)]--;
        }

        for (int i = 0; i < n; i++) {
            documents.set(i, output[i]);
        }
    }

    public SinglyLinkedList<Document> getDocuments() {
        return documents;
    }
}
