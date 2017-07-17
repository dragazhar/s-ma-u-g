package search_algorithm.bodies;


public class Body {
    Coordinates position;
    Coordinates newPosition;
    double mass = 0;

    Coordinates velocity;
    Coordinates newVelocity;

    Coordinates bestPosition;
    double bestMass;

    
    public void updateLocalBest(){
	if (mass> bestMass) {
	    setBestMass(mass);
	    setBestPosition(newPosition);
	}
	
    }
    
    public void replaceByNewPosition(){
	this.setPosition(newPosition);
    }
    
    
    public void replaceByNewVelocity(){
	this.setVelocity(newVelocity);
    }
    
    
    public Body(Point p) {
	super();
	position = new Coordinates(p.coordinatesT, p.coordinatesO);
	velocity = new Coordinates(p.coordinatesT, p.coordinatesO);
	mass = p.getValue();

	bestPosition = new Coordinates(p.coordinatesT, p.coordinatesO);
	bestMass = p.getValue();
	
	newPosition = new Coordinates(p.coordinatesT, p.coordinatesO);
	newVelocity = new Coordinates(p.coordinatesT, p.coordinatesO);
    }

    public Body(Coordinates position) {
	super();
	this.position = position;
    }

    public Coordinates getPosition() {
	return position;
    }

    public Coordinates getNewPosition() {
	return newPosition;
    }

    public Coordinates getVelocity() {
	return velocity;
    }

    public void setVelocity(Coordinates velocity) {
	this.velocity = velocity;
    }

    public Coordinates getNewVelocity() {
	return newVelocity;
    }

    public void setNewVelocity(Coordinates newVelocity) {
	this.newVelocity.setCoordinates(newVelocity.coordinatesT, newVelocity.coordinatesO);
    }

    public void setPosition(Coordinates position) {
	this.position = position;
    }

    public void setNewPosition(Coordinates newPosition) {
	this.newPosition.setCoordinates(newPosition.coordinatesT, newPosition.coordinatesO);
    }

    public double getMass() {
	return mass;
    }

    public void setMass(double mass) {
	this.mass = mass;
    }

    public Coordinates getBestPosition() {
	return bestPosition;
    }

    public void setBestPosition(Coordinates bestPosition) {
	this.bestPosition.setCoordinates( bestPosition.coordinatesT,  bestPosition.coordinatesO);
    }

    public double getBestMass() {
	return bestMass;
    }

    public void setBestMass(double bestMass) {
	this.bestMass = bestMass;
    }

}
