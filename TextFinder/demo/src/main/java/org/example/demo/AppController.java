package org.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AppController implements Initializable {

    private FinderController finderController;
    private TextAreaController textAreaController;
    private OrderViewController orderController;
    private ResultController resultController;
    @FXML
    public HBox sectionBox;
    @FXML
    public VBox sectionSecundaryBox;
    @FXML
    public VBox sectionOrderBox;


    @Override
    public void initialize(URL location, ResourceBundle resources){
        loadFinderSection();
        loadTextSection();
        loadOrderSection();
        loadResultSection();

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
    private void loadTextSection(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("View-Controllers/text-view.fxml"));
            Parent textSection = loader.load();
            textAreaController = loader.getController();
            sectionSecundaryBox.getChildren().add(textSection);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    private void loadOrderSection(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("View-Controllers/order-view.fxml"));
            Parent orderSection = loader.load();
            orderController = loader.getController();
            sectionOrderBox.getChildren().add(orderSection);

        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    private void loadResultSection(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("View-Controllers/result-view.fxml"));
            Parent resultSection = loader.load();
            resultController = loader.getController();
            sectionOrderBox.getChildren().add(resultSection);

        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}