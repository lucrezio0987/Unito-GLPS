import java.util.Collection;
import java.util.Set;
import java.util.Comparator;

/**
 * Interface for a generic graph data structure.
 *
 * @param <E> The type of nodes in the graph.
 */
public interface GrafoInterface<E extends Comparable<E>> {

    /**
     * Constructs an empty graph.
     *
     * @param comparator The comparator for comparing node labels.
     * @param directed   True if the graph is directed, false otherwise.
     */
    public Grafo(Comparator<E> comparator, boolean directed);

    /**
     * Checks if the graph is empty.
     *
     * @return True if the graph is empty, false otherwise.
     */
    public boolean empty();

    /**
     * Checks if the graph is directed.
     *
     * @return True if the graph is directed, false otherwise.
     */
    public boolean isDirected();

    /**
     * Calculates the total weight of the graph.
     *
     * @return The total weight of the graph.
     */
    public double getGraphWeight();

    /**
     * Adds a node to the graph.
     *
     * @param node The node to be added.
     */
    public void addNode(Node<E> node);

    /**
     * Checks if the graph contains a specific node.
     *
     * @param node The node to check for.
     * @return True if the node is present, false otherwise.
     */
    public boolean containsNode(Node<E> node);

    /**
     * Retrieves the set of nodes in the graph.
     *
     * @return A set of nodes in the graph.
     */
    public Set<Node<E>> getNodes();

    /**
     * Determines the number of nodes in the graph.
     *
     * @return The number of nodes in the graph.
     */
    public int getNodesNumber();

    /**
     * Retrieves the label associated with a pair of nodes.
     *
     * @param source      The source node.
     * @param destination The destination node.
     * @return The label associated with the edge between the nodes.
     */
    public float getNodesLabel(Node<E> source, Node<E> destination);

    /**
     * Retrieves the set of nodes adjacent to a given node.
     *
     * @param node The node for which to retrieve adjacent nodes.
     * @return A set of adjacent nodes.
     */
    public Set<Node<E>> getNodesAdjacent(Node<E> node);

    /**
     * Removes a node from the graph.
     *
     * @param node The node to be removed.
     */
    public void removeNode(Node<E> node);

    /**
     * Adds an edge to the graph.
     *
     * @param arch The edge to be added.
     */
    public void addArch(Arch<E> arch);

    /**
     * Checks if the graph contains a specific edge.
     *
     * @param arch The edge to check for.
     * @return True if the edge is present, false otherwise.
     */
    public boolean containsArch(Arch<E> arch);

    /**
     * Removes an edge from the graph.
     *
     * @param arch The edge to be removed.
     */
    public void removeArch(Arch<E> arch);

    /**
     * Retrieves the set of edges in the graph.
     *
     * @return A set of edges in the graph.
     */
    public Set<Arch<E>> getArches();

    /**
     * Determines the number of edges in the graph.
     *
     * @return The number of edges in the graph.
     */
    public int getArchNumber();

    /**
     * Applies the Prim's algorithm to find the minimum spanning forest in the graph.
     */
    public void MinForestPrim();

    /**
     * Returns the string representation of the graph.
     *
     * @return A string representing the graph.
     */
    @Override
    public String toString();
}
