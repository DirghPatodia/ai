import java.util.*;

public class MagicSquareAstar {

    static class State {
        int[][] square;
        int cost;

        State(int[][] square, int cost) {
            this.square = square;
            this.cost = cost;
        }

        @Override
        public int hashCode() {
            return Arrays.deepHashCode(square);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            State other = (State) obj;
            return Arrays.deepEquals(square, other.square);
        }
    }

    private static boolean isMagicSquare(int[][] square) {
        int n = square.length;
        int sum = 15; // Magic constant for a 3x3 magic square

        // Check the sum of each row
        for (int[] ints : square) {
            int rowSum = 0;
            for (int j = 0; j < n; j++) {
                rowSum += ints[j];
            }
            if (rowSum != sum) {
                return false;
            }
        }

        // Check the sum of each column
        for (int j = 0; j < n; j++) {
            int colSum = 0;
            for (int[] ints : square) {
                colSum += ints[j];
            }
            if (colSum != sum) {
                return false;
            }
        }

        // Check the sum of the main diagonal
        int mainDiagonalSum = square[0][0] + square[1][1] + square[2][2];
        if (mainDiagonalSum != sum) {
            return false;
        }

        // Check the sum of the secondary diagonal
        int secondaryDiagonalSum = square[0][2] + square[1][1] + square[2][0];
        return secondaryDiagonalSum == sum;
    }

    private static void printSquare(int[][] square) {
        for (int[] ints : square) {
            for (int anInt : ints) {
                System.out.print(anInt + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static int calculateHeuristic(int[][] square) {
        int n = square.length;
        int targetSum = 15; // Magic constant for a 3x3 magic square
        int sum = 0;

        // Calculate the sum of absolute differences from the target sum
        for (int[] ints : square) {
            for (int j = 0; j < n; j++) {
                sum += Math.abs(targetSum - ints[j]);
            }
        }

        return sum;
    }

    private static PriorityQueue<State> initializePriorityQueue() {
        return new PriorityQueue<>(Comparator.comparingInt(state -> state.cost + calculateHeuristic(state.square)));
    }

    private static void generateAndAddNextStates(State currentState, PriorityQueue<State> queue) {
        int n = currentState.square.length;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = i; k < n; k++) {
                    for (int l = (k == i) ? j + 1 : 0; l < n; l++) {
                        int[][] nextSquare = new int[n][];
                        for (int m = 0; m < n; m++) {
                            nextSquare[m] = Arrays.copyOf(currentState.square[m], currentState.square[m].length);
                        }

                        // Swap elements
                        int temp = nextSquare[i][j];
                        nextSquare[i][j] = nextSquare[k][l];
                        nextSquare[k][l] = temp;

                        int nextCost = currentState.cost + 1; // Cost is the number of moves
                        queue.add(new State(nextSquare, nextCost));
                    }
                }
            }
        }
    }

    private static State aStar(int[][] initialSquare) {
        PriorityQueue<State> queue = initializePriorityQueue();
        Set<State> visited = new HashSet<>();

        queue.add(new State(initialSquare, 0));

        while (!queue.isEmpty()) {
            State currentState = queue.poll();

            if (isMagicSquare(currentState.square)) {
                System.out.println("Magic Square Found:");
                printSquare(currentState.square);
                return currentState;
            }

            if (!visited.contains(currentState)) {
                visited.add(currentState);
                generateAndAddNextStates(currentState, queue);
            }
        }

        return null;
    }

    public static void main(String[] args) {
        int[][] initialSquare = {{2, 7, 6}, {9, 5, 1}, {4, 3, 8}}; // Initial state

        State result = aStar(initialSquare);

        if (result == null) {
            System.out.println("No magic square found.");
        }
    }
}