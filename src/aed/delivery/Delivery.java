package aed.delivery;

import es.upm.aedlib.positionlist.PositionList;
import es.upm.aedlib.Pair;
import es.upm.aedlib.Position;
import es.upm.aedlib.positionlist.NodePositionList;
import es.upm.aedlib.graph.DirectedGraph;
import es.upm.aedlib.graph.DirectedAdjacencyListGraph;
import es.upm.aedlib.graph.Vertex;
import es.upm.aedlib.graph.Edge;
import es.upm.aedlib.graph.Graph;
import es.upm.aedlib.map.HashTableMap;
import es.upm.aedlib.map.Map;
import es.upm.aedlib.set.HashTableMapSet;
import es.upm.aedlib.set.PositionListSet;
import es.upm.aedlib.set.Set;
import java.util.Iterator;

public class Delivery<V> {

	private V[] places;
	private Integer[][] gmat;
	private DirectedGraph<V, Integer> graph; 
	private int suma;
	// Construct a graph out of a series of vertices and an adjacency matrix.
	// There are 'len' vertices. A negative number means no connection. A non-negative
	// number represents distance between nodes.
	public Delivery(V[] places, Integer[][] gmat) {
		this.places=places;
		this.gmat=gmat;
		graph= new DirectedAdjacencyListGraph <V, Integer> ();
		graphConstructor(graph, gmat, places);
		System.out.println(graph.toDot());
	}
	
	private void graphConstructor(DirectedGraph<V, Integer> graph2, Integer[][] gmat2, V[] places2) {
		Map<Integer, Vertex<V>> mapVertex = new HashTableMap<Integer, Vertex<V>>();
		int length=places.length;
		for(int i=0; i<length; i++) {
			Vertex<V> vertPlaces = graph.insertVertex(places[i]);
			mapVertex.put(i, vertPlaces);
		}
		for (int i=0; i<length; i++){
			for(int j=0; j<length; j++){
				Integer weightEdge = gmat[i][j];
				if(weightEdge!=null){
					graph.insertDirectedEdge(mapVertex.get(i), mapVertex.get(j), weightEdge);
				}
			}
		}
	}

	// Just return the graph that was constructed
	public DirectedGraph<V, Integer> getGraph() {
		return graph;
	}

	// Return a Hamiltonian path for the stored graph, or null if there is non.
	// The list containts a series of vertices, with no repetitions (even if the path
	// can be expanded to a cycle).
	public PositionList <Vertex<V>> tour() { 
		PositionList<Vertex<V>> path = null;
		boolean found=false;
		Iterator<Vertex<V>> it = graph.vertices().iterator();
		while(it.hasNext() && found==false) {
			path = new NodePositionList<Vertex<V>>();;
			Pair<PositionList<Vertex<V>>, Integer> pair = new Pair<PositionList<Vertex<V>>, Integer>(path, 0);
			path=pair.getLeft();
			Vertex<V> startVert = it.next();
			try {
				if (graph.outDegree(startVert)!=0) {
					path.addLast(startVert);
					pair = HamiltonianPath(pair, this.getGraph(), startVert);
					path = pair.getLeft();
					suma = pair.getRight();
					if(path.size()==places.length) found=true;
				}
			}
			catch(java.lang.NullPointerException e) {}
		}
		if(!found) return null;
		return path;
	}

	private Pair<PositionList<Vertex<V>>, Integer> HamiltonianPath(Pair<PositionList<Vertex<V>>, Integer> pair, DirectedGraph<V, Integer> graph, Vertex<V> vert) {
		PositionList<Vertex<V>> path = pair.getLeft(); 
		int sumaCamino=pair.getRight();
		if(path.size()==places.length) return pair;
		for(Edge<Integer> edge : graph.outgoingEdges(vert)) {
			if(edge!=null) {
				Vertex<V> connectedVert = graph.endVertex(edge); 
				if(!isInPath(path, connectedVert)) {
					pair.setRight(pair.getRight()+edge.element());
					path.addLast(connectedVert);
					System.out.println(edge+ "\npath= "+path);
					System.out.println("\nSuma= "+sumaCamino);Pair<PositionList<Vertex<V>>, Integer> result = HamiltonianPath(pair, graph, connectedVert);
					if (result!=null) return result;
					else path.remove(path.last());
				}
			}
		}
		return null;
	}

	private boolean isInPath(PositionList<Vertex<V>> path, Vertex<V> vertex) {
		boolean result=false; 
		Iterator<Vertex<V>> it = path.iterator(); 
		while(it.hasNext() && !result) {
			Vertex<V> vertList = it.next(); 
			result = vertList.equals(vertex); 
		}
		return result;
	}
	public int length(PositionList<Vertex<V>> path) {
		return suma;
	}

	public String toString() {
		return "Delivery";
	}
}
