import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class Main {

	public static void main(String[] args) {
		
		
		// INPUT COLLECTION
		
		Scanner input = new Scanner(System.in);
		
		// input states
		String[][] s1 = new String[3][3];
		String[][] s2 = new String[3][3]; 
		
		// array for each row
		String[] rows_s1 = new String[3]; 
		String[] rows_s2 = new String[3];
		
		System.out.println("ENTER S1");
		for (int i = 0; i < 3; i++) {	
			System.out.print("row " + (i+1) + " values: ");
			rows_s1 = input.nextLine()
					.toLowerCase()
					.replaceAll("\\s+","")								// remove extra spaces
					.split(","); 										// create list from input string
			s1[i] = rows_s1; 											// set i'th row to list of strings
		}
		
		System.out.println("\nENTER S2");
		for (int i = 0; i < 3; i++) {	
			System.out.print("row " + (i+1) + " values: ");
			rows_s2 = input.nextLine().toLowerCase().replaceAll("\\s+","").split(",");
			s2[i] = rows_s2;
		}
		
		input.close();
		
		
		// CREATE GRIDS
			
		Grid grid_s1 = new Grid(s1, null, null, Grid.getEmptySquare(s1), 0, null);
		Grid grid_s2 = new Grid(s2, null, null, Grid.getEmptySquare(s2), 0, null);
		
		
		
		// OUTPUTS
		
		Integer sizeRS1, sizeRS2, sizeRS1RS2;
		
		System.out.println("\nS1:\n" + grid_s1.toString());
		System.out.println("\nS2:\n" + grid_s2.toString());
		
		System.out.println("\nCalculating R(S1) ...\n");			// R(S1)
		HashMap<Integer, Grid> RS1 = Grid.depthFirstTree(grid_s1);	// return map of hash, grid pairs 
		sizeRS1 = RS1.size();										// store number of reachable states
		System.out.println("\nR(S1):\n");
		for (Grid grid : RS1.values()) {							// print grid for every hash value
			System.out.println(grid.toString());
		}
		
		System.out.println("Calculating R(S2) ...\n");				// R(S2)
		HashMap<Integer, Grid> RS2 = Grid.depthFirstTree(grid_s2);
		sizeRS2 = RS2.size();
		System.out.println("\nR(S2):\n");
		for (Grid grid : RS2.values()) {
			System.out.println(grid.toString());
		}
		
		
		System.out.println("\nFinding common states ...");			// R(S1) & R(S2)
		Set<Integer> intersection1 = RS1.keySet();					// create new set of all hashes
		intersection1.retainAll(RS2.keySet());						// only retain hashes present in S1 & S2
		sizeRS1RS2 = intersection1.size();
		System.out.println("\nR(S1) & R(S2):\n");
		for (Integer hash : intersection1) {						// for every hash in the hash set
			System.out.println(RS1.get(hash).toString());			// get & print the value (grid)
		}
		if(!(intersection1.size() > 0))
			System.out.println("No common states found\n");
	
		System.out.println("|R(S1)| = " + sizeRS1); 				// |R(S1)|
		System.out.println("|R(S2)| = " + sizeRS2);					// |R(S2)|
		System.out.println("|R(S1) & R(S2)| = " + sizeRS1RS2);		// |R(S1) & R(S2)|
		
		
		
		// USING FIGURE 2, 3 (4iii)
		
		String[][] figure2 = new String[][] {{"a","b","c"},{"d","e","f"},{"g","h","_"}};
		String[][] figure3 = new String[][] {{"_","e","b"},{"a","h","c"},{"d","g","f"}};

		Grid grid_fig2 = new Grid(figure2, null, null, Grid.getEmptySquare(figure2), 0, null);
		Grid grid_fig3 = new Grid(figure3, null, null, Grid.getEmptySquare(figure3), 0, null);
		System.out.println("\n_____\n\nPart iii\n");
		
		System.out.println("\nFigure 2:\n" + grid_fig2.toString());
		System.out.println("\nFigure 3:\n" + grid_fig3.toString());
		RS1 = Grid.depthFirstTree(grid_fig2);
		RS2 = Grid.depthFirstTree(grid_fig2);
		Set<Integer> intersection2 = RS1.keySet();
		System.out.println("|R(S1)| = " + RS1.size());
		System.out.println("|R(S2)| = " + RS2.size());
		intersection2.retainAll(RS2.keySet());
		System.out.println("|R(S1) & R(S2)| = " + intersection2.size());
	}

}
