import java.util.*;

public class MagicSquareBestFS {

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
        int sum = 0;

        // Calculate the sum of the first row
        for (int j = 0; j < n; j++) {
            sum += square[0][j];
        }

        // Check the sum of each row
        for (int i = 1; i < n; i++) {
            int rowSum = 0;
            for (int j = 0; j < n; j++) {
                rowSum += square[i][j];
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
        int mainDiagonalSum = 0;
        for (int i = 0; i < n; i++) {
            mainDiagonalSum += square[i][i];
        }
        if (mainDiagonalSum != sum) {
            return false;
        }

        // Check the sum of the secondary diagonal
        int secondaryDiagonalSum = 0;
        for (int i = 0; i < n; i++) {
            secondaryDiagonalSum += square[i][n - 1 - i];
        }

        return secondaryDiagonalSum == sum;
    }

    private static void printSquare(int[][] square) {
        for (int i = 0; i < square.length; i++) {
            for (int j = 0; j < square[i].length; j++) {
                System.out.print(square[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static int calculateHeuristic(int[][] square) {
        // Simple heuristic: Count the number of misplaced elements
        int n = square.length;
        int misplacedCount = 0;
        int target = 1;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (square[i][j] != target) {
                    misplacedCount++;
                }
                target = (target % (n * n)) + 1;
            }
        }

        return misplacedCount;
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

    private static State bestFirstSearch(int[][] initialSquare) {
        PriorityQueue<State> queue = initializePriorityQueue();
        Set<State> visited = new HashSet<>();

        queue.add(new State(initialSquare, 0));
        State result = null;

        while (!queue.isEmpty()) {
            State currentState = queue.poll();

            if (isMagicSquare(currentState.square)) {
                System.out.println("Magic Square Found:");
                printSquare(currentState.square);
                result = currentState;
                break;
            }

            if (!visited.contains(currentState)) {
                visited.add(currentState);
                generateAndAddNextStates(currentState, queue);
            }
        }

        return result;
    }

    public static void main(String[] args) {
        int[][] initialSquare = {{2, 5, 6}, {9, 7, 1}, {3, 4, 8}}; // Initial state

        State result = bestFirstSearch(initialSquare);

        if (result == null) {
            System.out.println("No magic square found.");
        }
    }
}
