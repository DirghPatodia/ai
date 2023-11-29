import java.util.*;

public class MazeSolverDFS {
    public static void main(String[] args) {
        int[][] maze = {
                {0, 0, 1, 0, 0, 0},
                {0, 1, 0, 0, 1, 0},
                {0, 0, 1, 1, 1, 0},
                {1, 0, 0, 0, 1, 1},
                {0, 0, 1, 0, 0, 0}
        };

        int startRow = 0;
        int startCol = 0;
        int endRow = 4;
        int endCol = 5;

        boolean[][] visited = new boolean[maze.length][maze[0].length];
        List<int[]> path = new ArrayList<>();

        if (dfs(maze, visited, startRow, startCol, endRow, endCol, path)) {
            System.out.println("Path found:");
            for (int[] cell : path) {
                System.out.println("(" + cell[0] + ", " + cell[1] + ")");
            }
        } else System.out.println("No path found.");
    }

    public static boolean dfs(int[][] maze, boolean[][] visited, int row, int col, int endRow, int endCol, List<int[]> path) {
        if (row == endRow && col == endCol) {
            path.add(new int[]{row, col});
            return true; // Path found
        }

        if (row < 0 || row >= maze.length || col < 0 || col >= maze[0].length || maze[row][col] == 1 || visited[row][col]) return false; // Out of bounds, wall, or already visited

        visited[row][col] = true;
        path.add(new int[]{row, col});

        // Explore in four directions: Up, Down, Left, Right
        if (dfs(maze, visited, row - 1, col, endRow, endCol, path) || dfs(maze, visited, row + 1, col, endRow, endCol, path) ||
            dfs(maze, visited, row, col - 1, endRow, endCol, path) || dfs(maze, visited, row, col + 1, endRow, endCol, path)) return true;

        // If no valid path found, backtrack by removing the last cell from the path
        path.remove(path.size() - 1);
        return false;
    }
}