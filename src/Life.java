import processing.core.PApplet;
import processing.core.PFont;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;

/*
 * 04/18/2015
 * Ramona Harrison
 * Life.java
 *
 * Graphical Conway's Game of Life in Processing. Starting pattern can be loaded with any .lif file and in-range height x width input.
 */

public class Life extends PApplet {

    /*
     * some starting patterns:
    */

    // r-pentomino - (http://www.conwaylife.com/wiki/R-pentomino)
//    static String filepathString = "r-pentomino";
//    static int yPixels = 1000;
//    static int xPixels = 1600;
//    static int yOffset = -400;
//    static int xOffset = -100;
//    static int y=200;
//    static int x=200;
//    static int cell = 8;


    // max - (http://www.conwaylife.com/wiki/Max)
//    static String filepathString = "/Users/mona/Documents/coding/processing/life/max.txt";
//    static int yPixels = 1000;
//    static int xPixels = 1000;
//    static int yOffset = 0;
//    static int xOffset = 0;
//    static int y = 500;
//    static int x = 500;
//    static int cell = 2;

    //lobster (http://www.conwaylife.com/wiki/Breeder_1)
//    static String filepathString = "/Users/mona/Documents/coding/processing/life/lobster.txt";
//    static int yPixels = 1000;
//    static int xPixels = 1600;
//    static int yOffset = 0;
//    static int xOffset = 0;
//    static int y = 400;
//    static int x = 800;
//    static int cell = 2;

    //noah's ark (http://www.conwaylife.com/wiki/Noah%27s_ark)
    static String startPattern = "noahsArk";
    static String filepathString = "/Users/mona/Documents/coding/processing/life/noahsark.txt";
    static int yPixels = 1000;
    static int xPixels = 1600;
    static int yOffset = -250;
    static int xOffset = 0;
    static int y = 200;
    static int x = 200;
    static int cell = 8;

    static int generation = 1;
    static int[][] grid = new int[x][y];

    public void setup() {

        // prepares the frame
        size(xPixels, yPixels);
        frameRate(20);
        int centerX = x / 2;
        int centerY = y / 2;

        PFont orator = loadFont("OratorStd-24.vlw");
        textFont(orator, 18);

        // prepares the grid array with start pattern
        zeroGrid();
        if (filepathString.equals("r-pentomino")) {
            drawRPentomino(centerX, centerY);
        } else {
            try {
                drawPattern(centerX, centerY);
            } catch (FileNotFoundException fnfe) {
                System.out.println("File not found.");
            }
        }
    }


    public void draw() {

        // loops forever
        background(0);

        // draws each generation

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (grid[i][j] == 1) {

                    int r = redAdjust(i, j);
                    int g = greenAdjust(i, j);
                    int b = blueAdjust(i, j);

                    strokeWeight(1);
                    fill(r, g, b);
                    rect((i * cell) + xOffset, (j * cell) + yOffset, cell, cell);

                }
            }
        }

        // repopulate grid
        int[][] nextGrid = new int[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                boolean liveCell = grid[i][j] == 1;
                int liveNeighbors = liveNeighbors(grid, i, j, x, y);

                // determines each cell live/dead in the next generation based on current live/dead status and # of live neighbors
                if (liveCell && (liveNeighbors < 2 || liveNeighbors > 3)) {
                    nextGrid[i][j] = 0;
                } else if (liveCell && (liveNeighbors == 2 || liveNeighbors == 3)) {
                    nextGrid[i][j] = 1;
                } else if (!liveCell && liveNeighbors == 3) {
                    nextGrid[i][j] = 1;
                } else {
                    nextGrid[i][j] = 0;
                }
            }
        }

        grid = nextGrid;

        fill(255);
        text("Generation: " + generation, xPixels - 200, 50);
        generation++;

    }

    public static int liveNeighbors(int[][] grid, int i, int j, int x, int y) {

        // investigates adjacent cells and returns # of live cells
        int neighborCount = 0;

        if (0 < i && i < x - 1 && 0 < j && j < y - 1) {
            for (int k = i - 1; k <= i + 1; k++) {
                for (int l = j - 1; l <= j + 1; l++) {
                    if (!(i == k && l == j) && grid[k][l] == 1) {
                        neighborCount += 1;
                    }
                }
            }
        }

        return neighborCount;
    }

    // generates gradient rgb values
    public static int redAdjust(double x, double y) {
        return (int) (Math.abs(x/y) * 255);
    }

    public static int greenAdjust(double x, double y) {
        return (int) (Math.abs(y/x) * 255);
    }

    public static int blueAdjust(double x, double y) {
        return (int) (Math.abs(y/x) * 255);
    }

    public static void zeroGrid() {

        // populates grid with zeros (dead cells)
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                grid[i][j] = 0;
            }
        }
    }

    public static void drawPattern(int centerX, int centerY) throws FileNotFoundException {

        // parses xy coordinates from .lif files and populates grid
        File pattern = new File(filepathString);
        Scanner scanner = new Scanner(pattern);
        String header = scanner.nextLine();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            int x = Integer.valueOf(line.substring(0, line.indexOf(' ')));
            int y = Integer.valueOf(line.substring(line.indexOf(' ') + 1, line.length()));
            grid[centerX + x][centerY + y] = 1;
        }

    }

    public static void drawRPentomino(int x, int y) {

        // classic r-pentomino start pattern
        grid[x][y] = 1;
        grid[x][y + 1] = 1;
        grid[x + 1][y] = 1;
        grid[x + 1][y - 1] = 1;
        grid[x + 2][y] = 1;

    }

}
