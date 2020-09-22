
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.JFrame;

public class Maze extends JPanel {
    private int n;                 // size of maze
    private boolean[][] north;     // north wall
    private boolean[][] south;     // south wall
    private boolean[][] east;      // east wall
    private boolean[][] west;      // west wall
    private int[][] visited;       // if vertex is not visited the color is blue - visited red  - finished black
    private boolean solved = false;

    public Maze(int n) {
        this.n = n;
        create_grid();
        create_maze(1, 1);
       Reset_vertices();
       solve_maze(1,1,n,n);  //the position of first vertex and 10, 10 is the target
             
    }

    private void Reset_vertices() {
    	for(int k =1;k <= n; k++) {
    		for(int m = 1; m<= n;m++) {
    			visited[k][m] = 0;
    		}
    	}
	}
    private boolean solve_maze(int i, int j, int destx, int desty) {
		visited[i][j]++; //mark as visited (red)
		if(i==destx && j==desty) return true; //once maze is solved, stop program
		boolean found = false; 
		if((visited[i+1][j] == 0) && !east[i][j] && !found) { //if possible to move east
			found = solve_maze(i+1,j,destx,desty) || found; //recursive while moving east, or if not possible
		}													//anywhere, return false
		
		if((visited[i][j+1] == 0) && !south[i][j] && !found) {
			found = solve_maze(i,j+1,destx,desty) || found;
		}
		if((visited[i-1][j] == 0) && !west[i][j] && !found) {
			found = solve_maze(i-1,j,destx,desty) || found;
		}
		
		if((visited[i][j-1] == 0) && !north[i][j] && !found) {
			found = solve_maze(i,j-1,destx,desty) || found;
		}
		if(found) { 
			return true;
		} else {
			visited[i][j]++; //mark as already visited (cyan)
			return false;
		}
		}

    private void create_maze(int x, int y) {
    	visited[x][y]= 1;
    	while(visited[x][y+1]==0 || visited[x+1][y] ==0 || visited[x][y-1]==0 || visited[x-1][y] ==0) {
    		Random rn = new Random();
    		int rand = rn.nextInt(4);
    		switch(rand) {
    		case 0:
    			if (visited[x][y+1]==0) {
    				south[x][y] = false;
    				north[x][y+1] = false;
    				create_maze(x, y +1);
    			}
    			break;
    		case 1:
    			if (visited[x+1][y]==0) {
    				east[x][y] = false;
    				west[x+1][y] = false;
    				create_maze(x+1, y);
    			}
    		case 2:
    			if (visited[x][y-1]==0) {
    				north[x][y] = false;
    				south[x][y-1] = false;
    				create_maze(x, y-1);
    			}
    			break;
    		case 3:
    			if (visited[x-1][y]==0) {
    				west[x][y] = false;
    				east[x-1][y] = false;
    				create_maze(x-1, y);
    			}
    			break;
    		default:
    			break;
    		}
    	}
    }

	private void create_grid() {
        // Add border vertices and consider them as visited,  all other vertices are not visited
        visited = new int[n+2][n+2];
        for (int x = 0; x < n+2; x++) 
            for (int y = 0; y < n+2; y++)
             visited[x][y] = 0;
        
        for (int x = 0; x < n+2; x++) {
            visited[x][0] = 1;
            visited[x][n+1] = 1;
        }
        for (int y = 0; y < n+2; y++) {
            visited[0][y] = 1;
            visited[n+1][y] = 1;
        }


        // create the walls for vertices
        north = new boolean[n+2][n+2];
        east  = new boolean[n+2][n+2];
        south = new boolean[n+2][n+2];
        west  = new boolean[n+2][n+2];
        for (int x = 0; x < n+2; x++) {
            for (int y = 0; y < n+2; y++) {
                north[x][y] = true;
                east[x][y]  = true;
                south[x][y] = true;
                west[x][y]  = true;
            }
        }
    }
    
    
    
  
    public void paintComponent(Graphics g){
  super.paintComponent(g);
  this.setBackground(Color.WHITE);
  g.setColor(Color.RED);
  for (int x= 1 ; x<n+1; x++){
   for (int y= 1 ; y<n+1; y++){
    if(visited[x][y]==1 )
    g.setColor(Color.RED);
    else if(visited[x][y]==2 )
     g.setColor(Color.CYAN);
    else
       g.setColor(Color.BLUE);
    if((x==1&&y==1) || (x==n&&y==n)  )
     g.setColor(Color.GREEN);
       g.drawOval(50+x*50, 50+y*50, 10, 10);
       
       g.setColor(Color.BLACK);
       // west
       if (west[x][y])
       g.drawLine(25+x*50, 25+y*50, 25+x*50, 75+y*50);
       //east
        if (east[x][y])
         g.drawLine(75+x*50, 25+y*50, 75+x*50, 75+y*50);
       //north
      if (north[x][y])
       g.drawLine(25+x*50, 25+y*50, 75+x*50, 25+y*50);
       //south
       if (south[x][y])
       g.drawLine(25+x*50, 75+y*50, 75+x*50, 75+y*50);
       
   }
  }
  
 
 }
    
    public static void main(String[] args) {
     Maze M = new Maze(10);
  JFrame f = new JFrame("Maze");
  f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  M.setBackground(Color.WHITE);
  f.add(M);
  f.setSize(400,300);
  f.setVisible(true);

}
    
}