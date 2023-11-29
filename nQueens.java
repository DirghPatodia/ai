import java.util.Scanner;

public class nQueens {
    int N;

    public nQueens(int n) {
        N = n;
    }

    // A utility function to print solution
    void printSolution(int board[][]) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] == 1)
                    System.out.print("Q ");
                else
                    System.out.print(". ");
            }
            System.out.println();
        }
    }

    // A utility function to check if a queen can be placed on board[row][col].
    boolean isSafe(int board[][], int row, int col) {
        int i, j;

        // Check this row on the left side
        for (i = 0; i < col; i++)
            if (board[row][i] == 1)
                return false;

        // Check upper diagonal on the left side
        for (i = row, j = col; i >= 0 && j >= 0; i--, j--)
            if (board[i][j] == 1)
                return false;

        // Check lower diagonal on the left side
        for (i = row, j = col; j >= 0 && i < N; i++, j--)
            if (board[i][j] == 1)
                return false;

        return true;
    }

    // A recursive utility function to solve N Queen problem
    boolean solveNQUtil(int board[][], int col) {
        // Base case: If all queens are placed, return true
        if (col >= N)
            return true;

        // Consider this column and try placing this queen in all rows one by one
        for (int i = 0; i < N; i++) {
            // Check if the queen can be placed on board[i][col]
            if (isSafe(board, i, col)) {
                // Place this queen in board[i][col]
                board[i][col] = 1;

                // Recur to place the rest of the queens
                if (solveNQUtil(board, col + 1))
                    return true;

                // If placing the queen in board[i][col] doesn't lead to a solution, remove queen from board[i][col]
                board[i][col] = 0; // BACKTRACK
            }
        }

        // If the queen cannot be placed in any row in this column col, return false
        return false;
    }
    boolean solveNQ() {
        int board[][] = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                board[i][j] = 0;
            }
        }

        if (!solveNQUtil(board, 0)) {
            System.out.print("Solution does not exist");
            return false;
        }

        printSolution(board);
        return true;
    }

    public static void main(String args[]) {
        // Change 'n' to the desired number of queens
        Scanner sc= new Scanner(System.in);
        System.out.println("Enter the number of queens");
        int n=sc.nextInt();
        nQueens queen = new nQueens(n);
        queen.solveNQ();
    }
}



