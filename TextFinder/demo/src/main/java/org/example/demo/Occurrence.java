package org.example.demo;

public class Occurrence {
    private final Document document;
    private final int position;

    public Occurrence(Document document, int position) {
        this.document = document;
        this.position = position;
    }

    public Document getDocument() {
        return document;
    }

    public int getPosition() {
        return position;
    }
}
