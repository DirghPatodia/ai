import java.util.Arrays;

public class MagicSquareDFS {

    private static boolean isMagicSquare(int[][] square) {
        int n = square.length;
        int sum = 0;

        // Calculate the sum of the first row
        for (int j = 0; j < n; j++)
            sum += square[0][j];

        // Check the sum of each row
        for (int i = 1; i < n; i++) {
            int rowSum = 0;
            for (int j = 0; j < n; j++)
                rowSum += square[i][j];
            if (rowSum != sum) return false;
        }

        // Check the sum of each column
        for (int j = 0; j < n; j++) {
            int colSum = 0;
            for (int[] ints : square)
                colSum += ints[j];
            if (colSum != sum) return false;
        }

        // Check the sum of the main diagonal
        int mainDiagonalSum = 0;
        for (int i = 0; i < n; i++)
            mainDiagonalSum += square[i][i];
        if (mainDiagonalSum != sum) return false;

        // Check the sum of the secondary diagonal
        int secondaryDiagonalSum = 0;
        for (int i = 0; i < n; i++)
            secondaryDiagonalSum += square[i][n - 1 - i];
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

    private static boolean dfs(int[][] currentSquare, int depth) {
        if (depth == 0) {
            if (isMagicSquare(currentSquare)) {
                System.out.println("Magic Square Found:");
                printSquare(currentSquare);
                return true;
            }
            return false;
        }

        int n = currentSquare.length;

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

                        if (dfs(nextSquare, depth - 1)) return true;
                    }
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int[][] initialSquare = {{2, 7, 6}, {9, 5, 1}, {4, 3, 8}}; // Initial state
        int depth = 5; // Set the depth of the search
        if (!dfs(initialSquare, depth)) System.out.println("No magic square found.");
    }
}