package org.example.demo;

import dataStructures.AVLTree;
import dataStructures.SinglyLinkedList;

import java.util.List;

public class DocumentIndexer {
    private AVLTree index;

    public DocumentIndexer() {
        this.index = new AVLTree();
    }

    // Método para indizar una lista de documentos
    public void indexDocuments(List<Document> documents, int position) {
        for (Document document : documents) {
            indexDocument(document, position);
        }
    }

    // Método para indizar un documento
    public void indexDocument(Document document, int position) {
        String content = document.getContent();
        String[] words = content.split("\\s+"); // Dividir el contenido en palabras
        for (String word : words) {
            index.insert(word.toLowerCase(), document, position); // Convertir a minúsculas para normalización
        }
    }

    // Método para buscar texto en los documentos indexados
    public TextData search(String query) {
        return index.search(query.toLowerCase()); // Convertir a minúsculas para normalización
    }
}
