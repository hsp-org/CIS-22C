/**
 * The Queue class definition
 * @author Hari Prakash
 * @author Hari Prakash
 * CIS 22C, Lab 5
 * @param <T> the generic data stored in the Queue
 */
import java.util.NoSuchElementException;

public class Queue<T> implements Q<T> {
    private class Node {
        private T data;
        private Node next;

        public Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    private int size;
    private Node front;
    private Node end;

    /****CONSTRUCTORS****/

    /**
     * Default constructor for the Queue class
     * @postcondition a new Queue object with all fields
     * assigned default values
     */
    public Queue() {
        front = end = null;
        size = 0;
    }

    /**
     * Converts an array into a Queue
     * @param array the array to copy into
     * the Queue
     */
    public Queue(T[] array) {
        this(); 
        if (array != null) {
            for (T element : array) {
                enqueue(element);
            }
        }
    }


    /**
     * Copy constructor for the Queue class
     * Makes a deep copy of the parameter
     * @param original the Queue to copy
     * @postcondition <You fill in here>
     */
    public Queue(Queue<T> original) {
        this(); 
        if (original != null) {
            Node current = original.front;
            while (current != null) {
                enqueue(current.data);
                current = current.next;
            }
        }
    }


    /****ACCESSORS****/

    /**
     * Returns the value stored at the front
     * of the Queue
     * @return the value at the front of the queue
     * @precondition !isEmpty()
     * @throws NoSuchElementException when the
     * precondition is violated
     */
    public T getFront() throws NoSuchElementException {
        if (isEmpty()) {
        	throw new NoSuchElementException("getFront: Queue is empty!");
        }
        return front.data;
    }

    /**
     * Returns the size of the Queue
     * @return the size from 0 to n
     */
    public int getSize() {
        return size;
    }

    /**
     * Determines whether a Queue is empty
     * @return whether the Queue contains no elements
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /****MUTATORS****/

    /**
     * Inserts a new value at the end of the Queue
     *
     * @param data the new data to insert
     * @postcondition <You fill in here>
     */
    public void enqueue(T data) {
        Node newNode = new Node(data);
        if (isEmpty()) {
        	front = end = newNode;
        } else {
        	end.next = newNode;
        	end = newNode;
        }
        size++;
    }

    /**
     * Removes the front element in the Queue
     * @precondition <You fill in here>
     * @throws NoSuchElementException when
     * the precondition is violated
     * @postcondition <You fill in here>
     */
    public void dequeue() throws NoSuchElementException {
        if (isEmpty()) {
        	throw new NoSuchElementException("dequeue: Cannot dequeue because list is empty");
        }
        front = front.next;
        if (front == null) {
        	end = null;
        }
        size--;

    }

    /****ADDITONAL OPERATIONS****/

    /**
     * Returns the values stored in the Queue
     * as a String, separated by a blank space
     * with a new line character at the end
     * @return a String of Queue values
     */
    @Override public String toString() {
        StringBuilder sb = new StringBuilder();
        Node current = front;
        while (current != null) {
        	sb.append(current.data).append(" ");
        	current = current.next;
        }
        sb.append("\n");
        return sb.toString();
    }

    /**
     * Determines whether two Queues contain
     * the same values in the same order
     * @param obj the Object to compare to this
     * @return whether obj and this are equal
     */
    @SuppressWarnings("unchecked") // good practice to remove warning here
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Queue<?> other = (Queue<?>) obj;
        if (this.size != other.size) return false;

        Node currentThis = this.front;
        Queue<?>.Node currentOther = other.front;
        while (currentThis != null && currentOther != null) {
            if (!currentThis.data.equals(currentOther.data)) return false;
            currentThis = currentThis.next;
            currentOther = currentOther.next;
        }
        return true;
    }


}
