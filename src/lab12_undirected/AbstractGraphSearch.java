package lab12_undirected;

public abstract class AbstractGraphSearch {
	
	public void start() {
		while(someVertexUnvisited()) {	
			//picks an unvisited vertex and marks it
			handleInitialVertex();		
			
			//Starting from initial vertex s, marked as visited, 
			//adds one vertex at a time to the collection of visited vertices
			//by choosing (in unspecified way) next vertex that is adjacent
			//to some visited vertex
			//GENERIC ALGORITHM:
			// - initialize VISITED collection, inserting start vertex s
			// - for each edge (v,w), 
			//      if v in VISITED and w unvisited
			//          add w to VISITED
			//THEOREM: v is in VISITED if and only if there is a path in G
			//from s to v
			singleComponentLoop();	
			
			//performs necessary processing (for subclasses) between completions 
			//of successive components
			additionalProcessing();
		}
	}	
	
	protected abstract boolean someVertexUnvisited();
	protected abstract void handleInitialVertex();
	protected abstract void singleComponentLoop();
	protected abstract void additionalProcessing();
}
