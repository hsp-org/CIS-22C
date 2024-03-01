/**
 * Stack class - singly-linked list version
 * @author Hari Prakash
 * @author Hari Prakash
 * CIS 22C, Lab 6
 */
import java.util.NoSuchElementException;

public class Stack<T> implements LIFO<T> {
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
    @SuppressWarnings("unchecked")
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
}
