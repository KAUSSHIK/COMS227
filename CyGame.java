package hw2;
/**
 * Model of a Monopoly-like game. Two players take turns rolling dice to move
 * around a board. The game ends when one of the players has at least
 * MONEY_TO_WIN money or one of the players goes bankrupt (has negative money).
 * 
 * @author kausshik
 */
public class CyGame {
/**
 * Do nothing square type.
 */
public static final int DO_NOTHING = 0;
/**
 * Pass go square type.
 */
public static final int PASS_GO = 1;
/**
 * Cyclone square type.
 */
public static final int CYCLONE = 2;
/**
 * Pay the other player square type.
 */
public static final int PAY_PLAYER = 3;
/**
 * Get an extra turn square type.
 */
public static final int EXTRA_TURN = 4;
/**
 * Jump forward square type.
 */
public static final int JUMP_FORWARD = 5;
/**
 * Stuck square type.
 */
public static final int STUCK = 6;
/**
 * Points awarded when landing on or passing over go.
 */
public static final int PASS_GO_PRIZE = 200;
/**
 * The amount payed to the other player per unit when landing on a
 * PAY_PLAYER square.
 */
public static final int PAYMENT_PER_UNIT = 20;
/**
 * The amount of money required to win.
 */
public static final int MONEY_TO_WIN = 400;
/**
 * The cost of one unit.
 */
public static final int UNIT_COST = 50;

//PRIVATE VARIABLES
/**
 * The number of squares on the board
 */
private int numberOfSquaresOnBoard;
/**
 * The amount of money every player has when they start the game
 */
private int startingMoney;
/**
 * Amount of money Player 1 has with him at anytime during the game
 */
private int player1Money;
/**
 * Amount of money Player 2 has with him at anytime during the game
 */
private int player2Money;
/**
 * Position of player 1 at any given time
 */
private int player1Position;
/**
 * Position of player 2 at any given time
 */
private int player2Position;
/**
 * Number of Units player 1 has at any given time
 */
private int player1Units;
/**
 * Number of Units player 1 has at any given time
 */
private int player2Units;
/**
 * value is  1 if it is Player 1's turn, 2 if it is Player 2's turn
 */
private int currentPlayer;
 /**
  * tells us what type of square the current player is on
  */
private int squareType;
/**
 * Variable to check if the game is over
 */
private boolean isGameOver;

 
public String toString() {
String fmt = "Player 1%s: (%d, %d, $%d) Player 2%s: (%d, %d, $%d)";
String player1Turn = "";
String player2Turn = "";
if (getCurrentPlayer() == 1) {
player1Turn = "*";
} else {
player2Turn = "*";
}
return String.format(fmt,
player1Turn, getPlayer1Square(), getPlayer1Units(), 
getPlayer1Money(),
player2Turn, getPlayer2Square(), getPlayer2Units(), 
getPlayer2Money());
}
//CONSTRUCTOR
/**
 * Constructs a game that has he given number of squares and starts both players on square 0.
 * Both players start with the given amount of money and 1 unit each.
 * It is initially Player 1's turn to move
 * @param numSquares - - number of squares on board
 * @param startingMoney - - starting money for each player 
 */
public CyGame(int numSquares, int startingMoney) {
	this.numberOfSquaresOnBoard = numSquares;
	this.startingMoney = startingMoney;
	player1Money = this.startingMoney;
	player2Money = this.startingMoney;
	player1Units = 1;
	player2Units = 1;
	currentPlayer = 1;
	player1Position = 0;
	player2Position = 0;
	squareType = 0;
	isGameOver = false;
}

//METHODS
/**
 * Get player 1's square
 * @return the square number
 */
public int getPlayer1Square() {
	return player1Position;
}
/**
 * Get player 2's square
 * @return the square number
 */
public int getPlayer2Square() {
	return player2Position;
}
/**
 * Get Player 1's money
 * @return Player 1's money
 */
public int getPlayer1Money() {
	return player1Money;
}
/**
 * Get Player 2's money
 * @return Player 2's money
 */
public int getPlayer2Money() {
	return player2Money;
}
/**
 * Get player 1's Units
 * @return player 1's units
 */
public int getPlayer1Units() {
	return player1Units;
}
/**
 * Get player 2's Units
 * @return player 2's units
 */
public int getPlayer2Units() {
	return player2Units;
}
/**
 * Get the current player
 * @return 1 if Player 1's turn or 2 is Player 2's turn
 */
public int getCurrentPlayer() {
	return currentPlayer;
}
/**
 * Get the type of square for the given square number
 * Each square is assigned a single type based on the following priority order and rules
 * @param square - - the square number
 * @return the type of square the current player is on
 */
public int getSquareType(int square) {
	if (square % numberOfSquaresOnBoard == 0) {
		squareType = PASS_GO;
	}
	else if ((square % numberOfSquaresOnBoard) == numberOfSquaresOnBoard-1) {
		squareType = CYCLONE;
	}
	else if (square % 5 == 0) {
		squareType = PAY_PLAYER;
	}
	else if ((square % 7 == 0 ) || (square % 11 == 0)) {
		squareType = EXTRA_TURN;
	}
	else if (square % 3 == 0) {
		squareType = STUCK;
	}
	else if (square % 2 == 0) {
		squareType = JUMP_FORWARD;
	}
	else {
		squareType = DO_NOTHING;
	}
	return squareType;
}
/**
 * Method called to indicate that the current player's turn is ended
 * Change turns from Player 1 to PLayer 2 or vice versa
 */
public void endTurn() {
	if ((getCurrentPlayer() == 1) && (getSquareType(getPlayer1Square()) != EXTRA_TURN)) {
		currentPlayer = 2;
	}
	else if ((getCurrentPlayer() == 2) && (getSquareType(getPlayer2Square()) != EXTRA_TURN)) {
		currentPlayer = 1;
	}
}
/**
 * Returns true if game is over, false if it is not
 * The game is over when either player has atleast MONEY_TO_WIN or either player has a negative amount of money
 * @return true if game is over, false otherwise
 */
public boolean isGameEnded() {
	if((player1Money < 0) || (player2Money < 0) || (player1Money >= MONEY_TO_WIN) || (player2Money >= MONEY_TO_WIN)) {
		isGameOver = true;
	}
	return isGameOver;
}
/**
 * Method indicated when a player tries to buy a unit.
 * The player should have atleast UNIT_COST amount of money and they must currently be on a square of type DO_NOTHING
 * If allowed subtract the UNIT_COST amount of money and from the player's money and increment the player's units by one
 * If a player successfully buys a unit, end their turn
 * Update the turn to the other player
 * Method does nothing when game is over
 */
public void buyUnit() {
	if(getCurrentPlayer() == 1) {
		if((getSquareType(getPlayer1Square()) == DO_NOTHING) && (getPlayer1Money() >= UNIT_COST) && (!isGameEnded())) {
			player1Money -= UNIT_COST;
			player1Units += 1;
			endTurn();
		}
	}
	else {
		if((getSquareType(getPlayer2Square()) == DO_NOTHING) && (getPlayer2Money() >= UNIT_COST) && (!isGameEnded())) {
			player2Money -= UNIT_COST;
			player2Units += 1;
			endTurn();
	}
}
}
/**
 * Method indicated when a player tries to sell a unit.
 * The player should have atleast one unit and they must currently be on a square of type DO_NOTHING
 * If allowed decrement the player's units by one and add UNIT_COST amount of money to the player's money
 * If a player successfully sells a unit, end their turn
 * Update the turn to the other player
 * Method does nothing when game is over
 */
public void sellUnit() {
	if(getCurrentPlayer() == 1) {
		if((getPlayer1Units() >= 1) && (!isGameEnded())) {
			player1Money += UNIT_COST;
			player1Units -= 1;
			endTurn();
		}
	}
	else {
		if((getPlayer2Units() >= 1) && (!isGameEnded())) {
			player2Money += UNIT_COST;
			player2Units -= 1;
			endTurn();
	}
}
}
/**
 * Method called to indicate that the current player has rolled the die
 * Advance the player by the number of squares determined by the roll fuction's input 
 * EXCEPTION: If the player is currently on a STUCK square they only move forward if the value rolled is even, otherwise their turn is over
 * If the player passes over the PASS_GO square add PASS_GO_PRIZE to the player's money
 * Then apply the action of the square the player lands on.
 * @param value - - the number rolled by the dice (1 to 6 inclusive)
 */
public void roll(int value) {
	if((getCurrentPlayer() == 1) && (!isGameEnded()) && (((getSquareType(getPlayer1Square()) != STUCK)) || ((getSquareType(getPlayer1Square()) == STUCK) && (value % 2 == 0)))){
		player1Position += value;
		if (player1Position >= numberOfSquaresOnBoard) {
			player1Money += PASS_GO_PRIZE;
		}
		player1Position = (player1Position%numberOfSquaresOnBoard);
		if (getSquareType(getPlayer1Square()) == CYCLONE) {
			player1Position = player2Position;
			endTurn();
		}
		else if(getSquareType(getPlayer1Square()) == DO_NOTHING) {
			endTurn();
		}
		else if(getSquareType(getPlayer1Square()) == PAY_PLAYER) {
			player1Money -= (player2Units * PAYMENT_PER_UNIT);
			player2Money += (player2Units * PAYMENT_PER_UNIT);
			endTurn();
		}
		else if(getSquareType(getPlayer1Square()) == EXTRA_TURN) {
			endTurn();
		}
		else if(getSquareType(getPlayer1Square()) == JUMP_FORWARD) {
			player1Position += 4;
			if(player1Position >= numberOfSquaresOnBoard) {
				player1Money += PASS_GO_PRIZE;
			}
			player1Position = player1Position%numberOfSquaresOnBoard;
			endTurn();
		}
		else if(getSquareType(getPlayer1Square()) == PASS_GO) {
			endTurn();
		}
		else {
			endTurn();
		}
	}
	else if((getCurrentPlayer() == 2) && (!isGameEnded()) && (((getSquareType(getPlayer2Square()) != STUCK)) || ((getSquareType(getPlayer2Square()) == STUCK) && (value % 2 == 0)))){
		player2Position += value;
		if (player2Position >= numberOfSquaresOnBoard) {
			player2Money += PASS_GO_PRIZE;
		}
		player2Position = (player2Position%numberOfSquaresOnBoard);
		if (getSquareType(getPlayer2Square()) == CYCLONE) {
			player2Position = player1Position;
			endTurn();
		}
		else if(getSquareType(getPlayer2Square()) == DO_NOTHING) {
			endTurn();
		}
		else if(getSquareType(getPlayer2Square()) == PAY_PLAYER) {
			player2Money -= (player1Units * PAYMENT_PER_UNIT);
			player1Money += (player1Units * PAYMENT_PER_UNIT);
			endTurn();
		}
		else if(getSquareType(getPlayer2Square()) == EXTRA_TURN) {
			endTurn();
		}
		else if(getSquareType(getPlayer2Square()) == JUMP_FORWARD) {
			player2Position += 4;
			if(player2Position >= numberOfSquaresOnBoard) {
				player2Money += PASS_GO_PRIZE;
			}
			player2Position = player2Position%numberOfSquaresOnBoard;
			endTurn();
		}
		else if(getSquareType(getPlayer2Square()) == PASS_GO) {
			endTurn();
		}
		else {
			endTurn();
		}
	}
	else {
		endTurn();
	}
	
}
}