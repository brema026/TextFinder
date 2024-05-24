package org.example.demo;

import dataStructures.AVLTree;
import dataStructures.SinglyLinkedList;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Utility class for finding text in an AVL tree.
 */
public class TextFinder {
    /**
     * Search for a given text in the AVL Tree.
     *
     * @param text Text to search in the tree.
     * @param avlTree AVL Tree where to search the given text.
     * @return Result array that contains the founded text's data.
     * @throws NoSuchElementException If the AVLTree is empty.
     */
    public Result[] findText(String text, AVLTree avlTree) {
        if (avlTree.isEmpty()) {
            throw new NoSuchElementException("No hay documentos cargados en el programa." +
                    " No se puede buscar ninguna palabra en un conjunto vacío de documentos");
        }

        if (isPhrase(text)) {
            return findPhrase(text, avlTree);

        } else {
            return findWord(text, avlTree);
        }
    }

    /**
     * Checks if a given text contains more than one word.
     *
     * @param text Text to check.
     * @return True if the text contains more than one word, otherwise returns false.
     */
    private boolean isPhrase(String text) {
        String[] wordsInText = text.split(" ");
        return wordsInText.length > 1;
    }

    /**
     * Search for given word in a AVLTree that contains word occurrences.
     *
     * @param word Word to search in the tree.
     * @param avlTree AVL Tree where to search the given word.
     * @return Result array that contains the founded word's data.
     */
    private Result[] findWord(String word, AVLTree avlTree) {
        TextData foundedWordData = avlTree.search(word);

        if (foundedWordData != null) {
            SinglyLinkedList<Occurrence> occurrences = foundedWordData.getOccurrences();
            int resultsSize = occurrences.getSize();
            Result[] results = new Result[resultsSize];

            int currentIndex = 0;
            while (currentIndex < resultsSize) {
                Occurrence currentOccurrence = occurrences.get(currentIndex);
                Result result = createResultFromOccurrence(currentOccurrence, false, -1);
                results[currentIndex] = result;
                currentIndex++;
            }
            return results;

        } else {
            return new Result[0];
        }
    }

    /**
     * Search for a given phrase in the AVL Tree.
     *
     * @param phrase Phrase to search in the tree
     * @param avlTree AVL Tree where to search the given phrase.
     * @return Result array that contains the founded word's data.
     */
    private Result[] findPhrase(String phrase, AVLTree avlTree) {
        String[] phraseWords = splitPhrase(phrase);
        String firstWord = phraseWords[0];
        TextData foundedWordData = avlTree.search(firstWord);

        if (foundedWordData != null) {
            phraseWords = removeFirstWord(phraseWords);
            SinglyLinkedList<Occurrence> occurrences = foundedWordData.getOccurrences();
            SinglyLinkedList<Result> results = findMatchingOccurrences(phraseWords, occurrences);
            return singlyLinkedListToArray(results);

        } else {
            return new Result[0];
        }
    }

    /**
     * Splits the given phrase into an array of words.
     *
     * @param phrase The phrase to split.
     * @return An array of words obtained by splitting the phrase.
     */
    private String[] splitPhrase(String phrase) {
        return phrase.split("\\P{L}+");
    }

    /**
     * Removes the first word from the given array of words.
     *
     * @param phraseWords The array of words from which to remove the first word.
     * @return A new array containing the remaining words after removing the first word.
     */
    private String[] removeFirstWord(String[] phraseWords) {
        return Arrays.copyOfRange(phraseWords, 1, phraseWords.length);
    }

