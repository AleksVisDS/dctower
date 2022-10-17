package dctower;

public class ElevatorRequest {
	private static int counter = 0;
	private int id = 0;
	
	private int currentFloor;
	private int destinationFloor;
	//Elevator is not on floor but one was called.
	private boolean elevatorCalled = false;
	
	ElevatorRequest(int currentFloor, int destinationFloor) {
		this.currentFloor = currentFloor;
		this.destinationFloor = destinationFloor;
		id = ++counter;
	}
	
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
