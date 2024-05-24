package dataStructures;

import org.example.demo.Document;
import org.example.demo.Occurrence;
import org.example.demo.TextData;

class AVLNode {
    TextData key;
    int height;
    AVLNode left, right;

    AVLNode(TextData key) {
        this.key = key;
        this.height = 1;
        this.left = null;
        this.right = null;
    }
}

public class AVLTree {
    private AVLNode root;

    public boolean isEmpty() {
        return root == null;
    }

    private int height(AVLNode node) {
        return node == null ? 0 : node.height;
    }

    private AVLNode rightRotate(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    private AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    private int getBalance(AVLNode node) {
        return node == null ? 0 : height(node.left) - height(node.right);
    }

    public void insert(String word, Document document, int position) {
        root = insertRecursive(root, word, document, position);
    }

    private AVLNode insertRecursive(AVLNode node, String word, Document document, int position) {

        if (node == null)
            return new AVLNode(new TextData(word, new Occurrence(document, position)));

        int compare = word.compareTo(node.key.getText());
        if (compare < 0)
            node.left = insertRecursive(node.left, word, document, position);
        else if (compare > 0)
            node.right = insertRecursive(node.right, word, document, position);
        else
            node.key.addOccurrence(new Occurrence(document, position));

        node.height = 1 + Math.max(height(node.left), height(node.right));

        int balance = getBalance(node);

        if (balance > 1 && word.compareTo(node.left.key.getText()) < 0)
            return rightRotate(node);

        if (balance < -1 && word.compareTo(node.right.key.getText()) > 0)
            return leftRotate(node);

        if (balance > 1 && word.compareTo(node.left.key.getText()) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        if (balance < -1 && word.compareTo(node.right.key.getText()) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    public TextData search(String word) {
        return searchRecursive(root, word);
    }

    private TextData searchRecursive(AVLNode node, String word) {
        if (node == null)
            return null;

        int compare = word.compareTo(node.key.getText());
        if (compare == 0) {
            return node.key;

        } else if (compare < 0) {
            return searchRecursive(node.left, word);

        } else {
            return searchRecursive(node.right, word);
        }
    }
}
