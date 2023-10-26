import java.util.Scanner;


public class MathGame {


    private Player player1;
    private Player player2;
    private Player player3;
    private Player currentPlayer;
    private Player winner;
    private boolean gameOver;
    private Scanner scanner;
    private int p1d = 0;
    private int p2d = 0;
    private int p3d = 0;
    private int streakNum;
    private int streakCount;


    // create MathGame object
    public MathGame(Player player1, Player player2, Player player3, Scanner scanner) {
        this.player1 = player1;
        this.player2 = player2;
        this.player3 = player3;
        this.scanner = scanner;
        currentPlayer = null; // will get assigned at start of game
        winner = null; // will get assigned when a Player wins
        gameOver = false;
    }


// ------------ PUBLIC METHODS (to be used by client classes) ------------


    // returns winning Player; will be null if neither Player has won yet
    public Player getWinner() {
        return winner;
    }


    // plays a round of the math game
    public void playRound() {
        chooseStartingPlayer(); // this helper method (shown below) sets currentPlayer to either player1 or player2
        while (!gameOver) {
            printGameState(); // this helper method (shown below) prints the state of the Game
            System.out.println("Current player: " + currentPlayer.getName());
            boolean correct = askQuestion(); // this helper method (shown below) asks a question and returns T or F
            if (correct) {
                System.out.println("Correct!");
                currentPlayer.incrementScore(); // this increments the currentPlayer's score
                rightAnswer();
                swapPlayers(); // this helper method (shown below) sets currentPlayer to the other Player
            } else {
                System.out.println("INCORRECT!");
                wrongAnswer();
            }
        }
    }


    // prints the current scores of the two players
    private void printGameState() {
        System.out.println("--------------------------------------");
        System.out.println("Current Scores:");
        System.out.println(player1.getName() + ": " + player1.getScore());
        System.out.println(player2.getName() + ": " + player2.getScore());
        System.out.println(player3.getName() + ": " + player3.getScore());
        System.out.println("--------------------------------------");
    }


    // resets the game back to its starting state
    public void resetGame() {
        player1.reset(); // this method resets the player
        player2.reset();
        player3.reset();
        gameOver = false;
        currentPlayer = null;
        winner = null;
    }


// ------------ PRIVATE HELPER METHODS (internal use only) ------------


    // randomly chooses one of the Player objects to be the currentPlayer
    private void chooseStartingPlayer() {
        int randNum = (int) (Math.random() * 3) + 1;
        if (randNum == 1) {
            currentPlayer = player1;
        } else if (randNum == 2) {
            currentPlayer = player2;
        } else {
            currentPlayer = player3;
        }
    }


    // asks a math question and returns true if the player answered correctly, false if not
    private boolean askQuestion() {
        int operation = (int) (Math.random() * 4) + 1;
        int num1 = (int) (Math.random() * 100) + 1;
        int num2;
        int correctAnswer;
        System.out.println("Type in your answer as an integer (/ is int division)");
        if (operation == 1) {
            num2 = (int) (Math.random() * 100) + 1;
            System.out.print(num1 + " + " + num2 + " = ");
            correctAnswer = num1 + num2;
        } else if (operation == 2) {
            num2 = (int) (Math.random() * 100) + 1;
            System.out.print(num1 + " - " + num2 + " = ");
            correctAnswer = num1 - num2;
        } else if (operation == 3) {
            num2 = (int) (Math.random() * 10) + 1;
            System.out.print(num1 + " * " + num2 + " = ");
            correctAnswer = num1 * num2;
        } else { // option == 4
            num2 = (int) (Math.random() * 10) + 1;
            System.out.print(num1 + " / " + num2 + " = ");
            correctAnswer = num1 / num2;
        }


        int playerAnswer = scanner.nextInt(); // get player's answer using Scanner
        scanner.nextLine(); // clear text buffer after numeric scanner input


        if (playerAnswer == correctAnswer) {
            return true;
        } else {
            return false;
        }
    }


    // swaps the currentPlayer to the other player
    private void swapPlayers() {
        if (currentPlayer == player1) {
            currentPlayer = player2;
        } else if (currentPlayer == player3) {
            currentPlayer = player1;
        } else if (currentPlayer == player2) {
            currentPlayer = player3;
        }
    }


    // sets the winner when the game ends based on the player that missed the question
    private void determineWinner() {
        if (currentPlayer == player1 && p3d == 1) {
            winner = player2;
            gameOver = true;
        } else if (currentPlayer == player2 && p1d == 1) {
            winner = player3;
            gameOver = true;
        } else if (currentPlayer == player3 && p2d == 1) {
            winner = player1;
            gameOver = true;
        }
        swapPlayers();
    }

    private void wrongAnswer() {
        if (currentPlayer == player1) {
            p1d++;
            player1.decrementScore();
        } else if (currentPlayer == player2) {
            p2d++;
            player2.decrementScore();
        } else if (currentPlayer == player3) {
            p3d++;
            player3.decrementScore();
        }
        determineWinner();
    }

    private void rightAnswer() {
        p1d = 0;
        p2d = 0;
        p3d = 0;
    }

    public void Streak() {
        if ((streakCount == 0) || (streakCount == 1 && winner == player1) || (streakCount == 2 && winner == player2) || (streakCount == 3 && winner == player3)) {
            streakNum++;
            System.out.println(winner.getName() + " has achieved a winstreak of " + streakNum);
        } else {
            streakNum = 1;
            System.out.println(winner.getName() + " has achieved a winstreak of " + streakNum);
        }
        if (winner == player1) {
            streakCount = 1;
        } else if (winner == player2) {
            streakCount = 2;
        } else if (winner == player3) {
            streakCount = 3;
        }
    }
}
