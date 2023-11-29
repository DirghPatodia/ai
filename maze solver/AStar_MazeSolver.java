import java.util.*;

class Cell2 {
    int row, col;
    int f; // Total cost (g + h)
    int g; // Cost from start
    int h; // Heuristic cost to the end
    Cell2 parent;

    public Cell2(int row, int col, int g, int h, Cell2 parent) {
        this.row = row;
        this.col = col;
        this.g = g;
        this.h = h;
        this.f = g + h;
        this.parent = parent;
    }
}

public class AStar_MazeSolver {
    int[][] maze;
    Cell2 start;
    Cell2 end;

    public AStar_MazeSolver(int[][] maze, Cell2 start, Cell2 end) {
        this.maze = maze;
        this.start = start;
        this.end = end;
    }

    int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // Up, Down, Left, Right

    boolean isSafe(int[][] maze, int row, int col, boolean[][] visited) {
        return row >= 0 && row < maze.length && col >= 0 && col < maze[0].length && maze[row][col] == 0 && !visited[row][col];
    }

    void aStarSearch() {
        ArrayList<Cell2> path = aStarSearch(maze, start, end);
        if (path != null) {
            System.out.println("Path found:");
            for (Cell2 cell : path) {
                System.out.println("(" + cell.row + ", " + cell.col + ")");
            }
        } else System.out.println("No path found.");
    }

    ArrayList<Cell2> aStarSearch(int[][] maze, Cell2 start, Cell2 end) {
        int rows = maze.length;
        int cols = maze[0].length;

        PriorityQueue<Cell2> openSet = new PriorityQueue<>(Comparator.comparingInt(c -> c.f));
        boolean[][] visited = new boolean[rows][cols];
        openSet.add(start);

        while (!openSet.isEmpty()) {
            Cell2 current = openSet.poll();
            visited[current.row][current.col] = true;

            if (current.row == end.row && current.col == end.col) {
                // Path found, backtrack to start
                ArrayList<Cell2> path = new ArrayList<>();
                while (current != null) {
                    path.add(current);
                    current = current.parent;
                }
                Collections.reverse(path);
                return path;
            }

            for (int[] dir : directions) {
                int newRow = current.row + dir[0];
                int newCol = current.col + dir[1];
                if (isSafe(maze, newRow, newCol, visited)) {
                    int g = current.g + 1; // Cost from start
                    int h = (end.row - newRow) + (end.col - newCol); // Heuristic (Manhattan distance)
                    Cell2 neighbor = new Cell2(newRow, newCol, g, h, current);
                    openSet.add(neighbor);
                }
            }
        }

        return null; // No path found
    }

    public static void main(String[] args) {
        int[][] maze = {
                {0, 0, 1, 0, 0, 0},
                {0, 1, 0, 0, 1, 0},
                {0, 0, 1, 1, 1, 0},
                {1, 0, 0, 0, 1, 1},
                {0, 0, 1, 0, 0, 0}
        };

        Cell2 start = new Cell2(0, 0, 0, 0, null);
        Cell2 end = new Cell2(4, 5, 0, 0, null);

        AStar_MazeSolver a = new AStar_MazeSolver(maze, start, end);
        a.aStarSearch();
    }
}