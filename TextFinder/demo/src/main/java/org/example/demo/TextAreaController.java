package org.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        // Patrón para buscar la palabra (ignorando mayúsculas y minúsculas)
        Pattern pattern = Pattern.compile("\\b" + Pattern.quote(word) + "\\b", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(content);

        int lastMatchEnd = 0;
        while (matcher.find()) {
            // Agrega el texto entre las coincidencias anteriores y la actual
            if (matcher.start() > lastMatchEnd) {
                String beforeText = content.substring(lastMatchEnd, matcher.start());
                textFlow.getChildren().add(new Text(beforeText));
            }

            // Agrega la palabra resaltada
            String highlightedText = content.substring(matcher.start(), matcher.end());
            textFlow.getChildren().add(createHighlightedText(highlightedText));

            lastMatchEnd = matcher.end();
        }

        // Agrega el texto después de la última coincidencia
        if (lastMatchEnd < content.length()) {
            String afterText = content.substring(lastMatchEnd);
            textFlow.getChildren().add(new Text(afterText));
        }
    }

    public void highlightPhrase(String content, int startIdx, int endIdx) {
        textFlow.getChildren().clear();

        // Agregar el texto antes de la frase resaltada
        String beforeText = content.substring(0, startIdx);
        textFlow.getChildren().add(new Text(beforeText));

        // Agregar la frase resaltada
        String highlightedText = content.substring(startIdx, endIdx);
        textFlow.getChildren().add(createHighlightedText(highlightedText));

        // Agregar el texto después de la frase resaltada
        String afterText = content.substring(endIdx);
        textFlow.getChildren().add(new Text(afterText));
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
