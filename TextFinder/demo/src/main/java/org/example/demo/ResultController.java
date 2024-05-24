package org.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ResultController implements Initializable {
    @FXML
    private ListView<String> resultList;
    private FinderController finderController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    public void setFinderController(FinderController finderController) {
        this.finderController = finderController;
    }

    public void displayStringResults(String[] results) {
        ObservableList<String> items = FXCollections.observableArrayList();
        resultList.getItems().clear();
        String searchText = finderController.getTextFromFinder();

        List<String> allFileNames = finderController.getAllFileNames(); // Obtener todos los nombres de archivo

        for (String result : results) {
            // Crear el elemento que mostrará el texto buscado junto con todos los nombres de archivo
            for (String fileName : allFileNames) {
                String item = result + "  -->  " + fileName;
                items.add(item);
            }
        }

        resultList.setItems(items);
    }


    public void displayResults(Result[] results) {
        ObservableList<String> items = FXCollections.observableArrayList();
        resultList.getItems().clear();
        String searchText = finderController.getTextFromFinder();

        for (Result result : results) {
            // Obtener el nombre del archivo de la instancia de Result
            String fileName = result.getDocument().getFileName();
            System.out.println("Nombre del archivo: " + fileName);
            // Obtener la frase encontrada en el documento
            String item = searchText + "  -->  " + fileName;
            items.add(item);
        }

        resultList.setItems(items);
    }
}


