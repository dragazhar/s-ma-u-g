package trail_tracker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Trail {
    Grid trailGrid;
    int food=0;

    public Trail(String trailFileName) throws IOException {
	super();
	File trailDataFile = new File(trailFileName);
	BufferedReader trailData = new BufferedReader(new FileReader(
		trailDataFile));
	   trailGrid = new Grid();
	   food=0;
	try {

	    String line = new String();
	    int row = 0;
	    int col = 0;
	    while ((line = trailData.readLine()) != null) {

		for (int i = 0; i < line.length(); i++) {
		    if (line.charAt(i) != ' ') {

			int value = Integer.parseInt(String.valueOf(line
				.charAt(i)));
			if(value==1){
			   food++; 
			}
			trailGrid.setGridValue(row, col, value);
			col++;
		    }
		}
		row++;
		col = 0;
	    }

	} catch (IOException e) {
	    System.err.println("Error in input/output process...");
	} finally {
	    trailData.close();
	}

    }

    public static void main(String[] args) throws IOException {
	//johnMuirTrail ();
	santaFeTrail ();
	
	
    }
    
    private static void santaFeTrail ()throws IOException
    {
	Trail santaFeTrail = new Trail("trainingData\\SantaFeTrail.txt");
	System.out.println(santaFeTrail.trailGrid);
	System.out.println("Food: "+santaFeTrail.getFood());
	ShowTrail.draw("Santa Fe Trail", santaFeTrail.trailGrid);
    }
    private static void johnMuirTrail ()throws IOException{
	Trail johnMuirTrail = new Trail("trainingData\\JohnMuirTrail.txt");
	System.out.println(johnMuirTrail.trailGrid);
	System.out.println("Food: "+johnMuirTrail.getFood());
	ShowTrail.draw("John Muir Trail", johnMuirTrail.trailGrid);
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }
}
