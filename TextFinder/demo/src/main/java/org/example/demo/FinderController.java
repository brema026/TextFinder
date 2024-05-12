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
    private LibraryManager libraryManager;
    private TextAreaController textAreaController;
    private File storageFolder;
    private DocumentParser documentParser;
    private ResultController resultController;

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

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Create imageview with background image
        ImageView viewFind = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/find.png"))));
        viewFind.setFitWidth(25);
        viewFind.setFitHeight(25);
        findButton.setGraphic(viewFind);
        findButton.setOnAction(e -> {
            try {
                searchText();
            } catch (Exception ex) {
                ex.printStackTrace(); // o muestra un mensaje de error al usuario
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

    public String getTextFromFinder() {
        String palabra = finderText.getText();
        return palabra;
    }

    private void searchText() throws IllegalArgumentException, NoSuchElementException {
        String searchText = finderText.getText(); // No convertir la frase buscada a minúsculas

        if (!searchText.isEmpty()) {
            AVLTree avlTree = documentParser.getAVLTree();

            if (avlTree == null) {
                System.out.println("El árbol AVL es nulo.");
                return;
            }

            // Llamar a searchPhrase si searchText contiene espacios (es decir, es una frase)
            if (searchText.contains(" ")) {
                searchPhrase(searchText);
            } else {
                // Si no contiene espacios, buscar como una palabra individual
                TextFinder textFinder = new TextFinder();
                Result[] results = textFinder.findText(searchText.toLowerCase(), avlTree); // Convertir la palabra individual a minúsculas

                if (results.length > 0) {
                    System.out.println("Resultados de la búsqueda:");
                    for (Result result : results) {
                        System.out.println(result.getFragment());
                    }

                    // Resaltar la palabra en el TextAreaController
                    textAreaController.highlightWord(textAreaController.getContent(), searchText.toLowerCase()); // Convertir la palabra individual a minúsculas

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
        String content = textAreaController.getContent(); // Obtener el contenido del TextArea

        if (!phrase.isEmpty() && !content.isEmpty()) {
            textAreaController.highlightPhrase(content, phrase);
        } else {
            System.out.println("La frase o el contenido están vacíos.");
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
            libraryManager.addFile(file);
            File destFile = new File(storageFolder, file.getName());
            Files.copy(file.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Document List after adding file: " + libraryManager.getDocuments());
            refreshListView();

            String content = documentParser.parseDocument(file); // Llamar al método parseDocument() de la instancia

            if (textAreaController != null) {
                textAreaController.setContent(content);
            } else {
                System.err.println("TextAreaController no ha sido inicializado.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void refreshListView() {
        ObservableList<String> items = FXCollections.observableArrayList();

        SinglyLinkedList<Document> documents = libraryManager.getDocuments();
        System.out.println("Tamaño de la lista de documentos: " + documents.getSize());

        for (int i = 0; i < documents.getSize(); i++) {
            Document document = documents.get(i);
            items.add(document.getFileName());
        }

        System.out.println("Items antes de establecerla en la ListView: " + items);

        libraryListView.setItems(items);
        libraryListView.refresh();
    }

    private void updateTextArea(String selectedFileName) {
        File file = new File(storageFolder, selectedFileName);
        try {
            String content = documentParser.parseDocument(file);
            if (textAreaController != null) {
                textAreaController.setContent(content);
            } else {
                System.err.println("TextAreaController no ha sido inicializado.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private boolean isDocumentLoaded(String fileName) {
        ObservableList<String> items = libraryListView.getItems();
        return items.contains(fileName);
    }
}


