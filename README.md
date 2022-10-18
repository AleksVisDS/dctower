# dctower

A small project that simulates elevator logic.

Compile and run:

```
javac -d bin/ src/dctower/**/*.java
cd bin
java dctower.tests.Main
```

The output:

```
8(2)	24(2)	34(3)	34(1)	34(1)	24(1)	24(1)	(waiting:7)
```
The elevators are on floor 8, 24, 34, 34, 24 and 24 with 1 to 3 people inside the elevators.
There are 7 people waiting for the elevator on other floors.

```
New request: [current floor: 35, destination floor: 40, direction: UP]
New request: [current floor: 35, destination floor: 16, direction: DOWN]
Employee joins elevator 3 on floor 35, request: (35,40).
Employee enters empty elevator 4, request: (35,16).
```
One employee joins elevator 3 on the 35th floor and goes up to the 40th floor.
Another employee enters an empty elevator on the same floor and goes down to the 16th floor.
