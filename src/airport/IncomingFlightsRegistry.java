package airport;


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
	private Map<String, Entry<Long, String>> entrymap;
	private	PriorityQueue<Long, String> priority; 
	/**
	 * Constructs an class instance.
	 */
	public IncomingFlightsRegistry() {
		priority = new HeapPriorityQueue<Long, String>();
		table = new HashTableMap<String, FlightArrival>();
		entrymap = new HashTableMap<String, Entry<Long, String>>();
	}

	/**
	 * A flight is predicted to arrive at an arrival time (in seconds).
	 */
	public void arrivesAt(String flight, Long time) {
		FlightArrival newFlight = new FlightArrival(flight, time);
		table.put(flight, newFlight);
		entrymap.put(flight, priority.enqueue(time, flight));
	}

	/**
	 * A flight has been diverted, i.e., will not arrive at the airport.
	 */
	public void flightDiverted(String flight) {
		priority.remove(entrymap.get(flight));
		entrymap.remove(flight);
		table.remove(flight);
	}

	/**
	 * Returns the arrival time of the flight.
	 * @return the arrival time for the flight, or null if the flight is not predicted
	 * to arrive.
	 */
	public Long arrivalTime(String flight) {
		return table.get(flight).getRight();
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
		return null;
	}

}
