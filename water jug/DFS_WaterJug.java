import java.util.*;

//class Node {
//    int x, y;
//
//    public Node(int x, int y) {
//        this.x = x;
//        this.y = y;
//    }
//}

public class DFS_WaterJug {
    public static int total = 0;

    static Stack<Node> result = new Stack<>();
    static boolean[][] m = new boolean[5][5];

    // Solve the water jug problem using Depth-First Search (DFS)
    static boolean solveDFS(int curj1, int curj2, int jug1, int jug2, int tx, int ty) {

        // Got to the final state
        if (curj1 == tx && curj2 == ty) {
            result.add(new Node(curj1, curj2));
            return true;
        }

        total++; // Count the total number of visited nodes

        // If the node is already visited
        if (m[curj1][curj2]) {
            return false;
        }
        // Mark the node as visited
        m[curj1][curj2] = true;

        // Fill the 1st jug completely
        if (curj1 < jug1 && solveDFS(jug1, curj2, jug1, jug2, tx, ty)) {
            result.add(new Node(curj1, curj2));
            return true;
        }

        // Fill the 2nd jug completely
        if (curj2 < jug2 && solveDFS(curj1, jug2, jug1, jug2, tx, ty)) {
            result.add(new Node(curj1, curj2));
            return true;
        }

        // Empty the first jug
        if (curj1 > 0 && solveDFS(0, curj2, jug1, jug2, tx, ty)) {
            result.add(new Node(curj1, curj2));
            return true;
        }

        // Empty the second jug
        if (curj2 > 0 && solveDFS(curj1, 0, jug1, jug2, tx, ty)) {
            result.add(new Node(curj1, curj2));
            return true;
        }

        // Pour water from 1st jug to 2nd jug
        if (curj1 > 0 && curj2 < jug2) {
            boolean temp;
            if (curj2 + curj1 <= jug2) {
                temp = solveDFS(0, curj1 + curj2, jug1, jug2, tx, ty);
            } else {
                temp = solveDFS(curj1 - (jug2 - curj2), jug2, jug1, jug2, tx, ty);
            }
            if (temp) {
                result.add(new Node(curj1, curj2));
                return true;
            }
        }

        // Pour water from 2nd jug to 1st jug
        if (curj2 > 0 && curj1 < jug1 && solveDFS(Math.min(curj1 + curj2, jug1), curj2 - Math.min(jug1, curj2), jug1, jug2, tx, ty)) {
            boolean temp;

            if (curj2 + curj1 <= jug1) {
                temp = solveDFS(curj1 + curj2, 0, jug1, jug2, tx, ty);
            } else {
                temp = solveDFS(jug1, curj2 - (jug1 - curj2), jug1, jug2, tx, ty);
            }
            if (temp) {
                result.add(new Node(curj1, curj2));
                return true;
            }
        }
        m[curj1][curj2] = false;
        return false;
    }

    // Function to print the stack in reverse order
    public static void printReverseStack(Stack<Node> stack) {
        // Base case
        if (stack.isEmpty())
            return;

        // Create a temporary stack
        Stack<Node> temporaryStack = new Stack<>();

        // Copy all the elements from the given stack to the temporary stack
        while (stack.size() > 0) {
            temporaryStack.push(stack.pop());
        }

        // Print the elements of the temporary stack
        for (Node n : temporaryStack) {
            System.out.println("(" + n.x + "," + n.y + ")");
        }
    }

    public static void main(String[] args) {
        solveDFS(0, 0, 4, 3, 2, 0);
        printReverseStack(result); // Print the steps in reverse order
        System.out.println("Total Nodes Visited: " + total); // Print the total visited nodes
    }
}