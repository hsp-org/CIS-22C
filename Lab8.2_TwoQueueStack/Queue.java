/**
 * The Queue class definition
 * @author Hari Prakash
 * @author Hari Prakash
 * CIS 22C, Lab 7
 * @param <T> the generic data stored in the Queue
 */
import java.util.NoSuchElementException;

public class Queue<T extends Comparable<T>> implements Q<T> {
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
    
    /**
     * Creates a String of the Queue in reverse order by calling the
     * recursive helper method.
     * @return the Queue values as a String in reverse order.
     */
    public String reverseQueue() {
        return reverseQueue(front) + "\n";
    }
    	
    /**
     * Determines whether the values are sorted from
     * smallest to largest by calling its recursive helper.
     * @return whether the Queue is sorted
     */
    public boolean isSorted() {
    	return isSorted(front);
    }

    /**
     * Uses the recursive linear search algorithm to locate an element.
     * @param element the value to search for
     * @return whether the element is present
     * Note that in the case length == 0 the element is considered not found
     */
    public boolean linearSearch(T element) {
        return linearSearch(front, element);
    }

    /**
     * Uses the recursive binarySearch algorithm to determine if a specific
     * value is available by calling the private helper method binarySearch.
     * @param value the value to search for
     * @return whether the element is present
     * @precondition isSorted()
     * @throws IllegalStateException when the precondition is violated.
     */
    public boolean binarySearch(T value) throws IllegalStateException {
        if (!isSorted()) {
            throw new IllegalStateException("Queue must be sorted for binary search.");
        }
        return binarySearch(0, size - 1, value);
    }

    /** RECURSIVE HELPER METHODS */

    /**
     * Recursively (no loops) creates a String where the data is in reverse order
     * @param n the current node.
     * @return the Queue values as a String in reverse order.
     */
    private String reverseQueue(Node currentNode) {
        if (currentNode == null) {
            return "";
        }
        return reverseQueue(currentNode.next) + currentNode.data + " ";
    }


    /**
     * Helper method to isSorted recursively determines whether
     * data is sorted from smallest to largest.
     * @param node the current node
     * @return whether the data is sorted in ascending order
     */
    private boolean isSorted(Node currentNode) {
        if (currentNode == null || currentNode.next == null) {
            return true;
        }
        if (currentNode.data.compareTo(currentNode.next.data) > 0) {
            return false;
        }
        return isSorted(currentNode.next);
    }

    /**
     * Searches for the specified value by implementing the recursive
     * linearSearch algorithm.
     * @param value the value to search for
     * @return whether the value exists or not.
     */
    private boolean linearSearch(Node currentNode, T element) {
        if (currentNode == null) {
            return false;
        }
        if (currentNode.data.equals(element)) {
            return true;
        }
        return linearSearch(currentNode.next, element);
    }

    /**
     * Helper method for private binarySearch.
     * Searches for the data stored at a Node in a given "midpoint".
     * @param node the current Node in the Queue
     * @param mid the integer location in the Queue
     * @return the data stored at the mid Node
     */
    private T getMid(Node startNode, int mid) {
        if (mid == 0) {
            return startNode.data;
        } else {
            return getMid(startNode.next, mid - 1);
        }
    }

    /**
     * Searches for the specified value by implementing the recursive
     * binarySearch algorithm.
     * @param low   the lowest bounds of the search
     * @param high  the highest bounds of the search
     * @param value the value to search for
     * @return whether the value exists or not.
     */
    private boolean binarySearch(int low, int high, T value) {
        if (low > high) {
            return false;
        }
        int mid = low + (high - low) / 2;
        T midValue = getMid(front, mid);
        if (midValue.equals(value)) {
            return true;
        } else if (midValue.compareTo(value) > 0) {
            return binarySearch(low, mid - 1, value);
        } else {
            return binarySearch(mid + 1, high, value);
        }
    }

}
