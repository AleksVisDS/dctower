package dctower.tests;

import dctower.exceptions.InvalidFloorException;
import dctower.logic.ElevatorScheduler;
import dctower.model.Elevator;
import dctower.model.ElevatorRequest;
import dctower.util.ElevatorCollection;

/**
 * A simple class that runs a simulation of the elevator.
 * One thread is created to simulate the time where the elevators move.
 * A number of threads is created to simulate the requests that come in.
 * 
 * Runs approx. 1 minute.
 * 
 * @author Aleksandar Doknic
 * @version 2022-10-18
 *
 */
public class Main {
	
	public static void main(String[] args) {

		final int numOfElevators = 7;
		final int currentFloor = 0, minFloor = 0, maxFloor = 55;
		ElevatorCollection elevators = new ElevatorCollection();
		try {
			for(int i=0; i<numOfElevators; i++)
				elevators.add(new Elevator(currentFloor, minFloor, maxFloor));
		} catch (InvalidFloorException e) {
			e.printStackTrace();
		}
		ElevatorScheduler elevatorScheduler = new ElevatorScheduler(elevators);

		/*
		 * Simulates movement of the elevator, 1 timestep = moving 1 floor
		 */
		Thread timeThread = new Thread(new Runnable() {
			public void run() {
				final int numOfTimeSteps = 600;
				final int waitTimeBetweenSteps = 100; //600 timesteps * 100 ms = 60000 ms = at least 60 seconds
				
				for(int i=0; i<numOfTimeSteps; i++) {
					try {
						Thread.sleep(waitTimeBetweenSteps);
						nextTimeStep(elevators);
						elevatorScheduler.processRequests();
						
						//prints state of the elevators and the number of waiting employees/unprocessed requests:
						System.out.println(elevators + "(waiting:" + elevatorScheduler.numOfRemainingRequests()+")");
					} catch (InvalidFloorException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println("Exit");
				System.exit(0);
			}
		});
		timeThread.start();

		/*
		 * Simulates employee requests (randomly selected floors)
		 */
		final int testRequestThreads = 5;
		for(int i=0; i<testRequestThreads; i++) {
			Thread testRequestThread = new Thread(new Runnable() {
				public void run() {
					System.out.printf("Test thread %d\n",Thread.currentThread().getId());
					//int timeSteps = totalRuntimeInSeconds;
					final int numOfTimeSteps = 3;
					final int waitTimeBetweenSteps = 1000;
					for(int i=0; i<numOfTimeSteps; i++) {
						try {
							Thread.sleep(waitTimeBetweenSteps);
							int currentFloor = (int) (Math.random()*55);
							int destinationFloor = (int) (Math.random()*55);
							if(currentFloor != destinationFloor) {
								ElevatorRequest request = new ElevatorRequest(currentFloor,destinationFloor);
								elevatorScheduler.addRequest(request);
								System.out.printf("New request: [current floor: %d, destination floor: %d, direction: %s]\n",currentFloor,destinationFloor,request.getDirection());
							}
						} catch (InterruptedException e) {
						}
					}
					System.out.printf("Finished sending requests in thread %d\n",Thread.currentThread().getId());
				};
			});
			testRequestThread.start();
		}
	}
	
	/**
	 * Moves all elevators one step in the current moving direction.
	 * 
	 * @param elevators the elevators.
	 */
	public static void nextTimeStep(ElevatorCollection elevators) {
		elevators.forEach((Elevator e) -> {
			switch(e.getDirection()) {
				case "UP": 
					e.moveOneFloorUp();
					break;
				case "DOWN":
					e.moveOneFloorDown();
					break;
				default:
					break;
			}
		});
	}
}
