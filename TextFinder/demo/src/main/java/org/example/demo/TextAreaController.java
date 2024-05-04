package org.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import java.net.URL;
import java.util.ResourceBundle;



import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

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

    public TextFlow textFlow;
    public void initialize(URL location, ResourceBundle resources){
        setText("Esto es una ", "palabra", " destacada.");
    }

    /**
     * Sets the text in the TextFlow with optional highlighting for a specific section.
     *
     * @param beforeText     The text to be displayed before the highlighted section.
     * @param highlightedText The text to be highlighted.
     * @param afterText      The text to be displayed after the highlighted section.
     */
    private void setText(String beforeText, String highlightedText, String afterText) {
        textFlow.getChildren().clear();

        textFlow.getChildren().addAll(
                new Text(beforeText),
                createHighlightedText(highlightedText),
                new Text(afterText)
        );
    }

    /**
     * Creates a Text node with the specified text and applies a highlighting style to it.
     *
     * @param text The text content of the Text node.
     * @return A Text node with the specified text and highlighting style.
     */
    private Text createHighlightedText(String text) {
        Text highlightedText = new Text(text);
        highlightedText.getStyleClass().add("highlightedText");

        return highlightedText;

    }

    public void setContent(String content) {
        textArea.setText(content);
    }
}
