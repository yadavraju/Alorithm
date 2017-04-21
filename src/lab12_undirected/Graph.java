package lab12_undirected;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Graph class implements the simple undirected graph data type.
 * Vertices and edges are each stored in a linked list, and
 * adjacency is managed with an adjacency list, represented as a HashMap.
 * 
 * The Graph class provides instance variables for a spanning tree
 * and a list of connected components (where each component is itself
 * an instance of the Graph class). 
 * 
 * A Graph instance can be constructed by passing in one of two
 * representations of graph data: as an array of Edges (used
 * internally when getting a spanning tree or connected components)
 * or as a List of Pairs, where a Pair A,B signifies vertices
 * A and B as endpoints of an edge.
 * 
 * The public method loadMajorStructures is a top-level method
 * for creating all the auxiliary structures supported by the Graph,
 * including spanning tree, connected components.
 * (Initially, only finding spanning tree is supported.) If a user
 * attempts to access one of the cached structures for a Graph object
 * (such as a spanning tree) before loadMajorStructures has been run,
 * loadMajorStructures will be run first so that the required data
 * will be available.
 */
public class Graph {
	Graph spanningTree;
	List<Graph> connectedComponents;
	
	LinkedList<Vertex> vertices = new LinkedList<Vertex>();
	LinkedList<Edge> edges = new LinkedList<Edge>();
	HashMap<Vertex,LinkedList<Vertex>> adjList = new HashMap<Vertex,LinkedList<Vertex>>();
	 
	/**
	 * Constructs a Graph object from an array of Edges. Used internally to create
	 * a spanning tree or a set of connected components.
	 */
	public Graph(Edge[] inputEdges) {
		HashMap<Vertex,Vertex> dupverts = new HashMap<Vertex,Vertex>();
		
		for(Object ob: inputEdges) {
			if(ob.getClass() != Edge.class) continue;
			Edge e = (Edge)ob;
			//Assume dup edges are not allowed
			edges.add(e);
			Vertex v1 = e.u;
			Vertex v2 = e.v;
			if(!dupverts.containsKey(v1)){
				dupverts.put(v1,v1);
				vertices.add(v1);			
			}
			
			if(!dupverts.containsKey(v2)){
				dupverts.put(v2,v2);
				vertices.add(v2);
				
			}
			
			LinkedList<Vertex> l = adjList.get(v1);
			if(l == null) {
				l = new LinkedList<Vertex>();
			}
			l.add(v2);
			adjList.put(v1,l);
			
			LinkedList<Vertex> l2 = adjList.get(v2);
			if(l2 == null) {
				l2 = new LinkedList<Vertex>();
			}
			l2.add(v1);
			adjList.put(v2,l2);				
		}	
	}
	
	/**
	 * Constructs a graph from a list of pairs. A pair (A,B) 
	 * is transformed into vertices A and B together with an edge A-B.
	 */
	public Graph(List<Pair> pairs){
		HashMap<Vertex,Vertex> dupverts = new HashMap<Vertex,Vertex>();
		HashMap<Edge,Edge> dupedges = new HashMap<Edge,Edge>();
		for(Pair e : pairs){
			//handle the vertices and edges simultaneously
			Vertex v1 = new Vertex(e.ob1);
			Vertex v2 = new Vertex(e.ob2);
			Edge edge = new Edge(v1,v2);
			if(!dupverts.containsKey(v1)){
				dupverts.put(v1,v1);
				vertices.add(v1);	
			}
			if(!dupverts.containsKey(v2)){
				dupverts.put(v2,v2);
				vertices.add(v2);	
			}
			
			if(!dupedges.containsKey(edge)){
				dupedges.put(edge,edge);
				edges.add(edge);
			}
			
			LinkedList<Vertex> l = adjList.get(v1);
			if(l == null) {
				l = new LinkedList<Vertex>();
			}
			l.add(v2);
			adjList.put(v1,l);
			
			LinkedList<Vertex> l2 = adjList.get(v2);
			if(l2 == null) {
				l2 = new LinkedList<Vertex>();
			}
			l2.add(v1);
			adjList.put(v2,l2);
		}
		
	}
	
	private void loadMajorStructures() {
		FindSpanningTree fst = new FindSpanningTree(this);
		spanningTree = fst.computeSpanningTree();
		
		//also: computeConnectedComponents
	}
	public Graph getSpanningTree() {
		if(spanningTree == null){
			loadMajorStructures();
		}
		return spanningTree;
	}
	
	public List<Graph> getConnectedComponents() {
		//if null, first compute
		//then return connectedComponents
		return null;
	}
	
	public boolean isConnected() {
		//implement
		return false;
	}
	
	public boolean existsPathBetween(Vertex u, Vertex v) {
		//implement
		return false;
	}
	
	public boolean containsCycle() {
		//implement
		return false;
	}
	
	
	
	public boolean areAdjacent(Vertex v, Vertex w) {
		LinkedList<Vertex> l = adjList.get(v);
		if(l == null) return false;
		return l.contains(w);
	}
	public String toString() {
		StringBuilder sb = new StringBuilder("Vertices: \n"+" ");
		for(Vertex v : vertices) {
			sb.append(v+" ");
		}
		sb.append("\nEdges:\n"+" ");
		HashMap<String,String> dups = new HashMap<String,String>();
		for(Vertex v: vertices){
			LinkedList<Vertex> l  = adjList.get(v);
			
			for(Vertex w : l){
				String edge = v.toString()+"-"+w.toString();
				String edgeRev = reverse(edge);
				if(!dups.containsKey(edge) && !dups.containsKey(edgeRev)){
					sb.append(edge+" ");
					dups.put(edge,edge);
				}
			}
		}
		return sb.toString();
	}
	/**
	 * Important to return only a copy of the adjacency list. Running time for making 
	 * this copy: For each vertex v, the number of adjacent vertices to v that need to
	 * be copied into a new list (matched with v in the copy of the map) is deg v. Also, each
	 * vertex is processed (cloned and the clone is added to its list); processing is O(1) Therefore,
	 * it is the sum over v in V of 1 + deg(v), which is O(n+m).
	 */
	public HashMap<Vertex,LinkedList<Vertex>> getAdjacencyList() {
		HashMap<Vertex,LinkedList<Vertex>> copy = new HashMap<Vertex,LinkedList<Vertex>>();
		for(Vertex v : adjList.keySet()) {
			//LinkedList<Vertex> original = adjList.get(v);
			copy.put(v, getListOfAdjacentVerts(v));
			
		}
		return copy;
	}
	
	/**
	 * Returns a copy of the list of adjacent vertices
	 */
	public LinkedList<Vertex> getListOfAdjacentVerts(Vertex v) {
		List<Vertex> theList = adjList.get(v);
		LinkedList<Vertex> copy = new LinkedList<Vertex>();
		if (theList != null) {
			for(Vertex vert : theList) {
				copy.add(vert.clone());
			}
		}
		return copy;
	}
	private String reverse(String edge) {
		String[] vals = edge.split("-");
		return vals[1]+"-"+vals[0];
	}
}
