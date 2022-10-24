package hw3;

//import static api.Orientation.*;

import api.Direction;
import api.Orientation;

//import static api.Direction.*; 

/**
 * Represents a block in the Block Slider game.
 * @author kausshik
 */
public class Block {
	/**
	 * Private Variables
	 */
	
	/**
	 * Tracks the First Row of a block at any given instant
	 */
	private int blockFirstRow;
	/**
	 * Tracks the First Coloumn of a block at any given instant
	 */
	private int blockFirstCol;
	/**
	 * Tracks the First Row of a block which was passed into the constructor, used while resetting the block
	 */
	private int originalFirstRow;
	/**
	 * Tracks the First Coloumn of a block which was passed into the constructor, used while resetting the block
	 */
	private int originalFirstCol;
	/**
	 * Tracks length of a goven block
	 */
	private int blockLength;
	/**
	 * Tells us the orientation of the block : HORIZONTAL or VERTICAL
	 */
	private Orientation blockOrientation;

	/**
	 * Constructs a new Block with a specific location relative to the board. The
	 * upper/left most corner of the block is given as firstRow and firstCol. All
	 * blocks are only one cell wide. The length of the block is specified in cells.
	 * The block can either be horizontal or vertical on the board as specified by
	 * orientation.
	 * Also putting the firstCol and firstRow values to originalFirstRow and originalFirstCol 
	 * 
	 * @param firstRow    the first row that contains the block
	 * @param firstCol    the first column that contains the block
	 * @param length      block length in cells
	 * @param orientation either HORIZONTAL or VERTICAL
	 */
	public Block(int firstRow, int firstCol, int length, Orientation orientation) {
		originalFirstRow = firstRow;
		originalFirstCol = firstCol;
		blockFirstRow = firstRow;
		blockFirstCol = firstCol;
		blockLength = length;
		blockOrientation = orientation;
	}

	/**
	 * Resets the position of the block to the original firstRow and firstCol values
	 * that were passed to the constructor during initialization of the the block.
	 */
	public void reset() {
		blockFirstRow = originalFirstRow;
		blockFirstCol = originalFirstCol;
		
	}

	/**
	 * Move the blocks position by one cell in the direction specified. The blocks
	 * first column and row should be updated. The method will only move VERTICAL
	 * blocks UP or DOWN and HORIZONTAL blocks RIGHT or LEFT. Invalid movements are
	 * ignored.
	 * 
	 * @param dir direction to move (UP, DOWN, RIGHT, or LEFT)
	 */
	public void move(Direction dir) {
		if((dir == Direction.UP) && (blockOrientation == Orientation.VERTICAL)) {
			blockFirstRow -= 1;			
		}
		else if((dir == Direction.DOWN) && (blockOrientation == Orientation.VERTICAL)) {
			blockFirstRow += 1;			
		}
		else if((dir == Direction.RIGHT) && (blockOrientation == Orientation.HORIZONTAL)) {
			blockFirstCol += 1;
		}
		else if((dir == Direction.LEFT) && (blockOrientation == Orientation.HORIZONTAL)) {
			blockFirstCol -= 1;
		}
	}

	/**
	 * Gets the first row of the block on the board.
	 * 
	 * @return first row
	 */
	public int getFirstRow() {
		return blockFirstRow;
	}

	/**
	 * Sets the first row of the block on the board.
	 * 
	 * @param firstRow first row
	 */
	public void setFirstRow(int firstRow) {
		blockFirstRow = firstRow;
	}

	/**
	 * Gets the first column of the block on the board.
	 * 
	 * @return first column
	 */
	public int getFirstCol() {
		return blockFirstCol;
	}

	/**
	 * Sets the first column of the block on the board.
	 * 
	 * @param firstCol first column
	 */
	public void setFirstCol(int firstCol) {
		blockFirstCol = firstCol;
	}

	/**
	 * Gets the length of the block.
	 * 
	 * @return length measured in cells
	 */
	public int getLength() {
		return blockLength;
	}

	/**
	 * Gets the orientation of the block.
	 * 
	 * @return either VERTICAL or HORIZONTAL
	 */
	public Orientation getOrientation() {
		return blockOrientation;
	}

	@Override
	public String toString() {
		return "(row=" + getFirstRow() + ", col=" + getFirstCol() + ", len=" + getLength()
				+ ", ori=" + getOrientation() + ")";
	}
}
