package org.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
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

    public void displayResults(Result[] results) {
        ObservableList<String> items = FXCollections.observableArrayList();

        resultList.getItems().clear();
        String searchText = finderController.getTextFromFinder();

        for (Result result : results) {

            String item = searchText + "  -->  " + result.getDocument().getFileName();
            items.add(item);
            resultList.setItems(items);
        }
    }
    public void displayResultsPhrase(){
        ObservableList<String> items = FXCollections.observableArrayList();
        resultList.getItems().clear();
        String searchText = finderController.getTextFromFinder();



    }
}


