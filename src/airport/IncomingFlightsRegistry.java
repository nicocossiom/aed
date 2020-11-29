package airport;


import java.util.Comparator;
import java.util.Iterator;

import es.upm.aedlib.Entry;
import es.upm.aedlib.Pair;
import es.upm.aedlib.priorityqueue.*;
import es.upm.aedlib.map.*;
import es.upm.aedlib.positionlist.*;


/**
 * A registry which organizes information on airplane arrivals.
 */
public class IncomingFlightsRegistry {

	private Map<String, FlightArrival> table;
	private Map<String, Entry<Long, FlightArrival>> entrymap;
	private	PriorityQueue<Long, FlightArrival> priority; 
	/**
	 * Constructs an class instance.
	 */
	public IncomingFlightsRegistry() {
		priority = new HeapPriorityQueue<Long, FlightArrival>();
		table = new HashTableMap<String, FlightArrival>();
		entrymap = new HashTableMap<String, Entry<Long, FlightArrival>>();
	}
	
	Map<String, FlightArrival> getTable( ){
		return this.table;
	}
	 Map<String,  Entry<Long, FlightArrival>> getEntryMap( ){
		return this.entrymap;
	}
	 PriorityQueue<Long, FlightArrival> getPriority( ){
		return this.priority;
	}
	
	/**
	 * A flight is predicted to arrive at an arrival time (in seconds).
	 */
	public void arrivesAt(String flight, Long time) {
		FlightArrival newFlight = new FlightArrival(flight, time);
		if(!table.containsKey(flight)) {
			table.put(flight, newFlight);
			entrymap.put(flight, priority.enqueue(time, newFlight));
		}
		else {
			priority.replaceKey(entrymap.get(flight), time);
		}
	}

	/**
	 * A flight has been diverted, i.e., will not arrive at the airport.
	 */
	public void flightDiverted(String flight) {
		try { 
			priority.remove(entrymap.get(flight));
			entrymap.remove(flight);
			table.remove(flight);
		}
		catch(es.upm.aedlib.InvalidKeyException e) {
			System.out.println("This flight is not registered to arrive at this airport");
		}
		catch(es.upm.aedlib.tree.EmptyTreeException a) {
			System.out.println("No flights with destination to this airport");
		}
		catch( es.upm.aedlib.priorityqueue.EmptyPriorityQueueException b) {
			System.out.println("No flights with destination to this airport");
		}
	}

	/**
	 * Returns the arrival time of the flight.
	 * @return the arrival time for the flight, or null if the flight is not predicted
	 * to arrive.
	 */
	public Long arrivalTime(String flight) {
		Long time;
		try {
			time=table.get(flight).getRight();
		}
		catch(java.lang.NullPointerException e) {
			time=null;
		}
		return time;
	}


/**
 * Returns a list of "soon" arriving flights, i.e., if any 
 * is predicted to arrive at the airport within nowTime+180
 * then adds the predicted earliest arriving flight to the list to return, 
 * and removes it from the registry.
 * Moreover, also adds to the returned list, in order of arrival time, 
 * any other flights arriving withinfirstArrivalTime+120; these flights are 
 * also removed from the queue of incoming flights.
 * @return a list of soon arriving flights.
 */
public PositionList<FlightArrival> arriving(Long nowTime) {
	PositionList<FlightArrival> llegando = new NodePositionList<FlightArrival>();

	return llegando;
}

}
