/**
 * BST.java
 * @author Hari Prakash
 * @author Hari Prakash
 * CIS 22C Lab 12
 */
import java.util.Comparator;
import java.util.NoSuchElementException;

public class BST<T> {
    private class Node {
        private T data;
        private Node left;
        private Node right;

        public Node(T data) {
            this.data = data;
            left = null;
            right = null;
        }
    }

    private Node root;

    /***CONSTRUCTORS***/

    /**
     * Default constructor for BST sets root to null.
     */
    public BST() {
        root = null;
    }

    /**
     * Copy constructor for BST.
     * @param bst the BST of which to make a copy.
     * @param cmp the way the tree is organized.
     */
    public BST(BST<T> bst, Comparator<T> cmp) {
        if (bst != null && bst.root != null) {
            copyHelper(bst.root, cmp);
        }
    }

    /**
     * Helper method for copy constructor.
     * @param node the node containing data to copy.
     * @param cmp the way the tree is organized.
     */
    private void copyHelper(Node node, Comparator<T> cmp) {
        if (node != null) {
            insert(node.data, cmp);
            copyHelper(node.left, cmp);
            copyHelper(node.right, cmp);
        }
    }

    /**
     * Creates a BST of minimal height from an array of values.
     * @param array the list of values to insert.
     * @param cmp the way the tree is organized.
     * @precondition array must be sorted in ascending order.
     * @throws IllegalArgumentException when the array is unsorted.
     */
    public BST(T[] array, Comparator<T> cmp) throws IllegalArgumentException {
        if (array == null) {
            root = null;
        } else if (!isSorted(array, cmp)) {
            throw new IllegalArgumentException("Array must be sorted in ascending order.");
        } else {
            root = arrayHelper(0, array.length - 1, array);
        }
    }


