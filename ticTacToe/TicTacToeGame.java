package ticTacToe;

import ticTacToe.controller.GameController;
import ticTacToe.exception.GameOverException;
import ticTacToe.models.*;
import ticTacToe.service.winningStrategy.WinningStrategies;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class TicTacToeGame {
    public static void main(String[] args) throws GameOverException {
        Scanner sc = new Scanner(System.in);
        GameController gameController = new GameController();
        int dimension;
        String isBotPresent;
        int retryCount = 0;

        while (true) {
            try {
                // Get User Input
                System.out.println("Enter the dimension of the game");
                int user_input = Integer.parseInt(sc.nextLine());

                // Validate Input
                if (user_input < 0 || user_input > 10) {
                    throw new IllegalArgumentException("Number is out of range. correct range is 3 - 10.");
                }

                // If input is valid, break out of the loop
                retryCount = 0;
                dimension = user_input;
                break;
            } catch (NumberFormatException e) {
                // Step 3: Handle Validation Errors (invalid integer input)
                retryCount++;
                System.out.println("Invalid input. Please enter a valid integer.");
            } catch (IllegalArgumentException e) {
                // Step 3: Handle Validation Errors (negative number)
                retryCount++;
                System.out.println("Invalid input: " + e.getMessage());
            }
            if(retryCount > 5){
                System.out.println("We are initiating with default dimension as you have given wrong input multiple times.");
                dimension = 3;
                retryCount = 0;
                break;
            }
        }

        while (true) {
            try {
                // Get User Input
                System.out.println("Will there be any bot in the game ? Y/N");
                String user_input = sc.nextLine();

                // Validate Input
                if (user_input.trim().length() > 1 || (!user_input.equalsIgnoreCase("Y") && !(user_input.equalsIgnoreCase("N")))) {
                    throw new IllegalArgumentException("Please provide input as Y or N.");
                }

                // If input is valid, break out of the loop
                retryCount = 0;
                isBotPresent = user_input;
                break;
            } catch (IllegalArgumentException e) {
                // Step 3: Handle Validation Errors (negative number)
                retryCount++;
                System.out.println("Invalid input: " + e.getMessage());
            }
            if(retryCount > 5){
                System.out.println("We are initiating with game with a bot as you have given wrong input multiple times.");
                isBotPresent = "Y";
                retryCount = 0;
                break;
            }
        }

        List<Player> players = new ArrayList<>();
        int iteratorNumber = dimension - 1;

        if(isBotPresent.equalsIgnoreCase("Y")){
            iteratorNumber = iteratorNumber - 1;
        }

        for(int i=1;i<=iteratorNumber;i++){
            System.out.println("What is the name of player, number : " + i);
            String playerName = sc.next();

            System.out.println("What is the symbol of player, number : " + i);
            String symbol = sc.next();

            players.add(new Player(i, playerName, symbol.charAt(0), PlayerType.HUMAN));
        }

        if(isBotPresent.equalsIgnoreCase("Y")){
            System.out.println("What is the name of BOT");
            String botName = sc.next();

            System.out.println("What is the symbol of BOT");
            String botSymbol = sc.next();

            //TODO: Take input for BOT difficulty level, and set strategy accordingly
            BotDifficultyLevel botDifficultyLevel = BotDifficultyLevel.EASY;
            Bot bot = new Bot(
                    dimension,
                    botName,
                    botSymbol.charAt(0),
                    PlayerType.BOT,
                    botDifficultyLevel
            );
            players.add(bot);
        }

        //randomises the list of players, so order of playing is completely random
        Collections.shuffle(players);

        Game game = gameController.createGame(dimension, players, WinningStrategies.ORDERONE_WINNINGSTRATEGY);
        int playerIndex = -1;
        while(gameController.getGameStatus(game).equals(GameStatus.IN_PROGRESS)){
            System.out.println("Current board status");
            gameController.displayBoard(game);
            playerIndex++;
            playerIndex = playerIndex % players.size();
            Move movePlayed = gameController.executeMove(game, players.get(playerIndex));
//            System.out.println("Do you want to undo your move? Y/N");
//            String isUndoRequired = sc.next();
//            if(isUndoRequired.equalsIgnoreCase("Y")){
//                gameController.undoMove(game, movePlayed);
//            }
            Player winner = gameController.checkWinner(game, movePlayed);
            if(winner != null){
                System.out.println("WINNER IS : " + winner.getName());
                break;
            }
        }
        System.out.println("Final board status");
        gameController.displayBoard(game);


        //TODO : call the replay logic here
        System.out.println("Do you want a replay? Y / N");
        String user_input = sc.nextLine();
        if(user_input.equalsIgnoreCase("Y")){
            for(Board b : game.getBoardStates()){
                b.printBoard();
            }
        }
    }
}