import java.util.*;

class State {
    int a;
    int b;

    State(int a, int b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        State other = (State) obj;
        return this.a == other.a && this.b == other.b;
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b);
    }
}

public class BestFirstSearch {
    static int aCapacity;
    static int bCapacity;
    static int targetAmount;

    static boolean isGoalState(State state) {
        return state.a == targetAmount || state.b == targetAmount;
    }

    static List<State> getSuccessors(State currentState) {
        List<State> successors = new ArrayList<>();

        // Fill Jug A
        successors.add(new State(aCapacity, currentState.b));

        // Fill Jug B
        successors.add(new State(currentState.a, bCapacity));

        // Empty Jug A
        successors.add(new State(0, currentState.b));

        // Empty Jug B
        successors.add(new State(currentState.a, 0));

        // Pour from A to B
        int pourAmount = Math.min(currentState.a, bCapacity - currentState.b);
        successors.add(new State(currentState.a - pourAmount, currentState.b + pourAmount));

        // Pour from B to A
        pourAmount = Math.min(aCapacity - currentState.a, currentState.b);
        successors.add(new State(currentState.a + pourAmount, currentState.b - pourAmount));

        successors.remove(currentState); // Remove the current state from successors
        return successors;
    }

    static int heuristic(State state) {
        // You can define your heuristic here. For simplicity, we'll use the absolute difference from the target.
        return Math.abs(state.a - targetAmount) + Math.abs(state.b - targetAmount);
    }

    public static void main(String[] args) {
        aCapacity = 4; // Jug A capacity
        bCapacity = 3; // Jug B capacity
        targetAmount = 2; // Target amount of water

        PriorityQueue<State> queue = new PriorityQueue<>(Comparator.comparingInt(BestFirstSearch::heuristic));
        Set<State> visited = new HashSet<>();

        State initialState = new State(0, 0);
        queue.add(initialState);

        while (!queue.isEmpty()) {
            State currentState = queue.poll();
            System.out.println("Visiting state: (" + currentState.a + ", " + currentState.b + ")");

            if (isGoalState(currentState)) {
                System.out.println("Goal state reached: (" + currentState.a + ", " + currentState.b + ")");
                break;
            }

            visited.add(currentState);
            List<State> successors = getSuccessors(currentState);
            for (State successor : successors) {
                if (!visited.contains(successor)) {
                    queue.add(successor);
                }
            }
        }
    }
}
