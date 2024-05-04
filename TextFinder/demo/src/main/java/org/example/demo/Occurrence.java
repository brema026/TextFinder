package org.example.demo;

/**
 * Represents a word occurrence on a certain document.
 *
 * @param document Document where the word appears.
 * @param position Position where the word was found.
 */
public record Occurrence(Document document, int position) {
}