// import java.util.*;

// class Node {
//    int missionaries;
//    int cannibals;
//    int boat; // 1 for the original side, 0 for the other side
//    Node parent;

//    public Node(int missionaries, int cannibals, int boat, Node parent) {
//        this.missionaries = missionaries;
//        this.cannibals = cannibals;
//        this.boat = boat;
//        this.parent = parent;
//    }

//    // Check if the current state is a valid state
//    public boolean isValid() {
//        if (missionaries < 0 || cannibals < 0 || missionaries > 3 || cannibals > 3) {
//            return false;
//        }

//        // Check if missionaries are outnumbered by cannibals on either side
//        if ((missionaries < cannibals && missionaries > 0) || (missionaries > cannibals && missionaries < 3)) {
//            return false;
//        }

//        return true;
//    }

//    // Check if the current state is the goal state
//    public boolean isGoal() {
//        return missionaries == 0 && cannibals == 0 && boat == 0;
//    }

//    // Get possible next states from the current state
//    public List<Node> getNextStates() {
//        List<Node> nextStates = new ArrayList<>();

//        int newBoat = 1 - boat; // Flip the boat side

//        // Generate possible combinations of missionaries and cannibals to move
//        for (int m = 0; m <= 2; m++) {
//            for (int c = 0; c <= 2; c++) {
//                if (m + c >= 1 && m + c <= 2) {
//                    int newMissionaries = missionaries - m * boat + m * newBoat;
//                    int newCannibals = cannibals - c * boat + c * newBoat;

//                    Node nextState = new Node(newMissionaries, newCannibals, newBoat, this);

//                    if (nextState.isValid()) {
//                        nextStates.add(nextState);
//                    }
//                }
//            }
//        }

//        return nextStates;
//    }
// }

// public class mncBFS {
//     public static void main(String[] args) {
//         bfs();
//     }

//     public static void bfs() {
//         Queue<Node> queue = new LinkedList<>();
//         Set<Node> visited = new HashSet<>();

//         Node initialState = new Node(3, 3, 1, null);
//         queue.add(initialState);
//         visited.add(initialState);

//         while (!queue.isEmpty()) {
//             Node current = queue.poll();

//             if (current.isGoal()) {
//                 printSolution(current);
//                 return;
//             }

//             List<Node> nextStates = current.getNextStates();

//             for (Node nextState : nextStates) {
//                 if (!visited.contains(nextState)) {
//                     queue.add(nextState);
//                     visited.add(nextState);
//                 }
//             }
//         }

//         System.out.println("No solution found.");
//     }

//     public static void printSolution(Node goalNode) {
//         List<Node> path = new ArrayList<>();

//         while (goalNode != null) {
//             path.add(goalNode);
//             goalNode = goalNode.parent;
//         }

//         Collections.reverse(path);

//         for (Node node : path) {
//             System.out.println("(" + node.missionaries + ", " + node.cannibals + ", " + node.boat + ")");
//         }

//         System.out.println("Goal State Reached!");
//     }
// }