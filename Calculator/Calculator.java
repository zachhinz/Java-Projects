package calculator;
import java.util.Scanner;

public class Calculator {
	
	CalculatorMemory CalcMem;
	Scanner scanner;
	String next;
	
	/**
	 * empty constructor that sets up Calculator Memory and scanner
	 */
	public Calculator() {
		CalcMem = new CalculatorMemory();
		scanner = new Scanner(System.in); 
//		Operation Operation = new Operation();
	}
	
	/**
	 * runs program
	 */
	public void run() {
		//Creates Scanner
		System.out.print("Enter a number or operator: ");
		next = scanner.nextLine();
		
		while(!next.equals("quit")&&!next.equals("Quit")) {
			//call performOperation on top two things of the stack
			
			//clear function
			if(next.equals("clear")) {
				CalcMem.clear();
			}
			
			//pop function
			else if(next.equals("pop")||next.equals("Pop")) {
				
				//pop requires an argument
				if(CalcMem.size()>0) {
					System.out.println(CalcMem.pop());
				}else {
					System.out.println("Error: pop requires one argument");
				}
				
			}
			
			//looks for input operations
			else if(next.equals("+")||next.equals("-")||next.equals("*")||next.equals("/")) {
				
				//handles two argument requirements
				if(CalcMem.size()>1) {
					//Handles dividing by 0
					if(next.equals("/") && CalcMem.get(0)==0) {
						System.out.println("Error: divide by zero");
					}else{
						//pushes first two numbers together based on entered operation
						int opInt = Operation.performOperation(next.charAt(0), CalcMem.pop(), CalcMem.pop());
						CalcMem.push(opInt);
						System.out.println("Answer: "+Integer.toString(opInt));
					}
					
				}else {
					System.out.println("Error: operator requires two arguments");
				}
			}
			
			//handles non-operator or non-numerical entries
			else if(!next.equals("quit")&&!this.isInteger(next)&&!next.equals("pop")&&!next.equals("+")&&!next.equals("-")&&!next.equals("*")&&!next.equals("/")) {
				System.out.println("Error: expected number or operator");
				
			//adds integer to the stack
			}else if(this.isInteger(next)) {
				CalcMem.push(Integer.parseInt(next));
			}
			
			//Prints Memory Contents
			System.out.println(CalcMem.toString());
			
			
			//Calculator Asks for input again at the end of loop
			System.out.print("\nEnter a number or operator: ");
			next = scanner.nextLine();
		}
		
		//Ends the Calculator Program
		System.out.println("Program Ended.");
		scanner.close();
	}
	
	/**
	 * tells you if the string is really an integer
	 * @param 
	 * @return boolean
	 */
	public boolean isInteger(String s) {
		  try { 
		      Integer.parseInt(s); 
		   } catch(NumberFormatException e) { 
		      return false; 
		   }
		   // only got here if we didn't return false
		   return true;
		}
	
	//Main method runs program
	public static void main(String args[]) {
		Calculator cal = new Calculator();
		cal.run();
	}
}
