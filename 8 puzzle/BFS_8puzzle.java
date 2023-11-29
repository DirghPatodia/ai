//import java.util.*;
//
//class Node {
//    int[][] board;
//    int zeroRow, zeroCol;
//    Node parent;
//
//    public Node(int[][] board) {
//        this.board = new int[3][3];
//        for (int i = 0; i < 3; i++) {
//            for (int j = 0; j < 3; j++) {
//                this.board[i][j] = board[i][j];
//                if (board[i][j] == 0) {
//                    zeroRow = i;
//                    zeroCol = j;
//                }
//            }
//        }
//    }
//
//    public Node(int[][] board, Node parent) {
//        this(board);
//        this.parent = parent;
//    }
//
//    // Method to generate neighboring states by moving the blank space
//    public Iterable<Node> neighbors() {
//        Queue<Node> neighbors = new ArrayDeque<>();
//
//        int[][] moves = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // Up, Down, Left, Right
//
//        for (int[] move : moves) {
//            int newRow = zeroRow + move[0];
//            int newCol = zeroCol + move[1];
//
//            if (newRow >= 0 && newRow < 3 && newCol >= 0 && newCol < 3) {
//                int[][] newBoard = new int[3][3];
//                for (int i = 0; i < 3; i++) {
//                    for (int j = 0; j < 3; j++) {
//                        newBoard[i][j] = board[i][j];
//                    }
//                }
//
//                int temp = newBoard[zeroRow][zeroCol];
//                newBoard[zeroRow][zeroCol] = newBoard[newRow][newCol];
//                newBoard[newRow][newCol] = temp;
//
//                Node neighborNode = new Node(newBoard, this);
//                neighbors.add(neighborNode);
//            }
//        }
//
//        return neighbors;
//    }
//
//    // Check if the current state is the goal state
//    public boolean isGoal() {
//        int count = 1;
//        for (int i = 0; i < 3; i++) {
//            for (int j = 0; j < 3; j++) {
//                if (board[i][j] != count % 9) {
//                    return false;
//                }
//                count++;
//            }
//        }
//        return true;
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj) return true;
//        if (obj == null || getClass() != obj.getClass()) return false;
//        Node other = (Node) obj;
//        for (int i = 0; i < 3; i++) {
//            for (int j = 0; j < 3; j++) {
//                if (board[i][j] != other.board[i][j]) {
//                    return false;
//                }
//            }
//        }
//        return true;
//    }
//
//    @Override
//    public int hashCode() {
//        int result = 0;
//        int base = 31;
//        for (int i = 0; i < 3; i++) {
//            for (int j = 0; j < 3; j++) {
//                result = result * base + board[i][j];
//            }
//        }
//        return result;
//    }
//}
//
//public class BFS_8puzzle {
//
//    public static void main(String[] args) {
//        int[][] initialBoard = {
//                {1, 2, 3},
//                {4, 0, 5},
//                {6, 7, 8}
//        };
//
//        int[][] goalBoard = {
//                {1, 2, 3},
//                {4, 5, 6},
//                {7, 8, 0}
//        };
//
//        Node initialNode = new Node(initialBoard);
//        Node goalNode = new Node(goalBoard);
//
//        System.out.println("Initial State:");
//        printBoard(initialNode);
//
//        Solver solver = new Solver(initialNode, goalNode);
//        solver.solve();
//    }
//
//    static void printBoard(Node node) {
//        for (int i = 0; i < 3; i++) {
//            for (int j = 0; j < 3; j++) {
//                System.out.print(node.board[i][j] + " ");
//            }
//            System.out.println();
//        }
//        System.out.println();
//    }
//}
//
//class Solver {
//    private Node initialNode;
//    private Node goalNode;
//
//    public Solver(Node initialNode, Node goalNode) {
//        this.initialNode = initialNode;
//        this.goalNode = goalNode;
//    }
//
//    public void solve() {
//        Queue<Node> queue = new ArrayDeque<>();
//        Set<Node> visited = new HashSet<>();
//
//        queue.add(initialNode);
//        visited.add(initialNode);
//
//        System.out.println("Solving...");
//
//        while (!queue.isEmpty()) {
//            Node currentNode = queue.poll();
//
//            if (currentNode.equals(goalNode)) {
//                System.out.println("Solution Found!");
//                printMoves(currentNode);
//                return;
//            }
//
//            for (Node neighbor : currentNode.neighbors()) {
//                if (!visited.contains(neighbor)) {
//                    queue.add(neighbor);
//                    visited.add(neighbor);
//                }
//            }
//        }
//
//        System.out.println("No solution found.");
//    }
//
//    private void printMoves(Node goalNode) {
//        System.out.println("Moves:");
//
//        printMovesRecursive(goalNode);
//    }
//
//    private void printMovesRecursive(Node node) {
//        if (node == null) {
//            return;
//        }
//
//        printMovesRecursive(node.parent);
//        BFS_8puzzle.printBoard(node);
//    }
//}
