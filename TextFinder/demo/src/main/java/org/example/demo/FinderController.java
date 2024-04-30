package org.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class FinderController implements Initializable {
    @FXML
    private Button findButtom;
    @FXML
    private Button fileButtom;
    @FXML
    private Button addFileButtom;
    @FXML
    private Button deleteFileButtom;
    @FXML
    private TextField finderText;


    public void initialize(URL location, ResourceBundle resources) {

        // Create imageview with background image
        ImageView viewFind = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/find.png"))));
        viewFind.setFitWidth(25);
        viewFind.setFitHeight(25);
        findButtom.setGraphic(viewFind);
        findButtom.setOnAction(e -> {
            try {
                onClick();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });


        ImageView openFileView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/open.png"))));
        openFileView.setFitHeight(25);
        openFileView.setFitWidth(25);
        fileButtom.setGraphic(openFileView);

        ImageView addFileView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/add.png"))));
        addFileView.setFitHeight(25);
        addFileView.setFitWidth(25);
        addFileButtom.setGraphic(addFileView);

        ImageView deleteFileView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/delete.png"))));
        deleteFileView.setFitWidth(25);
        deleteFileView.setFitHeight(25);
        deleteFileButtom.setGraphic(deleteFileView);

    }

    private void onClick() throws Exception {
        String text = finderText.getText();
        if (text.isEmpty()) {
            throw new Exception("No text where entered.");
        } else {
            System.out.println(text);
        }
    }

}
