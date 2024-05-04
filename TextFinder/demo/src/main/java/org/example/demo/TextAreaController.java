package org.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class TextAreaController implements Initializable {

    @FXML
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
}
