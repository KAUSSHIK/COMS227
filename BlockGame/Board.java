package hw3;
import static api.Orientation.*; 
import static api.Direction.*; 
import static api.CellType.*; 


import java.util.ArrayList;

import api.Cell;
import api.Direction;
import api.Move;

/**
 * Represents a board in the Block Slider game. A board contains a 2D grid of
 * cells and a list of blocks that slide over the cells.
 * 
 * @author kausshik
 */
public class Board {
	/**
	 * Tracks the current cell clicked/selected by the user
	 */
	private Cell currentGrabbedCell = null;
	/**
	 * Tracks the current block clicked/selected by the user as a result of selecting a cell
	 */
	private Block currentGrabbedBlock = null;
	/**
	 * Tracks the total moves made by the user in a given turn
	 */
	private int totalMovesMade = 0;
	/**
	 * Boolean variable that keeps tracking if the game is over
	 */
	private boolean isGameOver = false;
	/**
	 * 2D array of cells, the indexes signify (row, column) with (0, 0) representing
	 * the upper-left cornner of the board.
	 */
	private Cell[][] grid;

	/**
	 * A list of blocks that are positioned on the board
	 */
	private ArrayList<Block> blocks = new ArrayList<Block>();

	/**
	 * A list of moves that have been made in order to get to the current position
	 * of blocks on the board.
	 */
	private ArrayList<Move> moveHistory = new ArrayList<Move>();

	/**
	 * Constructs a new board from a given 2D array of cells and list of blocks. The
	 * cells of the grid should be updated to indicate which cells have blocks
	 * placed over them (i.e., setBlock() method of Cell). The move history should
	 * be initialized as empty.
	 * 
	 * @param grid   a 2D array of cells which is expected to be a rectangular shape
	 * @param blocks list of blocks already containing row-column position which
	 *               should be placed on the board
	 */
	public Board(Cell[][] grid, ArrayList<Block> blocks) {
		this.grid = new Cell[grid.length][grid[0].length];
		for(int i = 0 ; i < grid.length; i++) {
			for(int j = 0 ; j < grid[0].length; j++) {
				this.grid[i][j] = grid[i][j];
			}
		}
		this.blocks = blocks;
		for(Block b : this.blocks) {
			if(b.getOrientation() == HORIZONTAL) {
				for(int i = 0; i < b.getLength(); i++) {
					this.grid[b.getFirstRow()][b.getFirstCol()+i].setBlock(b);
				}
			}
			else if(b.getOrientation() == VERTICAL) {
				for(int i = 0; i < b.getLength(); i++) {
					this.grid[b.getFirstRow()+i][b.getFirstCol()].setBlock(b);
				}
			}
		}
		
		moveHistory = new ArrayList<Move>();
	}
	
	/**
	 * This method takes in a grid of Cell[][] type and returns a string description of what the grid is like
	 * This helper method was made to help reset the grid
	 * It basically acts like a method that decodes the block and makes it a string description with the help of "[" "]" "#" "." "*" "v" "^"
	 * @param gridToBeReset
	 * @return newGrid - string description of the current grid
	 */
	/* ALTERNATE METHOD TO RESET
	private String[][] newGridMaker(Cell[][] gridToBeReset){
		String[][] newGrid = new String[gridToBeReset.length][gridToBeReset[0].length];
		for(int i = 0; i < newGrid.length; i++) {
			for(int j = 0; j < newGrid[0].length; j++) {
				if(gridToBeReset[i][j].isFloor()) {
					newGrid[i][j] = ".";
				}
				else if(gridToBeReset[i][j].isExit()) {
					newGrid[i][j] = "e";
				}
				else {
					newGrid[i][j] = "*";
				}
			}
		}
		return newGrid;
	}
	*/
	/**
	 * Constructs a new board from a given 2D array of String descriptions.
	 * <p>
	 * DO NOT MODIFY THIS CONSTRUCTOR
	 * 
	 * @param desc 2D array of descriptions
	 */
	public Board(String[][] desc) {
		this(GridUtil.createGrid(desc), GridUtil.findBlocks(desc));
	}

	/**
	 * Models the user grabbing a block over the given row and column. The purpose
	 * of grabbing a block is for the user to be able to drag the block to a new
	 * position, which is performed by calling moveGrabbedBlock(). This method
	 * records two things: the block that has been grabbed and the cell at which it
	 * was grabbed.
	 * 
	 * @param row row to grab the block from
	 * @param col column to grab the block from
	 */
	public void grabBlockAtCell(int row, int col) {
		currentGrabbedCell = grid[row][col];
		if(currentGrabbedCell.hasBlock()) {
			for(Block b : blocks) {
				if(b == currentGrabbedCell.getBlock()) {
					currentGrabbedBlock = b;
				}
			}
		}
	}

