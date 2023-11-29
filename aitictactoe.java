import java.util.Scanner;

public class aitictactoe {
    char[][] board;
    char player;
    char aiPlayer;
    char currPlayer;

    public aitictactoe(char player) {
        board = new char[3][3];
        this.player = player;
        this.aiPlayer = (player == 'x') ? 'o' : 'x';
        this.currPlayer = player;
        initializeBoard();
    }

    void initializeBoard() {
        for(int i=0; i<3; i++ ){
            for(int j=0; j<3; j++ ){
                board[i][j] = '-';
            }
        }
    }

    void playGame() {
        boolean gameOver = false;
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to Tic-Tac-Toe!");
        printBoard();

        while(!gameOver) {
            if(currPlayer == player) {
                System.out.println("Player " + currPlayer + ", enter your move (row [1-3] column [1-3]) : ");
                int row = sc.nextInt() - 1;
                int col = sc.nextInt() - 1;
                if(isValidMove(row, col)) {
                    makeMove(row, col, currPlayer);
                    printBoard();
                    if(checkWin(currPlayer)) {
                        System.out.println("Player " + currPlayer + " wins!");
                        gameOver = true;
                    } else if(boardFull()) {
                        System.out.println("It's a draw!");
                        gameOver = true;
                    } else {
                        currPlayer = aiPlayer;
                    }
                } else {
                    System.out.println("Invalid move! Try again.");
                }
            }
            else {
                System.out.println("AI player's turn!");
                int[] move = getBestMove();
                makeMove(move[0], move[1], aiPlayer);
                printBoard();
                if(checkWin(aiPlayer)) {
                    System.out.println("AI Player wins!");
                    gameOver = true;
                } else if(boardFull()) {
                    System.out.println("It's a draw!");
                    gameOver = true;
                } else {
                    currPlayer = player;
                }
            }
        }
        System.out.println("Game Over!");
    }

    private int[] getBestMove() {
        int[] bestMove = new int[]{-1, -1};
        int bestScore = Integer.MIN_VALUE;
        for(int i=0; i<3; i++) {
            for(int j=0; j<3; j++) {
                if(board[i][j] == '-') {
                    board[i][j] = aiPlayer;
                    int score = minimax(0, false);
                    board[i][j] = '-';
                    if(score > bestScore) {
                        bestScore = score;
                        bestMove[0] = i;
                        bestMove[1] = j;
                    }
                }
            }
        }
        return bestMove;
    }

    private int minimax(int depth, boolean isMaxPlayer) {
        char opponent = (aiPlayer == 'x') ? 'o' : 'x';
        if(checkWin(aiPlayer)) {
            return 1;
        } else if(checkWin(opponent)) {
            return -1;
        } else if(boardFull()) {
            return 0;
        }

        if(isMaxPlayer) {
            int bestScore = Integer.MIN_VALUE;
            for(int i=0; i<3; i++) {
                for (int j=0; j<3; j++) {
                    if(board[i][j] == '-') {
                        board[i][j] = aiPlayer;
                        int score = minimax(depth + 1, false);
                        board[i][j] = '-';
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for(int i=0; i<3; i++) {
                for (int j=0; j<3; j++) {
                    if(board[i][j] == '-') {
                        board[i][j] = opponent;
                        int score = minimax(depth + 1, true);
                        board[i][j] = '-';
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
            return bestScore;
        }
    }

    private void makeMove(int row, int col, char player) {
        board[row][col] = player;
    }

    boolean boardFull() {
        for(int i=0; i<3; i++) {
            for(int j=0; j<3; j++) {
                if(board[i][j] == '-') return false;
            }
        }
        return true;
    }

    boolean checkWin(char player) {
        for(int i=0; i<3; i++) {
            if(board[i][0] == player && board[i][1] == player && board[i][2] == player) return true;
            if(board[0][i] == player && board[1][i] == player && board[2][i] == player) return true;
        }
        if(board[0][0] == player && board[1][1] == player && board[2][2] == player) return true;
        if(board[0][2] == player && board[1][1] == player && board[2][0] == player) return true;
        return false;
    }

    boolean isValidMove(int row, int col) {
        if (row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == '-') return true;
        return false;
    }

    void printBoard() {
        for(int i=0; i<3; i++) {
            for(int j=0; j<3; j++) {
                System.out.print(board[i][j] + "  ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        char player = 'x';
        aitictactoe b = new aitictactoe(player);
        b.playGame();
    }
}