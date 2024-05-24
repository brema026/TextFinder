package org.example.demo;

import dataStructures.AVLTree;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
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
    public VBox sectionSecondaryBox;
    @FXML
    public VBox sectionOrderBox;

    public static AVLTree avlTree;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadFinderSection();
        loadTextSection();
        loadOrderSection();
        loadResultSection();
        // Configurar el controlador del área de texto en el controlador del buscador
        finderController.setTextAreaController(textAreaController);
        finderController.setResultController(resultController);
        resultController.setFinderController(finderController);
        resultController.setTextAreaController(textAreaController);

        DocumentParser documentParser = new DocumentParser(); // Crear una instancia de DocumentParser
        try {
            documentParser.parseDocumentsInFolder(new File("documents")); // Llamar al método parseDocumentsInFolder() de la instancia
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            sectionSecondaryBox.getChildren().add(textSection);
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
            System.out.println("ResultController loaded successfully: " + resultController);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}