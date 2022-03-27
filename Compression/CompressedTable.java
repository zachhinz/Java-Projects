/** 
 * Class representing an efficient implementation of a 2-dimensional table 
 * when lots of repeated entries as a doubly linked list. Idea is to record entry only when a 
 * value changes in the table as scan from left to right through 
 * successive rows.
 * 
 * @author cs62
 * @param <E> type of value stored in the table
 */
package compression;

class CompressedTable<E> implements TwoDTable<E> {
	// List holding table entries - do not change
	// We've made the variables protected to facilitate testing (grading)
	protected CurDoublyLinkedList<Association<RowOrderedPosn, E>> tableInfo;
	protected int numRows, numCols; // Number of rows and cols in table
	protected E defaultValue;

	/**
	 * Constructor for table of size rows x cols, all of whose values are initially
	 * set to defaultValue
	 * 
	 * @param rows
	 *            # of rows in table
	 * @param cols
	 *            # of columns in table
	 * @param defaultValue
	 *            initial value of all entries in table
	 */
	public CompressedTable(int rows, int cols, E defaultValue) {
		numRows = rows;
		numCols = cols;
		
		RowOrderedPosn newTable = new RowOrderedPosn(0,0,numRows,numCols);
		Association<RowOrderedPosn, E> assoc = new Association<RowOrderedPosn, E>(newTable,defaultValue);
		
		tableInfo = new CurDoublyLinkedList<Association<RowOrderedPosn, E>>();
		
		tableInfo.addFirst(assoc);
		
		this.defaultValue = defaultValue;
	}

	/**
	 * Given a (x, y, rows, cols) RowOrderedPosn object, it searches for it in the
	 * table which is represented as a doubly linked list with a current pointer. If
	 * the table contains the (x,y) cell, it sets the current pointer to it.
	 * Otherwise it sets it to the closest cell in the table which comes before that
	 * entry.
	 * 
	 * e.g., if the table only contains a cell at (0,0) and you pass the cell (3,3)
	 * it will set the current to (0,0).
	 */
	private void find(RowOrderedPosn findPos) {
		tableInfo.first();
		Association<RowOrderedPosn, E> entry = tableInfo.currentValue();
		RowOrderedPosn pos = entry.getKey();
		while (!findPos.less(pos)) {
			// search through list until pass elt looking for
			tableInfo.next();
			if (tableInfo.isOff()) {
				break;
			}
			entry = tableInfo.currentValue();
			pos = entry.getKey();
		}
		tableInfo.back(); // Since passed desired entry, go back to it.
	}

	/**
	 * Given a legal (row, col) cell in the table, update its value to newInfo. 
	 * 
	 * @param row
	 *            row of cell to be updated
	 * @param col
	 *            column of cell to be update
	 * @param newInfo
	 *            new value to place in cell (row, col)
	 */
	public void updateInfo(int row, int col, E newInfo) {
		//exception checking
		if(row>=numRows || col >= numCols) {
			throw new IllegalArgumentException("Row or Column does not exist in current table");
		}
		
		RowOrderedPosn inputPosition = new RowOrderedPosn(row,col,numRows,numCols);
		RowOrderedPosn nextPosition = inputPosition.next();
		
		//checks if this table entry already has this value
		if(!this.getInfo(row, col).equals(newInfo)) {
			
			Association<RowOrderedPosn, E> newEntry = new Association<RowOrderedPosn, E>(inputPosition, newInfo);
			E previousItem = this.getInfo(row, col);
			
			if(this.doesAssociationExist(row, col)){
				//if the list item exists, find and replace item
				this.find(inputPosition);
				tableInfo.currentValue().setValue(newInfo);
				
				//if the next position is not null and there is not a list time for it, create new list item to retain order
				if(nextPosition!=null && !this.doesAssociationExist(nextPosition.getRow(), nextPosition.getCol())) {
					tableInfo.addAfterCurrent(new Association<RowOrderedPosn, E>(nextPosition, previousItem));
				}
				
			}else{
				//if list item does not exist, create new item
				tableInfo.addAfterCurrent(newEntry);
				
				//if entry is not at the end of table or does not already have an entry, make a new list item
				if((row+1!=numRows||col+1!=numCols)&&
						(!this.doesAssociationExist(nextPosition.getRow(), nextPosition.getCol()))) {
					tableInfo.addAfterCurrent(new Association<RowOrderedPosn, E>(nextPosition, previousItem));
				}
			}
		}
		
		//clean excess list links, goes through list and removes unneeded nodes
		//not the most efficient method but it works
		tableInfo.current = tableInfo.first.next;
		for(int i = 1;i<tableInfo.size();i++) {
			E prevItem = tableInfo.current.prev.item.theValue;
			E currentItem = tableInfo.current.item.theValue;
			if(currentItem.equals(prevItem)) {
				tableInfo.removeCurrent();
				i--;
			}else {
				tableInfo.current=tableInfo.current.next;
			}
		}
	}

