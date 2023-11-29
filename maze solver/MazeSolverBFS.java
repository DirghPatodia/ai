import java.util.*;

public class MazeSolverBFS {
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

        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        int rows = maze.length;
        int cols = maze[0].length;
        boolean[][] visited = new boolean[rows][cols];

        Queue<List<int[]>> queue = new ArrayDeque<>();
        List<int[]> initialPath = new ArrayList<>();
        initialPath.add(new int[]{startRow, startCol});
        queue.add(initialPath);

        while (!queue.isEmpty()) {
            List<int[]> currentPath = queue.poll();
            int[] currentCell = currentPath.get(currentPath.size() - 1);
            int row = currentCell[0];
            int col = currentCell[1];

            if (row == endRow && col == endCol) {
                System.out.println("Path found:");
                for (int[] cell : currentPath) {
                    System.out.println("(" + cell[0] + ", " + cell[1] + ")");
                }
                break;
            }

            for (int[] direction : directions) {
                int newRow = row + direction[0];
                int newCol = col + direction[1];

                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols && maze[newRow][newCol] == 0 && !visited[newRow][newCol]) {
                    List<int[]> newPath = new ArrayList<>(currentPath);
                    newPath.add(new int[]{newRow, newCol});
                    queue.add(newPath);
                    visited[newRow][newCol] = true;
                }
            }
        }

        if (!visited[endRow][endCol]) {
            System.out.println("No path found.");
        }
    }
}