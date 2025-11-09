/* public class Queue<T> {

    private DoubleLinkedList<T> list;


    public Queue() {
        list = new DoubleLinkedList<>();
    }

    public void enqueue(T data) {
        list.insert(data);  // becomes new tail (since insertion is at the end)
    }


    public T dequeue() {
        if (list.isEmpty()) {
            return null;  
        }
        T data = list.getHead().data;
        list.remove(data); // removes from front
        return data;
    }

    public T getFront() {
        if (list.isEmpty()) {
            return null; 
        }
        return list.getHead().data; 
    }


    public boolean isEmpty() {
        return list.isEmpty();
    }

    public int size() {
        return list.getSize(); 
    }
    
}
*/