/**
 * Heap.java
 * @author Hari Prakash
 * @author Hari Prakash
 * CIS 22C, Lab 19
 */
import java.util.ArrayList;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class Heap<T> {
    private int heapSize;
    private ArrayList<T> heap;
    private Comparator<T> cmp;

    /**Constructors/

    /**
     * Constructor for the Heap class.
     * Sets heapSize to data size, stores parameters, inserts null at heap
     * element 0, and calls buildHeap().
     * @param data an unordered ArrayList, where element 0 is not used.
     * @param comparator that determines organization of heap
     * based on priority.
     */
    public Heap(ArrayList<T> data, Comparator<T> cmp) {
    	this.heap = new ArrayList<>(data);
        this.heap.add(0, null);
        this.cmp = cmp;
        this.heapSize = data.size();
        buildHeap();
    }

    /**Mutators*/

    /**
     * Converts an ArrayList into a valid max heap. Called by constructor.
     * Calls helper method heapify.
     */
    public void buildHeap() {
    	for (int i = heapSize / 2; i > 0; i--) {
            heapify(i);
        }
    }

    /**
     * Helper method to buildHeap, remove, and sort.
     * Bubbles an element down to its proper location within the heap.
     * @param index an index in the heap
     */
    private void heapify(int index) {
        int left = getLeft(index);
        int right = getRight(index);
        int largest = index;

        if (left <= heapSize && cmp.compare(heap.get(left), heap.get(index)) > 0) {
            largest = left;
        }

        if (right <= heapSize && cmp.compare(heap.get(right), heap.get(largest)) > 0) {
            largest = right;
        }

        if (largest != index) {
            T swap = heap.get(index);
            heap.set(index, heap.get(largest));
            heap.set(largest, swap);
            heapify(largest);
        }
    }



    /**
     * Inserts the given data into heap.
     * Calls helper method heapIncreaseKey.
     * @param key the data to insert
     */
    public void insert(T key) {
        heapSize++;
        if (heapSize >= heap.size()) {
            heap.add(key);
        } else {
            heap.set(heapSize, key);
        }
        heapIncreaseKey(heapSize, key);
    }


    /**
     * Helper method for insert.
     * Bubbles an element up to its proper location
     * @param index the current index of the key
     * @param key the data
     */
    private void heapIncreaseKey(int index, T key) {
        if (cmp.compare(key, heap.get(index)) < 0) {
            throw new IllegalArgumentException("helpIncreaseKey(): New key is smaller than current key");
        }
        heap.set(index, key);
        while (index > 1 && cmp.compare(heap.get(getParent(index)), heap.get(index)) < 0) {
            T temp = heap.get(index);
            heap.set(index, heap.get(getParent(index)));
            heap.set(getParent(index), temp);
            index = getParent(index);
        }
    }


    /**
     * Removes the element at the specified index.
     * Calls helper method heapify
     * @param index the index of the element to remove
     */
    public void remove(int index) {
        if (index <= 0 || index > heapSize) {
            throw new IndexOutOfBoundsException("remove(): Index out of heap range");
        }
        T lastElement = heap.get(heapSize);
        heap.set(index, lastElement);
        heap.remove(heapSize);
        heapSize--;
        if (index <= heapSize) {
            heapify(index);
        }
    }


    /**Accessors*/

    /**
     * Returns the heap size (current number of elements)
     * @return the size of the heap
     */
    public int getHeapSize() {
        return heapSize;
    }

    /**
     * Returns the location (index) of the
     * left child of the element stored at index.
     * @param index the current index
     * @return the index of the left child.
     * @precondition 0 < index <= heap_size
     * @throws IndexOutOfBoundsException when precondition is violated.
     */
    public int getLeft(int index) throws IndexOutOfBoundsException{
    	if (index <= 0 || index > heapSize) {
    		throw new IndexOutOfBoundsException("getLeft(): out of bound");
    	}
    	int left = index * 2;
    	return left;
    }


    /**
     * Returns the location (index) of the right child of the element
     * stored at index.
     * @param index the current index
     * @return the index of the right child
     * @precondition 0 < i <= heap_size
     * @throws IndexOutOfBoundsException when precondition is violated.
     */
    public int getRight(int index) throws IndexOutOfBoundsException {
    	if (index <= 0 || index > heapSize) {
    		throw new IndexOutOfBoundsException("getRight(): out of bound");
    	}
    	int right = index * 2 + 1;
    	return right;
    	
    }



    /**
     * Returns the location (index) of the
     * parent of the element stored at index.
     * @param index the current index
     * @return the index of the parent
     * @precondition 1 < i <= heap_size
     * @throws IndexOutOfBoundsException when precondition is violated.
     */
    public int getParent(int index) throws IndexOutOfBoundsException {
        if (index <= 1 || index > heapSize) {
            throw new IndexOutOfBoundsException("Index " + index + " does not have a parent.");
        }
        return index / 2;
    }


    /**
     * Returns the maximum element (highest priority)
     * @return the max value
     */
    public T getMax() {
        if (heapSize > 0) {
            return heap.get(1);
        } else {
            throw new NoSuchElementException("getMax(): Heap is empty.");
        }
    }

    /**
     * Returns the element at a specific index.
     * @param index an index in the heap.
     * @return the data at the index.
     * @precondition 0 < i <= heap_size
     * @throws IndexOutOfBoundsException when precondition is violated.
     */
    public T getElement(int index) {
        if (index > 0 && index <= heapSize) {
            return heap.get(index);
        } else {
            throw new IndexOutOfBoundsException("Index out of bounds for getElement().");
        }
    }

    /**Additional Operations*/

    /**
     * Creates a String of all elements in the heap, separated by ", ".
     * @return a String of all elements in the heap, separated by ", ".
     */
    @Override
    public String toString() {
        if (heapSize == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= heapSize; i++) {
            sb.append(heap.get(i));
            if (i < heapSize) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    /**
     * Uses the heap sort algorithm to sort the heap into ascending order.
     * Calls helper method heapify.
     * @return an ArrayList of sorted elements
     * @postcondition heap remains a valid heap
     */
    public ArrayList<T> sort() {
        ArrayList<T> backup = new ArrayList<>(heap);
        int originalHeapSize = heapSize;
        
        ArrayList<T> sortedList = new ArrayList<>();
        while (heapSize > 0) {
            max = heap.get(1);
            last = heap.get(heapSize);
            heap.set(1, last);
            heapSize--; 
            if (heapSize > 0) {
                heapify(1);
            }
            sortedList.add(max);
        }

        heap = backup;
        heapSize = originalHeapSize;

        ArrayList<T> reversedList = new ArrayList<>();
        for (int i = sortedList.size() - 1; i >= 0; i--) {
            reversedList.add(sortedList.get(i));
        }

        return reversedList;
    }
}
