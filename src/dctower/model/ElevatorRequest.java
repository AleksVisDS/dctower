package dctower.model;

/**
 * Model of an elevator request with current floor and destination floor.
 * 
 * @author Aleksandar Doknic
 * @version 2022-10-18
 * 
 */
public class ElevatorRequest {
	private static int counter = 0;
	private int id = 0;
	
	private int currentFloor;
	private int destinationFloor;
	//An elevator was already called for this request:
	private boolean elevatorCalled = false;
	
	/**
	 * Elevator request
	 * 
	 * @param currentFloor the floor where the request was made
	 * @param destinationFloor the destination floor
	 */
	public ElevatorRequest(int currentFloor, int destinationFloor) {
		this.currentFloor = currentFloor;
		this.destinationFloor = destinationFloor;
		id = ++counter;
	}
	
	/**
	 * @return the direction of the elevator
	 */
	public String getDirection() {
		String direction = "";
		if(getDestinationFloor() > getCurrentFloor())
			direction = "UP";
		if(getDestinationFloor() < getCurrentFloor())
			direction = "DOWN";
		if(getDestinationFloor() == getCurrentFloor())
			direction = "NOT SELECTED";
		return direction;
	}

	/**
	 * @return the currentFloor
	 */
	public int getCurrentFloor() {
		return currentFloor;
	}

	/**
	 * @return the destinationFloor
	 */
	public int getDestinationFloor() {
		return destinationFloor;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the elevatorCalled
	 */
	public boolean isElevatorCalled() {
		return elevatorCalled;
	}

	/**
	 * @param elevatorCalled the elevatorCalled to set
	 */
	public void setElevatorCalled(boolean elevatorCalled) {
		this.elevatorCalled = elevatorCalled;
	}

	public String toString() {
		return "("+currentFloor+","+destinationFloor+")";
	}

}
