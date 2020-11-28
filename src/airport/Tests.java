package airport;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Iterator;

import es.upm.aedlib.positionlist.*;


import es.upm.aedlib.Entry;
import es.upm.aedlib.Pair;
import es.upm.aedlib.priorityqueue.*;
import es.upm.aedlib.map.*;
import es.upm.aedlib.positionlist.*;

public class Tests {
	public static void main(String []args) {
		Map<String, FlightArrival> table = new HashTableMap<String, FlightArrival>();
		PriorityQueue<Long, FlightArrival> flightOrder = new HeapPriorityQueue<Long,FlightArrival>();
		FlightArrival flight1 = new FlightArrival("Vuelo1",2020);
		FlightArrival flight2 = new FlightArrival("Vuelo2",2010);
		FlightArrival flight3 = new FlightArrival("Vuelo3",0010);
		FlightArrival flight4 = new FlightArrival("ESP",0000);
		FlightArrival flight5 = new FlightArrival("ESP",1010);
		FlightArrival flight6 = new FlightArrival("ESP",1310);
		table.put(flight1.getLeft(), flight1);
		table.put(flight2.getLeft(), flight2);
		table.put(flight3.getLeft(), flight3);
		Iterator<Entry<String, FlightArrival>> it =table.entries().iterator();
		System.out.println( table.toString());
		
		flightOrder.enqueue((long) 2020, flight1);
		flightOrder.enqueue((long) 2010, flight2);
		flightOrder.enqueue((long) 0010, flight3);
		flightOrder.enqueue((long) 0000, flight4);
		flightOrder.enqueue((long) 1010, flight5);
		flightOrder.enqueue((long) 1310, flight6);
		System.out.println(flightOrder.toString());
		System.out.println(flightOrder.dequeue().toString());
		System.out.println(flightOrder.dequeue().toString());
		System.out.println(flightOrder.dequeue().toString());
		System.out.println(flightOrder.toString());
	}
}

