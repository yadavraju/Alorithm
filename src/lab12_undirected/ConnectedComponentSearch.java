package lab12_undirected;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

//implement
public class ConnectedComponentSearch extends DepthFirstSearch {
	int compentCount;
	List<Vertex> myVertices;
	HashMap<Integer, List<Vertex>> connected = new HashMap<>();
	public ConnectedComponentSearch(Graph graph) {
		super(graph);
	}
	
	@Override
	protected void processVertex(Vertex w) {
		// TODO Auto-generated method stub
		//super.processVertex(w);
		/*
		 * Added to do lab 12 qn 3 as per requirement
		 *
		 */
		if(connected.get(compentCount) == null){
			myVertices = new ArrayList<>();
			myVertices.add(w);
			connected.put(compentCount, myVertices);
		}else{
			connected.get(compentCount).add(w);
		}
		
		compentCount++;

	}

}
