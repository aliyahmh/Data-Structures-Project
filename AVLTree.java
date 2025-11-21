
package com.mycompany.datastructureproject;

import java.util.Comparator;

public class AVLTree<T> {
    
    private AVLNode<T> root;
    private Comparator<T> comparator;

    public AVLTree(Comparator<T> comparator) {
        this.comparator = comparator;
        this.root = null;
    }

    // ... (keep all your existing AVLTree methods but make them use the comparator)
    
    // Helper to get the height of a node (or 0 for null)
    private int height(AVLNode<T> node) {
        return (node == null) ? 0 : node.height;
    }

    // Helper to get the maximum of two integers
    private int max(int a, int b) {
        return Math.max(a, b);
    }

    // Get the balance factor of a node
    private int getBalance(AVLNode<T> node) {
        return (node == null) ? 0 : height(node.left) - height(node.right);
    }

    // Right rotation
    private AVLNode<T> rightRotate(AVLNode<T> y) {
        AVLNode<T> x = y.left;
        AVLNode<T> T2 = x.right;

        // Perform rotation
        x.right = y;
        y.left = T2;

        // Update heights
        y.height = max(height(y.left), height(y.right)) + 1;
        x.height = max(height(x.left), height(x.right)) + 1;

        return x;
    }

    // Left rotation
    private AVLNode<T> leftRotate(AVLNode<T> x) {
        AVLNode<T> y = x.right;
        AVLNode<T> T2 = y.left;

        // Perform rotation
        y.left = x;
        x.right = T2;

        // Update heights
        x.height = max(height(x.left), height(x.right)) + 1;
        y.height = max(height(y.left), height(y.right)) + 1;

        return y;
    }

    // Public insert method
    public void insert(T key) {
        root = insert(root, key);
    }

    // Private recursive insert
    private AVLNode<T> insert(AVLNode<T> node, T key) {
        if (node == null) {
            return new AVLNode<>(key);
        }

        if (comparator.compare(key, node.key) < 0) {
            node.left = insert(node.left, key);
        } else if (comparator.compare(key, node.key) > 0) {
            node.right = insert(node.right, key);
        } else {
            return node; // Duplicate not allowed
        }

        node.height = 1 + max(height(node.left), height(node.right));
        int balance = getBalance(node);

        // Left Left Case
        if (balance > 1 && comparator.compare(key, node.left.key) < 0) {
            return rightRotate(node);
        }

        // Right Right Case
        if (balance < -1 && comparator.compare(key, node.right.key) > 0) {
            return leftRotate(node);
        }

        // Left Right Case
        if (balance > 1 && comparator.compare(key, node.left.key) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Left Case
        if (balance < -1 && comparator.compare(key, node.right.key) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    // Search method
    public T search(T key) {
        return search(root, key);
    }

    private T search(AVLNode<T> node, T key) {
        if (node == null) return null;

        int comparison = comparator.compare(key, node.key);
        if (comparison == 0) {
            return node.key;
        } else if (comparison < 0) {
            return search(node.left, key);
        } else {
            return search(node.right, key);
        }
    }

    // Remove method
    public boolean remove(T key) {
        if (search(key) == null) return false;
        root = remove(root, key);
        return true;
    }
    
    private AVLNode<T> remove(AVLNode<T> node, T key) {
        if (node == null) return null;
        
        int comparison = comparator.compare(key, node.key);
        
        if (comparison < 0) {
            node.left = remove(node.left, key);
        } else if (comparison > 0) {
            node.right = remove(node.right, key);
        } else {
            if (node.left == null || node.right == null) {
                AVLNode<T> temp = (node.left != null) ? node.left : node.right;
                if (temp == null) {
                    node = null;
                } else {
                    node = temp;
                }
            } else {
                AVLNode<T> temp = minValueNode(node.right);
                node.key = temp.key;
                node.right = remove(node.right, temp.key);
            }
        }
        
        if (node == null) return null;
        
        node.height = 1 + max(height(node.left), height(node.right));
        return balance(node);
    }
    
    private AVLNode<T> minValueNode(AVLNode<T> node) {
        AVLNode<T> current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }
    
    private AVLNode<T> balance(AVLNode<T> node) {
        int balance = getBalance(node);
        
        if (balance > 1 && getBalance(node.left) >= 0) {
            return rightRotate(node);
        }
        if (balance > 1 && getBalance(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        if (balance < -1 && getBalance(node.right) <= 0) {
            return leftRotate(node);
        }
        if (balance < -1 && getBalance(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        return node;
    }

    // In-order traversal
    public LinkedList<T> inOrderTraversal() {
        LinkedList<T> result = new LinkedList<>();
        inOrderTraversal(root, result);
        return result;
    }

    private void inOrderTraversal(AVLNode<T> node, LinkedList<T> result) {
        if (node != null) {
            inOrderTraversal(node.left, result);
            result.insert(node.key);
            inOrderTraversal(node.right, result);
        }
    }

    // Range query
    public LinkedList<T> rangeQuery(T min, T max) {
        LinkedList<T> result = new LinkedList<>();
        rangeQuery(root, min, max, result);
        return result;
    }

    private void rangeQuery(AVLNode<T> node, T min, T max, LinkedList<T> result) {
        if (node == null) return;

        int compMin = comparator.compare(min, node.key);
        int compMax = comparator.compare(max, node.key);

        if (compMin <= 0 && compMax >= 0) {
            rangeQuery(node.left, min, max, result);
            result.insert(node.key);
            rangeQuery(node.right, min, max, result);
        } else if (compMin > 0) {
            rangeQuery(node.right, min, max, result);
        } else {
            rangeQuery(node.left, min, max, result);
        }
    }

    public boolean isEmpty() {
        return root == null;
    }
    
    public int size() {
        return size(root);
    }
    
    private int size(AVLNode<T> node) {
        if (node == null) return 0;
        return 1 + size(node.left) + size(node.right);
    }
}