	/**
	 * Set the currently grabbed block to null.
	 */
	public void releaseBlock() {
		currentGrabbedBlock = null;
	}

	/**
	 * Returns the currently grabbed block.
	 * 
	 * @return the current block
	 */
	public Block getGrabbedBlock() {
		return currentGrabbedBlock;
	}

	/**
	 * Returns the currently grabbed cell.
	 * 
	 * @return the current cell
	 */
	public Cell getGrabbedCell() {
		return currentGrabbedCell;
	}

	/**
	 * Returns true if the cell at the given row and column is available for a block
	 * to be placed over it. Blocks can only be placed over floors and exits. A
	 * block cannot be placed over a cell that is occupied by another block.
	 * 
	 * @param row row location of the cell
	 * @param col column location of the cell
	 * @return true if the cell is available for a block, otherwise false
	 */
	public boolean canPlaceBlock(int row, int col) {
		boolean canPlace = false;
		if(((grid[row][col].isFloor() && (!(grid[row][col].hasBlock())) || (grid[row][col].isExit())))) {
			canPlace = true;
		}
		return canPlace;
	}

	/**
	 * Returns the number of moves made so far in the game.
	 * 
	 * @return the number of moves
	 */
	public int getMoveCount() {
		return totalMovesMade;
	}

	/**
	 * Returns the number of rows of the board.
	 * 
	 * @return number of rows
	 */
	public int getRowSize() {
		return grid.length;
	}

	/**
	 * Returns the number of columns of the board.
	 * 
	 * @return number of columns
	 */
	public int getColSize() {
		return grid[0].length;
	}

	/**
	 * Returns the cell located at a given row and column.
	 * 
	 * @param row the given row
	 * @param col the given column
	 * @return the cell at the specified location
	 */
	public Cell getCell(int row, int col) {
		return grid[row][col];
	}

	/**
	 * Returns a list of all blocks on the board.
	 * 
	 * @return a list of all blocks
	 */
	public ArrayList<Block> getBlocks() {
		return blocks;
	}

	/**
	 * Returns true if the player has completed the puzzle by positioning a block
	 * over an exit, false otherwise.
	 * Iterates over every cell in the grid and checks if a cell that also has a block is at the exit
	 * sets isGameOver to true and returns it
	 * 
	 * @return true if the game is over
	 */

	public boolean isGameOver() {
		for(Cell[] Clist : grid) {
			for(Cell cell : Clist ) {
				if(cell.isExit() && cell.hasBlock()) {
					isGameOver = true;
					return isGameOver;
				}
			}
		}
		return isGameOver;
	}

