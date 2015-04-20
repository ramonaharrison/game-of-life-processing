# Spruced up Game of Life

Conway's Game of Life as a Processing sketch. 

Includes classic start patterns:

* Lobster 
* Noah's Ark
* Max
* R-pentomino

Requires Processing core libraries -- to run from IntelliJ, import the core.jar file ( File > Project Structure... > Libraries > + > core.jar ).

Patterns are toggled with commenting in/out top lines of Life.java. New patterns can be obtained as Life 1.06 (.lif) files -- try http://www.conwaylife.com/wiki/ for a good selection of patterns.

Include a new pattern:

```
    // setup for pattern
    static String filepathString = "pattern.lif"; // save pattern .lif file in project home directory and point filepathString to it
    static int yPixels = 1000;
    static int xPixels = 1000;
    static int yOffset = 0;
    static int xOffset = 0;
    static int y = 500;
    static int x = 500;
    static int cell = 2;
```

## More info:

