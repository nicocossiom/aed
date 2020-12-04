package individual6;

import es.upm.aedlib.graph.Edge;
import es.upm.aedlib.graph.Vertex;
import es.upm.aedlib.graph.DirectedAdjacencyListGraph;
import es.upm.aedlib.graph.DirectedGraph;
import es.upm.aedlib.map.Map;
import es.upm.aedlib.map.HashTableMap;

public class Suma {

	private static <E> void sumReachable ( Map<Vertex<Integer>,Integer> map, DirectedGraph<Integer,E> graph) {
		if (!graph.isEmpty()) {	
			for ( Edge<E> edge : graph.edges()) {	

				Vertex<Integer> vert = graph.startVertex(edge);
				int intvert = vert.element();
				System.out.println(graph.toString() + "vertice" + vert.toString()+ "intvertr" + intvert);

			}

		}
	}

	public static <E> Map<Vertex<Integer>,Integer> sumVertices(DirectedGraph<Integer,E> g) {
		Map<Vertex<Integer>,Integer> map = new HashTableMap<Vertex<Integer>,Integer>();
		DirectedGraph<Integer, E> grap = new DirectedAdjacencyListGraph<Integer, E>();
		sumReachable(map, grap);
		return map;
	}
}
