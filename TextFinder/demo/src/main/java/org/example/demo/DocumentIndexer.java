package org.example.demo;

import dataStructures.AVLTree;
import dataStructures.SinglyLinkedList;

import java.util.List;

public class DocumentIndexer {
    private AVLTree index;

    public DocumentIndexer() {
        this.index = new AVLTree();
    }

    public void indexDocuments(List<Document> documents, int position) {
        for (Document document : documents) {
            indexDocument(document, position);
        }
    }

    public void indexDocument(Document document, int position) {
        String content = document.getContent();
        String[] words = content.split("\\s+");
        for (String word : words) {
            index.insert(word.toLowerCase(), document, position);
        }
    }

    public TextData search(String query) {
        return index.search(query.toLowerCase());
    }
}
