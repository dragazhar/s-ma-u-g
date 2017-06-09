package search_algorithm;

public class Body {
    Coordinates position;
    Coordinates newPosition;
    double mass = 0;
    
    Coordinates velocity;
    Coordinates newVelocity;
    
    Coordinates bestPosition;
    double bestMass;
    
    
    public Body(Point p) {
	super();
	position = new Coordinates(p.coordinatesT, p.coordinatesO);
	velocity= new Coordinates(p.coordinatesT, p.coordinatesO);
	mass=p.getValue();
	
	bestPosition= new Coordinates(p.coordinatesT, p.coordinatesO);
	bestMass=p.getValue();
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
        this.newVelocity = newVelocity;
    }

   

    public void setPosition(Coordinates position) {
        this.position = position;
    }

    public void setNewPosition(Coordinates newPosition) {
        this.newPosition = newPosition;
    }
   
}
