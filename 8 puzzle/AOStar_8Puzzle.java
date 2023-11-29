import java.util.*;

class AOStarNode implements Comparable<AOStarNode> {
    int[][] board;
    int zeroRow, zeroCol;
    int g; // cost to reach this node
    double h; // heuristic estimate of the cost to the goal
    double f; // f(n) = g(n) + h(n)
    AOStarNode parent;

    public AOStarNode(int[][] board, int g, double h, AOStarNode parent) {
        this.board = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.board[i][j] = board[i][j];
                if (board[i][j] == 0) {
                    zeroRow = i;
                    zeroCol = j;
                }
            }
        }
        this.g = g;
        this.h = h;
        this.f = g + h;
        this.parent = parent;
    }

    public Iterable<AOStarNode> neighbors() {
        List<AOStarNode> neighbors = new ArrayList<>();

        int[][] moves = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // Up, Down, Left, Right

        for (int[] move : moves) {
            int newRow = zeroRow + move[0];
            int newCol = zeroCol + move[1];

            if (newRow >= 0 && newRow < 3 && newCol >= 0 && newCol < 3) {
                int[][] newBoard = new int[3][3];
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        newBoard[i][j] = board[i][j];
                    }
                }

                int temp = newBoard[zeroRow][zeroCol];
                newBoard[zeroRow][zeroCol] = newBoard[newRow][newCol];
                newBoard[newRow][newCol] = temp;

                AOStarNode neighborNode = new AOStarNode(newBoard, g + 1, calculateHeuristic(newBoard), this);
                neighbors.add(neighborNode);
            }
        }

        return neighbors;
    }

    public boolean isGoal() {
        int count = 1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] != count % 9) {
                    return false;
                }
                count++;
            }
        }
        return true;
    }

    private double calculateHeuristic(int[][] board) {
        // Simple heuristic: count the number of misplaced tiles
        int misplacedTiles = 0;
        int count = 1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] != count % 9) {
                    misplacedTiles++;
                }
                count++;
            }
        }
        return misplacedTiles;
    }

    @Override
    public int compareTo(AOStarNode other) {
        // Compare nodes based on their f values
        return Double.compare(this.f, other.f);
    }
}

public class AOStar_8Puzzle {

    public static void main(String[] args) {
        int[][] initialBoard = {
                {1, 2, 3},
                {4, 0, 5},
                {6, 7, 8}
        };

        int[][] goalBoard = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 0}
        };

        AOStarNode initialNode = new AOStarNode(initialBoard, 0, 0, null);
        AOStarNode goalNode = new AOStarNode(goalBoard, 0, 0, null);

        AOStarSolver solver = new AOStarSolver(initialNode, goalNode);
        solver.solve();
    }
}

class AOStarSolver {
    private AOStarNode initialNode;
    private AOStarNode goalNode;

    public AOStarSolver(AOStarNode initialNode, AOStarNode goalNode) {
        this.initialNode = initialNode;
        this.goalNode = goalNode;
    }

    public void solve() {
        PriorityQueue<AOStarNode> openSet = new PriorityQueue<>();
        Set<AOStarNode> closedSet = new HashSet<>();

        openSet.add(initialNode);

        while (!openSet.isEmpty()) {
            AOStarNode currentNode = openSet.poll();

            if (currentNode.isGoal()) {
                printMoves(currentNode);
                return;
            }

            closedSet.add(currentNode);

            for (AOStarNode neighbor : currentNode.neighbors()) {
                if (closedSet.contains(neighbor)) {
                    continue; // skip already evaluated neighbors
                }

                if (!openSet.contains(neighbor)) {
                    openSet.add(neighbor);
                }
            }
        }

        System.out.println("No solution found.");
    }

    private void printMoves(AOStarNode goalNode) {
        System.out.println("Moves:");

        printMovesRecursive(goalNode);
    }

    private void printMovesRecursive(AOStarNode node) {
        if (node == null) {
            return;
        }

        printMovesRecursive(node.parent);
        printBoard(node);
        System.out.println();
    }

    private void printBoard(AOStarNode node) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(node.board[i][j] + " ");
            }
            System.out.println();
        }
    }
}