    /**
     * Private helper method for array constructor
     * to check for a sorted array.
     * @param array the array to check.
     * @param cmp the way the tree is organized.
     * @return whether the array is sorted.
     */
    private boolean isSorted(T[] array, Comparator<T> cmp) {
        if (array == null) {
            return true; 
        }
        for (int i = 0; i < array.length - 1; i++) {
            if (cmp.compare(array[i], array[i + 1]) > 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Recursive helper for the array constructor.
     * @param begin beginning array index.
     * @param end ending array index.
     * @param array array to search.
     * @return the newly created Node.
     */
    private Node arrayHelper(int begin, int end, T[] array) {
        if (begin > end) {
            return null;
        }
        int mid = (begin + end) / 2;
        Node node = new Node(array[mid]);
        node.left = arrayHelper(begin, mid - 1, array);
        node.right = arrayHelper(mid + 1, end, array);
        return node;
    }

    /***ACCESSORS***/

    /**
     * Returns the data stored in the root.
     * @precondition !isEmpty()
     * @return the data stored in the root.
     * @throws NoSuchElementException when precondition is violated.
     */
    public T getRoot() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("The tree is empty.");
        }
        return root.data;
    }


    /**
     * Determines whether the tree is empty.
     * @return whether the tree is empty.
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Returns the current size of the tree (number of nodes).
     * @return the size of the tree.
     */
    public int getSize() {
        return getSize(root);
    }

    /**
     * Helper method for the getSize method.
     * @param node the current node to count.
     * @return the size of the tree.
     */
    private int getSize(Node node) {
        if (node == null) {
            return 0;
        } else {
            return 1 + getSize(node.left) + getSize(node.right);
        }
    }

    /**
     * Returns the height of tree by counting edges.
     * @return the height of the tree.
     */
    public int getHeight() {
        return getHeight(root);
    }

    /**
     * Helper method for getHeight method.
     * @param node the current node whose height to count.
     * @return the height of the tree.
     */
    private int getHeight(Node node) {
        if (node == null) {
            return -1;
        } else {
            return 1 + Math.max(getHeight(node.left), getHeight(node.right));
        }
    }

    /**
     * Returns the smallest value in the tree.
     * @precondition !isEmpty()
     * @return the smallest value in the tree.
     * @throws NoSuchElementException when the precondition is violated.
     */
    public T findMin() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("BST is empty.");
        }
        return findMin(root).data;
    }

    /**
     * Recursive helper method to findMin method.
     * @param node the current node to check if it is the smallest.
     * @return the smallest value in the tree.
     */
    private Node findMin(Node node) {
        if (node.left == null) {
            return node;
        } else {
            return findMin(node.left);
        }
    }

    /**
     * Returns the largest value in the tree
     * @precondition !isEmpty()
     * @return the largest value in the tree.
     * @throws NoSuchElementException when the precondition is violated.
     */
    public T findMax() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("BST is empty.");
        }
        return findMax(root).data;
    }


    /**
     * Recursive helper method to findMax method.
     * @param node the current node to check if it is the largest.
     * @return the largest value in the tree.
     */
    private Node findMax(Node node) {
        if (node.right == null) {
            return node;
        } else {
            return findMax(node.right);
        }
    }

    /**
     * Searches for a specified value in the tree.
     * @param data the value to search for.
     * @param cmp the Comparator that indicates the way
     * the data in the tree was ordered.
     * @return the data stored in that Node of the tree, otherwise null.
     */
    public T search(T data, Comparator<T> cmp) {
        return search(data, root, cmp);
    }

    /**
     * Helper method for the search method.
     * @param data the data to search for.
     * @param node the current node to check.
     * @param cmp the Comparator that determines how the BST is organized.
     * @return the data stored in that Node of the tree, otherwise null.
     */
    private T search(T data, Node node, Comparator<T> cmp) {
        if (node == null) {
            return null;
        } else if (cmp.compare(data, node.data) == 0) {
            return node.data;
        } else if (cmp.compare(data, node.data) < 0) {
            return search(data, node.left, cmp);
        } else {
            return search(data, node.right, cmp);
        }
    }

    /***MUTATORS***/

    /**
     * Inserts a new node in the tree.
     * @param data the data to insert.
     * @param cmp the Comparator indicating how data in the tree is ordered.
     * @return This method does not return a value but modifies the state of the BST by inserting a new node with the specified data.
     */
    public void insert(T data, Comparator<T> cmp) {
        root = insert(data, root, cmp);
    }

    /**
     * Helper method to insert.
     * Inserts a new value in the tree.
     * @param data the data to insert.
     * @param node the current node in the search for the correct insert
     *     location.
     * @param cmp the Comparator indicating how data in the tree is ordered.
     */
    private Node insert(T data, Node node, Comparator<T> cmp) {
        if (node == null) {
            node = new Node(data);
        } else {
            if (cmp.compare(data, node.data) < 0) {
                node.left = insert(data, node.left, cmp);
            } else if (cmp.compare(data, node.data) > 0) {
                node.right = insert(data, node.right, cmp);
            }
        }
        return node;
    }

    /**
     * Removes a value from the BST
     * @param data the value to remove
     * @param cmp the Comparator indicating how data in the tree is organized.
     * Note: updates nothing when the element is not in the tree.
     */
    public void remove(T data, Comparator<T> cmp) {
        root = remove(data, root, cmp);
    }

    /**
     * Helper method to the remove method.
     * @param data the data to remove.
     * @param node the current node.
     * @param cmp the Comparator indicating how data in the tree is organized.
     * @return an updated reference variable.
     */
    private Node remove(T data, Node node, Comparator<T> cmp) {
        if (node == null) {
            return null;
        }
        int compareResult = cmp.compare(data, node.data);
        if (compareResult < 0) {
            node.left = remove(data, node.left, cmp);
        } else if (compareResult > 0) {
            node.right = remove(data, node.right, cmp);
        } else if (node.left != null && node.right != null) { // Two children
            node.data = findMin(node.right).data;
            node.right = remove(node.data, node.right, cmp);
        } else {
            node = (node.left != null) ? node.left : node.right;
        }
        return node;
    }
    
    /**
     * Finds the shared precursor (lowest common ancestor) of two elements in the BST.
     * Both elements must exist in the BST; otherwise, an IllegalArgumentException is thrown.
     *
     * @param data1 The first element to find the shared precursor for.
     * @param data2 The second element to find the shared precursor for.
     * @param cmp The comparator to determine the order of the BST.
     * @return The shared precursor (lowest common ancestor) of data1 and data2.
     * @throws IllegalArgumentException If either data1 or data2 does not exist in the BST.
     */
    public T sharedPrecursor(T data1, T data2, Comparator<T> cmp) {
        if (search(data1, root, cmp) == null || search(data2, root, cmp) == null) {
            throw new IllegalArgumentException("Both elements must exist in the BST.");
        }
        Node sharedPrecursorNode = sharedPrecursor(root, data1, data2, cmp);
        if (sharedPrecursorNode == null) {
        	throw new IllegalArgumentException("No shared precursor found for existing elements.");
        }
        return sharedPrecursorNode.data;
    }
    
    private Node sharedPrecursor(Node node, T data1, T data2, Comparator<T> cmp) {
        if (node == null) {
            return null;
        }
        if (cmp.compare(data1, node.data) < 0 && cmp.compare(data2, node.data) < 0) {
            return sharedPrecursor(node.left, data1, data2, cmp);
        } else if (cmp.compare(data1, node.data) > 0 && cmp.compare(data2, node.data) > 0) {
            return sharedPrecursor(node.right, data1, data2, cmp);
        } else {
            return node;
        }
    }
    
    /***ADDITONAL OPERATIONS***/

    /**
     * Returns a String containing the data in pre order
     * followed by a new line.
     * @return a String of data in pre order.
     */
    public String preOrderString() {
        StringBuilder preOrder = new StringBuilder();
        preOrderString(root, preOrder);
        return preOrder.toString() + "\n";
    }

    /**
     * Helper method to preOrderString method.
     * Prints the data in pre order to the console followed by a new line.
     * @param node the current Node
     * @param preOrder a StringBuilder containing the data
     */
    private void preOrderString(Node node, StringBuilder preOrder) {
        if (node != null) {
            preOrder.append(node.data + " ");
            preOrderString(node.left, preOrder);
            preOrderString(node.right, preOrder);
        }
    }

    /**
     * Returns a String containing the data in order followed by a new line.
     * @return a String of data in order
     */
    public String inOrderString() {
        StringBuilder inOrder = new StringBuilder();
        inOrderString(root, inOrder);
        return inOrder.toString() + "\n";
    }

    /**
     * Helper method to inOrderString.
     * Inserts the data in order into a String in order.
     * @param node the current Node
     * @param inOrder a String containing the data
     */
    private void inOrderString(Node node, StringBuilder inOrder) {
        if (node != null) {
            inOrderString(node.left, inOrder);
            inOrder.append(node.data + " ");
            inOrderString(node.right, inOrder);
        }
    }

    /**
     * Returns a String containing the data in post order.
     * @return a String of data in post order
     */
    public String postOrderString() {
        StringBuilder postOrder = new StringBuilder();
        postOrderString(root, postOrder);
        return postOrder.toString() + "\n";
    }

    /**
     * Helper method to postOrderString
     * Inserts the data in post order into a String
     * @param node the current Node
     * @param postOrder a String containing the data
     */
    private void postOrderString(Node node, StringBuilder postOrder) {
        if (node != null) {
            postOrderString(node.left, postOrder);
            postOrderString(node.right, postOrder);
            postOrder.append(node.data + " ");
        }
    }

    /**
     * Creates a String that is a height order
     * traversal of the data in the tree starting at
     * the Node with the largest height (the root)
     * down to Nodes of smallest height - with
     * Nodes of equal height added from left to right.
     * @return the level order traversal as a String
     */
    public String levelOrderString() {
        Queue<Node> que  = new Queue<>();
        StringBuilder sb = new StringBuilder();
        que.enqueue(root);
        levelOrderString(que, sb);
        return sb.toString() + "\n";
    }

    /**
     * Helper method to levelOrderString.
     * Inserts the data in level order into a String.
     * @param que the Queue in which to store the data.
     * @param heightTraverse a StringBuilder containing the data.
     */
    private void levelOrderString(Queue<Node> que, StringBuilder heightTraverse) {
        if(!que.isEmpty()) {
            Node nd = que.getFront();
            que.dequeue();
            if(nd != null) {
                que.enqueue(nd.left);
                que.enqueue(nd.right);
                heightTraverse.append(nd.data + " ");
            }
            levelOrderString(que, heightTraverse);
        }
    }
}
