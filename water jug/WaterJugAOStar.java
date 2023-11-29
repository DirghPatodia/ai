import java.util.*;

class AOStarState {
    int jug1;
    int jug2;

    public AOStarState(int jug1, int jug2) {
        this.jug1 = jug1;
        this.jug2 = jug2;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AOStarState that = (AOStarState) obj;
        return jug1 == that.jug1 && jug2 == that.jug2;
    }

    @Override
    public int hashCode() {
        return 31 * jug1 + jug2;
    }
}

public class WaterJugAOStar {

    public static void main(String[] args) {
        int jug1Capacity = 4;
        int jug2Capacity = 3;
        int targetAmount = 2;

        solveWaterJug(jug1Capacity, jug2Capacity, targetAmount);
    }

    private static void solveWaterJug(int jug1Capacity, int jug2Capacity, int targetAmount) {
        Queue<AOStarState> queue = new ArrayDeque<>();
        Set<AOStarState> visited = new HashSet<>();

        AOStarState initialState = new AOStarState(0, 0);
        queue.offer(initialState);
        visited.add(initialState);

        while (!queue.isEmpty()) {
            AOStarState currentState = queue.poll();
            System.out.println("Visited State: " + currentState.jug1 + " " + currentState.jug2);

            if (currentState.jug1 == targetAmount || currentState.jug2 == targetAmount) {
                System.out.println("Solution found: " + currentState.jug1 + " " + currentState.jug2);
                return;
            }

            // Fill jug1
            AOStarState fillJug1 = new AOStarState(jug1Capacity, currentState.jug2);
            addToQueue(fillJug1, queue, visited);

            // Fill jug2
            AOStarState fillJug2 = new AOStarState(currentState.jug1, jug2Capacity);
            addToQueue(fillJug2, queue, visited);

            // Pour water from jug1 to jug2
            AOStarState pourJug1ToJug2 = new AOStarState(
                    Math.max(0, currentState.jug1 - (jug2Capacity - currentState.jug2)),
                    Math.min(jug2Capacity, currentState.jug2 + currentState.jug1)
            );
            addToQueue(pourJug1ToJug2, queue, visited);

            // Pour water from jug2 to jug1
            AOStarState pourJug2ToJug1 = new AOStarState(
                    Math.min(jug1Capacity, currentState.jug1 + currentState.jug2),
                    Math.max(0, currentState.jug2 - (jug1Capacity - currentState.jug1))
            );
            addToQueue(pourJug2ToJug1, queue, visited);

            // Empty jug1
            AOStarState emptyJug1 = new AOStarState(0, currentState.jug2);
            addToQueue(emptyJug1, queue, visited);

            // Empty jug2
            AOStarState emptyJug2 = new AOStarState(currentState.jug1, 0);
            addToQueue(emptyJug2, queue, visited);
        }

        System.out.println("No solution found.");
    }

    private static void addToQueue(AOStarState state, Queue<AOStarState> queue, Set<AOStarState> visited) {
        if (!visited.contains(state)) {
            queue.offer(state);
            visited.add(state);
        }
    }
}