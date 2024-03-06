package Lab17_Graph_DFS;
/**
 * Defines a doubly-linked list class
 * @author Hari Prakash
 * @author Hari Prakash
 * CIS Applied Lab 3.2
 */
import java.util.NoSuchElementException;

public class LinkedList<T> {
    private class Node {
        private T data;
        private Node next;
        private Node prev;

        public Node(T data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }
    }

    private int length;
    private Node first;
    private Node last;
    private Node iterator;

    /**** CONSTRUCTORS ****/

    /**
     * Instantiates a new LinkedList with default values
     * @postcondition first, last, and iterator is set to null and length is 0.
     */
    public LinkedList() {
    	this.first = null;
    	this.last = null;
    	this.iterator = null;
    	this.length = 0;
    }

    /**
     * Converts the given array into a LinkedList
     * @param array the array of values to insert into this LinkedList
     * @postcondition Linkedlist is created where each element of the array is a node in the list in the same order
     */
    public LinkedList(T[] array) {
        this(); 
        if (array != null) {
            for (T element : array) {
                addLast(element);
            }
        }
    }

    /**
     * Instantiates a new LinkedList by copying another List
     * @param original the LinkedList to copy
     * @postcondition a new List object, which is an identical,
     * but separate, copy of the LinkedList original
     */
    public LinkedList(LinkedList<T> original) {
        this();
        if (original != null) {
            Node current = original.first;
            while (current != null) {
                addLast(current.data);
                current = current.next;
            }
        }
    }


    /**** ACCESSORS ****/

    /**
     * Returns the value stored in the first node
     * @precondition List can not be empty
     * @return the value stored at node first
     * @throws NoSuchElementException if list is empty
     */
    public T getFirst() throws NoSuchElementException {
        if (first == null) {
        	throw new NoSuchElementException("The list is empty!");
        }
        return first.data;
    }

    /**
     * Returns the value stored in the last node
     * @precondition list can not be empty
     * @return the value stored in the node last
     * @throws NoSuchElementException if list is empty
     */
    public T getLast() throws NoSuchElementException {
    	if (last == null) {
        	throw new NoSuchElementException("The list is empty!");
        }
        return last.data;
    }

    /**
     * Returns the data stored in the iterator node
     * @precondition Iterator must not be null
     * @return the data stored in the iterator node
     * @throws NullPointerException if iterator is null
     */
    public T getIterator() throws NullPointerException {
        if (iterator == null) {
        	throw new NullPointerException("getIterator: iterator is at the end of the list");
        }
        
        return iterator.data;
    }

    /**
     * Returns the current length of the LinkedList
     * @return the length of the LinkedList from 0 to n
     */
    public int getLength() {
        return length;
    }

    /**
     * Returns whether the LinkedList is currently empty
     * @return whether the LinkedList is empty
     */
    public boolean isEmpty() {
        return length == 0;
    }

   /**
     * Returns whether the iterator is offEnd, i.e. null
     * @return whether the iterator is null
     */
    public boolean offEnd() {
        return iterator == null;
    }

    /**** MUTATORS ****/

    /**
     * Creates a new first element
     * @param data the data to insert at the front of the LinkedList
     * @postcondition adds new node to the front of the list
     */
    public void addFirst(T data) {
        Node newNode = new Node(data);
        if (length == 0) {
        	first = last = newNode;
        } else {
        	newNode.next = first;
        	first.prev = newNode;
        	first = newNode;
        }
        length++;
    }

    /**
     * Creates a new last element
     * @param data the data to insert at the end of the LinkedList
     * @postcondition adds new node to the end of the list
     */
    public void addLast(T data) {
        Node newNode = new Node(data);
        if (length == 0) {
        	first = last = newNode;
        } else {
        	last.next = newNode;
        	newNode.prev = last;
        	last = newNode;
        }
        length++;
    }

    /**
     * Inserts a new element after the iterator
     * @param data the data to insert
     * @precondition Iterator must not be null
     * @throws NullPointerException if iterator is null
     */
    public void addIterator(T data) throws NullPointerException{
        if(iterator == null) {
        	throw new NullPointerException("addIterator: iterator is null");
        } else if (iterator == last) {
        	addLast(data);
        } else {
        	Node newNode = new Node(data);
            newNode.next = iterator.next;
            newNode.prev = iterator;
            iterator.next.prev = newNode;
            iterator.next = newNode;
            length++;
        }
        
    }

