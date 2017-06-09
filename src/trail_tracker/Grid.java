package trail_tracker;

public class Grid {
    
    //0- no food
    //1- food
    //2- FSM was here
    //3- eaten food
    
    private int[][] grid = new int[32][32];
    


    public int getGridValue(int row, int col) {
	return grid[row][col];
    }

    public void setGridValue(int row, int col, int value) {
	this.grid[row][col] = value;
    }

   

    @Override
    public String toString() {
	String s="";
	 for (int i = 0; i < 32; i++) {
		for (int j = 0; j < 32; j++) {
		   s+=this.getGridValue(i, j)+" ";
		}
		s+="\n";
		}
	return s;
    }

  
   

}
