package hw3;

import static api.Orientation.*; 
//import static api.Direction.*; 
import static api.CellType.*; 

import java.util.ArrayList;
import java.util.Arrays;

import api.Cell;
//import api.CellType;

/**
 * Utilities for parsing string descriptions of a grid.
 */
public class GridUtil {
	/**
	 * Constructs a 2D grid of Cell objects given a 2D array of cell descriptions.
	 * String descriptions are a single character and have the following meaning.
	 * <ul>
	 * <li>"*" represents a wall.</li>
	 * <li>"e" represents an exit.</li>
	 * <li>"." represents a floor.</li>
	 * <li>"[", "]", "^", "v", or "#" represent a part of a block. A block is not a
	 * type of cell, it is something placed on a cell floor. For these descriptions
	 * a cell is created with CellType of FLOOR. This method does not create any
	 * blocks or set blocks on cells.</li>
	 * </ul>
	 * The method only creates cells and not blocks. See the other utility method
	 * findBlocks which is used to create the blocks.
	 * 
	 * @param desc a 2D array of strings describing the grid
	 * @return a 2D array of cells the represent the grid without any blocks present
	 */
	public static Cell[][] createGrid(String[][] desc) {
		Cell[][] grid = new Cell[desc.length][desc[0].length];
		for (int i = 0; i < desc.length; i++) {
			for(int j = 0; j < desc[0].length; j++) {
				if(desc[i][j].equals("*")) {
					grid[i][j] = new Cell(WALL, i, j);
				}
				else if(desc[i][j].equals("e")) {
					grid[i][j] = new Cell(EXIT, i, j);
				}
				else {
					grid[i][j] = new Cell(FLOOR, i, j);
				}
			}
		}
		return grid;
	}

	/**
	 * Returns a list of blocks that are constructed from a given 2D array of cell
	 * descriptions. String descriptions are a single character and have the
	 * following meanings.
	 * <ul>
	 * <li>"[" the start (left most column) of a horizontal block</li>
	 * <li>"]" the end (right most column) of a horizontal block</li>
	 * <li>"^" the start (top most row) of a vertical block</li>
	 * <li>"v" the end (bottom most column) of a vertical block</li>
	 * <li>"#" inner segments of a block, these are always placed between the start
	 * and end of the block</li>
	 * <li>"*", ".", and "e" symbols that describe cell types, meaning there is not
	 * block currently over the cell</li>
	 * </ul>
	 * 
	 * Iterating through the grid to detect for blocks, if we find the start of the block we move in the same direction to reach the blocks ending
	 * We record the parameters and make a new block and add it to the ArrayList of blocks
	 * @param desc a 2D array of strings describing the grid
	 * @return a list of blocks found in the given grid description
	 */
	public static ArrayList<Block> findBlocks(String[][] desc) {
		ArrayList<Block> blocks = new ArrayList<Block>();
		int startRow1;
		int startCol1;
		for(int i = 0; i < desc.length; i++) {
			for(int j = 0; j < desc[0].length; j++) {
				if(desc[i][j].equals("[")) {
					startRow1 = i;
					startCol1 = j;
					int temp = j+1;
					while(desc[i][temp].equals("#")) {
						temp++;
					}
					if(desc[i][temp].equals("]")) {
						int length = temp - j + 1;
						blocks.add(new Block(startRow1, startCol1, length, HORIZONTAL));
					}
				}
			}
		}
		int startRow2;
		int startCol2;
		for(int j = 0; j < desc[0].length; j++) {
			for( int i = 0; i < desc.length; i++) {
				if(desc[i][j].equals("^")) {
					startRow2 = i;
					startCol2 = j;
					int temp = i+1;
					while(desc[temp][j].equals("#")) {
						temp++;
					}
					if(desc[temp][j].equals("v")) {
						int length = temp - i + 1;
						blocks.add(new Block(startRow2, startCol2,length, VERTICAL));
					}
				}
			}
		}
		return blocks;
	}
}
