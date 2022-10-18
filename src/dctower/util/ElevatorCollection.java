package dctower.util;

import java.util.ArrayList;

import dctower.model.Elevator;

/**
 * ArrayList of Elevators.
 * 
 * @author Aleksandar Doknic
 * @version 2022-10-18
 *
 */
public class ElevatorCollection extends ArrayList<Elevator> {
	
	/**
	 * @return the current floor and the number of people in the elevator.
	 */
	public String toString() {
		String s = "";
		for(Elevator e: this) {
			s += e.getCurrentFloor();
			s += "(";
			s += e.getSelectedFloors().size();
			s += ")";
			s += "\t";
		}
		return s;
	}

}
