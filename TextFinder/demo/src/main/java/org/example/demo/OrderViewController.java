package org.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class OrderViewController implements Initializable {

    @FXML
    private Button nameButton;
    @FXML
    private Button dateButton;
    @FXML
    private Button sizeButton;

    private FinderController finderController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sortName();
    }

    @FXML
    private void sortName() {
        if (finderController != null) {
            finderController.sortDocuments(SortOption.NAME);
        }
    }

    @FXML
    private void sortDate() {
        if (finderController != null) {
            finderController.sortDocuments(SortOption.DATE);
        }
    }

    @FXML
    private void sortSize() {
        if (finderController != null) {
            finderController.sortDocuments(SortOption.SIZE);
        }
    }

    public void setFinderController(FinderController finderController) {
        this.finderController = finderController;
    }

    public enum SortOption {
        NAME("Nombre"),
        DATE("Fecha"),
        SIZE("Tamaño");

        private final String displayName;

        SortOption(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }

        public static SortOption fromDisplayName(String displayName) {
            for (SortOption option : values()) {
                if (option.getDisplayName().equals(displayName)) {
                    return option;
                }
            }
            throw new IllegalArgumentException("Unknown display name: " + displayName);
        }
    }
}
