package org.example.demo;

import dataStructures.AVLTree;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.ResourceBundle;

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

        ImageView addFileView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/add.png"))));
        addFileView.setFitHeight(25);
        addFileView.setFitWidth(25);
        addFileButton.setGraphic(addFileView);

        ImageView deleteFileView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/delete.png"))));
        deleteFileView.setFitWidth(25);
        deleteFileView.setFitHeight(25);
        deleteFileButton.setGraphic(deleteFileView);

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

}
