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
import es.upm.aedlib.map.Map;
import es.upm.aedlib.set.HashTableMapSet;
import es.upm.aedlib.set.PositionListSet;
import es.upm.aedlib.set.Set;
import java.util.Iterator;

public class Delivery<V> {

	private V[] places;
	private Integer[][] gmat;
	private DirectedGraph<V, Integer> graph; 
	// Construct a graph out of a series of vertices and an adjacency matrix.
	// There are 'len' vertices. A negative number means no connection. A non-negative
	// number represents distance between nodes.
	public Delivery(V[] places, Integer[][] gmat) {
		this.places=places;
		this.gmat=gmat;
		gmapPrinter(gmat, places);
		graph= new DirectedAdjacencyListGraph <V, Integer> ();
		graphConstructor(graph, gmat, places);
		System.out.println(graph.toDot());
	}
	private void gmapPrinter (Integer[][] gmap,  V[] places) {
		for(int i=0; i<places.length; i++) {
			System.out.println(i+ " = " + places[i].toString());
		}
		for (int row = 0; row < gmap.length; row++){
			for (int col = 0; col < gmap[row].length; col++){
				System.out.printf("%5d", gmap[row][col]);
			}
			System.out.println();
		}	
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
		Set<Edge<Integer>> visited = new PositionListSet<Edge<Integer>>();
		PositionList<Vertex<V>> path = new NodePositionList<Vertex<V>>();
		Vertex<V> startVert = graph.vertices().iterator().next();
		HamiltonianPath(path, this.getGraph(), visited, startVert); 
		return path;
	}

	private void HamiltonianPath(PositionList<Vertex<V>> path, DirectedGraph<V, Integer> graph, Set<Edge<Integer>> visited, Vertex<V> vert) {
		System.out.println("Vertex actual: " + vert.toString());
		if (visited.size()==graph.size()) return;
		for(Edge<Integer> edge : graph.outgoingEdges(vert)) {
			if (!visited.contains(edge) && !isInPath(path, vert)) visited.add(edge); path.addLast(vert);
			Vertex<V> connectedVert = graph.endVertex(edge); 
			HamiltonianPath(path, graph, visited, connectedVert);
		}

	}
	private boolean isInPath(PositionList<Vertex<V>> path, Vertex<V> vertex) {
		boolean result=false; 
		Iterator<Vertex<V>> it = path.iterator(); 
		while(it.hasNext() && !result) {
			Vertex<V> vertList = it.next(); 
			result = vertList.equals(vertex); 
		}
		return false;
	}
	public int length(PositionList<Vertex<V>> path) {
		return tour().size();
	}

	public String toString() {
		return "Delivery";
	}
}
