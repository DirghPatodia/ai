import java.util.*;

class Node {
    int x, y;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

public class waterjug {

    public static int total = 0;

    void solveBFS() {
        int[][] m = new int[5][5];  // Matrix to mark visited states
        for (int[] i : m) {
            Arrays.fill(i, -1);
        }

        boolean isSolveable = false; // Flag to track if a solution is found

        Vector<Node> result = new Vector<>();
        Queue<Node> q = new LinkedList<>();

        // Add the starting position to the queue
        q.add(new Node(0, 0));

        while (!q.isEmpty()) {
            Node temp = q.poll(); // Get the next node from the queue

            // Skip if out of capacity
            if (temp.x > 4 || temp.x < 0 || temp.y > 3 || temp.y < 0) continue;
            total++; // Count total explored nodes

            // Skip if already visited node
            if (m[temp.x][temp.y] > -1) continue;

            // Add to the result
            result.add(new Node(temp.x, temp.y));
            m[temp.x][temp.y] = 1; // Mark as a visited node

            // Check if target state is reached
            if (temp.x == 2 || temp.y == 2) {
                isSolveable = true;

                // Handle final state and print intermediate steps
                if (temp.x == 2 && temp.y != 0) {
                    System.out.println("(" + temp.x + ", 0)");
                    result.add(new Node(temp.x, 0));

                } else if (temp.y == 2 && temp.x != 0) {
                    System.out.println("(0, " + temp.y + ")");
                    result.add(new Node(0, temp.y));
                }

                // Print intermediate steps
                for (Node node : result) {
                    System.out.println("(" + node.x + ", " + node.y + ")");
                }
                break;
            }

            // Fill the second jug
            q.add(new Node(temp.x, 3));

            // Fill the first jug
            q.add(new Node(4, temp.y));

            for (int j = 0; j <= Math.max(4, 3); j++) {
                int state1 = temp.x + j;
                int state2 = temp.y - j;

                // Add valid states to the queue
                if (state1 == 4 || (state2 == 0)) q.add(new Node(state1, state2));

                state1 = temp.x - j;
                state2 = temp.y + j;

                // Add valid states to the queue
                if ((state1 == 0) || state2 == 3) q.add(new Node(state1, state2));
            }

            // Empty the second jug
            q.add(new Node(4, 0));

            // Empty the first jug
            q.add(new Node(0, 3));
        }

        // Print if no solution is found
        if (!isSolveable) System.out.println("No solution");
    }

    public static void main(String[] args) {
        waterjug wj = new waterjug();
        wj.solveBFS(); // Solve the water jug problem for the given capacities and target
        System.out.println("Total nodes explored: " + (total + 1)); // Print the total explored nodes
    }
}
