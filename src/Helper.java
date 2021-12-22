
public class Helper {
	
	/**
	 * Simple class for non-tree related methods that simplify some parts of the program
	 * 
	 * @author	Jordan Edoimioya
	 */

	
	public static String[][] copyState(String[][] original) {

		/**
		 * Creates duplicate array by copying all elements of one array into another
		 * 
		 * @param original the source array to be duplicated
		 * @return new copy of source array
		 */

		String[][] duplicate = new String[original.length][original[0].length];
		for (int i = 0; i < original.length; i++) {
			for (int j = 0; j < original[i].length; j++) {
				duplicate[i][j] = original[i][j];
			}
		}

		return duplicate;
	}

	
	public static int hashOf(String[][] childState) {
		
		/**
		 * Creates unique hashed value for each possible state by assigning each letter to a value
		 * 
		 * @param	childState	input state 2D array to create hash for
		 * @returns				unique 10-digit number for each state
		 */
		
		String hashValue = "1";
		for (int i = 0; i < childState.length; i++) {
			for (int j = 0; j < childState[i].length; j++) {
				if (childState[i][j].equals("a"))
					hashValue += Integer.toString(1);
				if (childState[i][j].equals("b"))
					hashValue += Integer.toString(2);
				if (childState[i][j].equals("c"))
					hashValue += Integer.toString(3);
				if (childState[i][j].equals("d"))
					hashValue += Integer.toString(4);
				if (childState[i][j].equals("e"))
					hashValue += Integer.toString(5);
				if (childState[i][j].equals("f"))
					hashValue += Integer.toString(6);
				if (childState[i][j].equals("g"))
					hashValue += Integer.toString(7);
				if (childState[i][j].equals("h"))
					hashValue += Integer.toString(8);
				if (childState[i][j].equals("_"))
					hashValue += Integer.toString(0);
			}
		}
		
		return Integer.parseInt(hashValue);
	}

}
