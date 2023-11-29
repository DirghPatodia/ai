import java.util.*;

// Node class to represent a state in the search space
class Node2 {
    int x;  // Water amount in the first jug
    int y;  // Water amount in the second jug
    int cost;  // Cost to reach this node
    int heuristic;  // Heuristic value (estimated cost to reach the goal)
    Node2 parent;  // Parent node

    public Node2(int x, int y, int cost, int heuristic, Node2 parent) {
        this.x = x;
        this.y = y;
        this.cost = cost;
        this.heuristic = heuristic;
        this.parent = parent;
    }
}

// Custom comparator to order nodes by their total cost (cost + heuristic)
class NodeComparator implements Comparator<Node2> {
    public int compare(Node2 n1, Node2 n2) {
        return (n1.cost + n1.heuristic) - (n2.cost + n2.heuristic);
    }
}

public class AStar_WaterJug {
    static boolean[][] visited = new boolean[5][5];  // Matrix to track visited states
    static int totalNode2s = 0;  // Count of total Nodes explored

    // Solve the water jug problem using A* algorithm
    static void solveAStar(int jug1, int jug2, int target) {
        PriorityQueue<Node2> pq = new PriorityQueue<>(new NodeComparator());

        // Add the initial state to the priority queue
        pq.add(new Node2(0, 0, 0, heuristic(0, 0, target), null));

        while (!pq.isEmpty()) {
            Node2 current = pq.poll();  // Get the Node2 with the lowest total cost
            int curX = current.x;
            int curY = current.y;

            // Check if the goal state is reached
            if (curX == target || curY == target) {
                printSolution(current);  // Print the solution path
                return;
            }

            // Skip visited Node2s
            if (visited[curX][curY]) {
                continue;
            }
            visited[curX][curY] = true;  // Mark current state as visited
            totalNode2s++;

            // Generate and enqueue new states by filling and emptying the jugs
            enqueueState(pq, curX, curY, jug1, jug2, target, current);
        }

        System.out.println("No solution");
    }

    // Heuristic function: Manhattan distance between (x, y) and the target
    static int heuristic(int x, int y, int target) {
        return Math.abs(x - target) + Math.abs(y - target);
    }

    // Enqueue new states after performing valid actions
    static void enqueueState(PriorityQueue<Node2> pq, int curX, int curY, int jug1, int jug2, int target, Node2 parent) {
        // Fill the jugs
        pq.add(new Node2(jug1, curY, parent.cost + 1, heuristic(jug1, curY, target), parent));
        pq.add(new Node2(curX, jug2, parent.cost + 1, heuristic(curX, jug2, target), parent));

        // Empty the jugs
        pq.add(new Node2(0, curY, parent.cost + 1, heuristic(0, curY, target), parent));
        pq.add(new Node2(curX, 0, parent.cost + 1, heuristic(curX, 0, target), parent));

        // Pour water from one jug to another
        int pourXToY = Math.min(curX + curY, jug2);
        int pourYToX = Math.min(curX + curY, jug1);
        pq.add(new Node2(curX - (pourXToY - curY), pourXToY, parent.cost + 1, heuristic(curX - (pourXToY - curY), pourXToY, target), parent));
        pq.add(new Node2(pourYToX, curY - (pourYToX - curX), parent.cost + 1, heuristic(pourYToX, curY - (pourYToX - curX), target), parent));
    }

    // Print the solution path
    static void printSolution(Node2 Node2) {
        Stack<Node2> path = new Stack<>();
        while (Node2 != null) {
            path.push(Node2);
            Node2 = Node2.parent;
        }

        while (!path.isEmpty()) {
            Node2 n = path.pop();
            System.out.println("(" + n.x + ", " + n.y + ")");
        }
    }

    public static void main(String[] args) {
        solveAStar(4, 3, 2);  // Solve the water jug problem using A*
        System.out.println("Total Node2s visited: " + totalNode2s);  // Print the total visited Nodes
    }
}