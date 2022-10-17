package dctower;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * The scheduler for the elevators. A request is processed when the
 * employee enters the elevators and the correct destination floor
 * is selected.
 */
public class ElevatorScheduler {
	
	private ElevatorCollection elevators;
	private ConcurrentLinkedQueue<ElevatorRequest> elevatorRequests = new ConcurrentLinkedQueue<ElevatorRequest>();
	
	public ElevatorScheduler(ElevatorCollection elevators) {
		this.elevators = elevators;
		System.out.println("Elevator scheduler initialized.");
	}
	
	public void addRequest(ElevatorRequest elevatorRequest) {
		elevatorRequests.add(elevatorRequest);
	}
	
	public String checkAvailableElevators() {
		String s = elevators.toString();
		System.out.printf("%s", s);
		return s;
	}
	
	/**
	 * Tries to assign the requests to elevators by first trying to find elevators on the same
	 * floor that are empty or move into the same direction. If that fails, it tries to call
	 * an available elevator from another floor. If that fails, the user has to wait for the next cycle.
	 * @throws InvalidFloorException
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
	 * How many employees are still waiting
	 * @return
	 */
	public int numOfRemainingRequests() {
		return elevatorRequests.size();
	}
}
