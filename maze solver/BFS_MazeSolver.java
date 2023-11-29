import java.util.*;

class Cell {
    int row, col;
    Cell parent;

    public Cell(int row, int col, Cell parent) {
        this.row = row;
        this.col = col;
        this.parent = parent;
    }
}

public class BFS_MazeSolver {
    int[][] maze;
    Cell start;
    Cell end;

    public BFS_MazeSolver(int[][] maze, Cell start, Cell end) {
        this.maze = maze;
        this.start = start;
        this.end = end;
    }

    int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // Up, Down, Left, Right

    boolean isSafe(int[][] maze, int row, int col, boolean[][] visited) {
        return row >= 0 && row < maze.length && col >= 0 && col < maze[0].length && maze[row][col] == 0 && !visited[row][col];
    }

    int heuristic(Cell current, Cell end) {
        return Math.abs(current.row - end.row) + Math.abs(current.col - end.col);
    }

    void bestFirstSearch() {
        ArrayList<Cell> path = bestFirstSearch(maze, start, end);
        if (path != null) {
            System.out.println("Path found:");
            for (Cell cell : path) {
                System.out.println("(" + cell.row + ", " + cell.col + ")");
            }
        } else System.out.println("No path found.");
    }

    ArrayList<Cell> bestFirstSearch(int[][] maze, Cell start, Cell end) {
        int rows = maze.length;
        int cols = maze[0].length;

        PriorityQueue<Cell> priorityQueue = new PriorityQueue<>((c1, c2) -> {
            int h1 = heuristic(c1, end); // Heuristic value for cell c1
            int h2 = heuristic(c2, end); // Heuristic value for cell c2
            return Integer.compare(h1, h2); // Compare based on the heuristic values
        });

        boolean[][] visited = new boolean[rows][cols];
        priorityQueue.add(start);

        while (!priorityQueue.isEmpty()) {
            Cell current = priorityQueue.poll();
            visited[current.row][current.col] = true;

            if (current.row == end.row && current.col == end.col) {
                // Path found, backtrack to start
                ArrayList<Cell> path = new ArrayList<>();
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
                    priorityQueue.add(new Cell(newRow, newCol, current));
                }
            }
        }

        return null; // No path found
    }

    public static void main(String[] args) {
        int[][] maze = {
                {0, 0, 1, 0, 0, 0},
                {0, 0, 1, 0, 1, 0},
                {0, 0, 1, 1, 1, 0},
                {1, 0, 0, 0, 1, 1},
                {0, 0, 1, 0, 0, 0}
        };

        Cell start = new Cell(0, 0, null);
        Cell end = new Cell(4, 5, null);

        BFS_MazeSolver a = new BFS_MazeSolver(maze, start, end);
        a.bestFirstSearch();
    }
}