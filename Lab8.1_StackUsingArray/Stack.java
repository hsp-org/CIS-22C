/**
 * Stack class - Array Version
 * @author Hari Prakash
 * @author Hari Prakash
 * CIS 22C, Lab 8.1
 */
import java.util.NoSuchElementException;

public class Stack<T> implements LIFO<T> {
    private T[] stack;
    private int currSize;
    private final int SIZE = 10;

    /****CONSTRUCTORS****/

    /**
     * Default constructor for the Queue class
     * with an initial length of 10 and
     * no elements
     */
    @SuppressWarnings("unchecked")
    public Stack() {
        stack = (T[]) new Object[SIZE];
        currSize = 0;

    }

   /**
     * Converts an array into a Stack in the same order
     * @param array the array to copy
     */
    @SuppressWarnings("unchecked")
    public Stack(T[] array) {
        this(); // Initialize with default constructor
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
     */
    @SuppressWarnings("unchecked")
    public Stack(Stack<T> original) {
        if (original == null) {
            this.stack = (T[]) new Object[SIZE];
            this.currSize = 0;
        } else {
            this.stack = (T[]) new Object[original.stack.length];
            this.currSize = original.currSize;
            for (int i = 0; i < currSize; i++) {
                this.stack[i] = original.stack[i];
            }
        }
    }

    /****ACCESSORS****/
    
    
    /**
     * Returns the value at the top of the Stack
     * @return the value at the top of the Stack
     * @throws NoSuchElementException when the Stack is empty
     */
    @Override
    public T peek() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }
        return stack[currSize - 1];
    }
    
    
    /**
     * Returns the size of the Stack
     * @return the size of the Stack from 0 to n
     */
    @Override
    public int getSize() {
        return currSize;
    }
    
    
    /**
     * Determines whether the Stack is empty
     * @return true if the Stack is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return currSize == 0;
    }

    /****MUTATORS****/

    @Override
    public void push(T data) {
        if (currSize == stack.length) {
            resize();
        }
        stack[currSize++] = data;
    }
    
    
    /**
     * Removes the top element from the Stack
     * @throws NoSuchElementException when the Stack is empty
     * @postcondition the top element has been removed from the Stack
     */
    @Override
    public void pop() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }
        stack[--currSize] = null;
    }
    

    /****ADDITONAL OPERATIONS****/

    /**
     * Returns the values stored in the Stack
     * as a String, separated by a blank space
     * with a new line character at the end
     * @return a String of Stack values
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = currSize - 1; i >= 0; i--) {
            sb.append(stack[i]).append(" ");
        }
        sb.append("\n");
        return sb.toString();
    }

    /**
     * Determines whether two obects are Stacks and
     * contain the same values in the same order
     * @param obj the Object to compare to this Stack
     * @return whether obj and this are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Stack)) return false;
        Stack<?> other = (Stack<?>) obj;
        if (this.currSize != other.currSize) return false;
        for (int i = 0; i < currSize; i++) {
            if (!stack[i].equals(other.stack[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * Creates a String of the Stack in reverse order by calling the
     * recursive helper method.
     * @return a Stack in reverse order
     */
//    public String reverseStack() {
//        return reverseStack(currSize - 1);
//    }
    
    public String reverseStack() {
        StringBuilder sb = new StringBuilder();
        reverseStack(sb);
        sb.append("\n");
        return sb.toString();
    }


    /**PRIVATE HELPER METHODS*/

    /**
     * Recursively creates a String where the data is in reverse order.
     * @param index the current index
     * @return a String of this Stack in reverse order
     */
//    private String reverseStack(int index) {
//        if (index < 0) {
//            return "\n";
//        }
//        return stack[index] + " " + reverseStack(index - 1);
//    }
    
    private void reverseStack(StringBuilder sb) {
        if (!isEmpty()) {
            T topElement = peek();
            pop();
            reverseStack(sb);
            sb.append(topElement).append(" ");
            push(topElement); 
        }
    }

    /**
     * Increases the current array
     * size by 10
     */
    @SuppressWarnings("unchecked")
    private void resize() {
        T[] temp = (T[]) new Object[stack.length + SIZE];
        System.arraycopy(stack, 0, temp, 0, stack.length);
        stack = temp;
    }
}
