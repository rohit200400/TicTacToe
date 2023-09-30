package ticTacToe.models;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private int size;
    private List<List<Cell>> board;

    public Board(int size) {
        this.size = size; //3
        this.board = new ArrayList<>();

        /*
                    [_, _, _]
                    [_, _, _]
                    [_, _, _]
         */

        for(int i=0;i<size;i++){
            this.board.add(new ArrayList<>());
            for(int j=0;j<size;j++){
                this.board.get(i).add(new Cell(i,j));
            }
        }
    }

    public Board(Board otherBoard) {
        this.size = otherBoard.size;
        this.board = new ArrayList<>();

        for (int i = 0; i < otherBoard.size; i++) {
            List<Cell> row = new ArrayList<>();
            for (int j = 0; j < otherBoard.size; j++) {
                // Create a new Cell with a copy constructor
                Cell cell = new Cell(otherBoard.getBoard().get(i).get(j));
                row.add(cell);
            }
            this.board.add(row);
        }
    }

    public void printBoard(){
        for(int i=0;i<size;i++){
            List<Cell> cells = board.get(i);
            for(Cell cell : cells){
                cell.display();
            }
            System.out.println();
        }
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<List<Cell>> getBoard() {
        return board;
    }

    public void setBoard(List<List<Cell>> board) {
        this.board = board;
    }
}

// 8:20 AM -> break