package dctower;

import java.util.PriorityQueue;

public class Elevator {
	public static int counter = 0;
	private int id;
	
	private int currentFloor;
	//private int destinationFloor;
	private PriorityQueue<Integer> selectedFloors;
	private int minFloor;
	private int maxFloor;
	
	/**
	 * Elevator class
	 * @param currentFloor the currentFloor to set
	 * @param destinationFloor the destinationFloor to set
	 * @param minFloor the minFloor to set
	 * @param maxFloor the maxFloor to set
	 * @throws InvalidFloorException
	 */
	public Elevator(int currentFloor, int minFloor, int maxFloor) throws InvalidFloorException {
		if(minFloor > maxFloor) 
			throw new InvalidFloorException("Minimum floor is higher than maximum floor.");
		this.minFloor = minFloor;
		this.maxFloor = maxFloor;
		
		this.setCurrentFloor(currentFloor);
		this.selectedFloors = new PriorityQueue<Integer>();
		this.id = ++counter;
		System.out.println("Elevator "+ this.getId() +" created.");
	}

	/**
	 * @return the currentFloor
	 */
	public int getCurrentFloor() {
		return currentFloor;
	}

	/**
	 * @param currentFloor the currentFloor to set
	 * @throws InvalidFloorException 
	 */
	public void setCurrentFloor(int currentFloor) throws InvalidFloorException {
		if(currentFloor > this.maxFloor || currentFloor < this.minFloor) {
			throw new InvalidFloorException("Current floor ("+currentFloor+") is outside valid range (between "+minFloor+" and "+maxFloor+")");
		} else {
			this.currentFloor = currentFloor;
		}
	}

	/**
	 * @return the destinationFloor
	 */
	public PriorityQueue<Integer> getSelectedFloors() {
		return selectedFloors;
	}

	/**
	 * @param Selects another floor
	 * @throws InvalidFloorException 
	 */
	public void addFloorSelection(int floor) throws InvalidFloorException {
		if(currentFloor > this.maxFloor || currentFloor < this.minFloor) {
			throw new InvalidFloorException("Selected floor ("+floor+") is outside valid range (between "+minFloor+" and "+maxFloor+")");
		} else {
			if(!this.selectedFloors.contains(floor))
				selectedFloors.add(floor);
		}
	}

	/**
	 * @return returns the direction as String
	 */
	public String getDirection() {
		String direction = "";
		
		if(selectedFloors.isEmpty()) {
			direction = "";
		} else {
			if(selectedFloors.peek() > currentFloor)
				direction = "UP";
			if(selectedFloors.peek() < currentFloor)
				direction = "DOWN";
		}
		return direction;
	}

	/**
	 * moves one floor up
	 */
	public void moveOneFloorUp() {
		if(currentFloor < maxFloor) currentFloor++;
		if(currentFloor == selectedFloors.peek()) {
			selectedFloors.poll();
			//System.out.println("Employee leaves on floor "+currentFloor);
		}
	}
	
	/**
	 * moves one floor down
	 */
	public void moveOneFloorDown() {
		if(currentFloor > minFloor) currentFloor--;
		if(currentFloor == selectedFloors.peek()) {
			selectedFloors.poll();
			//System.out.println("Employee leaves on floor "+currentFloor);
		}
	}
	
	/**
	 * @return returns if elevator is available (no button is pressed)
	 */
	public boolean isAvailable() {
		return selectedFloors.isEmpty();
	}

	public String toString() {
		String s = "[Elevator "+getId()+": floor: "+currentFloor+", destination: "+ selectedFloors +", direction: "+getDirection()+"]";
		return s;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
}
