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
//        return (missionaries >= cannibals || missionaries == 0) && (missionaries <= cannibals || missionaries == 3);
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

//    // Override equals and hashCode methods to use in HashSet
//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj) return true;
//        if (obj == null || getClass() != obj.getClass()) return false;
//        Node node = (Node) obj;
//        return missionaries == node.missionaries && cannibals == node.cannibals && boat == node.boat;
//    }

//    @Override
//    public int hashCode() {
//        return Objects.hash(missionaries, cannibals, boat);
//    }
// }

// public class mncDFS {
//     public static void main(String[] args) {
//         dfs();
//     }

//     public static void dfs() {
//         Stack<Node> stack = new Stack<>();
//         Set<Node> visitedStates = new HashSet<>();

//         Node initialState = new Node(3, 3, 1, null);
//         stack.push(initialState);

//         while (!stack.isEmpty()) {
//             Node current = stack.pop();

//             // Print the current state when it is added to the stack
//             // System.out.println("(" + current.missionaries + ", " + current.cannibals + ", " + current.boat + ")");

//             if (current.isGoal()) {
//                 System.out.println("Goal state reached!");
//                 printSolution(current);
//                 return;
//             }

//             visitedStates.add(current);

//             List<Node> nextStates = current.getNextStates();
//             for (Node nextState : nextStates) {
//                 if (!visitedStates.contains(nextState)) {
//                     stack.push(nextState);
//                 }
//             }
//         }

//         System.out.println("No solution found.");
//     }

//     public static void printSolution(Node goalNode) {
//         List<Node> path = new ArrayList<>();

//         // Build the path from the initial state to the goal state
//         while (goalNode != null) {
//             path.add(goalNode);
//             goalNode = goalNode.parent;
//         }

//         // Print the states in the correct order
//         for (int i = path.size() - 1; i >= 0; i--) {
//             Node node = path.get(i);
//             System.out.println("(" + node.missionaries + ", " + node.cannibals + ", " + node.boat + ")");
//         }
//     }
// }