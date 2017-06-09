package trail_tracker;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import fsm.Alphabet;
import fsm.FSM;
import fsm.Transition;

public class TrailTracker extends FSM {
    private final int NO_FOOD = 0;
    private final int FOOD = 1;

    private final int VISITED = 2;
    private final int EATEN = 3;

    private final int EAST = 0;
    private final int SOUTH = 1;
    private final int WEST = 2;
    private final int NORTH = 3;

    private final int MOVE = 0;
    private final int LEFT = 1;
    private final int RIGHT = 2;
    private final int WAIT = 3;

    int maxTime = 400;
    // E =empty
    // F =food

    // W=wait. do nothing
    // M= move forward
    // L=turn left
    // R=turn right

    static Alphabet events = new Alphabet("EF".toCharArray());
    // static Alphabet actions = new Alphabet( "MLRW".toCharArray());

    static Alphabet actions = new Alphabet("MLR".toCharArray());

    Grid antGrid = new Grid();
    int steps = 0;
    int food = 0;

    double maxFood;

    int[] currentPosition = { 0, 0 };
    int currentOrientation = EAST;

    public TrailTracker() {
	super();

	this.setInputAlphabet(events);
	this.setOutputAlphabet(actions);
    }

    public void createTrailTracker(FSM fsm) {

	this.setStates(fsm.states);
	this.setTransitions(fsm.transitions);
	this.setTransitionTable(fsm.transitionTable);

    }

    public double getScore() {
	double score = this.food / maxFood;

	return score;
    }

    public void step(int foodTF) {
	Transition tr = getTransition(transitionTable[currentState.getStateID()][foodTF]);
	currentState = tr.getToState();
	int[] nextCell = nextCellCoordinates();
	this.act(actions.findPosition(tr.getOutputSymbol()), nextCell);
    }

    public void walk() {
	food = 0;
	steps = 1;
	this.currentState = states.get(0);

	do{
		this.step(this.foodInTheNextCell());
		steps++;
	    
	}while ((food <this.maxFood)&&(steps<maxTime)) ;
    }

    public void act(int action, int[] nextCell) {
	if ((antGrid.getGridValue(currentPosition[0], currentPosition[1]) == 1)
		|| (antGrid
			.getGridValue(currentPosition[0], currentPosition[1]) == 3)) {
	    antGrid.setGridValue(currentPosition[0], currentPosition[1], EATEN);
	} else {
	    antGrid.setGridValue(currentPosition[0], currentPosition[1],
		    VISITED);
	}
	switch (action) {
	case WAIT: {
	    break;
	}
	case MOVE: {

	    int foodTF = this.foodInTheNextCell();
	    this.moveForward(nextCell);
	    if (foodTF == 1) {
		food++;
	    }


	    break;
	}
	case LEFT: {
	    this.turnLeft();

	    break;
	}
	case RIGHT: {
	    this.turnRight();

	    break;
	}
	}
    }

    public void moveForward(int[] nextCell) {

	currentPosition[0] = nextCell[0];
	currentPosition[1] = nextCell[1];

    }

    public int[] nextCellCoordinates() {
	int[] nextCell = { 0, 0 };
	switch (this.currentOrientation) {
	case EAST: {
	    nextCell[0] = currentPosition[0];
	    nextCell[1] = currentPosition[1] + 1;
	    break;
	}
	case SOUTH: {
	    nextCell[0] = currentPosition[0] + 1;
	    nextCell[1] = currentPosition[1];
	    break;
	}
	case WEST: {
	    nextCell[0] = currentPosition[0];
	    nextCell[1] = currentPosition[1] - 1;
	    break;
	}
	case NORTH: {
	    nextCell[0] = currentPosition[0] - 1;
	    nextCell[1] = currentPosition[1];
	    break;
	}

	}
	for (int i = 0; i < 2; i++) {
	    if (nextCell[i] == -1) {
		nextCell[i] = 31;
	    } else if (nextCell[i] == 32) {
		nextCell[i] = 0;
	    }
	}

	return nextCell;
    }

    public int foodInTheNextCell() {
	int[] visCell = nextCellCoordinates();
	if (antGrid.getGridValue(visCell[0], visCell[1]) == 1) {
	    return FOOD;
	} else
	    return NO_FOOD;
    }

    public void turnLeft() {
	switch (this.currentOrientation) {
	case EAST:
	    this.currentOrientation = NORTH;
	    break;
	case SOUTH:
	    this.currentOrientation = EAST;
	    break;
	case WEST:
	    this.currentOrientation = SOUTH;
	    break;
	case NORTH:
	    this.currentOrientation = WEST;
	    break;

	}

    }

    public void turnRight() {
	switch (this.currentOrientation) {
	case EAST:
	    this.currentOrientation = SOUTH;
	    break;
	case SOUTH:
	    this.currentOrientation = WEST;
	    break;
	case WEST:
	    this.currentOrientation = NORTH;
	    break;
	case NORTH:
	    this.currentOrientation = EAST;
	    break;

	}

    }

    public int getMaxTime() {
	return maxTime;
    }

    public void setMaxTime(int maxTime) {
	this.maxTime = maxTime;
    }

    public double getMaxFood() {
	return maxFood;
    }

    public void setMaxFood(double maxFood) {
	this.maxFood = maxFood;
    }

    public void printToFile(String fileName) throws IOException {
	PrintWriter res = null;
	try {
	    res = new PrintWriter(new BufferedWriter(new FileWriter(new File(
		    fileName))));

	    res.println("<html>");
	    res.println("<body><font face=\"courier new\">");

	    res.println(this.toHTMLString());
	    res.println("<br/>Score: " + this.getScore() + "<br/>");
	    res.println("</font>");

	    res.println("</body>");
	    res.println("</html>");
	} catch (IOException e) {
	    System.err.println("Error in input/output process...");
	} finally {
	    res.close();
	}
    }

    public void setAntGrid(Grid grid) {
	for (int i = 0; i < 32; i++) {
	    for (int j = 0; j < 32; j++) {
		this.antGrid.setGridValue(i, j, grid.getGridValue(i, j));
	    }

	}

    }

}
