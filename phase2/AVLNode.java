

public class AVLNode<T> {
    T key;
    int height; 
    AVLNode<T> left;
    AVLNode<T> right;

    AVLNode(T key) {
        this.key = key;
        this.height = 1; // New node is initially a leaf, height 1
        this.left = null;
        this.right = null;
    }
    
           
}