	/**
	 * Moves the currently grabbed block by one cell in the given direction. A
	 * horizontal block is only allowed to move right and left and a vertical block
	 * is only allowed to move up and down. A block can only move over a cell that
	 * is a floor or exit and is not already occupied by another block. The method
	 * does nothing under any of the following conditions:
	 * <ul>
	 * <li>The game is over.</li>
	 * <li>No block is currently grabbed by the user.</li>
	 * <li>A block is currently grabbed by the user, but the block is not allowed to
	 * move in the given direction.</li>
	 * </ul>
	 * If none of the above conditions are meet, the method does the following:
	 * <ul>
	 * <li>Moves the block object by calling its move method.</li>
	 * <li>Sets the block for the grid cell that the block is being moved into.</li>
	 * <li>For the grid cell that the block is being moved out of, sets the block to
	 * null.</li>
	 * <li>Moves the currently grabbed cell by one cell in the same moved direction.
	 * The purpose of this is to make the currently grabbed cell move with the block
	 * as it is being dragged by the user.</li>
	 * <li>Adds the move to the end of the moveHistory list.</li>
	 * <li>Increment the count of total moves made in the game.</li>
	 * </ul>
	 * 
	 * This method updates the presence of blocks on the cell that it is moved to and the cell that it is moved out of
	 * 
	 * @param dir the direction to move
	 */
	public void moveGrabbedBlock(Direction dir) {
		if((!isGameOver()) && (currentGrabbedBlock != null)) {
			if((dir == RIGHT) && (currentGrabbedBlock.getOrientation() == HORIZONTAL)) {
				if(canPlaceBlock(currentGrabbedBlock.getFirstRow(), currentGrabbedBlock.getFirstCol() + currentGrabbedBlock.getLength())) {
					moveHistory.add(new Move(currentGrabbedBlock, dir));
					currentGrabbedBlock.move(dir);
					currentGrabbedCell = grid[currentGrabbedCell.getRow()][currentGrabbedCell.getCol() + 1];
					grid[currentGrabbedBlock.getFirstRow()][currentGrabbedBlock.getFirstCol() - 1].setBlock(null);
					grid[currentGrabbedBlock.getFirstRow()][currentGrabbedBlock.getFirstCol() + currentGrabbedBlock.getLength() - 1].setBlock(currentGrabbedBlock);
					totalMovesMade++;
				}
			}
			
			else if((dir == LEFT) && (currentGrabbedBlock.getOrientation() == HORIZONTAL)) {
				if(canPlaceBlock(currentGrabbedBlock.getFirstRow(), currentGrabbedBlock.getFirstCol() - 1)) {
					moveHistory.add(new Move(currentGrabbedBlock, dir));
					currentGrabbedBlock.move(dir);
					currentGrabbedCell = grid[currentGrabbedCell.getRow()][currentGrabbedCell.getCol() - 1];
					grid[currentGrabbedBlock.getFirstRow()][currentGrabbedBlock.getFirstCol()].setBlock(currentGrabbedBlock);
					grid[currentGrabbedBlock.getFirstRow()][currentGrabbedBlock.getFirstCol() + currentGrabbedBlock.getLength()].setBlock(null);
					totalMovesMade++;
				}
			}
			
			else if((dir == UP) && (currentGrabbedBlock.getOrientation() == VERTICAL)) {
				if(canPlaceBlock(currentGrabbedBlock.getFirstRow() - 1, currentGrabbedBlock.getFirstCol())) {
					moveHistory.add(new Move(currentGrabbedBlock, dir));
					currentGrabbedBlock.move(dir);
					currentGrabbedCell = grid[currentGrabbedCell.getRow() - 1][currentGrabbedCell.getCol()];
					grid[currentGrabbedBlock.getFirstRow()][currentGrabbedBlock.getFirstCol()].setBlock(currentGrabbedBlock);
					grid[currentGrabbedBlock.getFirstRow() + currentGrabbedBlock.getLength()][currentGrabbedBlock.getFirstCol()].setBlock(null);
					totalMovesMade++;
				}
			}
			
			else if((dir == DOWN) && (currentGrabbedBlock.getOrientation() == VERTICAL)) {
				if(canPlaceBlock(currentGrabbedBlock.getFirstRow() + currentGrabbedBlock.getLength(), currentGrabbedBlock.getFirstCol())) {
					moveHistory.add(new Move(currentGrabbedBlock, dir));
					currentGrabbedBlock.move(dir);
					currentGrabbedCell = grid[currentGrabbedCell.getRow() + 1][currentGrabbedCell.getCol()];
					grid[currentGrabbedBlock.getFirstRow() + currentGrabbedBlock.getLength() - 1][currentGrabbedBlock.getFirstCol()].setBlock(currentGrabbedBlock);
					grid[currentGrabbedBlock.getFirstRow() - 1][currentGrabbedBlock.getFirstCol()].setBlock(null);
					totalMovesMade++;
				}
			}
		
		}

	}

	/**
	 * Resets the state of the game back to the start, which includes the move
	 * count, the move history, and whether the game is over. The method calls the
	 * reset method of each block object. It also updates each grid cells by calling
	 * their setBlock method to either set a block if one is located over the cell
	 * or set null if no block is located over the cell.
	 * 
	 * We reset the blocks and clear the grid and we place the blocks again (similar code to the one in constructor)
	 */
	public void reset() {
		isGameOver = false;
		totalMovesMade = 0;
		for(Block b : blocks) {
			b.reset();
		}
		for(Cell[] cellList : grid) {
			for(Cell cell : cellList) {
				if(cell.hasBlock()) {
					cell.clearBlock();
				}
			}
		}
		for(Block b : this.blocks) {
			if(b.getOrientation() == HORIZONTAL) {
				for(int i = 0; i < b.getLength(); i++) {
					this.grid[b.getFirstRow()][b.getFirstCol()+i].setBlock(b);
				}
			}
			else if(b.getOrientation() == VERTICAL) {
				for(int i = 0; i < b.getLength(); i++) {
					this.grid[b.getFirstRow()+i][b.getFirstCol()].setBlock(b);
				}
			}
		}
		moveHistory = new ArrayList<Move>();
		currentGrabbedCell = null;
		currentGrabbedBlock = null;
		isGameOver = false;
		
		/* ALTERNATE METHOD TO RESET BLOCK
		//String[][] noveauGrid = newGridMaker(grid);
		//grid = GridUtil.createGrid((noveauGrid));
		//Board board = new Board(grid, blocks);
		*/
		
		
	}

