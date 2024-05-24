package org.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.control.ScrollPane;

public class TextAreaController implements Initializable {

    @FXML
    public TextFlow textFlow;
    @FXML
    public ScrollPane scrollPane;

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
        String currentContent = getContent(); // Obtener el contenido actual del TextFlow
        if (!content.equals(currentContent)) { // Verificar si el nuevo contenido es diferente al contenido actual
            textFlow.getChildren().clear();
            textFlow.getChildren().add(new Text(content));
        }
    }

    public void highlightWord(String content, String word) {
        textFlow.getChildren().clear();
        Pattern pattern = Pattern.compile("\\b" + Pattern.quote(word) + "\\b", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(content);

        int lastMatchEnd = 0;
        while (matcher.find()) {
            if (matcher.start() > lastMatchEnd) {
                String beforeText = content.substring(lastMatchEnd, matcher.start());
                textFlow.getChildren().add(new Text(beforeText));
            }
            String highlightedText = content.substring(matcher.start(), matcher.end());
            textFlow.getChildren().add(createHighlightedText(highlightedText));
            lastMatchEnd = matcher.end();
        }
        if (lastMatchEnd < content.length()) {
            String afterText = content.substring(lastMatchEnd);
            textFlow.getChildren().add(new Text(afterText));
        }
    }

    public List<String> highlightPhrase(String content, String phrase) {
        textFlow.getChildren().clear();
        List<String> foundPhrases = new ArrayList<>();

        if (!phrase.isEmpty() && !content.isEmpty()) {
            Pattern pattern = Pattern.compile("\\b" + Pattern.quote(phrase) + "\\b", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(content);

            int lastMatchEnd = 0;
            while (matcher.find()) {
                if (matcher.start() > lastMatchEnd) {
                    String beforeText = content.substring(lastMatchEnd, matcher.start());
                    textFlow.getChildren().add(new Text(beforeText));
                }

                String highlightedText = content.substring(matcher.start(), matcher.end());
                foundPhrases.add(highlightedText);
                textFlow.getChildren().add(createHighlightedText(highlightedText));
                lastMatchEnd = matcher.end();
            }

            if (lastMatchEnd < content.length()) {
                String afterText = content.substring(lastMatchEnd);
                textFlow.getChildren().add(new Text(afterText));
            }
        } else {
            System.out.println("La frase o el contenido están vacíos.");
        }
        return foundPhrases;
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
    public void focusWord(int position) {
        if (position >= 0 && position < textFlow.getChildren().size()) {
            Node node = textFlow.getChildren().get(position);
            if (node instanceof Text) {
                Text textNode = (Text) node;
                // Obtener las coordenadas Y del nodo en relación con el TextFlow
                Bounds bounds = textNode.getBoundsInParent();
                double nodeMinY = bounds.getMinY();
                double nodeMaxY = bounds.getMaxY();
                // Obtener la altura visible del ScrollPane
                double visibleHeight = scrollPane.getViewportBounds().getHeight();
                // Obtener el valor de desplazamiento actual
                double currentScrollPosition = scrollPane.getVvalue() * (textFlow.getHeight() - visibleHeight);
                // Calcular la posición del nodo centrado en relación con el TextFlow
                double targetScrollPosition = (nodeMinY + nodeMaxY - visibleHeight) / 2.0;
                // Ajustar la posición para mantenerla dentro de los límites
                targetScrollPosition = Math.max(0, Math.min(targetScrollPosition, textFlow.getHeight() - visibleHeight));
                // Calcular el cambio necesario en el desplazamiento
                double scrollChange = targetScrollPosition - currentScrollPosition;
                // Aplicar el cambio en el desplazamiento
                scrollPane.setVvalue(scrollPane.getVvalue() + scrollChange / (textFlow.getHeight() - visibleHeight));
            }
        }
    }
}
