package ticTacToe.models;

import ticTacToe.exception.GameOverException;

import java.util.Scanner;

public class Player {
    private int id;
    private String name;
    private char symbol;
    private PlayerType playerType;

    public Player() {
    }

    public Player(int id, String name, char symbol, PlayerType playerType) {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
        this.playerType = playerType;
    }

    public Move makeMove(Board board) throws GameOverException {
        Scanner sc = new Scanner(System.in);
        int row;
        int col;
        //TODO: validation for the move, check row and column, and cell status
        while (true) {
            try {
                while (true) {
                    try {
                        // Get User Input
                        System.out.println("Enter the row for your move, " + this.getName());
                        int user_input = Integer.parseInt(sc.nextLine());

                        // Validate Input
                        if (user_input < 0 || user_input > (board.getSize()-1)) {
                            throw new IllegalArgumentException("Number is out of range. correct range is 0 - " + (board.getSize()-1) + ".");
                        }

                        // If input is valid, break out of the loop
                        row = user_input;
                        break;
                    } catch (IllegalArgumentException e) {
                        // Step 3: Handle Validation Errors
                        System.out.println("Invalid input: " + e.getMessage());
                    }
                }
                while (true) {
                    try {
                        // Get User Input
                        System.out.println("Enter the col for your move, " + this.getName());
                        int user_input = Integer.parseInt(sc.nextLine());

                        // Validate Input
                        if (user_input < 0 || user_input > (board.getSize()-1)) {
                            throw new IllegalArgumentException("Number is out of range. correct range is 0 - " + (board.getSize()-1) + ".");
                        }

                        // If input is valid, break out of the loop
                        col = user_input;
                        break;
                    } catch (IllegalArgumentException e) {
                        // Handle Validation Errors
                        System.out.println("Invalid input: " + e.getMessage());
                    }
                }

                // Validate Input
                if(board.getBoard().get(row).get(col).getCellState() == CellState.FILLED){
                    throw new IllegalArgumentException("Following cell is already filled. Please select any other cell.");
                }

                // If input is valid, break out of the loop
                break;
            } catch (IllegalArgumentException e) {
                // Step 3: Handle Validation Errors
                row = -1;
                col = -1;
                System.out.println("Invalid cell selection: " + e.getMessage());
            }
        }

        board.getBoard().get(row).get(col).setCellState(CellState.FILLED);
        board.getBoard().get(row).get(col).setPlayer(this);
        return new Move(row, col, this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public void setPlayerType(PlayerType playerType) {
        this.playerType = playerType;
    }
}
