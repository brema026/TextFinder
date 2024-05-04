package org.example.demo;

/**
 * Class that represents the result of a founded text on the TextFinder.
 */
public class Result {
    Document document; // Document where the text was founded
    String fragment; // Document text
    int[] position; // Position where the text is starts and ends on the document

    public Result(Document document, String fragment, int[] position) {
        this.document = document;
        this.fragment = fragment;
        this.position = position;
    }

    public Document getDocument() {
        return document;
    }

    public String getFragment() {
        return fragment;
    }
}
