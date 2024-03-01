/**
 * Stack class - Two Queue Version
 * @author Hari Prakash
 * @author Hari Prakash
 * CIS 22C, Lab 8.2
 */
import java.util.NoSuchElementException;

public class Stack<T extends Comparable<T>> implements LIFO<T> {
    private Queue<T> queue1;
    private Queue<T> queue2;

    /**** CONSTRUCTORS ****/

    /**
     * Default constructor for the Stack class
     */
    public Stack() {
    	queue1 = new Queue<>();
    	queue2 = new Queue<>();
    	
    }

   /**
     * Converts an array into a Stack in the same order.
     * @param array the array to copy
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
     * @postcondition a new Stack object which is an identical,
     * but distinct, copy of original
     */
    public Stack(Stack<T> original) {
        this();
        if (original != null) {
            Queue<T> tempQueue = new Queue<>(original.queue1);
            Stack<T> tempStack = new Stack<>();

            while (!tempQueue.isEmpty()) {
                tempStack.push(tempQueue.getFront());
                tempQueue.dequeue();
            }
            
            while (!tempStack.isEmpty()) {
                push(tempStack.peek());
                tempStack.pop();
            }
        }
    }


    /**** ACCESSORS ****/
    
    /**
     * Returns the value at the top of the Stack without removing it.
     * @return the value at the top of the Stack
     * @throws NoSuchElementException if the stack is empty
     */
    @Override
    public T peek() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty.");
        }
        return queue1.getFront();
    }
    
    /**
     * Returns the size of the Stack
     * @return the size from 0 to n
     */
    @Override
    public int getSize() {
        return queue1.getSize();
    }
    
    /**
     * Determines whether a Stack is empty
     * @return whether the Stack contains no elements
     */
    @Override
    public boolean isEmpty() {
        return queue1.isEmpty();
    }

    /**** MUTATORS ****/

    @Override
    public void push(T data) {
        queue2.enqueue(data);
        while (!queue1.isEmpty()) {
            queue2.enqueue(queue1.getFront());
            queue1.dequeue();
        }
        Queue<T> temp = queue1;
        queue1 = queue2;
        queue2 = temp;
    }
    
    /**
     * Removes the top element in the Stack
     * @precondition !isEmpty()
     * @throws NoSuchElementException when the precondition is violated
     * @postcondition the top element has been removed
     */
    @Override
    public void pop() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot pop from an empty stack.");
        }
        queue1.dequeue();
    }
    /**** ADDITONAL OPERATIONS ****/

    /**
     * Returns the values stored in the Stack
     * as a String, separated by a blank space
     * with a new line character at the end
     * @return a String of Stack values
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Queue<T> tempQueue = new Queue<>(queue1);

        while (!tempQueue.isEmpty()) {
            sb.append(tempQueue.getFront()).append(" ");
            tempQueue.dequeue();
        }

        sb.append("\n");
        return sb.toString();
    }

    /**
     * Determines whether two objects are Stacks and
     * contain the same values in the same order
     * @param obj the Object to compare to this Stack
     * @return whether obj and this are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Stack<?> other = (Stack<?>) obj;
        return queue1.equals(other.queue1);
    }

    /**
     * Creates a String of the Stack in reverse order.
     * @return a Stack in reverse order
     */
    public String reverseStack() {
        String reversedQueue = queue1.reverseQueue();
        queue1.reverseQueue();
        return reversedQueue;
    }


}
