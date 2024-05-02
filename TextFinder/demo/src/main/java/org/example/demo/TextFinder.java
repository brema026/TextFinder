package org.example.demo;

import dataStructures.AVLTree;
import dataStructures.SinglyLinkedList;

import java.util.Arrays;

public class TextFinder {
    /**
     * Search for a given text in the AVL Tree.
     *
     * @param text Text to search in the tree.
     * @param avlTree AVL Tree where to search the given text.
     * @return Result array that contains the founded text's data.
     */
    public Result[] findText(String text, AVLTree avlTree) {
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
        //  Search word in the AVL Tree
        TextData foundedWordData = avlTree.search(word);

        if (foundedWordData != null) {
            //  Get occurrences from the founded word data
            SinglyLinkedList<Occurrence> occurrences = foundedWordData.getOccurrences();

            //  Create an empty array with the size of the occurrences list to store all results
            int resultsSize = occurrences.getSize();
            Result[] results = new Result[resultsSize];

            int currentIndex = 0;
            while (currentIndex < resultsSize) {
                Occurrence currentOccurrence = occurrences.get(currentIndex);

                //  Create a Result object from the current occurrence
                Result result = createResultFromOccurrence(currentOccurrence);

                //  Add the result object to the result array
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
        //  Separate phrase words
        String[] phraseWords = phrase.split("\\P{L}+");

        //  Search the first phrase word on the AVLTree
        String firstWord = phraseWords[0];
        TextData foundedWordData = avlTree.search(firstWord);

        if (foundedWordData != null) {
            // Remove the first word from the phrase words list
            phraseWords = Arrays.copyOfRange(phraseWords, 1, phraseWords.length);

            //  Get occurrences from the founded word data
            SinglyLinkedList<Occurrence> occurrences = foundedWordData.getOccurrences();

            //  Create an empty SinglyLinkedList to store the results
            SinglyLinkedList<Result> results = new SinglyLinkedList<>();

            int occurrencesSize = occurrences.getSize();
            int currentOccurrenceIndex = 0;
            while (currentOccurrenceIndex < occurrencesSize) {
                Occurrence currentOccurrence = occurrences.get(currentOccurrenceIndex);

                // Get the phrase occurrence words
                int phraseSize = phraseWords.length;
                String[] phraseOccurrenceWords = getPhraseOccurrenceWords(currentOccurrence, phraseSize);

                // Compare both phrase's words
                boolean samePhrases = comparePhrases(phraseWords, phraseOccurrenceWords);
                if (samePhrases) {
                    //  Create a Result object from the current occurrence
                    Result result = createResultFromOccurrence(currentOccurrence);

                    // Add the result object to the list
                    results.add(result);
                }
                currentOccurrenceIndex++;

            }

            // Transform the SinglyLinkedList to an array and return it
            return singlyLinkedListToArray(results);

        } else {
            return new Result[0];
        }
    }

    /**
     * Creates a Result object from a given Occurrence.
     *
     * @param occurrence Occurrence to create the Result object.
     * @return The created Result object.
     */
    private Result createResultFromOccurrence(Occurrence occurrence) {
        Document occurrenceDocument = occurrence.getDocument();
        String documentContent = occurrenceDocument.getContent();
        int occurrencePosition = occurrence.getPosition();

        return new Result(occurrenceDocument, documentContent, occurrencePosition);
    }

    /**
     * Gets the phrase words of a given occurrence.
     *
     * @param occurrence Occurrence where the phrase is contained.
     * @param phraseSize Amount of words in the phrase.
     * @return A String array with the phrase words.
     */
    private String[] getPhraseOccurrenceWords(Occurrence occurrence, int phraseSize) {
        String occurrenceDocumentContent = occurrence.getDocument().getContent();
        int occurrencePosition = occurrence.getPosition();
        String[] occurrenceDocumentWords = occurrenceDocumentContent.split("\\P{L}+");

        int phraseStartIndex = occurrencePosition + 1;
        int phraseFinalIndex = phraseStartIndex + phraseSize;
        return Arrays.copyOfRange(occurrenceDocumentWords, phraseStartIndex, phraseFinalIndex);
    }

    /**
     * Compare if two phrases are the same.
     *
     * @param phraseWords Original phrase to compare.
     * @param phraseOccurrenceWords The phrase occurrence to check.
     * @return Boolean values that represents if both phrases are the same.
     */
    private boolean comparePhrases(String[] phraseWords, String[] phraseOccurrenceWords) {
        int currentIndex = 0;
        int phrasesSize = phraseWords.length;

        while (currentIndex < phrasesSize) {
            String phraseWord = phraseWords[currentIndex];
            String phraseOccurrenceWord = phraseOccurrenceWords[currentIndex];
            if (!phraseWord.equals(phraseOccurrenceWord)) {
                return false;
            }

            currentIndex++;
        }
        return true;
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
