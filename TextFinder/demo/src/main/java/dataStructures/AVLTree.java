package dataStructures;

class AVLNode {
    int key, height;
    AVLNode left, right;

    AVLNode(int key) {
        this.key = key;
        this.height = 1;
        this.left = this.right = null;
    }
}

public class AVLTree {
    private AVLNode root;

    // Get height of node
    private int height(AVLNode node) {
        if (node == null)
            return 0;
        return node.height;
    }

    // Get balance factor of node
    private int getBalance(AVLNode node) {
        if (node == null)
            return 0;
        return height(node.left) - height(node.right);
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

    // Insert a key into the tree
    public void insert(int key) {
        root = insertRecursive(root, key);
    }

    // Recursive function to insert a key into the tree
    private AVLNode insertRecursive(AVLNode node, int key) {
        // Perform normal BST insertion
        if (node == null)
            return new AVLNode(key);

        if (key < node.key)
            node.left = insertRecursive(node.left, key);
        else if (key > node.key)
            node.right = insertRecursive(node.right, key);
        else // Duplicate keys not allowed
            return node;

        // Update height of this ancestor node
        node.height = 1 + Math.max(height(node.left), height(node.right));

        // Get the balance factor of this ancestor node
        int balance = getBalance(node);

        // If node becomes unbalanced, perform rotations
        // Left Left Case
        if (balance > 1 && key < node.left.key)
            return rightRotate(node);

        // Right Right Case
        if (balance < -1 && key > node.right.key)
            return leftRotate(node);

        // Left Right Case
        if (balance > 1 && key > node.left.key) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Left Case
        if (balance < -1 && key < node.right.key) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    // Search for a key in the tree
    public boolean search(int key) {
        return searchRecursive(root, key);
    }

    // Recursive function to search for a key in the tree
    private boolean searchRecursive(AVLNode node, int key) {
        if (node == null)
            return false;
        if (key == node.key)
            return true;
        if (key < node.key)
            return searchRecursive(node.left, key);
        else
            return searchRecursive(node.right, key);
    }
}
