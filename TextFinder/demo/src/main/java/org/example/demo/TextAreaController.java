package org.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.awt.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public void highlightPhrase(String content, String phrase) {
        textFlow.getChildren().clear();

        if (!phrase.isEmpty() && !content.isEmpty()) {
            // Crear un patrón de búsqueda con la frase completa
            Pattern pattern = Pattern.compile("\\b" + Pattern.quote(phrase) + "\\b", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(content);

            int lastMatchEnd = 0;
            while (matcher.find()) {
                // Agregar el texto entre las coincidencias anteriores y la actual
                if (matcher.start() > lastMatchEnd) {
                    String beforeText = content.substring(lastMatchEnd, matcher.start());
                    textFlow.getChildren().add(new Text(beforeText));
                }

                // Agregar la frase resaltada
                String highlightedText = content.substring(matcher.start(), matcher.end());
                textFlow.getChildren().add(createHighlightedText(highlightedText));

                lastMatchEnd = matcher.end();
            }

            // Agregar el texto después de la última coincidencia
            if (lastMatchEnd < content.length()) {
                String afterText = content.substring(lastMatchEnd);
                textFlow.getChildren().add(new Text(afterText));
            }
        } else {
            System.out.println("La frase o el contenido están vacíos.");
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
    public void focusWord(int position) {
        if (position >= 0 && position < textFlow.getChildren().size()) {
            Node node = textFlow.getChildren().get(position);
            if (node instanceof Text) {
                Text textNode = (Text) node;
                double layoutY = textNode.getBoundsInParent().getMinY();
                double layoutHeight = textFlow.getHeight();
                double visibleHeight = scrollPane.getViewportBounds().getHeight();

                System.out.println("layoutY: " + layoutY);
                System.out.println("layoutHeight: " + layoutHeight);
                System.out.println("visibleHeight: " + visibleHeight);

                // Calcular el desplazamiento necesario para que el nodo esté centrado
                double scrollPosition = Math.max(0, layoutY - (visibleHeight / 2));

                System.out.println("scrollPosition: " + scrollPosition);

                // Ajustar el desplazamiento máximo para no exceder el contenido visible
                scrollPosition = Math.min(scrollPosition, layoutHeight - visibleHeight);

                System.out.println("Adjusted scrollPosition: " + scrollPosition);

                // Establecer el valor de desplazamiento vertical del ScrollPane
                scrollPane.setVvalue(scrollPosition / layoutHeight);
            }
        }
    }
}
