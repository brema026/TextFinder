package org.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;



public class AppController implements Initializable {

    private FinderController finderController;
    @FXML
    public HBox sectionBox;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        loadFinderSection();

    }

    private void loadFinderSection() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("View-Controllers/finder-view.fxml"));
            Parent finderSection = loader.load();
            finderController = loader.getController();
            sectionBox.getChildren().add(finderSection);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}