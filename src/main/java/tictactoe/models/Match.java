package tictactoe.models;

import tictactoe.enums.Symbol;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

public class Match implements Serializable {

    private static final long serialVersionUID = 5024562836011110440L;

    private Symbol[][] board = new Symbol[3][3];
    private Player xPlayer;
    private Player oPlayer;
    private int turn;


    public Match(Player xPlayer, Player oPlayer) {
        this.xPlayer = xPlayer;
        this.oPlayer = oPlayer;

        this.turn = new Random().nextInt(3) + 1;
    }


    @Override
    public String toString() {
        showboard();
        return String.format("Player X: %s\nPlayer Y: %s\nTurn: %s", xPlayer.getName(), oPlayer.getName(),
                (turn == xPlayer.getId() ? xPlayer.getName() : oPlayer.getName()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((xPlayer == null) ? 0 : xPlayer.hashCode());
        result = prime * result + ((oPlayer == null) ? 0 : oPlayer.hashCode());
        result = prime * result + Arrays.deepHashCode(board);
        result = prime * result + turn;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Match)) {
            return false;
        }
        Match other = (Match) obj;
        return Arrays.deepEquals(board, other.board) && xPlayer.equals(other.xPlayer) && oPlayer.equals(other.oPlayer)
                && turn == other.turn;
    }


    public int changeTurn() {
        return turn = (turn == 1) ? 2 : 1;
    }

    public void showboard() {
        boolean newLine;
        for (Symbol[] cell : board) {
            newLine = true;
            for (int j = 0; j < board[0].length; j++) {
                if (newLine) {
                    System.out.print("\n| ");
                    newLine = false;
                }
                System.out.print(cell[j] + " | ");
            }
        }
        System.out.println("\n");
    }

    public int analyzeMove() {
        int winner = -1; // The game hasn't ended
        if (board[0][0] == board[1][1] && board[0][0] == board[2][2]) { // Inclinada '\' (Diagonal principal)
            winner = cellNotNull(board[0][0]);
        } else if (board[2][0] == board[1][1] && board[2][0] == board[0][2]) { // Inclinada '/' (Diagonal secund�ria)
            winner = cellNotNull(board[2][0]);
        } else if (board[0][0] == board[1][0] && board[0][0] == board[2][0]) { // Em p� '|' (1� coluna)
            winner = cellNotNull(board[0][0]);
        } else if (board[0][1] == board[1][1] && board[0][1] == board[2][1]) { // Em p� '|' (2� coluna)
            winner = cellNotNull(board[0][1]);
        } else if (board[0][2] == board[1][2] && board[0][2] == board[2][2]) { // Em p� '|' (3� coluna)
            winner = cellNotNull(board[0][2]);
        } else if (board[0][0] == board[0][1] && board[0][0] == board[0][2]) { // Deitada '_' (1� linha)
            winner = cellNotNull(board[0][0]);
        } else if (board[1][0] == board[1][1] && board[1][0] == board[1][2]) { // Deitada '_' (2� linha)
            winner = cellNotNull(board[1][0]);
        } else if (board[2][0] == board[2][1] && board[2][0] == board[2][2]) { // Deitada '_' (3� linha)
            winner = cellNotNull(board[2][0]);
        } else { // No winner
            if (!freeCell()) {// Deu velha
                winner = 0;
            }
        }
        return winner;
    }

    private boolean freeCell() {
        for (Symbol[] cell : board) {
            for (int j = 0; j < board[0].length; j++) {
                if (cell[j] == null) {
                    return true;
                }
            }
        }
        return false;
    }

    private int cellNotNull(Symbol cell) {
        int winner = -1;
        if (cell != null) {
            winner = (cell == Symbol.X) ? 1 : 2;
            if (winner == 1) {
                xPlayer.setScore(xPlayer.getScore() + 1);
            } else {
                oPlayer.setScore(oPlayer.getScore() + 1);
            }
        }
        return winner;
    }


    // Getters e setters

    public int getTurn() {
        return turn;
    }

    public Symbol[][] getBoard() {
        return board;
    }

    public void setBoard(Symbol[][] board) {
        this.board = board;
    }

    public Symbol getBoardCell(int row, int column) {
        return board[row][column];
    }

    public void setBoardCell(int row, int column, Symbol symbol) {
        board[row][column] = symbol;
    }

    public Player getXPlayer() {
        return xPlayer;
    }

    public Player getOPlayer() {
        return oPlayer;
    }

}
