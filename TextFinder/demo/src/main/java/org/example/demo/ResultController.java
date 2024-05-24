package org.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import org.apache.poi.ss.formula.functions.T;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ResultController implements Initializable {
    @FXML
    private ListView<String> resultList;
    private FinderController finderController;
    private TextAreaController textAreaController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Agregar un manejador de eventos de doble clic al ListView
        resultList.setOnMouseClicked(this::handleItemClick);
    }

    private void handleItemClick(javafx.scene.input.MouseEvent event) {
        if (event.getClickCount() == 1) {
            String selectedItem = resultList.getSelectionModel().getSelectedItem();
            System.out.println("SelectedItem: " + selectedItem);

            // Dividir la cadena por el separador " --> "
            String[] parts = selectedItem.split(" --> ");

            if (parts.length >= 2) { // Cambiado de 3 a 2
                String searchText = parts[0].trim();
                String selectedFileName = parts[1].trim();
                String positionPart = parts.length == 3 ? parts[2].trim() : null; // Manejar cuando no haya posición

                try {
                    int[] position = positionPart != null ? getPositionFromSelectedItem(positionPart) : new int[]{0}; // Usar 0 si no hay posición
                    if (finderController != null) {
                        finderController.updateTextArea(selectedFileName);
                        System.out.println("Listo");

                        textAreaController.focusWord(position[0]);
                    }
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getMessage());
                }
            } else {
                System.err.println("Formato de selectedItem inesperado: " + selectedItem);
            }
        }
    }

    private int[] getPositionFromSelectedItem(String positionPart) {
        try {
            String[] parts = positionPart.split(",");
            int[] position = new int[parts.length];
            for (int i = 0; i < parts.length; i++) {
                position[i] = Integer.parseInt(parts[i].trim());
            }
            return position;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("No se pudo extraer la posición del archivo: " + positionPart, e);
        }
    }




    public void displayResults(Result[] results) {
        ObservableList<String> items = FXCollections.observableArrayList();
        resultList.getItems().clear();
        String searchText = finderController.getTextFromFinder();

        for (Result result : results) {
            // Obtener el nombre del archivo de la instancia de Result
            String fileName = result.getDocument().getFileName();
            String positionString = serializePosition(result.getPosition());
            System.out.println("Nombre del archivo: " + fileName);
            // Obtener la frase encontrada en el documento
            String item = searchText + "  -->  " + fileName + " --> " + positionString;
            items.add(item);
        }

        resultList.setItems(items);
    }
    public void displayStringResults(String[] results) {
        ObservableList<String> items = FXCollections.observableArrayList();
        resultList.getItems().clear();
        String searchText = finderController.getTextFromFinder();

        List<String> allFileNames = finderController.getAllFileNames();

        for (String result : results) {
            for (String fileName : allFileNames) {
                if (result.contains(fileName)) {
                    String item = result;
                    items.add(item);
                }
            }
        }

        resultList.setItems(items);
    }

    private String serializePosition(int[] position) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < position.length; i++) {
            sb.append(position[i]);
            if (i < position.length - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    public void setTextAreaController(TextAreaController textAreaController) {
        this.textAreaController = textAreaController;}
    public void setFinderController(FinderController finderController) {
        this.finderController = finderController;
    }
}
