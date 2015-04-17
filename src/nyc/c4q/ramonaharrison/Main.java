package nyc.c4q.ramonaharrison;

import java.util.Random;

public class Main {

    final static int numCols = TerminalSize.getNumColumns();
    final static int numRows = TerminalSize.getNumLines();
    static int[][] grid = new int[numRows][numCols];

    public static void main(String[] args) {

        zeroGrid();
        drawRPentomino(numRows/4, numCols/4);
        drawRPentomino(numRows/2, numCols/2);
        drawRPentomino(3*numRows/4, 3*numCols/4);

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
            AnsiTerminal.pause(0.3);
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