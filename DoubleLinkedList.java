public class DoubleLinkedList<T> {
    private Node<T> head;
    private Node<T> tail; // to act like a queue
    private int size; // update size with each add/remove


    public DoubleLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void insert(T val) {
        Node<T> tmp = new Node<T>(val);

        if(isEmpty())
        {
            tail = head = tmp;
        }
        else {
            tail.next = tmp; // insert at the end
            tmp.prev = tail;
            tail = tmp;
        }

        size++;
    }


    public void remove(T data) {
        Node<T> current = head;
        while (current != null) {
            if (current.data.equals(data)) {
                if (current.prev != null) {
                    current.prev.next = current.next;
                } else {
                    head = current.next;
                }

                if (current.next != null) {
                    current.next.prev = current.prev;
                } else {
                    tail = current.prev;
                }
                size--;
                return;
            }
            current = current.next;
        }
    }


    public boolean contains(T data) {
        Node<T> current = head;  // starting from head
        
        while (current != null) {
            if (current.data.equals(data)) {
                return true;  
            }
            current = current.next; 
        }
        return false;
    }

    public Node<T> getHead() {
        return head; 
    }

    public Node<T> getTail() { 
        return tail; 
    }

    public int getSize() {
        return size;
    }











}