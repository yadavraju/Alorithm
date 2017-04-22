package lab12_undirected;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;

/**
 * DFS implementation. We ensure O(|V|+|E|) running time by making sure that
 * each vertex is processed at most once and each edge is examined at most
 * twice. In the implementation, this means that (1) each of the vertices in
 * graph.vertices is read at most once, and (2) in the adjacency list, each
 * vertex adjacent to a given vertex is read at most twice.
 * 
 * (1) is accomplished as follows: We maintain an iterator for graph.vertices.
 * This iterator is consulted only at the beginning of each componentLoop. The
 * iterator will need to move through each vertex and check with the
 * vistedVertices hashtable to see if it has been visited. Before first loop,
 * iterator pointer points to 0th vertex. After componentLoop, iterator points
 * to vertex 1. At the next step of outer loop, iterator moves through vertices
 * until an unvisited vertex is found at (say) position k1. At end of this
 * componentLoop, iterator points to postiion k1+1. To find the next unvisited
 * vertex, it is not necessary to examine positions 0 to k1 again -- they are
 * all visited. Continues like this.
 * 
 * (2) is accomplished by ensuring that a given edge (v,w) is never examined
 * more than two times. One time happens when the list of adjacent vertices for
 * v is searched for an unvisited node. The second time happens when the list of
 * adjacent vertices for w is searched for an unvisited node. In these searches,
 * a node is found if and only if the list is nonempty. We ensure this by
 * removing nodes from the list when they are found. This guarantees that the
 * only elements in the list of vertices adjacent to v are unvisited, so the
 * list, each time, finds a match on position 0 of the list.
 * 
 *
 */
public class DepthFirstSearch extends AbstractGraphSearch {

	protected HashMap<Vertex, Vertex> visitedVertices = new HashMap<Vertex, Vertex>();
	Graph graph;
	Stack<Vertex> stack;
	List<Vertex> vertices = null;
	Iterator<Vertex> iterator = null;
	HashMap<Vertex, LinkedList<Vertex>> adjacencyList;

	HashMap<Integer, List<Vertex>> connected = new HashMap<>();
	ArrayList<Vertex> myVertices = null;

	protected int numVertices;
	private int compentCount = 0;

	public DepthFirstSearch(Graph graph) {
		stack = new Stack<Vertex>();
		this.graph = graph;
		vertices = graph.vertices;
		iterator = vertices.iterator();
		numVertices = vertices.size();
		// this is a copy, so we can modify it - O(n+m) to acquire this
		adjacencyList = graph.getAdjacencyList();
	}

	protected void resetVisitedVertices() {
		visitedVertices.clear();
	}

	protected void resetVertexIterator() {
		iterator = vertices.iterator();
	}

	@Override
	protected boolean someVertexUnvisited() {
		return visitedVertices.size() < numVertices;
	}

	@Override
	protected void additionalProcessing() {
		// by default do nothing
		List<Vertex> list = null;
		System.out.println("Connected Component" + compentCount);
		Set<Integer> keySet = connected.keySet();
		for (Integer integer : keySet) {
			list = connected.get(integer);
			
		}
		System.out.println(list);
		// Set<Entry<Integer, List<Vertex>>> entrySet = connected.entrySet();
		// for (Entry<Integer, List<Vertex>> entry : entrySet) {
		// System.out.println("List Of Vetices"+entry.getValue());
		// }
	}

	// inserts an initial vertex into the stack as preparation for
	// dfs for this component
	@Override
	protected void handleInitialVertex() {
		Vertex v = nextUnvisited();
		if (v != null) {
			setHasBeenVisited(v);
			processVertex(v);
			stack.push(v);
			
			/*
			 * Added to do lab 12 qn 3 as per requirement
			 *
			 */
				myVertices = new ArrayList<>();
				myVertices.add(v);
				connected.put(compentCount, myVertices);
			
		}

	}

	// search for deeper nodes and backtrack, in a loop
	// starts on a new component after a component has been completely visited
	@Override
	protected void singleComponentLoop() {
		while (!stack.isEmpty()) {
			Vertex v = nextUnvisitedAdjacent(stack.peek());

			if (v == null)
				stack.pop();
			else {
				setHasBeenVisited(v);
				processEdge(new Edge(stack.peek(), v));
				processVertex(v);
				stack.push(v);
				/*
				 * Added to do lab 12 qn 3 as per requirement
				 *
				 */
				myVertices.add(v);
				connected.put(compentCount, myVertices);
		/*----------------------------------------------*/

			}
		}
		compentCount++;
	}

	protected void processEdge(Edge e) {

		// override for needed functionality
	}

	public boolean getHasBeenVisited(Vertex v) {
		return visitedVertices.containsKey(v);
	}

	public void setHasBeenVisited(Vertex v) {
		visitedVertices.put(v, v);
	}

	protected void processVertex(Vertex w) {

		// should be overridden; by default, do nothing
	}

	public Vertex nextUnvisited() {
		while (iterator.hasNext()) {
			Vertex next = iterator.next();
			if (!visitedVertices.containsKey(next)) {
				return next;

			}
		}
		return null;
	}

	/**
	 * 
	 */
	public Vertex nextUnvisitedAdjacent(Vertex v) {
		List<Vertex> listOfAdjacent = adjacencyList.get(v);
		Iterator<Vertex> it = listOfAdjacent.iterator();
		Vertex retVert = null;
		// this loop will visit each element matched with v in the adjacency
		// list
		// ONLY ONCE, since whenever a list element is encountered,
		// it is removed after processing
		while (it.hasNext()) {
			Vertex u = it.next();
			if (visitedVertices.containsKey(u)) {
				it.remove();
			}
			if (!visitedVertices.containsKey(u)) {
				retVert = u;
				it.remove();
				return retVert;
			}
		}
		// unvisited adjacent vertex not found
		return retVert; // returning null
	}

}
