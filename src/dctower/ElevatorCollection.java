package dctower;

import java.util.ArrayList;

public class ElevatorCollection extends ArrayList<Elevator> {
	
	public String toString() {
		String s = "";
		/*
		for(Elevator e: this) {
			s += e;
			s += "\n";
		}*/
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
