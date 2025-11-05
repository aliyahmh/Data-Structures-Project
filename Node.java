public class Node<T> {
    public T data;
    public Node<T> next;
    public Node<T> prev;

    public Node() {
        data = null;
        next = null;
        prev = null;
    }


    public Node(T val) {
    data = val;
    next = null;
    prev = null;
    }
}

