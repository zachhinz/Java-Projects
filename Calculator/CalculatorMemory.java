package calculator;

import java.util.LinkedList;

public class CalculatorMemory{
	
	LinkedList<Integer> ll = new LinkedList<Integer>();
	
	/**
	 * Add number to memory
	 * Must run O(1)
	 * 
	 * @param number
	 */
	public void push(int number) {
		ll.addFirst(number);
	}
	
	/**
	 * return and remove most recently pushed value
	 * @return
	 */
	public int pop() {
		return ll.remove();
	}
	
	/**
	 * returns boolean on whether Memory is empty
	 * @return
	 */
	public boolean isEmpty() {		
		return ll.size()==0;
	}
	
	
	/**
	 * returns memory size
	 * @return integer
	 */
	public int size() {
		return ll.size();
	}
	
	/**
	 * clears stack
	 */
	public void clear() {
		ll.clear();
	}
	
	public int get(int index) {
		return ll.get(index);
	}
	
	/**
	 * returns string of numbers
	 */
	public String toString() {
		String theString = new String();
		theString +="\nMemory Contents: ";
		
		
		for(int i=0;i<ll.size();i++) {
			theString = theString +"\n"+ ll.get(i).toString();		
		}
		
		theString += "\n---";
		
		
		return theString;
	}
}
