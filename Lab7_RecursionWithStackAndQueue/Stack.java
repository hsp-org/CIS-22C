/**
 * Stack class - singly-linked list version
 * @author Hari Prakash
 * @author Hari Prakash
 * CIS 22C, Lab 7
 */
import java.util.NoSuchElementException;


public class Stack<T extends Comparable<T>> implements LIFO<T> {
    private class Node {
        private T data;
        private Node next;

        public Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node top;
    private int size;

    /****CONSTRUCTORS****/

    /**
     * Default constructor for the Stack class
     * @postcondition a new Stack object with all fields
     * assigned default values
     */
    public Stack() {
    	top = null;
    	size = 0;
    }

    /**
     * Constructor for the Stack class
     * Converts an array into a Stack in the same order
     * @param an array of elements to copy
     * e.g. [1,2,3] becomes 1->2->3->null
     */
    public Stack(T[] array) {
        this();
        if (array != null) {
            for (int i = array.length - 1; i >= 0; i--) {
                push(array[i]);
            }
        }
    }

    /**
     * Copy constructor for the Stack class
     * @param original the Stack to copy
     * @postcondition a new Stack object which is
     * an identical, but distinct, copy of original
     * REQUIRED: THIS METHOD MUST BE IMPLEMENTED
     * IN O(N) TIME
     */
    public Stack(Stack<T> original) {
    	this();
        if (original != null) {
            Stack<T> tempStack = new Stack<>();
            Node current = original.top;
            while (current != null) {
                tempStack.push(current.data);
                current = current.next;
            }
            while (!tempStack.isEmpty()) {
                push(tempStack.top.data);
                tempStack.pop();
            }
        }
    }

    /****ACCESSORS****/

    /**
     * Returns the value at the top of the Stack.
     * @return the value at the top of the Stack
     * @throws NoSuchElementException if the stack is empty
     */
    public T peek() throws NoSuchElementException{
    	if (isEmpty()) {
    		throw new NoSuchElementException("peek: Stack is empty");
    	}
    	return top.data;
    }
    
    /**
     * Returns the number of elements in the Stack.
     * @return the size of the Stack from 0 to n
     */
    @Override
    public int getSize() {
        return size;
    }
    
    /**
     * Determines whether the Stack is empty.
     * @return true if the Stack is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /****MUTATORS****/

    @Override
    public void push(T data) {
    	Node newNode = new Node (data);
    	newNode.next = top;
    	top = newNode;
    	size++;
    }
    
    /**
     * Removes the element at the top of the Stack.
     * @throws NoSuchElementException if the stack is empty
     * @postcondition the top element has been removed from the Stack
     */
    @Override
    public void pop() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("pop: Stack is empty");
        }
        top = top.next;
        size--;
    }

    /****ADDITONAL OPERATIONS****/

    /**
     * Returns the values stored in the Stack
     * as a String, separated by a blank space
     * with a new line character at the end
     * @return a String of Stack values
     */
    public String toString() {
    	StringBuilder sb = new StringBuilder();
        Node current = top;
        while (current != null) {
            sb.append(current.data).append(" ");
            current = current.next;
        }
        return sb.append("\n").toString();
    }

    /**
     * Determines whether two Stacks contain
     * the same values in the same order
     * @param obj the Object to compare to this Stack
     * @return whether obj and this Stack are equal
     */
    @Override public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Stack<?> other = (Stack<?>) obj;
        if (this.size != other.size) return false;

        Node currentThis = this.top;
        Object currentOther = other.top;
        while (currentThis != null && currentOther != null) {
            if (!currentThis.data.equals(((Stack<?>.Node) currentOther).data)) {
                return false;
            }
            currentThis = currentThis.next;
            currentOther = ((Stack<?>.Node) currentOther).next;
        }
        return true;
    }

    /**
     * Creates a String of the Stack in reverse order
     *
     * @return a Stack in reverse order
     */
    public String reverseStack() {
        return reverseStack(top) + "\n";
    }

    /**
     * Uses the recursive linear search algorithm to locate an element.
     * @param element the value to search for
     * @return whether the element is present
     * Note that in the case length == 0 the element is considered not found
     */
    public boolean linearSearch(T element) {
        return linearSearch(top, element);
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
    	    throw new IllegalStateException("Stack must be sorted for binary search.");
    	  }
    	  
    	  return binarySearch(0, size-1, value);
    }
    
    
    /** RECURSIVE HELPER METHODS */

    /**
     * Recursively (no loops) creates a String where the data is in reverse order.
     * @param n the current node
     * @return the Stack values as a String in reverse order.
     */
    private String reverseStack(Node currentNode) {
        if (currentNode == null) {
            return "";
        }
        return reverseStack(currentNode.next) + currentNode.data + " ";
    }
    
    public boolean isSorted() {
        return isSorted(top);
    }

    /**
     * Helper method to isSorted recursively determines whether
     * data is sorted from smallest to largest.
     * @param node the current node
     * @return whether the data is sorted in ascending order
     */
    private boolean isSorted(Node currentNode) {
        if (currentNode == null) {
            return true;
        }
        if (currentNode.next == null) {
        	return true;
        }
        if(isSorted(currentNode.next)) {
        	if (currentNode.data.compareTo(currentNode.next.data) <= 0) {
        		return true;
        	}
        }
        return false;
    }


    /**
     * Searches for the specified value by implementing the recursive
     * linearSearch algorithm.
     * @param value the value to search for
     * @return whether the value exists or not.
     */
    private boolean linearSearch(Node currentNode, T value) {
        if (currentNode == null) {
            return false;
        }
        if (currentNode.data.equals(value)) {
            return true;
        }
        return linearSearch(currentNode.next, value);
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
        T midValue = getMid(top, mid);
        
        if (midValue.equals(value)) {
            return true;
        } else if (midValue.compareTo(value) > 0) {
            return binarySearch( low, mid - 1, value);
        } else {
            return binarySearch( mid + 1, high, value);
        }
    }
    
}
