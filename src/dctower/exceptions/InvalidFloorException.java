package dctower.exceptions;

/**
 * InvalidFloorException is thrown when elevators reach a floor
 * outside given boundaries or if the boundaries are invalid.
 * 
 * @author Aleksandar Doknic
 * @version 2022-10-18
 *
 */
public class InvalidFloorException extends Exception {

	/**
	 * @param errorMessage Error message to print
	 */
	public InvalidFloorException(String errorMessage) {
		super(errorMessage);
	}
}
