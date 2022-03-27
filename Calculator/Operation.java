package calculator;

public class Operation {
	
	public static int performOperation(char op, int left, int right) {
		
		/*
		if(op=='/' && right == 0) {
			throw new ArithmeticException("Attempted to divide by zero");
		}
		*/
		
//		try {
			if(op=='+') {
				return left+right;
			}else if(op=='-') {
				return right-left;
			}else if(op=='*') {
				return left*right;
			}else if(op=='/') {
				return right/left;
			}
			
			/*
		}catch(IllegalArgumentException e){
			System.out.println("Inputted argument is not valid");
		} */
		
		return -1;
	}
}