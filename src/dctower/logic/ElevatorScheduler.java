package dctower.logic;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

import dctower.exceptions.InvalidFloorException;
import dctower.model.Elevator;
import dctower.model.ElevatorRequest;
import dctower.util.ElevatorCollection;

/**
 * The scheduler for the elevators. A request is created by a push on the elevator
 * button and has a current floor and a destination floor. The scheduler tries to
 * find the next available elevator in the following way.
 * 
 * <p>
 * 1. Check if an elevator is already on the same floor and moving into the same direction.
 * If yes, use this elevator and select the destination floor in the elevator as an additional stop.
 * 
 * If not:
 * 2. Check if an elevator is already on the same floor but not being used.
 * If yes, use this elevator and select the destination floor in the elevator.
 * 
 * If not:
 * 3. Try to find available elevator on another floor and call it to the current floor.
 * If not, the employee has to wait until an elevator becomes available or passes by in the same direction.
 * 
 * A request is processed when the employee enters an elevator on the floor (case 1 and 2).
 * </p>
 * 
 * @author Aleksandar Doknic
 * @version 2022-10-18
 */
public class ElevatorScheduler {
	
	private ElevatorCollection elevators;
	private ConcurrentLinkedQueue<ElevatorRequest> elevatorRequests = new ConcurrentLinkedQueue<ElevatorRequest>();
	
	/**
	 * Elevator scheduler.
	 * 
	 * @param elevators Collection of elevators that should be scheduled.
	 */
	public ElevatorScheduler(ElevatorCollection elevators) {
		this.elevators = elevators;
		System.out.println("Elevator scheduler initialized.");
	}
	
	/**
	 * Adds (thread-safe) request for an elevator.
	 * 
	 * @param elevatorRequest the elevator request
	 */
	public void addRequest(ElevatorRequest elevatorRequest) {
		elevatorRequests.add(elevatorRequest);
	}
	
	/**
	 * Tries to assign the requests to elevators by first trying to find elevators on the same
	 * floor that are empty or move into the same direction. If that fails, it tries to call
	 * an available elevator from another floor. If that fails, the user has to wait for the next cycle.
	 * 
	 * @throws InvalidFloorException Thrown when an invalid floor was selected.
	 */
	public void processRequests() throws InvalidFloorException {
		ArrayList<ElevatorRequest> processedRequests = new ArrayList<ElevatorRequest>();
		
		for (ElevatorRequest request: elevatorRequests) {
			for (Elevator e: elevators) {
				//Elevator available on the same floor
				if(e.getCurrentFloor() == request.getCurrentFloor()) {
					//Elevator on same floor passing by into same direction
					if(e.getDirection().equals(request.getDirection())) {
						//System.out.println("Employee enters elevator "+e.getId()+" for request " + request + " while going "+e.getDirection());
						System.out.println("Employee joins elevator "+e.getId()+" on floor "+e.getCurrentFloor() +", request: "+ request + ".");
						e.addFloorSelection(request.getDestinationFloor());
						processedRequests.add(request);
						break;
					}
					//Elevator on same floor is empty and available
					if(e.isAvailable()) {
						System.out.println("Employee enters empty elevator "+e.getId()+", request: "+ request + ".");
						e.addFloorSelection(request.getDestinationFloor());
						processedRequests.add(request);
						break;
					}
				} else {
				//Check if free elevator is available on another floor and none was called before
					if(e.isAvailable() && !request.isElevatorCalled()) {
						System.out.println("Employee calls elevator "+e.getId()+", request: "+ request + ".");
						e.addFloorSelection(request.getCurrentFloor());
						request.setElevatorCalled(true);
						break;
					}
				}
				//Employee has to wait otherwise until elevator passes by or becomes available
			}	
		}
		
		//Remove all processed requests from queue (= employee is in elevator)
		for(ElevatorRequest request: processedRequests)
			elevatorRequests.remove(request);
	}
	
	/**
	 * @return the number of unprocessed requests (employees that are still waiting for an elevator).
	 */
	public int numOfRemainingRequests() {
		return elevatorRequests.size();
	}
}
