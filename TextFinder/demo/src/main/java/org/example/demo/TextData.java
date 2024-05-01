package org.example.demo;

import dataStructures.SinglyLinkedList;

public class TextData {
    String text;
    SinglyLinkedList<Occurrence> occurrences;

    public TextData(String text, Occurrence occurrence) {
        this.text = text;
        addOccurrence(occurrence);
    }

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
