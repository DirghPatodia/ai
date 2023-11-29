import java.util.*;

public class MagicSquareBFS {
    static class State {
        int[][] square;

        State(int[][] square) {
            this.square = square;
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
            for (int i = 0; i < n; i++) {
                colSum += square[i][j];
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

    private static void generateAndAddNextStates(int[][] currentSquare, Queue<State> queue) {
        int n = currentSquare.length;

        // For demonstration purposes, let's just swap two elements
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = i; k < n; k++) {
                    for (int l = (k == i) ? j + 1 : 0; l < n; l++) {
                        int[][] nextSquare = new int[n][];
                        for (int m = 0; m < n; m++) {
                            nextSquare[m] = Arrays.copyOf(currentSquare[m], currentSquare[m].length);
                        }

                        // Swap elements
                        int temp = nextSquare[i][j];
                        nextSquare[i][j] = nextSquare[k][l];
                        nextSquare[k][l] = temp;

                        if (!queue.contains(new State(nextSquare))) {
                            queue.add(new State(nextSquare));
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        int[][] initialSquare = {{2, 7, 6}, {9, 5, 1}, {4, 3, 8}}; // Initial state

        Queue<State> queue = new LinkedList<>();
        queue.add(new State(initialSquare));

        while (!queue.isEmpty()) {
            int levelSize = queue.size(); // Determine the size of the current level

            for (int i = 0; i < levelSize; i++) {
                State currentState = queue.poll();
                int[][] currentSquare = currentState.square;

                if (isMagicSquare(currentSquare)) {
                    System.out.println("Magic Square Found:");
                    printSquare(currentSquare);
                    return; // Terminate the search once a solution is found
                }

                generateAndAddNextStates(currentSquare, queue);
            }
        }

        System.out.println("No magic square found.");
    }
}