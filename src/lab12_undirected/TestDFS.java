package lab12_undirected;

import java.util.ArrayList;
import java.util.List;

public class TestDFS extends DepthFirstSearch {

	public TestDFS(Graph graph) {
		super(graph);
		
	}
	protected void processVertex(Vertex w){
		System.out.println("Visiting " + w.getData());
	}
	
	public static void main(String[] args) {
		List<Pair> l = new ArrayList<Pair>();
		l.add(new Pair("A","B"));
		l.add(new Pair("B","C"));
		l.add(new Pair("A","D"));
		l.add(new Pair("B","D"));
		Graph g = new Graph(l);
		String s = g.toString();
		System.out.println(s);
		DepthFirstSearch dfs = new TestDFS(g);
		dfs.start();
	}
}
