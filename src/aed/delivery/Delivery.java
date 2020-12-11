package aed.delivery;

import es.upm.aedlib.positionlist.PositionList;
import es.upm.aedlib.Position;
import es.upm.aedlib.positionlist.NodePositionList;
import es.upm.aedlib.graph.DirectedGraph;
import es.upm.aedlib.graph.DirectedAdjacencyListGraph;
import es.upm.aedlib.graph.Vertex;
import es.upm.aedlib.graph.Edge;
import es.upm.aedlib.graph.Graph;
import es.upm.aedlib.map.HashTableMap;
import es.upm.aedlib.set.HashTableMapSet;
import es.upm.aedlib.set.Set;
import java.util.Iterator;

public class Delivery<V> {

	private V[] places;
	private Integer[][] gmat;
	// Construct a graph out of a series of vertices and an adjacency matrix.
	// There are 'len' vertices. A negative number means no connection. A non-negative
	// number represents distance between nodes.
	public Delivery(V[] places, Integer[][] gmat) {
		this.places=places;
		this.gmat=gmat;
		DirectedGraph<V, Integer> graph = new DirectedGraph<V, Integer> ();
 	}

	// Just return the graph that was constructed
	public DirectedGraph<V, Integer> getGraph() {
		
		return graph;
	}

	// Return a Hamiltonian path for the stored graph, or null if there is non.
	// The list containts a series of vertices, with no repetitions (even if the path
	// can be expanded to a cycle).
	public PositionList <Vertex<V>> tour() {

		return null;
	}

	public int length(PositionList<Vertex<V>> path) {
		return tour().size();
	}

	public String toString() {

		return "Delivery";
	}
}
