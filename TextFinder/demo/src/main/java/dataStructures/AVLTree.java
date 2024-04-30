package dataStructures;

import org.example.demo.Document;

import java.util.ArrayList;
import java.util.List;

class AVLNode {
    String word;
    List<Document> occurrences;
    int height;
    AVLNode left, right;

    AVLNode(String word, Document document) {
        this.word = word;
        this.occurrences = new ArrayList<>();
        this.occurrences.add(document);
        this.height = 1;
        this.left = null;
        this.right = null;
    }
}

public class AVLTree {
    private AVLNode root;

    // Get height of node
    private int height(AVLNode node) {
        return node == null ? 0 : node.height;
    }

    // Right rotate subtree rooted with y
    private AVLNode rightRotate(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;

        // Perform rotation
        x.right = y;
        y.left = T2;

        // Update heights
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    // Left rotate subtree rooted with x
    private AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;

        // Perform rotation
        y.left = x;
        x.right = T2;

        // Update heights
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    // Get balance factor of node
    private int getBalance(AVLNode node) {
        return node == null ? 0 : height(node.left) - height(node.right);
    }

    // Insert a word and its occurrence into the tree
    public void insert(String word, Document document) {
        root = insertRecursive(root, word, document);
    }

    // Recursive function to insert a word and its occurrence into the tree
    private AVLNode insertRecursive(AVLNode node, String word, Document document) {
        // Perform normal BST insertion
        if (node == null)
            return new AVLNode(word, document);

        int compare = word.compareTo(node.word);
        if (compare < 0)
            node.left = insertRecursive(node.left, word, document);
        else if (compare > 0)
            node.right = insertRecursive(node.right, word, document);
        else
            node.occurrences.add(document); // Word already exists, add occurrence to the list

        // Update height of this ancestor node
        node.height = 1 + Math.max(height(node.left), height(node.right));

        // Get the balance factor of this ancestor node
        int balance = getBalance(node);

        // If node becomes unbalanced, perform rotations
        // Left Left Case
        if (balance > 1 && word.compareTo(node.left.word) < 0)
            return rightRotate(node);

        // Right Right Case
        if (balance < -1 && word.compareTo(node.right.word) > 0)
            return leftRotate(node);

        // Left Right Case
        if (balance > 1 && word.compareTo(node.left.word) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Left Case
        if (balance < -1 && word.compareTo(node.right.word) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    // Search for a word in the tree and return its occurrences
    public List<Document> search(String word) {
        return searchRecursive(root, word);
    }

    // Recursive function to search for a word in the tree
    private List<Document> searchRecursive(AVLNode node, String word) {
        List<Document> result = new ArrayList<>();
        if (node == null)
            return result;
        int compare = word.compareTo(node.word);
        if (compare == 0)
            result.addAll(node.occurrences);
        if (compare < 0)
            result.addAll(searchRecursive(node.left, word));
        if (compare > 0)
            result.addAll(searchRecursive(node.right, word));
        return result;
    }
}
