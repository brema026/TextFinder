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
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

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

    public FinderController() {
        libraryManager = LibraryManager.getInstance();
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
                throw new RuntimeException(ex);
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
    }

    /**
     * Creates a TextFinder object to search the user input on the TextField.
     *
     * @throws IllegalArgumentException If the text to search is empty.
     * @throws NoSuchElementException   If the AVLTree is empty.
     */
    private void searchText() throws IllegalArgumentException, NoSuchElementException {
        String text = finderText.getText();

        if (!text.isEmpty()) {
            TextFinder textFinder = new TextFinder();
            Result[] results = textFinder.findText(text, new AVLTree());
            System.out.println(results[0].fragment); // Temp
            System.out.println(text);

        } else {
            throw new IllegalArgumentException("No se puede buscar una palabra o frase vacía.");
        }
    }

    public void setTextAreaController(TextAreaController textAreaController) {
        this.textAreaController = textAreaController;
    }

    private void deleteSelectedFile() {
        int selectedIndex = libraryListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            libraryManager.deleteDocument(selectedIndex);
            refreshListView();
        }
    }

    @FXML
    public void handleFileButton() {
        Stage stage = (Stage) fileButton.getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File");
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(stage);

        if (selectedFiles != null) {
            try {
                for (File selectedFile : selectedFiles) {
                    if (selectedFile.getName().toLowerCase().endsWith(".zip")) {
                        libraryManager.addFilesFromZip(selectedFile);
                    } else {
                        uploadFile(selectedFile);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadFile(File file) {
        try {
            libraryManager.addFile(file);
            System.out.println("Document List after adding file: " + libraryManager.getDocuments());
            refreshListView();

            AVLTree tree = new AVLTree(); // Suponiendo que AVLTree almacena Strings internamente
            String content = DocumentParser.parseDocument(file, tree);

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
        SinglyLinkedList<Document> documents = libraryManager.getDocuments();

        for (Document document : documents) {
            if (document.getFileName().equals(selectedFileName)) {
                String content = document.getContent();
                if (textAreaController != null) {
                    textAreaController.setContent(content);
                } else {
                    System.err.println("TextAreaController no ha sido inicializado.");
                }
                break;
            }
        }
    }
}


