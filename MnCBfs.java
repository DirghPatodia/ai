import java.util.*;

class state{
    int missionariesOnLeft;
    int cannibalsOnLeft;
    boolean boatOnLeft;

    // Constructor to initialize a state
    public state(int missionariesOnLeft, int cannibalsOnLeft, boolean boatOnLeft) {
        this.missionariesOnLeft = missionariesOnLeft;
        this.cannibalsOnLeft = cannibalsOnLeft;
        this.boatOnLeft = boatOnLeft;
    }

    // Check if the state is valid based on given conditions
    public boolean isValid() {
        // Conditions for invalid state
        if (missionariesOnLeft < 0 || cannibalsOnLeft < 0 || missionariesOnLeft > 3 || cannibalsOnLeft > 3)
            return false;
        if (missionariesOnLeft > 0 && missionariesOnLeft < cannibalsOnLeft)
            return false;
        int missionariesOnRight = 3 - missionariesOnLeft;
        int cannibalsOnRight = 3 - cannibalsOnLeft;
        if (missionariesOnRight > 0 && missionariesOnRight < cannibalsOnRight)
            return false;
        return true;
    }

    // Check if the current state is the goal state
    public boolean isGoal() {
        return missionariesOnLeft == 0 && cannibalsOnLeft == 0 && !boatOnLeft;
    }

    // Generate all possible successor states from the current state
    public List<state> generateSuccessors() {
        List<state> successors = new ArrayList<>();
        int m = boatOnLeft ? -1 : 1;
        int c = boatOnLeft ? -1 : 1;

        // Loop through all possible combinations of missionaries and cannibals
        for (int i = 0; i <= 2; i++) {
            for (int j = 0; j <= 2; j++) {
                if (i + j >= 1 && i + j <= 2) {
                    state newState = new state(missionariesOnLeft + m * i, cannibalsOnLeft + c * j, !boatOnLeft);
                    if (newState.isValid()) {
                        successors.add(newState);
                    }
                }
            }
        }

        return successors;
    }

    // Hashcode function for unique identification of states in a hash-based collection
    @Override
    public int hashCode() {
        return Objects.hash(missionariesOnLeft, cannibalsOnLeft, boatOnLeft);
    }

    // Equality check for states
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        state state = (state) obj;
        return missionariesOnLeft == state.missionariesOnLeft &&
                cannibalsOnLeft == state.cannibalsOnLeft &&
                boatOnLeft == state.boatOnLeft;
    }
}

public class MnCBfs {
    public static void main(String[] args) {
        state initialState = new state(3, 3, true);
        state goalState = new state(0, 0, false);

        // Initialize BFS structures
        Queue<state> queue = new LinkedList<>();
        Set<state> visited = new HashSet<>();
        Map<state, state> parentMap = new HashMap<>();

        queue.offer(initialState);
        visited.add(initialState);
        boolean found = false;

        // Main BFS loop
        while (!queue.isEmpty()) {
            state currentState = queue.poll();
            if (currentState.isGoal()) {
                found = true;
                break;
            }

            // Expand current state
            for (state successor : currentState.generateSuccessors()) {
                if (!visited.contains(successor)) {
                    visited.add(successor);
                    parentMap.put(successor, currentState);
                    queue.offer(successor);
                }
            }
        }

        // If a solution is found, reconstruct the path from the goal state
        if (found) {
            List<state> path = new ArrayList<>();
            state currentState = goalState;
            while (currentState != null) {
                path.add(currentState);
                currentState = parentMap.get(currentState);
            }
            Collections.reverse(path);

            // Display the solution
            for (state state : path) {
                System.out.println(state.missionariesOnLeft + "M " + state.cannibalsOnLeft + "C " +
                        (state.boatOnLeft ? "L -> R" : "R -> L"));
            }
        } else {
            System.out.println("No solution found.");
        }
    }
}
