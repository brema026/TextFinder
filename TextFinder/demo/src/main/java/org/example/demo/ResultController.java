package org.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import dataStructures.SinglyLinkedList;
import javafx.scene.layout.VBox;

public class ResultController implements Initializable {
    /*@FXML
    private VBox resultControllerVBox;*/
    @FXML
    private ListView<String> resultList;

    private LibraryManager libraryManager;

    public ResultController() {
        libraryManager = LibraryManager.getInstance();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //refreshListView();
    }

    /*@FXML
    public void refreshListView() {
        SinglyLinkedList<Document> documents = libraryManager.getDocuments();
        ObservableList<String> items = FXCollections.observableArrayList();

        System.out.println("Tamaño de la lista de documentos: " + documents.getSize());

        for (int i = 0; i < documents.getSize(); i++) {
            Document document = documents.get(i);
            items.add(document.getFileName());
        }

        System.out.println("Items antes de establecerla en la ListView: " + items);

        resultList.setItems(items);
        System.out.println("Corre hasta acá?");
        resultList.refresh();
    }*/
}


