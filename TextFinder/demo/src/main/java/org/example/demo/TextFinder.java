package org.example.demo;

import dataStructures.AVLTree;
import dataStructures.SinglyLinkedList;

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
                Document occurrenceDocument = currentOccurrence.getDocument();
                String documentContent = occurrenceDocument.getContent();
                int occurrencePosition = currentOccurrence.getPosition();
                Result result = new Result(occurrenceDocument, documentContent, occurrencePosition);

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
        /*
            1. Dividir la frase en un array de strings por medio del metodo split(" ")
            2. Busca la primera palabra por medio del metodo findWord()
            3. Si devuelve una lista de resultados -> Paso 3 / De lo contrario devuelve una lista vacia
            4. Crear una lista simplemente enlazada de results vacio
            - Por cada resultado
            5. Separar las palabras en el fragment del resultado utilizando el metodo split("delimitadores")
            6. Del array obtenido obtener la palabra que sigue utilizando la posicion del resultado (Se le suma 1 para obtener la palabra siguiente)
            7. Comparar la palabra obtenida con la siguiente palabra de la frase
            Si la palabra es igual y no quedan mas palabras en la frase -> Crear un objeto resultado y agregarlo a la lista de resultados
            Si la palabra es igual y quedan mas palabras en la frase -> repetir el proceso desde el paso 5 con la palabra que sigue
            - Al finalizar
            8. Pasar la lista de resultados a un arrary del tamaño de la lista
            9. Devolver el array con los resultados
         */
        return null;
    }
}
