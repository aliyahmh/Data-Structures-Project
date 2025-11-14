public class Node<T> {

    public T data;
    public Node<T> next;
    public Node () {
        data = null;
        next = null;
    }
    public Node (T val) {
        data = val;
        next = null;
    }
    
    //----------- Setters ------------//

    public void setData(T data) {
        this.data = data;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }
    
    //----------- Getters ------------//
    
    public T getData() {
        return data;
    }

    public Node<T> getNext() {
        return next;
    }

}
