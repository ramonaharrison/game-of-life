package nyc.c4q.ramonaharrison;

import java.util.Random;

public class Main {

    final static int numCols = TerminalSize.getNumColumns();
    final static int numRows = TerminalSize.getNumLines();
    static int[][] grid = new int[numRows][numCols];

    public static void main(String[] args) {
        int generation = 1;

        zeroGrid();
        //drawRPentomino(numRows/4, numCols/4);
        //drawRPentomino(numRows/2, numCols/2);
        //drawRPentomino(3*numRows/4, 3*numCols/4);

        //drawGlider(numRows/2, numCols/2);

        //drawGosperGliderGun(numRows/2, (numCols/2)-20);

        //drawAcorn((numRows/2)-10, (numCols/2)-10);

        final AnsiTerminal terminal = new AnsiTerminal();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                terminal.showCursor();
                terminal.reset();
                terminal.scroll(1);
                terminal.moveTo(numRows, 0);
            }
        });

        terminal.setBackgroundColor(AnsiTerminal.Color.BLACK);
        terminal.clear();
        terminal.hideCursor();

        while (true)
        {
            terminal.clear();
            drawGeneration(terminal);
            repopulateGrid();

            terminal.setTextColor(AnsiTerminal.Color.RED);
            terminal.moveTo(numRows-1, numCols-20);
            terminal.write("Generation:  " + generation);
            terminal.setTextColor(AnsiTerminal.Color.WHITE);
            generation++;
            AnsiTerminal.pause(0.1);
        }

    }

    public static void drawGeneration(AnsiTerminal terminal) {

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (grid[i][j] == 1) {
                    terminal.moveTo(i, j);
                    terminal.write("░");
                }
            }
        }
    }

    public static void repopulateGrid() {

        int[][] nextGrid = new int[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                boolean liveCell = grid[i][j] == 1;
                int liveNeighbors = liveNeighbors(grid, i, j, numRows, numCols);

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
    }


    public static int liveNeighbors(int[][] grid, int i, int j, int numRows, int numCols) {
        int neighborCount = 0;

        if (0 < i && i < numRows - 1 && 0 < j && j < numCols - 1) {
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

    public static void zeroGrid() {
        // populates grid with zeros (dead cells)
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                grid[i][j] = 0;
            }
        }
    }


    public static void drawRPentomino(int y, int x) {

        grid[y][x] = 1;
        grid[y][x + 1] = 1;
        grid[y + 1][x] = 1;
        grid[y + 1][x - 1] = 1;
        grid[y + 2][x] = 1;

    }

    public static void drawPentadecathlon(int y, int x) {
        grid[y][x] = 1;
        grid[y][x + 2] = 1;
        grid[y][x + 3] = 1;
        grid[y][x + 4] = 1;
        grid[y][x + 5] = 1;
        grid[y][x + 6] = 1;
        grid[y][x + 7] = 1;
        grid[y][x + 8] = 1;
        grid[y][x + 9] = 1;
        grid[y][x + 10] = 1;
    }

    public static void drawGlider(int y, int x) {
        grid[y][x] = 1;
        grid[y-1][x] = 1;
        grid[y-1][x+1] = 1;
        grid[y-2][x-1] = 1;
        grid[y-2][x+1] = 1;

    }

    public static void drawGosperGliderGun(int y, int x) {
        grid[y][x] = 1;
        grid[y][x + 1] = 1;
        grid[y - 1][x] = 1;
        grid[y - 1][x + 1] = 1;

        grid[y][x + 10] = 1;
        grid[y-1][x + 10] = 1;
        grid[y-2][x + 10] = 1;
        grid[y-3][x + 11] = 1;
        grid[y-4][x + 12] = 1;
        grid[y-4][x + 13] = 1;
        grid[y+1][x + 11] = 1;
        grid[y+2][x + 12] = 1;
        grid[y+2][x + 13] = 1;

        grid[y-1][x + 14] = 1;

        grid[y+1][x + 15] = 1;
        grid[y-3][x + 15] = 1;
        grid[y][x + 16] = 1;
        grid[y-1][x + 16] = 1;
        grid[y-2][x + 16] = 1;
        grid[y-1][x + 17] = 1;

        grid[y][x + 20] = 1;
        grid[y+1][x + 20] = 1;
        grid[y+2][x + 20] = 1;
        grid[y][x + 21] = 1;
        grid[y+1][x + 21] = 1;
        grid[y+2][x + 21] = 1;
        grid[y-1][x + 22] = 1;
        grid[y+3][x + 22] = 1;

        grid[y-1][x + 24] = 1;
        grid[y-2][x + 24] = 1;
        grid[y+3][x + 24] = 1;
        grid[y+4][x + 24] = 1;

        grid[y+1][x + 34] = 1;
        grid[y+1][x + 35] = 1;
        grid[y+2][x + 34] = 1;
        grid[y+2][x + 35] = 1;

    }

    public static void drawAcorn(int y, int x) {
        grid[y][x] = 1;
        grid[y][x+1] = 1;
        grid[y][x+4] = 1;
        grid[y][x+5] = 1;
        grid[y][x+6] = 1;
        grid[y+1][x+3] = 1;
        grid[y+2][x+1] = 1;
    }

    public static void drawDiehard(int y, int x) {

    }


    public static void randomStart() {

        Random random = new Random();

        // populates grid randomly (~50/50)
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                grid[i][j] = random.nextInt(2);
            }
        }
    }

}