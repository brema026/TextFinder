package org.example.demo;

import dataStructures.AVLTree;
import dataStructures.SinglyLinkedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class FinderController implements Initializable {

    @FXML
    private Button findButton;
    @FXML
    private Button fileButton;
    @FXML
    private Button addFileButton;
    @FXML
    private Button deleteFileButton;
    @FXML
    private TextField finderText;
    @FXML
    private ListView<String> libraryListView;
    private OrderViewController.SortOption currentSortOption;
    private LibraryManager libraryManager;
    private TextAreaController textAreaController;
    private File storageFolder;
    private DocumentParser documentParser;
    private ResultController resultController;
    private OrderViewController orderViewController;

    public FinderController() {
        libraryManager = LibraryManager.getInstance();
        documentParser = new DocumentParser();
        storageFolder = new File("documents");
        if (!storageFolder.exists()) {
            boolean created = storageFolder.mkdirs();
            if (!created) {
                System.err.println("No se pudo crear la carpeta 'documents'.");
            }
        }
        currentSortOption = null;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ImageView viewFind = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/find.png"))));
        viewFind.setFitWidth(25);
        viewFind.setFitHeight(25);
        findButton.setGraphic(viewFind);
        findButton.setOnAction(e -> {
            try {
                searchText();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        ImageView openFileView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/open.png"))));
        openFileView.setFitHeight(25);
        openFileView.setFitWidth(25);
        fileButton.setGraphic(openFileView);

        ImageView addFileView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/lib.png"))));
        addFileView.setFitHeight(25);
        addFileView.setFitWidth(25);
        addFileButton.setGraphic(addFileView);

        ImageView deleteFileView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/delete.png"))));
        deleteFileView.setFitWidth(25);
        deleteFileView.setFitHeight(25);
        deleteFileButton.setGraphic(deleteFileView);

        deleteFileButton.setOnAction(e -> deleteSelectedFile());

        libraryListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                updateTextArea(newValue);
            }
        });

        File[] files = storageFolder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    String fileName = file.getName();
                    if (!isDocumentLoaded(fileName)) {
                        libraryListView.getItems().add(fileName);
                    }
                }
            }
        }
        //sortDocuments(OrderViewController.SortOption.NAME);
    }

    /**
     * Creates a TextFinder object to search the user input on the TextField.
     *
     * @throws IllegalArgumentException If the text to search is empty.
     * @throws NoSuchElementException   If the AVLTree is empty.
     */
    public void setResultController(ResultController resultController) {
        this.resultController = resultController;
    }

    public void setOrderViewController(OrderViewController orderViewController) {
        this.orderViewController = orderViewController;
        this.orderViewController.setFinderController(this);
    }

    public String getTextFromFinder() {
        String palabra = finderText.getText();
        return palabra;
    }

    public List<String> getAllFileNames() {
        ObservableList<String> loadedFileNames = libraryListView.getItems();
        return new ArrayList<>(loadedFileNames);
    }

    private void searchText() throws IllegalArgumentException, NoSuchElementException {
        String searchText = finderText.getText();

        if (!searchText.isEmpty()) {
            AVLTree avlTree = documentParser.getAVLTree();

            if (avlTree == null) {
                System.out.println("El árbol AVL es nulo.");
                return;
            }

            if (searchText.contains(" ")) {
                searchPhrase(searchText);
            } else {
                TextFinder textFinder = new TextFinder();
                Result[] results = textFinder.findText(searchText.toLowerCase(), avlTree);

                if (results.length > 0) {
                    System.out.println("Resultados de la búsqueda:");
                    for (Result result : results) {
                        System.out.println(result.getFragment());
                    }

                    textAreaController.highlightWord(textAreaController.getContent(), searchText.toLowerCase());

                    if (resultController != null) {
                        resultController.displayResults(results);
                    }

                } else {
                    System.out.println("No se encontraron resultados para la búsqueda.");
                }
            }

            System.out.println("Texto buscado: " + searchText);
        } else {
            throw new IllegalArgumentException("No se puede buscar una palabra o frase vacía.");
        }
    }

    private void searchPhrase(String phrase) {
        String searchText = phrase.toLowerCase();
        List<String> foundPhrases = new ArrayList<>();
        List<String> allFileNames = getAllFileNames();

        for (String fileName : allFileNames) {
            String content = loadFileContent(fileName);

            if (!content.isEmpty()) {
                // Realizar una copia del contenido original
                String originalContent = content;

                // Buscar la frase en el contenido del archivo
                if (content.toLowerCase().contains(searchText)) {
                    // Llamar a highlightPhrase con la copia del contenido
                    List<String> phrases = textAreaController.highlightPhrase(originalContent, searchText);
                    if (phrases != null && !phrases.isEmpty()) {
                        for (String foundPhrase : phrases) {
                            foundPhrases.add(foundPhrase + "  -->  " + fileName);
                        }
                    }
                    // No necesitamos actualizar loadedContents aquí
                }
            }
        }

        if (foundPhrases.isEmpty()) {
            System.out.println("No se encontraron resultados para la búsqueda de la frase.");
        } else {
            System.out.println("Resultados de la búsqueda de la frase:");
            for (String foundPhrase : foundPhrases) {
                System.out.println(foundPhrase);
            }
            if (resultController != null) {
                resultController.displayStringResults(foundPhrases.toArray(new String[0]));
            }
        }
    }

    private String loadFileContent(String fileName) {
        File file = new File(storageFolder, fileName);
        try {
            String content = documentParser.parseDocument(file);
            if (content.isEmpty()) {
                System.err.println("El contenido del archivo " + fileName + " está vacío.");
            } else {
                System.out.println("Contenido del archivo " + fileName + ":");
                System.out.println(content);
                loadedContents.put(fileName, content); // Actualizar o agregar el contenido al mapa
            }
            return content;
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al cargar el contenido del archivo " + fileName + ": " + e.getMessage());
            return "";
        }
    }


    public void setTextAreaController(TextAreaController textAreaController) {
        this.textAreaController = textAreaController;
    }

    private void deleteSelectedFile() {
        int selectedIndex = libraryListView.getSelectionModel().getSelectedIndex();
        System.out.println("Selected Index: " + selectedIndex);

        if (selectedIndex >= 0) {
            try {
                Document document = libraryManager.getDocumentAtIndex(selectedIndex);
                System.out.println("Deleting document: " + document.getFileName());
                libraryManager.deleteDocument(selectedIndex);
                File fileToDelete = new File(storageFolder, document.getFileName());
                System.out.println("Deleting file: " + fileToDelete.getName());
                fileToDelete.delete();
                refreshListView();
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleFileButton() {
        Stage stage = (Stage) fileButton.getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File");
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(stage);

        if (selectedFiles != null) {
            try {
                for (File selectedFile : selectedFiles) {
                    uploadFile(selectedFile);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadFile(File file) {
        try {
            SinglyLinkedList<Document> documents = libraryManager.getDocuments();
            libraryManager.addFile(file);
            libraryManager.quicksort(documents);
            File destFile = new File(storageFolder, file.getName());
            Files.copy(file.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Document List after adding file: " + libraryManager.getDocuments());
            String content = documentParser.parseDocument(file);
            if (textAreaController != null) {
                textAreaController.setContent(content);
            } else {
                System.err.println("TextAreaController no ha sido inicializado.");
            }
            applyCurrentSort();
            refreshListView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sortDocuments(OrderViewController.SortOption sortOption) {
        this.currentSortOption = sortOption;
        if (sortOption != null) {
            SinglyLinkedList<Document> documents = libraryManager.getDocuments();
            if (documents != null) {
                switch (sortOption) {
                    case NAME:
                        libraryManager.quicksort(documents);
                        printDocumentList(documents);
                        break;
                    case DATE:
                        libraryManager.bubblesort();
                        printBubblesort(documents);
                        break;
                    case SIZE:
                        libraryManager.radixsort();
                        printRadix(documents);
                        break;
                }
            } else {
                System.err.println("La lista de documentos es nula");
            }
        }
        refreshListView();
    }

    private void printDocumentList(SinglyLinkedList<Document> documents) {
        System.out.println("Lista de documentos después de quicksort:");
        for (int i = 0; i < documents.getSize(); i++) {
            System.out.println(documents.get(i).getFileName());
        }
    }

    private void printBubblesort(SinglyLinkedList<Document> documents) {
        System.out.println("Lista de documentos después de bubblesort:");
        for (int i = 0; i < documents.getSize(); i++) {
            System.out.println(documents.get(i).getFileName());
        }
    }

    private void printRadix(SinglyLinkedList<Document> documents) {
        System.out.println("Lista de documentos después de radix:");
        for (int i = 0; i < documents.getSize(); i++) {
            System.out.println(documents.get(i).getFileName());
        }
    }

    private void refreshListView() {
        libraryListView.getItems().clear();
        for (Document doc : libraryManager.getDocuments()) {
            libraryListView.getItems().add(doc.getFileName());
        }
    }

    private void applyCurrentSort() {
        if (currentSortOption != null) {
            sortDocuments(currentSortOption);
        }
    }

    private Map<String, String> loadedContents = new HashMap<>();

    public void updateTextArea(String selectedFileName) {
        if (loadedContents.containsKey(selectedFileName)) { // Verificar si el contenido ya está cargado
            textAreaController.setContent(loadedContents.get(selectedFileName));
        } else {
            File file = new File(storageFolder, selectedFileName);
            try {
                String content = documentParser.parseDocument(file);
                if (textAreaController != null) {
                    textAreaController.setContent(content);
                    loadedContents.put(selectedFileName, content); // Agregar el contenido cargado al mapa
                } else {
                    System.err.println("TextAreaController no ha sido inicializado.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isDocumentLoaded(String fileName) {
        ObservableList<String> items = libraryListView.getItems();
        return items.contains(fileName);
    }
}


