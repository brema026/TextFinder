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
    @FXML
    private ListView<String> resultList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}


