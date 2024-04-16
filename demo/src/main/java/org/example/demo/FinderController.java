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
    private Button addFileButtom;
    @FXML
    private TextField FinderText;


    public void initialize(URL location, ResourceBundle resources){

        //Create imageview with background image
        ImageView viewFind = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/find.png"))));
        viewFind.setFitWidth(30);
        viewFind.setFitHeight(30);
        findButtom.setGraphic(viewFind);

        ImageView openFileView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/open.png"))));
        openFileView.setFitHeight(30);
        openFileView.setFitWidth(30);
        addFileButtom.setGraphic(openFileView);

    }
}