	/**
	 * Returns a list of all legal moves that can be made by any block on the
	 * current board. If the game is over there are no legal moves.
	 * 
	 * @return a list of legal moves
	 */
	public ArrayList<Move> getAllPossibleMoves() {
		ArrayList<Move> allPossibleMoves = new ArrayList<Move>();
		
		if(!isGameOver) {
		for (Block b : blocks) {
			if(b.getOrientation() == HORIZONTAL) {
				if(canPlaceBlock(b.getFirstRow(), b.getFirstCol() + b.getLength())) {
					allPossibleMoves.add(new Move(b, RIGHT));
				}
				if((canPlaceBlock(b.getFirstRow(), b.getFirstCol() - 1))){
					allPossibleMoves.add(new Move(b, LEFT));
				}
			}
			else if(b.getOrientation() == VERTICAL) {
				if(canPlaceBlock(b.getFirstRow() - 1, b.getFirstCol())) {
					allPossibleMoves.add(new Move(b, UP));
				}
				if(canPlaceBlock(b.getFirstRow() + b.getLength(), b.getFirstCol())) {
					allPossibleMoves.add(new Move(b, DOWN));
				}
			}
		}
		return allPossibleMoves;
		}
		return null;
	}

	/**
	 * Gets the list of all moves performed to get to the current position on the
	 * board.
	 * 
	 * @return a list of moves performed to get to the current position
	 */
	public ArrayList<Move> getMoveHistory() {
		return moveHistory;
	}

	/**
	 * EXTRA CREDIT 5 POINTS
	 * <p>
	 * This method is only used by the Solver.
	 * <p>
	 * Undo the previous move. The method gets the last move on the moveHistory list
	 * and performs the opposite actions of that move, which are the following:
	 * <ul>
	 * <li>grabs the moved block and calls moveGrabbedBlock passing the opposite
	 * direction</li>
	 * <li>decreases the total move count by two to undo the effect of calling
	 * moveGrabbedBlock twice</li>
	 * <li>if required, sets is game over to false</li>
	 * <li>removes the move from the moveHistory list</li>
	 * </ul>
	 * If the moveHistory list is empty this method does nothing.
	 */
	public void undoMove() {
		isGameOver = false; 
		if(moveHistory.size() > 0) {
		Move lastMove = moveHistory.get(moveHistory.size() - 1);
		Block lastBlock = lastMove.getBlock();
		grabBlockAtCell(lastBlock.getFirstRow(), lastBlock.getFirstCol());
		Direction dir = lastMove.getDirection();
		if(dir == UP) {
			currentGrabbedBlock.move(DOWN);
			currentGrabbedCell = grid[currentGrabbedCell.getRow() + 1][currentGrabbedCell.getCol()];
			grid[currentGrabbedBlock.getFirstRow() + currentGrabbedBlock.getLength() - 1][currentGrabbedBlock.getFirstCol()].setBlock(currentGrabbedBlock);
			grid[currentGrabbedBlock.getFirstRow() - 1][currentGrabbedBlock.getFirstCol()].setBlock(null);
		}
		else if(dir == DOWN) {
			currentGrabbedBlock.move(UP);
			currentGrabbedCell = grid[currentGrabbedCell.getRow() - 1][currentGrabbedCell.getCol()];
			grid[currentGrabbedBlock.getFirstRow()][currentGrabbedBlock.getFirstCol()].setBlock(currentGrabbedBlock);
			grid[currentGrabbedBlock.getFirstRow() + currentGrabbedBlock.getLength()][currentGrabbedBlock.getFirstCol()].setBlock(null);
		}
		else if(dir == RIGHT) {
			currentGrabbedBlock.move(LEFT);
			currentGrabbedCell = grid[currentGrabbedCell.getRow()][currentGrabbedCell.getCol() - 1];
			grid[currentGrabbedBlock.getFirstRow()][currentGrabbedBlock.getFirstCol()].setBlock(currentGrabbedBlock);
			grid[currentGrabbedBlock.getFirstRow()][currentGrabbedBlock.getFirstCol() + currentGrabbedBlock.getLength()].setBlock(null);
		}
		else if(dir == LEFT) {
			currentGrabbedBlock.move(RIGHT);
			currentGrabbedCell = grid[currentGrabbedCell.getRow()][currentGrabbedCell.getCol() + 1];
			grid[currentGrabbedBlock.getFirstRow()][currentGrabbedBlock.getFirstCol() - 1].setBlock(null);
			grid[currentGrabbedBlock.getFirstRow()][currentGrabbedBlock.getFirstCol() + currentGrabbedBlock.getLength() - 1].setBlock(currentGrabbedBlock);
		}
		totalMovesMade -= 1;
		moveHistory.remove(moveHistory.size() - 1);
		//moveHistory.remove(moveHistory.size() - 1);
		}
	}

	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer();
		boolean first = true;
		for (Cell row[] : grid) {
			if (!first) {
				buff.append("\n");
			} else {
				first = false;
			}
			for (Cell cell : row) {
				buff.append(cell.toString());
				buff.append(" ");
			}
		}
		return buff.toString();
	}
}
