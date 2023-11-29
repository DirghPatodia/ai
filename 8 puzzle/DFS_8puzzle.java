import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

class Node {
    int[][] board;
    int zeroRow, zeroCol;
    Node parent;

    public Node(int[][] board) {
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
    }

    public Node(int[][] board, Node parent) {
        this(board);
        this.parent = parent;
    }

    public Iterable<Node> neighbors() {
        Stack<Node> neighbors = new Stack<>();

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

                Node neighborNode = new Node(newBoard, this);
                neighbors.push(neighborNode);
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Node other = (Node) obj;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] != other.board[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        int base = 31;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result = result * base + board[i][j];
            }
        }
        return result;
    }
}

public class DFS_8puzzle {

    public static void main(String[] args) {
        int[][] initialBoard = {
                {1, 2, 3},
                {4, 0, 5},
                {6, 7, 8}
        };

        int[][] goalBoard = {
                {1, 2, 3},
                {4, 5, 0},
                {7, 8, 6}
        };

        Node initialNode = new Node(initialBoard);
        Node goalNode = new Node(goalBoard);

        System.out.println("Initial State:");
        printBoard(initialNode);

        DFSSolver solver = new DFSSolver(initialNode, goalNode);
        solver.solve();
    }

    static void printBoard(Node node) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(node.board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}

class DFSSolver {
    private Node initialNode;
    private Node goalNode;
    private Set<Node> visited;

    public DFSSolver(Node initialNode, Node goalNode) {
        this.initialNode = initialNode;
        this.goalNode = goalNode;
        this.visited = new HashSet<>();
    }

    public void solve() {
        Stack<Node> stack = new Stack<>();
        stack.push(initialNode);
        visited.add(initialNode);

        System.out.println("Solving...");

        while (!stack.isEmpty()) {
            Node currentNode = stack.pop();

            if (currentNode.isGoal()) {
                System.out.println("Solution Found!");
                printSolution(currentNode);
                return;
            }

            System.out.println("Expanding Node:");
            printBoard(currentNode);

            for (Node neighbor : currentNode.neighbors()) {
                if (!visited.contains(neighbor)) {
                    stack.push(neighbor);
                    visited.add(neighbor);
                    System.out.println("Adding Node to Stack:");
                    printBoard(neighbor);
                }
            }
        }

        System.out.println("No solution found.");
    }

    private void printSolution(Node goalNode) {
        printMoves(goalNode);
        printBoard(initialNode);
    }

    private void printMoves(Node goalNode) {
        System.out.println("Moves:");
        printMovesRecursive(goalNode);
    }

    private void printMovesRecursive(Node goalNode) {
        Stack<Node> stack = new Stack<>();
        Node node = goalNode;

        while (node != null) {
            stack.push(node);
            node = node.parent;
        }

        while (!stack.isEmpty()) {
            printBoard(stack.pop());
            System.out.println();
        }
    }

    private void printBoard(Node node) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(node.board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}