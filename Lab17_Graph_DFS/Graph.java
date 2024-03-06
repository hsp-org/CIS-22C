package Lab17_Graph_DFS;

/**
 * Graph.java
 * @author Hari Prakash
 * @author Hari Prakash
 * CIS 22C, Lab 17
 */
import java.util.ArrayList;

public class Graph {
    private int vertices;
    private int edges;
    private ArrayList<LinkedList<Integer>> adj;
    private ArrayList<Character> color;
    private ArrayList<Integer> distance;
    private ArrayList<Integer> parent;
    private ArrayList<Integer> discoverTime;
    private ArrayList<Integer> finishTime;
    private static int time = 0;

    /** Constructors and Destructors */

    /**
     * initializes an empty graph, with n vertices and 0 edges
     *
     * @param numVtx the number of vertices in the graph
     * @precondition numVtx > 0
     * @throws IllegalArgumentException when numVtx <= 0
     */
    public Graph(int numVtx) throws IllegalArgumentException {
        if (numVtx <= 0) {
            throw new IllegalArgumentException("Number of vertices must be greater than 0.");
        }
        this.vertices = numVtx;
        this.edges = 0;
        this.adj = new ArrayList<>(numVtx);
             
        this.color = new ArrayList<>(numVtx);
        this.distance = new ArrayList<>(numVtx);
        this.parent = new ArrayList<>(numVtx);
        this.discoverTime = new ArrayList<>(numVtx);
        this.finishTime = new ArrayList<>(numVtx);
        
        for (int i = 0; i < numVtx; i++) {
        	this.adj.add(new LinkedList<>());
            this.color.add('W'); 
            this.distance.add(-1); 
            this.parent.add(0); 
            this.discoverTime.add(-1); 
            this.finishTime.add(-1);
        }
    }


    /*** Accessors ***/

    /**
     * Returns the number of edges in the graph
     *
     * @return the number of edges
     */
    public int getNumEdges() {
        return this.edges;
    }

    /**
     * Returns the number of vertices in the graph
     *
     * @return the number of vertices
     */
    public int getNumVertices() {
        return this.vertices;
    }

    /**
     * returns whether the graph is empty (no edges)
     *
     * @return whether the graph is empty
     */
    public boolean isEmpty() {
        return this.edges == 0;
    }

    /**
     * Returns the value of the distance[v]
     *
     * @param v a vertex in the graph
     * @precondition 0 < v <= vertices
     * @return the distance of vertex v
     * @throws IndexOutOfBoundsException when v is out of bounds
     */
    public Integer getDistance(Integer v) throws IndexOutOfBoundsException {
        if (v < 1 || v > this.vertices) {
            throw new IndexOutOfBoundsException("Vertex is out of bounds.");
        }
        return this.distance.get(v - 1);
    }

    /**
     * Returns the value of the parent[v]
     *
     * @param v a vertex in the graph
     * @precondition 0 < v <= vertices
     * @return the parent of vertex v
     * @throws IndexOutOfBoundsException when v is out of bounds
     */
    public Integer getParent(Integer v) throws IndexOutOfBoundsException {
        if (v < 1 || v > this.vertices) {
            throw new IndexOutOfBoundsException("Vertex is out of bounds.");
        }
        return this.parent.get(v - 1);
    }

    /**
     * Returns the value of the color[v]
     *
     * @param v a vertex in the graph
     * @precondition 0 < v <= vertices
     * @return the color of vertex v
     * @throws IndexOutOfBoundsException when v is out of bounds
     */
    public Character getColor(Integer v) throws IndexOutOfBoundsException {
        if (v < 1 || v > this.vertices) {
            throw new IndexOutOfBoundsException("Vertex is out of bounds.");
        }
        return this.color.get(v - 1);
    }

    /**
     * Returns the value of the discoverTime[v]
     *
     * @param v a vertex in the graph
     * @precondition 0 < v <= vertices
     * @return the discover time of vertex v
     * @throws IndexOutOfBoundsException when v is out of bounds
     */
    public Integer getDiscoverTime(Integer v) throws IndexOutOfBoundsException {
        if (v < 1 || v > this.vertices) {
            throw new IndexOutOfBoundsException("Vertex is out of bounds.");
        }
        return this.discoverTime.get(v - 1);
    }

    /**
     * Returns the value of the finishTime[v]
     *
     * @param v a vertex in the graph
     * @precondition 0 < v <= vertices
     * @return the finish time of vertex v
     * @throws IndexOutOfBoundsException when v is out of bounds
     */
    public Integer getFinishTime(Integer v) throws IndexOutOfBoundsException {
        if (v < 1 || v > this.vertices) {
            throw new IndexOutOfBoundsException("Vertex is out of bounds.");
        }
        return this.finishTime.get(v - 1);
    }

