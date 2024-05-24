package org.example.demo;

/**
 * Class that represents the result of finding text in a Document using the TextFinder.
 */
public class Result {
    Document document; // Document where the text was founded
    String fragment; // Document text
    int[] position; // Position where the text is starts and ends on the document
    private boolean isHighlighted;

    /**
     * Result object constructor.
     *
     * @param document  The document where the text was found.
     * @param fragment  The text fragment found in the document.
     * @param position  The position where the text starts and ends in the document.
     */
    public Result(Document document, String fragment, int[] position, boolean isHighlighted) {
        this.document = document;
        this.fragment = fragment;
        this.position = position;
        this.isHighlighted = isHighlighted;
    }

    public Document getDocument() {
        return document;
    }

    public String getFragment() {
        return fragment;
    }

    public int[] getPosition(){return position;}

    public boolean isHighlighted() {
        return isHighlighted;
    }
}
