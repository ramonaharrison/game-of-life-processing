import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;

/*
 * 04/18/2015
 * Ramona Harrison
 * life.pde
 * 
 * Graphical Conway's Game of Life in Processing. Starting pattern can be modified with any .lif file and in-range height x width input. 
 */
 
 
/*
 * some starting patterns:
 */

// max - (http://www.conwaylife.com/wiki/Max)
//static String filepathString = "/Users/mona/Documents/coding/processing/life/max.txt";
//static int yPixels=1000;
//static int xPixels=1000;
//static int y=500;
//static int x=500;
//static int cell = 2;

//lobster (rect size = 2)
//static String filepathString = "/Users/mona/Documents/coding/processing/life/lobster.txt";
//static int yPixels=800;
//static int xPixels=1600;
//static int y=400;
//static int x=800;
//static int cell = 2;

//noah's ark (rect size = 4)
static String filepathString = "/Users/mona/Documents/coding/processing/life/noahsark.txt";
static int yPixels=1050;
static int xPixels=1680;
static int y=250;
static int x=250;
static int cell = 8;


static int generation=1;
static int[][]grid=new int[x][y];

        void setup() {

        // prepares the frame
        size(xPixels,yPixels);
        frameRate(20);
        int centerX=x/2;
        int centerY=y/2;

        // prepares the grid array with start pattern
        zeroGrid();
        try {
            drawPattern(centerX,centerY);
        } catch(FileNotFoundException fnfe) { 
            System.out.println("fnfe");
        } 
    
        }

        // loops forever
        void draw(){
        
        background(0);
        
        // draw generation
        for(int i=0;i<x;i++){
        for(int j=0;j<y;j++){
        if(grid[i][j]==1){
        strokeWeight(1);
        fill(135,205,255);
        rect(i*cell,j*cell,cell,cell);
        }
        }
        }

        // repopulate grid
        int[][]nextGrid=new int[x][y];
        for(int i=0;i<x;i++){
        for(int j=0;j<y;j++){
        boolean liveCell=grid[i][j]==1;
        int liveNeighbors=liveNeighbors(grid,i,j,x,y);

        // determines each cell live/dead in the next generation based on current live/dead status and # of live neighbours
        if(liveCell&&(liveNeighbors<2||liveNeighbors>3)){
        nextGrid[i][j]=0;
        }else if(liveCell&&(liveNeighbors==2||liveNeighbors==3)){
        nextGrid[i][j]=1;
        }else if(!liveCell&&liveNeighbors==3){
        nextGrid[i][j]=1;
        }else{
        nextGrid[i][j]=0;
        }
        }
        }

        grid=nextGrid;

        fill(255);
        textSize(12);
        text("Generation: " + generation, xPixels-150, 20); 
        generation++;

        }

public static void zeroGrid(){

        // populates grid with zeros (dead cells)
        for(int i=0;i<x;i++){
        for(int j=0;j<y;j++){
        grid[i][j]=0;
        }
        }
        }

public static void drawRPentomino(int x,int y){

        grid[x][y]=1;
        grid[x][y+1]=1;
        grid[x+1][y]=1;
        grid[x+1][y-1]=1;
        grid[x+2][y]=1;

        }

public static int liveNeighbors(int[][]grid,int i,int j,int x,int y){
        int neighborCount=0;

        if(0<i&&i<x-1&&0<j&&j<y-1){
        for(int k=i-1;k<=i+1;k++){
        for(int l=j-1;l<=j+1;l++){
        if(!(i==k&&l==j)&&grid[k][l]==1){
        neighborCount+=1;
        }
        }
        }
        }

        return neighborCount;
        }

    
    public static void drawPattern(int centerX, int centerY)  throws FileNotFoundException {
        File pattern = new File(filepathString);
        Scanner scanner = new Scanner(pattern);
        String header = scanner.nextLine();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            int x = Integer.valueOf(line.substring(0,line.indexOf(' ')));
            int y = Integer.valueOf(line.substring(line.indexOf(' ') + 1, line.length()));
            grid[centerX + x][centerY + y] = 1;
        }

    }
    
    
