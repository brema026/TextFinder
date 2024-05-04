package org.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import java.net.URL;
import java.util.ResourceBundle;

public class TextAreaController implements Initializable {

    @FXML
    private TextArea textArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textArea.setEditable(false);
        // Configurar la fuente y el tamaño de texto
        Font font = new Font("Arial", 12);
        textArea.setFont(font);

        // Configurar el espaciado de línea mediante CSS
        textArea.setStyle("-fx-line-spacing: 1.5em;");

        // Configurar el color del texto
        textArea.setStyle("-fx-text-fill: black;");

        // Configurar el TextArea para que no tenga bordes
        textArea.setStyle("-fx-border-color: transparent;");

        // Establecer la alineación del texto
        textArea.setStyle("-fx-alignment: top-left;");
    }

    public void setContent(String content) {
        textArea.setText(content);
    }
}
