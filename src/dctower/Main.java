package dctower;

public class Main {
	
	public static void main(String[] args) {
		
		ElevatorCollection elevators = new ElevatorCollection();
		int currentFloor = 0, destinationFloor = 0, minFloor = 0, maxFloor = 55;
		try {
			for(int i=0; i<7; i++)
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
				int timeSteps = 600;
				int waitTime = 100;
				for(int i=0; i<timeSteps; i++) {
					try {
						Thread.sleep(waitTime);
						nextTimeStep(elevators);
						elevatorScheduler.processRequests();
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
		 * Simulates employee requests
		 */
		final int testRequestThreads = 5;
		for(int i=0; i<testRequestThreads; i++) {
			Thread testRequestThread = new Thread(new Runnable() {
				public void run() {
					System.out.printf("Test thread %d\n",Thread.currentThread().getId());
					//int timeSteps = totalRuntimeInSeconds;
					int timeSteps = 3;
					int waitTime = 1000;
					for(int i=0; i<timeSteps; i++) {
						try {
							int currentFloor = (int) (Math.random()*55);
							int destinationFloor = (int) (Math.random()*55);
							if(currentFloor != destinationFloor) {
								ElevatorRequest request = new ElevatorRequest(currentFloor,destinationFloor);
								elevatorScheduler.addRequest(request);
								System.out.printf("New request: [current floor: %d, destination floor: %d, direction: %s]\n",currentFloor,destinationFloor,request.getDirection());
							}
							Thread.sleep(1000);
						} catch (InterruptedException e) {
						}
					}
					System.out.printf("Finished sending requests in thread %d\n",Thread.currentThread().getId());
				};
			});
			testRequestThread.start();
		}
	}
	
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