    /**
     * Finds occurrences in a list matching the specified phrase words.
     *
     * @param phraseWords   The words of the phrase to match.
     * @param occurrences   The list of occurrences to search.
     * @return A SinglyLinkedList containing the matching results.
     */
    private SinglyLinkedList<Result> findMatchingOccurrences(String[] phraseWords, SinglyLinkedList<Occurrence> occurrences) {
        SinglyLinkedList<Result> results = new SinglyLinkedList<>();

        int occurrencesSize = occurrences.getSize();
        int currentOccurrenceIndex = 0;
        while (currentOccurrenceIndex < occurrencesSize) {
            Occurrence currentOccurrence = occurrences.get(currentOccurrenceIndex);
            int phraseSize = phraseWords.length;
            String[] occurrenceWords = getOccurrenceWords(currentOccurrence, phraseSize);
            if (areSamePhrases(phraseWords, occurrenceWords)) {
                int phraseEndPosition = currentOccurrence.position() + phraseSize;
                Result result = createResultFromOccurrence(currentOccurrence, true, phraseEndPosition);
                results.add(result);
            }
            currentOccurrenceIndex++;

        }

        return results;
    }

    /**
     * Gets the phrase words of a given occurrence.
     *
     * @param occurrence Occurrence where the phrase is contained.
     * @param phraseSize Amount of words in the phrase.
     * @return A String array with the phrase words.
     */
    private String[] getOccurrenceWords(Occurrence occurrence, int phraseSize) {
        String occurrenceDocumentContent = occurrence.document().getContent();
        int occurrencePosition = occurrence.position();
        String[] occurrenceDocumentWords = occurrenceDocumentContent.split("\\P{L}+");

        int phraseStartIndex = occurrencePosition + 1;
        int phraseFinalIndex = phraseStartIndex + phraseSize;
        return Arrays.copyOfRange(occurrenceDocumentWords, phraseStartIndex, phraseFinalIndex);
    }

    /**
     * Compare if two phrases are the same.
     *
     * @param phraseWords Original phrase to compare.
     * @param occurrenceWords The phrase occurrence to check.
     * @return Boolean values that represents if both phrases are the same.
     */
    private boolean areSamePhrases(String[] phraseWords, String[] occurrenceWords) {
        return Arrays.equals(phraseWords, occurrenceWords);
    }

    /**
     * Creates a Result object from a given Occurrence, this occurrence could be from a phrase or from a word.
     *
     * @param occurrence Occurrence to create the Result object.
     * @param isPhraseOccurrence Represents if the given occurrence is from a phrase.
     * @param phraseEndPosition Position where the phrase ends.
     * @return The created Result object.
     */
    /**
     * Creates a Result object from a given Occurrence, this occurrence could be from a phrase or from a word.
     *
     * @param occurrence         Occurrence to create the Result object.
     * @param isPhraseOccurrence Represents if the given occurrence is from a phrase.
     * @param phraseEndPosition  Position where the phrase ends.
     * @return The created Result object.
     */
    private Result createResultFromOccurrence(Occurrence occurrence, boolean isPhraseOccurrence, int phraseEndPosition) {
        Document occurrenceDocument = occurrence.document();
        String documentContent = occurrenceDocument.getContent();
        int occurrenceStartPosition = occurrence.position();
        String fragment;

        if (isPhraseOccurrence) {
            int phraseStartPosition = occurrenceStartPosition;
            int phraseEndIndex = phraseEndPosition;
            if (phraseEndIndex > documentContent.length()) {
                phraseEndIndex = documentContent.length();
            }
            fragment = documentContent.substring(phraseStartPosition, phraseEndIndex);
        } else {
            if (occurrenceStartPosition < documentContent.length()) {
                fragment = documentContent.substring(occurrenceStartPosition, occurrenceStartPosition + 1);
            } else {
                fragment = "";
            }
        }

        return new Result(occurrenceDocument, fragment, new int[]{occurrenceStartPosition, phraseEndPosition}, true);
    }




    /**
     * Converts the linked list to an array.
     *
     * @param list List to convert.
     * @return An array containing the elements of the linked list.
     */
    public Result[] singlyLinkedListToArray(SinglyLinkedList<Result> list) {
        int arraySize = list.getSize();
        Result[] array = new Result[arraySize];
        int currentIndex = 0;
        while (currentIndex < arraySize) {
            Result currentResult = list.get(currentIndex);
            array[currentIndex] = currentResult;
            currentIndex++;
        }
        return array;
    }
}
