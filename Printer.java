 package hw1;
/**
 * Creating a class called Printer, that creates a simple printer
 * @author kausshik
 *
 */
public class Printer {
	
// INSTANCE VARIABLES
	/**
	 * maximumTrayCapacity is the maximum capacity or number of sheets the printer's tray can hold
	 */
	private int maximumTrayCapacity;
	/**
	 * sheetsAvailableForPrinting is the total sheets available to the printer which can be used for printing
	 */
	private int sheetsAvailableForPrinting;
	/**
	 * sheetsInTray is the total in the tray at any given time
	 */
	private int sheetsInTray;
	/**
	 * numberOfPagesInDocument is the number of pages in the document that is given to the printer to print
	 */
	private int numberOfPagesInDocument;
	/**
	 * numberOfPagesPrinted is the total number of pages printed by the printer thus far
	 */
	private int numberOfPagesPrinted;
	/**
	 * nextPageNumber is the next page number of the page that is about to be printed
	 */
	private int nextPageNumber;
	
// CONSTRUCTORS
	
	/**
	 * Constructor: initialises the printer
	 * @param trayCapacity
	 * Sets/Initializes the maximumTrayCapacity, sets sheetsAvailableForPrinting, sheetsAvailableForPrinting, nextPageNumber equal to ZERO 
	 */
	public Printer(int trayCapacity) {
		maximumTrayCapacity = trayCapacity;
		sheetsAvailableForPrinting = 0;
		sheetsInTray = 0;
		nextPageNumber = 0;
	}
	
// METHODS
	
	/**
	 * This method (getSheetsAvailable()) returns total sheets available to the printer which can be used for printing (sheetsAvailableForPrinting)
	 * @return sheetsAvailableForPrinting
	 */
	public int getSheetsAvailable() {
		return sheetsAvailableForPrinting;
	}
	
	/**
	 * This method (getTotalPages()) returns the total number of pages printed thus far (numberOfPagesPrinted)
	 * @return numberOfPagesPrinted
	 */
	public int getTotalPages() {
		return numberOfPagesPrinted;
	}
	
	/**
	 * This method (startPrintJob(int documentPages)) 
	 * @param documentPages
	 * numberOfPagesInDocument is given the value of documentPages
	 * nextPageNumber is initialized to ZERO
	 */
	public void startPrintJob(int documentPages) {
		numberOfPagesInDocument = documentPages;
		nextPageNumber = 0;
	}
	
	/**
	 * This method (getNextPage()) returns the page number of the next sheet that is going to  be printed
	 * @return nextPageNumber
	 */
	public int getNextPage() {
		return nextPageNumber;
	
	}
	
	/**
	 * This method (printPage()) simulates the printing of a page
	 * temporaryVariable1 is the variable that controls by how much the sheetsInTray, sheetsAvailableForPrinting, nextPageNumber, numberOfPagesPrinted is incremented
	 * It can either be ZERO or ONE, because it should increase by 0 if the sheetsAvailableForPrinting is ZERO, and 1 if the sheetsAvailableForPrinting is anything other than zero
	 * over here nextPageNumber also wraps around i.e., it goes back to 0 from page number (numberOfPagesInDocument-1)
	 */
	public void printPage() {
		int temporaryVariable1= Math.min(1, Math.max(0,  sheetsAvailableForPrinting));
		sheetsInTray -= temporaryVariable1 ;
		sheetsAvailableForPrinting -= temporaryVariable1;
		nextPageNumber += temporaryVariable1 ;
		nextPageNumber = nextPageNumber%(numberOfPagesInDocument);
		numberOfPagesPrinted += temporaryVariable1;	               
		
	}
	
	/**
	 * This method (removeTray()) simulates the removal of the printer's tray from the printer 
	 * sets the sheetsInTray as sheetsAvailableForPrinting
	 * and it then makes the sheetsAvailableForPrinting equal to ZERO, because when the tray is removed the sheets that can be printed would reduce to zero
	 */
	public void removeTray() {
		sheetsInTray = sheetsAvailableForPrinting;
		sheetsAvailableForPrinting = 0;
	}
	
	/**
	 * This method (replaceTray()) simulates the process of placing the removed printer tray, that holds the sheets, back into the printer
	 * this resets the value of sheetsAvailableForPrinting from ZERO to the sheetsInTray
	 */
	public void replaceTray() {
		sheetsAvailableForPrinting = sheetsInTray;
	}
	
	/**
	 * This method simulates the process of adding a given number of sheets to the printer's tray
	 * this method also ensures that the total number of sheets after the addition does not exceed the printers maximum capacity
	 * It caps off the maximum number of sheets one can have in the printing tray
	 * both sheetsInTray and sheetsAvailableForPrinting are updated to the same new value
	 * @param numberOfSheetsToBeAdded
	 */
	public void addPaper(int numberOfSheetsToBeAdded) {
		sheetsInTray = Math.min(numberOfSheetsToBeAdded + sheetsInTray, maximumTrayCapacity);
		sheetsAvailableForPrinting = sheetsInTray;
	}
	
	/**
	 * This method simulates the process of removing a given number of sheets from the printer's tray
	 * This method also ensures that the total number of sheets after the addition does not exceed the printers maximum capacity
	 * It caps off the minimum number of sheets one can have in the printing tray to ZERO, and does not let it become negative
	 * @param numberOfSheetsToBeRemoved
	 */
	public void removePaper(int numberOfSheetsToBeRemoved) {
		sheetsInTray = Math.max(sheetsInTray - numberOfSheetsToBeRemoved, 0);
		sheetsAvailableForPrinting = sheetsInTray;
	}
	
}
