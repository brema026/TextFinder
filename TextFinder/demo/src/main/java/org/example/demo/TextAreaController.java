package org.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.Node;
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

    public String getContent() {
        StringBuilder contentBuilder = new StringBuilder();
        for (Node node : textFlow.getChildren()) {
            if (node instanceof Text) {
                Text textNode = (Text) node;
                contentBuilder.append(textNode.getText());
            }
        }
        return contentBuilder.toString();
    }
    public void setContent(String content) {
        textFlow.getChildren().clear();

        textFlow.getChildren().add(new Text(content));
    }

    public void highlightWord(String content, String word) {
        textFlow.getChildren().clear();

        // Busca la palabra sin importar mayúsculas o minúsculas
        int index = content.toLowerCase().indexOf(word.toLowerCase());
        if (index != -1) {
            // Obtiene la palabra original
            String originalWord = content.substring(index, index + word.length());

            String beforeText = content.substring(0, index);
            String highlightedText = originalWord;
            String afterText = content.substring(index + word.length());

            setText(beforeText, highlightedText, afterText);
        } else {
            // La palabra no se encontró en el contenido
            setContent(content);
        }
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
