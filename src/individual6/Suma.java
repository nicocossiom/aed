package individual6;

import es.upm.aedlib.graph.Edge;
import es.upm.aedlib.graph.Vertex;

import java.util.Iterator;

import es.upm.aedlib.Position;
import es.upm.aedlib.graph.DirectedAdjacencyListGraph;
import es.upm.aedlib.graph.DirectedGraph;
import es.upm.aedlib.map.Map;
import es.upm.aedlib.set.PositionListSet;
import es.upm.aedlib.set.Set;
import es.upm.aedlib.map.HashTableMap;

public class Suma {

	private static <E> int sumReachable(Vertex<Integer> vert, int intv, DirectedGraph<Integer, E> graph, Set<Position<Integer>> visitados) {
		if(!visitados.contains(vert)) {
			intv+= vert.element();
			visitados.add(vert);
		}
		for( Edge<E> outEdge : graph.outgoingEdges(vert)) {
			sumReachable(graph.endVertex(outEdge), intv, graph, visitados);
		}
		return intv;
	}

	public static <E> Map<Vertex<Integer>, Integer> sumVertices(DirectedGraph<Integer, E> graph) {
		Map<Vertex<Integer>, Integer> map = new HashTableMap<Vertex<Integer>, Integer>();
		DirectedGraph<Integer, E> grap = new DirectedAdjacencyListGraph<Integer, E>();
		System.out.println(graph.toDot());
		if (!graph.isEmpty()) {
			for (Vertex<Integer> vertex : graph.vertices()) { 
				Set<Position<Integer>> visitados = new PositionListSet<Position<Integer>>();
				int sum = sumReachable(vertex, 0, graph, visitados);
				map.put(vertex, sum);
			}
		}
		return map;
	}
}
