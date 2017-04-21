package lab12_undirected;

public class Vertex implements Cloneable {

	private Object data;
	
	
	public Vertex(Object data){
		this.data = data;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	public String toString() {
		return data.toString();
	}

	
	public boolean equals(Object ob){
		if(ob == null) return false;
		if(getClass() != ob.getClass()) return false;
		Vertex v = (Vertex)ob;
		return v.data.equals(data);
	}
	public int hashCode() {
		return data.hashCode();
	}
	
	/** 
	 * Returns a shallow copy of this Vertex. For some purposes,
	 * a deep copy would be preferable (but this graph package does not
	 * require support for this).
	 */
	public Vertex clone() {
		try {
			Vertex copy = (Vertex)super.clone();
			return copy;
		} catch(CloneNotSupportedException e) {
			throw new IllegalStateException("Unable to clone this vertex: " + data.toString());
		}
		
	}

	
	

}