    /**
     * removes the element at the front of the LinkedList
     * @precondition list can not be empty
     * @postcondition first node is removed from the list
     * @throws NoSuchElementException list can not be empty
     */
    public void removeFirst() throws NoSuchElementException {
    	if (first == null) {
            throw new NoSuchElementException("List can not be empty!");
        }
        if (iterator == first) {
            iterator = null;
        }
        if (first == last) {
            first = last = null;
        } else {
            first = first.next;
            first.prev = null;
        }
        length--;
    }

    /**
     * removes the element at the end of the LinkedList
     * @precondition list can not be empty
     * @postcondition removes the last node from the list
     * @throws NoSuchElementException list can not be empty
     */
    public void removeLast() throws NoSuchElementException {
    	if (last == null) {
            throw new NoSuchElementException("List can not be empty!");
        }
        if (iterator == last) {
            iterator = null;
        }
        if (length == 1) {
            first = last = null;
        } else {
            last = last.prev;
            last.next = null;
        }
        length--;
    }

    /**
     * Removes the element referenced by the iterator
     * @precondition Iterator must not be null and must reference a valid node
     * @postcondition The element pointed by the iterator is removed
     * @throws NullPointerException if iterator is null or invalid
     */
    public void removeIterator() throws NullPointerException {
    	if(iterator == null) {
    		throw new NullPointerException("removeIterator: iterator is at the end of the list");
    	}
    	if (iterator == first) {
    	    removeFirst();
    	} else if (iterator == last) {
    	    removeLast();
    	} else {
    	    iterator.prev.next = iterator.next;
    	    iterator.next.prev = iterator.prev;
    	    length--;
    	}

    	iterator = null;
    	
    }

    /**
     * Places the iterator at the first node
     * @postcondition Iterator is positioned at the beginning of the list
     */
    public void positionIterator(){
    	iterator = first;
    }
    

    /**
     * Moves the iterator one node towards the last
     * @precondition Iterator must not be at the end of the list
     * @postcondition Iterator is advanced by one node
     * @throws NullPointerException if iterator is at the end of the list
     */
    public void advanceIterator() throws NullPointerException {
    	if (iterator == null) {
    		throw new NullPointerException("advanceIterator: iterator is at the end of the list");
    	}
    	iterator = iterator.next;
    }

    /**
     * Moves the iterator one node towards the first
     * @precondition Iterator must not be at the beginning of the list
     * @postcondition Iterator is moved back by one node
     * @throws NullPointerException if iterator is at the beginning of the list
     */
    public void reverseIterator() throws NullPointerException {
    	if (iterator == null) {
    		throw new NullPointerException("reverseIterator: iterator is at the end of the list.");
    	}
    	iterator = iterator.prev;
    }

    /**** ADDITIONAL OPERATIONS ****/

    /**
     * Re-sets LinkedList to empty as if the
     * default constructor had just been called
     */
    public void clear() {
    	first = null;
    	last = null;
    	iterator = null;
    	length = 0;
    }