    /**
     * Returns the LinkedList stored at index v
     *
     * @param v a vertex in the graph
     * @return the adjacency LinkedList at v
     * @precondition 0 < v <= vertices
     * @throws IndexOutOfBoundsException when v is out of bounds
     */
    public LinkedList<Integer> getAdjacencyList(Integer v) throws IndexOutOfBoundsException {
        if (v < 1 || v > this.vertices) {
            throw new IndexOutOfBoundsException("Vertex is out of bounds.");
        }
        return this.adj.get(v - 1);
    }

    /*** Manipulation Procedures ***/

    /**
     * Inserts vertex v into the adjacency list of vertex u (i.e. inserts v into
     * the list at index u) @precondition, 0 < u, v <= vertices
     *
     * @param u a vertex in the graph
     * @param v a vertex in the graph
     * @throws IndexOutOfBounds exception when u or v is out of bounds
     */
    public void addDirectedEdge(Integer u, Integer v) throws IndexOutOfBoundsException {
        if (u < 1 || u > this.vertices || v < 1 || v > this.vertices) {
            throw new IndexOutOfBoundsException("Vertex is out of bounds.");
        }
        this.adj.get(u - 1).addLast(v);
        this.edges++;
    }

    /**
     * Inserts vertex v into the adjacency list of vertex u (i.e. inserts v into
     * the list at index u) and inserts u into the adjacent vertex list of v.
     *
     * @param u a vertex in the graph
     * @param v a vertex in the graph
     * @precondition, 0 < u, v <= vertices
     * @throws IndexOutOfBoundsException when u or v is out of bounds
     */
    public void addUndirectedEdge(Integer u, Integer v) throws IndexOutOfBoundsException {
        if (u < 1 || u > this.vertices || v < 1 || v > this.vertices) {
            throw new IndexOutOfBoundsException("Vertex is out of bounds.");
        }
        this.adj.get(u - 1).addLast(v);
        this.adj.get(v - 1).addLast(u);
        this.edges++;
    }

    /*** Additional Operations ***/

    /**
     * Creates a String representation of the Graph Prints the adjacency list of
     * each vertex in the graph, vertex: <space separated list of adjacent
     * vertices>
     * @return a space separated list of adjacent vertices
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < vertices; i++) {
            sb.append(i + 1).append(": ");
            LinkedList<Integer> list = adj.get(i);
            list.positionIterator();
            while (!list.offEnd()) {
                sb.append(list.getIterator()).append(" ");
                list.advanceIterator();
            }
            sb.append("\n");
        }
        return sb.toString();
    }


    /**
     * Performs breath first search on this Graph give a source vertex
     *
     * @param source the starting vertex
     * @precondition source is a vertex in the graph
     * @throws IndexOutOfBoundsException when the source vertex is out of bounds
     *     of the graph
     */
    public void BFS(Integer source) throws IndexOutOfBoundsException {
        if (source < 1 || source > this.vertices) {
            throw new IndexOutOfBoundsException("Source vertex is out of bounds.");
        }
        LinkedList<Integer> temp = new LinkedList<>();
        temp.addLast(source);
        color.set(source - 1, 'G');
        distance.set(source - 1, 0);
        parent.set(source - 1, 0);

        while (!temp.isEmpty()) {
            Integer num = temp.getFirst() - 1;
            temp.removeFirst();
            adj.get(num).positionIterator();

            while (!adj.get(num).offEnd()) {
                Integer indexInteger = adj.get(num).getIterator() - 1;
                if (color.get(indexInteger).equals('W')) {
                    color.set(indexInteger, 'G');
                    distance.set(indexInteger, distance.get(num) + 1);
                    parent.set(indexInteger, num + 1); 
                    temp.addLast(indexInteger + 1);
                }
                adj.get(num).advanceIterator();
            }
            color.set(num, 'B');
        }
    }


    /**
     * Performs depth first search on this Graph in order of vertex lists
     */
    public void DFS() {
        for(int i = 0; i < vertices; i++) {
            color.set(i, 'W');
            parent.set(i, -1);
            discoverTime.set(i, -1);
            finishTime.set(i, -1);
        }

        time = 0;
        for(int i = 0; i < vertices; i++) {
            if(color.get(i).equals('W')) {
                visit(i);
            }
        }
    }

    /**
     * Private recursive helper method for DFS
     *
     * @param vertex the vertex to visit
     */
    private void visit(int vertex) {
        color.set(vertex, 'G');
        discoverTime.set(vertex, ++time);
        LinkedList<Integer> vertexAdjList = adj.get(vertex); 
        vertexAdjList.positionIterator(); 
        while(!vertexAdjList.offEnd()) { 
            Integer adjacentVertex = vertexAdjList.getIterator() - 1;
            if(color.get(adjacentVertex).equals('W')) {
                parent.set(adjacentVertex, vertex + 1);
                visit(adjacentVertex);
            }
            vertexAdjList.advanceIterator();
        }
        color.set(vertex, 'B');
        finishTime.set(vertex, ++time);
    }
}