	/**
	 * Returns contents of specified cell
	 * 
	 * @pre: (row,col) is legal cell in table
	 * 
	 * @param row
	 *            row of cell to be queried
	 * @param col
	 *            column of cell to be queried
	 * @return value stored in (row, col) cell of table
	 */
	public E getInfo(int row, int col) {
		if(row>=numRows || col >= numCols) {
			throw new IllegalArgumentException("Row or Column does not exist in current table");
		}
		
		RowOrderedPosn newPosition = new RowOrderedPosn(row,col,numRows,numCols);
		this.find(newPosition);

		return tableInfo.current.item.theValue;
	}
	
	/**
	 * 
	 * @param row
	 *            row of cell to be queried
	 * @param col
	 *            column of cell to be queried
	 * @return boolean regarding if there is a node item with this row/col key
	 */
	public boolean doesAssociationExist(int row, int col) {
		if(row>=numRows || col >= numCols) {
			throw new IllegalArgumentException("Row or Column does not exist in current table");
		}
		
		for(int i = 0;i<tableInfo.size();i++) {
			if(tableInfo.get(i).theKey.equals(new RowOrderedPosn(row,col,numRows,numCols))){
				return true;
			}
		}
		
		return false;
	}

	/**
	 *  @return
	 *  		 succinct description of contents of table
	 */
	public String toString() { // do not change
	    return tableInfo.otherString();
	}

	public String entireTable() { //do not change
		StringBuilder ans = new StringBuilder("");
		for (int r = 0; r<numRows; r++) {
			for (int c = 0; c< numCols; c++) {
				ans.append(this.getInfo(r, c));
			}
			ans.append("\n");
		}
		return ans.toString();

	}

	/**
	 * program to test implementation of CompressedTable
	 * @param args
	 * 			ignored, as not used in main
	 */
	public static void main(String[] args) {
		
		// add your own tests to make sure your implementation is correct!!
		CompressedTable<String> table = new CompressedTable<String>(5, 5, "r");
		table.updateInfo(0, 3, "g");
		System.out.println("table is " + table);
		table.updateInfo(4, 4, "g");
		System.out.println("table is " + table);
		table.updateInfo(4, 4, "b");
		System.out.println("table is " + table);
		table.updateInfo(0, 2, "b");
		System.out.println("table is " + table);
		table.updateInfo(0, 2, "g");
		System.out.println("table is " + table);
		table.updateInfo(0, 3, "b");
		System.out.println("table is " + table);
		table.updateInfo(1, 1, "b");
		System.out.println("table is " + table);
		table.updateInfo(1, 2, "b");
		System.out.println("table is " + table);
		table.updateInfo(1, 3, "b");
		System.out.println("table is " + table);
		table.updateInfo(4, 4, "r");
		System.out.println("table is " + table);
		table.updateInfo(4, 4, "e");
		System.out.println("table is " + table);
		
		table.updateInfo(0, 0, "e");
		System.out.println("table is " + table);
	}

}