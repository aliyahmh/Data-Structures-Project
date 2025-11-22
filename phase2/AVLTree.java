
import java.util.Comparator;

public class AVLTree<T> {
    
    private AVLNode<T> root;
    private Comparator<T> comparator;

    // Constructor - requires a comparator
    public AVLTree(Comparator<T> comparator) {
        this.comparator = comparator;
        this.root = null;
    }

    //========== UTILITY METHODS ==========//
    
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

    //========== ROTATION METHODS ==========//
    
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

    //========== INSERT METHODS ==========//
    
    // Public insert method - O(log n)
    public void insert(T key) {
        root = insert(root, key);
    }

    // Private recursive insert
    private AVLNode<T> insert(AVLNode<T> node, T key) {
        // 1. Standard BST insertion
        if (node == null) {
            return new AVLNode<>(key);
        }

        // Use comparator to compare keys
        if (comparator.compare(key, node.key) < 0) {
            node.left = insert(node.left, key);
        } else if (comparator.compare(key, node.key) > 0) {
            node.right = insert(node.right, key);
        } else {
            return node; // Duplicate keys not allowed
        }

        // 2. Update height of this ancestor node
        node.height = 1 + max(height(node.left), height(node.right));

        // 3. Get the balance factor
        int balance = getBalance(node);

        // 4. If node becomes unbalanced, there are 4 cases:

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

        // Return the (unchanged) node pointer
        return node;
    }

    //========== SEARCH METHODS ==========//
    
    // Search for a key - O(log n)
    public T search(T key) {
        return search(root, key);
    }

    private T search(AVLNode<T> node, T key) {
        if (node == null) return null;

        int comparison = comparator.compare(key, node.key);
        
        if (comparison == 0) {
            return node.key; // Found
        } else if (comparison < 0) {
            return search(node.left, key);
        } else {
            return search(node.right, key);
        }
    }

    //========== REMOVE METHODS ==========//
    
    // Public remove method - O(log n)
    public boolean remove(T key) {
        if (search(key) == null) return false;
        root = remove(root, key);
        return true;
    }
    
    // Private recursive remove
    private AVLNode<T> remove(AVLNode<T> node, T key) {
        // 1. Standard BST deletion
        if (node == null) return null;
        
        int comparison = comparator.compare(key, node.key);
        
        if (comparison < 0) {
            node.left = remove(node.left, key);
        } else if (comparison > 0) {
            node.right = remove(node.right, key);
        } else {
            // Node to be deleted found
            
            // Case 1: Node with only one child or no child
            if (node.left == null || node.right == null) {
                AVLNode<T> temp = (node.left != null) ? node.left : node.right;
                
                if (temp == null) {
                    // No child case
                    node = null;
                } else {
                    // One child case
                    node = temp;
                }
            } else {
                // Case 2: Node with two children
                // Get the inorder successor (smallest in the right subtree)
                AVLNode<T> temp = minValueNode(node.right);
                
                // Copy the inorder successor's content to this node
                node.key = temp.key;
                
                // Delete the inorder successor
                node.right = remove(node.right, temp.key);
            }
        }
        
        // If the tree had only one node, return
        if (node == null) return null;
        
        // 2. Update height of current node
        node.height = 1 + max(height(node.left), height(node.right));
        
        // 3. Balance the node
        return balance(node);
    }
    
    // Helper to find node with minimum value
    private AVLNode<T> minValueNode(AVLNode<T> node) {
        AVLNode<T> current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }
    
    // Balance a node after insertion/deletion
    private AVLNode<T> balance(AVLNode<T> node) {
        int balance = getBalance(node);
        
        // Left Left Case
        if (balance > 1 && getBalance(node.left) >= 0) {
            return rightRotate(node);
        }
        
        // Left Right Case
        if (balance > 1 && getBalance(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        
        // Right Right Case
        if (balance < -1 && getBalance(node.right) <= 0) {
            return leftRotate(node);
        }
        
        // Right Left Case
        if (balance < -1 && getBalance(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        
        return node;
    }

    //========== TRAVERSAL METHODS ==========//
    
    // In-order traversal - returns LinkedList with sorted elements - O(n)
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
    
    // Pre-order traversal - O(n)
    public LinkedList<T> preOrderTraversal() {
        LinkedList<T> result = new LinkedList<>();
        preOrderTraversal(root, result);
        return result;
    }
    
    private void preOrderTraversal(AVLNode<T> node, LinkedList<T> result) {
        if (node != null) {
            result.insert(node.key);
            preOrderTraversal(node.left, result);
            preOrderTraversal(node.right, result);
        }
    }
    
    // Post-order traversal - O(n)
    public LinkedList<T> postOrderTraversal() {
        LinkedList<T> result = new LinkedList<>();
        postOrderTraversal(root, result);
        return result;
    }
    
    private void postOrderTraversal(AVLNode<T> node, LinkedList<T> result) {
        if (node != null) {
            postOrderTraversal(node.left, result);
            postOrderTraversal(node.right, result);
            result.insert(node.key);
        }
    }

    //========== RANGE QUERY METHOD ==========//
    
    // Range query - returns all elements between min and max - O(n)
    public LinkedList<T> rangeQuery(T min, T max) {
        LinkedList<T> result = new LinkedList<>();
        rangeQuery(root, min, max, result);
        return result;
    }

    private void rangeQuery(AVLNode<T> node, T min, T max, LinkedList<T> result) {
        if (node == null) return;

        int compMin = comparator.compare(min, node.key);
        int compMax = comparator.compare(max, node.key);

        // If current node is greater than min, go left
        if (compMin <= 0) {
            rangeQuery(node.left, min, max, result);
        }
        
        // If current node is within range, add it
        if (compMin <= 0 && compMax >= 0) {
            result.insert(node.key);
        }
        
        // If current node is less than max, go right
        if (compMax >= 0) {
            rangeQuery(node.right, min, max, result);
        }
    }

    //========== UTILITY CHECKING METHODS ==========//
    
    // Check if tree is empty
    public boolean isEmpty() {
        return root == null;
    }
    
    // Get size of tree - O(n)
    public int size() {
        return size(root);
    }
    
    private int size(AVLNode<T> node) {
        if (node == null) return 0;
        return 1 + size(node.left) + size(node.right);
    }
    
    // Get height of tree
    public int getHeight() {
        return height(root);
    }
    
    // Clear the entire tree
    public void clear() {
        root = null;
    }
    
    // Check if tree contains a key
    public boolean contains(T key) {
        return search(key) != null;
    }
    
    // Get minimum value in tree
    public T findMin() {
        if (root == null) return null;
        AVLNode<T> node = minValueNode(root);
        return node.key;
    }
    
    // Get maximum value in tree
    public T findMax() {
        if (root == null) return null;
        AVLNode<T> current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.key;
    }
    
  }
