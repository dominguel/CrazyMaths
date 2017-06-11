package mathEval;

import java.util.LinkedList;

public class Evaluator {

	//rules:
	//expressions must be valid
	//expressions must contain only operators from the list
	//passive operators must be treated as functions and be followed by brackets

	//operators between numbers: + - * / ^ rt(abreviated as r) %
	//passive operators: sqrt, sqr, inv, trig
	//{sqrt,sqr, inv, sin, cos, tan, sec, csc, cot, arcsin, arccos, arctan, arcsec, arccsc, arccot}
	//brackets
	
	//12+34+5^(6-sin(7-8+1)*9) should give 15671

	public static Double evaluate(LinkedList<Object> expression) {

//		Iterator<Object> itExp = expression.iterator();
//		while(itExp.hasNext()) {
//			System.out.print(itExp.next().toString());
//		}
//		System.out.println();
		//method summary:
		//divide expression in chunks
		//look for brackets, separate into sub expressions
		//by expression level
		//any passive operators
		//any operators, by descending priority order
		
		//recursively evaluate any sub-expresisons
		for(int i = 0; i < expression.size(); i++) {
			if(expression.get(i).getClass() == expression.getClass()) {
				@SuppressWarnings("unchecked")
				LinkedList<Object> temp = (LinkedList<Object>) expression.remove(i);
				expression.add(i, (Double) evaluate(temp));
			}
		}

		//evaluate the local sub-expression
		//start with any passive operators
		for(int i = 0; i < expression.size(); i++) {
			if(expression.get(i).getClass() == String.class) {
				String pOp = (String) expression.remove(i);
				Double val = 0.0;
				if(expression.get(i).getClass() == Double.class) {
					val = (Double) expression.remove(i);
				} else {
					System.out.println("error 43 at Evaluation");
					System.exit(0);
				}
				expression.add(i, calculate(pOp, val));
			}
		}

		//and then do active operators, in order of priority
		int cPriority = 2;
		while(expression.size() != 1) {
			for(int i = 1; i < expression.size(); i+=2) {
				
				if(expression.get(i).getClass() != Character.class) {
					System.out.println("error 55 at Evaluation");
					System.exit(0);
				}
				
				if(getOpPriority((Character) expression.get(i)) == cPriority) {
					Double val0 = (Double) expression.remove(i-1);
					Character op = (Character) expression.remove(i-1);
					Double val1 = (Double) expression.remove(i-1);
					expression.add(i-1, calculate(val0, op, val1));
					i-=2;
				}
			}
			cPriority--;
		}

		return (Double) expression.get(0);
	}

	public static double calculate(String passive, double val) {

//		System.out.println(passive + val);
		switch(passive) {
		case "sin":
			return Math.sin(val);
		case "cos":
			return Math.cos(val);
		case "tan":
			return Math.tan(val);
		case "arcsin":
			return Math.asin(val);
		case "arccos":
			return Math.acos(val);
		case "arctan":
			return Math.atan(val);
		default:
			System.out.println(passive + " is not a recognized operator");
			System.exit(0);
		}
		return 0;
	}

	public static double calculate(double val0, char op, double val1) {

//		System.out.println(val0+" " +op+" "+val1);
		switch(op) {
		case '+':
			return val0+val1;
		case '-':
			return val0-val1;
		case '*':
			return val0*val1;
		case '/':
			return val0/val1;
		case '^':
			return Math.pow(val0, val1);
		case '%':
			return val0%val1;
		}

		return 0;
	}

	public static LinkedList<Object> primeExp(String validExp) {

		//divides expression into Character, Doubles and sub-expressions
		LinkedList<Object> exp = new LinkedList<Object>();
		String waitPOp = "";
		String waitDouble = "";

		for(int i = 0; i < validExp.length(); i++) {

			//is part of a number
			if("0123456789.".indexOf(validExp.charAt(i)) != -1) {

				//collapse any passive operators into exp
				if(!waitPOp.equals("")) {
					exp.add(waitPOp);
					waitPOp = "";
				}

				//add to waiting number list
				waitDouble += validExp.charAt(i);

			} else
				//is the start of a sub-expression
				if(validExp.charAt(i) == '(') {

					//collapse any passive operators into exp
					if(!waitPOp.equals("")) {
						exp.add(waitPOp);
						waitPOp = "";
					}

					//create new expression
					LinkedList<Object> subExp;

					//parse for the closing bracket (could be better using substring()
					String validSubExp = "";
					int bracketVal = 1;
					for(i++; i < validExp.length(); i++) {
						if(validExp.charAt(i) == '(') {
							bracketVal++;
						} else if(validExp.charAt(i) == ')') {
							bracketVal--;
						}

						if(bracketVal == 0) {
							break;
						}

						validSubExp += validExp.charAt(i);
					}

					//use String parsed to primeExp() the new expression
					subExp = primeExp(validSubExp);

					//put the sub-expression in exp
					exp.addLast(subExp);

				} else
					//is an active operator
					if(getOpPriority(validExp.charAt(i)) != -1) {

						//collapse any waiting numbers into exp
						if(!waitDouble.equals("")) {
							exp.add(Double.parseDouble(waitDouble));
						}
						waitDouble = "";

						//put the operator in exp
						exp.add((Character) validExp.charAt(i));
					}

			//is part of a passive operator(else)
					else {
						//add to waiting passive operator list
						waitPOp += validExp.charAt(i);
					}
		}

		if(!waitDouble.equals("")) {
			exp.add(Double.parseDouble(waitDouble));
		}

		return exp;
	}

	public static int getOpPriority(char c) {

		switch(c) {
		case '+': case '-':
			return 0;
		case '*': case '/': case '%':
			return 1;
		case '^': case 'r':
			return 2;
		default:
			return -1;
		}
	}
}