import java.util.*;

/**
 * This class represents a tree template, where each node is a 3x3 grid from the 8-puzzle problem.
 * Each grid has 8 letters (a-h) with one square left blank, modelled by "_"
 * 
 * @author	Jordan Edoimioya
 */

public class Grid {
	
	// FIELDS
	
    private String[][] gridState;
    private ArrayList<Grid> children;
    private Grid parent;
    private int[] emptySquare = new int[2]; 	// position given as [row, column]
    private int depth;
    private Direction lastMove;
    
	
    
    // CONSTRUCTOR
    
    public Grid(String[][] gridState, ArrayList<Grid> children, Grid parent, int[] emptySquare, int depth, Direction lastMove) {
    	
    	this.gridState = gridState;
    	this.children = children;
    	this.parent = parent;
    	this.emptySquare = emptySquare;
    	this.depth = depth;
    	this.lastMove = lastMove;
    	
    }
    
    
    // SETTERS
	
 	public void setDepth(int depth) {
 		this.depth = depth;
 	}

    
    // GETTERS
    
    public String[][] getGridState() {
		return gridState;
	}
	
    public ArrayList<Grid> getChildren(Grid g) {
    	
    	/**
    	 * Generates a list of children for a given grid by getting possible moves and rearranging the empty square
    	 * 
    	 * @param	g	input grid
    	 * @returns		array list of children for g
    	 */
    	
    	String[][] currentState = Helper.copyState(getGridState()); // original states & resulting states
    	String[][] upResult = Helper.copyState(getGridState());
    	String[][] downResult = Helper.copyState(getGridState());
    	String[][] leftResult = Helper.copyState(getGridState());
    	String[][] rightResult = Helper.copyState(getGridState());
		
    	Grid newGrid;												// resulting grid
    	
		int[] emptySquare = g.emptySquare;   						// empty square values
		int x = emptySquare[0];
		int y = emptySquare[1];
		
		ArrayList<Direction> legalMoves = g.getLegalMoves(emptySquare);
		g.children = new ArrayList<Grid>();
		
		Direction lastMove = g.getLastMove();						// prevent immediate duplicates
		if (lastMove != null) {
			switch (lastMove) {
			case UP:
				legalMoves.remove(Direction.DOWN);
				break;
			case DOWN:
				legalMoves.remove(Direction.UP);
				break;
			case LEFT:
				legalMoves.remove(Direction.RIGHT);
				break;
			case RIGHT:
				legalMoves.remove(Direction.LEFT);
				break;
			}
		}
				
		for (Direction d : legalMoves) {
			
			switch(d) {
			case UP:
				upResult[x][y] = currentState[x-1][y];				// moves letter
				upResult[x-1][y] = "_";								// moves empty square
				newGrid = new Grid(upResult,null, g, getEmptySquare(upResult), g.getDepth()+1, d);	// result grid
				g.children.add(newGrid);
				newGrid = new Grid(currentState, null, g, getEmptySquare(currentState), g.getDepth(), d); // reset grid to original				
				break;
				
			case DOWN:
				downResult[x][y] = currentState[x+1][y];
				downResult[x+1][y] = "_";
				newGrid = new Grid(downResult, null, g, getEmptySquare(downResult), g.getDepth()+1, d);
				g.children.add(newGrid);
				newGrid = new Grid(currentState, null, g, getEmptySquare(currentState), g.getDepth(), d);
				break;
				
			case LEFT:
				leftResult[x][y] = currentState[x][y-1];
				leftResult[x][y-1] = "_";
				newGrid = new Grid(leftResult, null, g, getEmptySquare(leftResult), g.getDepth()+1, d);
				g.children.add(newGrid);
				newGrid = new Grid(currentState, null, g, getEmptySquare(currentState), g.getDepth(), d);
				break;

			case RIGHT:
				rightResult[x][y] = currentState[x][y+1];
				rightResult[x][y+1] = "_";
				newGrid = new Grid(rightResult, null, g, getEmptySquare(rightResult), g.getDepth()+1, d);
				g.children.add(newGrid);
				newGrid = new Grid(currentState, null, g, getEmptySquare(currentState), g.getDepth(), d);
				break;
				
			default:
				break;
				
			}
		}
		
		g.setDepth(g.getDepth()+1);									// increment depth
		
		return g.children;
	}
	
	
	public static int[] getEmptySquare(String[][] state) {
		
		/**
		 * Locates position of the empty square on a given state.
		 * 
		 * @param	state	input state to search
		 * @returns			integer array: [row, column]
		 */
		
		int[] emptySquare = new int[2]; 							// new empty array
		for (int i = 0; i < state.length; i++) {					// loop through every value to find "_"
			for (int j = 0; j < state[i].length; j++) {
				if (state[i][j].equals("_")) {
					emptySquare[0] = i;
					emptySquare[1] = j;
				}	
			}
		}
 		
		return emptySquare;
	}
	
	
	public ArrayList<Direction> getLegalMoves(int[] emptySquare) {
		
		/**
		 * Outputs the next possible moves within a grid, based on the position of the empty square
		 * 
		 * @param	emptySquare	[row, column] location within state
		 * @return 				array list of possible moves
		 */
		
		ArrayList<Direction> legalMoves = new ArrayList<Direction>();
		int x = emptySquare[0];
		int y = emptySquare[1];
		
		if (x == 1 || x == 2)
			legalMoves.add(Direction.UP);
		if (x == 0 || x == 1)
			legalMoves.add(Direction.DOWN);
		if (y == 0 || y == 1)
			legalMoves.add(Direction.RIGHT);
		if (y == 1 || y == 2)
			legalMoves.add(Direction.LEFT);
		
		return legalMoves;
	}
	
	public int getDepth() {
		return depth;
	}
	
	public Direction getLastMove() {
		return lastMove;
		
	}
	
	@Override
	public String toString() {
		
		/**
		 * Displays any given state as a (m x n) grid
		 * 
		 * @returns		formatted string
		 */
		
		String result = "";
		for (int i = 0; i < this.gridState.length; i++) {
			for (int j = 0; j < this.gridState[i].length; j++) {
				result += this.gridState[i][j] + "  ";
				if (j >= this.gridState[i].length-1)
					result += "\n";
			}
		}
		return result;
	}
	
	
	public static HashMap<Integer, Grid> depthFirstTree(Grid root) {
		
		/**
		 * Applies DFS to generate a tree of distinct reachable states from a given state.
		 * 
		 * @param	root	initial grid to be expanded
		 * @returns			hash map of all distinct reachable states from the root: {hash, grid}
		 */
		
		Stack<Grid> stack = new Stack<Grid>();						// initialise empty stack
		HashSet<Integer> visitedStates = new HashSet<Integer>();	// empty set of visited states
		HashMap<Integer, Grid> visitedGrids = new HashMap<Integer, Grid>();	// empty map of visited grid objects
		stack.push(root);											// push first grid
		visitedStates.add(Helper.hashOf(root.getGridState()));		// store hash of grids in a set
		visitedGrids.put(Helper.hashOf(root.getGridState()), root);
				
		while (!stack.isEmpty()) {
			Grid current = stack.pop();								
			ArrayList<Grid> children = current.getChildren(current);
			
			for (Grid child : children) {
				if(visitedStates.add(Helper.hashOf(child.getGridState()))) { // if child is not in visited
					stack.push(child);
					visitedStates.add(Helper.hashOf(child.getGridState()));  // add hash of child to visited
					visitedGrids.put(Helper.hashOf(child.getGridState()),child); // store {hash, grid} pair in map
				}
			}
		}
		return visitedGrids;
	}

}
