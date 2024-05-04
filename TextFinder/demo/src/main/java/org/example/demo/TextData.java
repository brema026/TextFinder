package org.example.demo;

import dataStructures.SinglyLinkedList;

/**
 * Represents the text data stored on the AVLTree
 */
public class TextData {
    String text; // The stored text
    SinglyLinkedList<Occurrence> occurrences = new SinglyLinkedList<>(); // The text occurrence on the library documents

    /**
     * TextData object constructor.
     *
     * @param text       The text to be stored.
     * @param occurrence The occurrence of the text.
     */
    public TextData(String text, Occurrence occurrence) {
        this.text = text;
        addOccurrence(occurrence);
    }

    /**
     * Adds an occurrence of the text.
     *
     * @param occurrence The occurrence to add.
     */
    public void addOccurrence(Occurrence occurrence) {
        occurrences.add(occurrence);
    }

    public String getText() {
        return text;
    }

    public SinglyLinkedList<Occurrence> getOccurrences() {
        return occurrences;
    }
}