    /**
     * Converts the LinkedList to a String, with each value separated by a blank
     * line At the end of the String, place a new line character
     * @return the LinkedList as a String
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        Node temp = first;
        while(temp != null) {
        	result.append(temp.data + " ");
        	temp = temp.next;
        }
        return result.toString() + "\n";
    }



    /**
     * Determines whether the given Object is
     * another LinkedList, containing
     * the same data in the same order
     * @param obj another Object
     * @return whether there is equality
     */
    @SuppressWarnings("unchecked") //good practice to remove warning here
    @Override 
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof LinkedList)) {
            return false;
        } else {
            LinkedList<T> L = (LinkedList<T>) obj;
            if (length != L.length) {
                return false;
            } else {
                Node temp1 = this.first;
                Node temp2 = L.first;
                while (temp1 != null) {
                    if (temp1.data == null || temp2.data == null) {
                        if (temp1.data != temp2.data) {
                            return false;
                        }
                    } else if (!temp1.data.equals(temp2.data)) { // Corrected this line
                        return false;
                    }
                    temp1 = temp1.next;
                    temp2 = temp2.next;
                }
                return true;
            }
        }
    }




    /**CHALLENGE METHODS*/

   /**
     * Moves all nodes in the list towards the end
     * of the list the number of times specified
     * Any node that falls off the end of the list as it
     * moves forward will be placed the front of the list
     * For example: [1, 2, 3, 4, 5], numMoves = 2 -> [4, 5, 1, 2 ,3]
     * For example: [1, 2, 3, 4, 5], numMoves = 4 -> [2, 3, 4, 5, 1]
     * For example: [1, 2, 3, 4, 5], numMoves = 7 -> [4, 5, 1, 2 ,3]
     * @param numMoves the number of times to move each node.
     * @precondition numMoves >= 0
     * @postcondition iterator position unchanged (i.e. still referencing
     * the same node in the list, regardless of new location of Node)
     * @throws IllegalArgumentException when numMoves < 0
     */
    public void spinList(int numMoves) throws IllegalArgumentException {
        if (numMoves < 0) {
            throw new IllegalArgumentException("numMoves must be >= 0");
        }
        if (length <= 1) {
            return;
        }
        numMoves %= length;
        for (int i = 0; i < numMoves; i++) {
            Node lastNode = last;
            removeLast();
            addFirst(lastNode.data);
        }
    }



   /**
     * Splices together two LinkedLists to create a third List
     * which contains alternating values from this list
     * and the given parameter
     * For example: [1,2,3] and [4,5,6] -> [1,4,2,5,3,6]
     * For example: [1, 2, 3, 4] and [5, 6] -> [1, 5, 2, 6, 3, 4]
     * For example: [1, 2] and [3, 4, 5, 6] -> [1, 3, 2, 4, 5, 6]
     * @param list the second LinkedList
     * @return a new LinkedList, which is the result of
     * interlocking this and list
     * @postcondition this and list are unchanged
     */
    public LinkedList<T> altLists(LinkedList<T> list) {
        LinkedList<T> resultList = new LinkedList<>();
        Node currentA = this.first;
        Node currentB = (list != null) ? list.first : null;

        while (currentA != null || currentB != null) {
            if (currentA != null) {
                resultList.addLast(currentA.data);
                currentA = currentA.next;
            }
            if (currentB != null) {
                resultList.addLast(currentB.data);
                currentB = currentB.next;
            }
        }
        return resultList;
    }
    
    /**
     * Returns each element in the LinkedList along with its
     * numerical position from 1 to n, followed by a newline.
     * @return A string representation of the LinkedList where each element is prefixed
     * with its numerical position, starting from 1, followed by a newline character at the end.
     */
    public String numberedListString() {
        StringBuilder listRepresentation = new StringBuilder();
        Node currentNode = first;
        int position = 1;
        while (currentNode != null) {
            listRepresentation.append(position++).append(". ").append(currentNode.data).append("\n");
            currentNode = currentNode.next;
        }
        listRepresentation.append("\n");
        return listRepresentation.toString();
    }
    
    /**
     * Searches the LinkedList for a given element's index.
     * @param targetData the data whose index to locate.
     * @return the index of the data or -1 if the data is not contained in the LinkedList.
     */
    public int findIndex(T targetData) {
        Node currentNode = first;
        int currentIndex = 0;
        while (currentNode != null) {
            if ((currentNode.data == null && targetData == null) ||
                (currentNode.data != null && currentNode.data.equals(targetData))) {
                return currentIndex;
            }
            currentNode = currentNode.next;
            currentIndex++;
        }
        return -1; // Element not found
    }
    
    /**
     * Advances the iterator to location within the LinkedList
     * specified by the given index.
     * @param targetIndex the index at which to place the iterator.
     * @precondition targetIndex >= 0,  targetIndex < length
     * @throws IndexOutOfBoundsException when targetIndex is out of bounds
     */
    public void advanceIteratorToIndex(int targetIndex) throws IndexOutOfBoundsException {
        if (targetIndex < 0 || targetIndex >= length) {
            throw new IndexOutOfBoundsException("Index " + targetIndex + " is out of bounds");
        }
        iterator = first;
        for (int currentIndex = 0; currentIndex < targetIndex; currentIndex++) {
            iterator = iterator.next;
        }
    }

}