import java.util.ArrayList;
import java.util.Random;

/**
 * A class to represent a series of lines connecting points in a 2D space.
 * The methods and variables in this class aim to build a program which can consistently generate from a series of points a polygon, or, more technically described,
 * a Hamilton cycle with no intersections. 
 * 
 * @author Felipe Valverde
 * @author Murilo Rosa
 * 
 */
class Candidate extends ArrayList<Coordinate> {
	
	private static final long serialVersionUID = 1L;
	Candidate parent;
	private int intersectionCount;
//	IntersectionList intersected;
	/**
	 * A List of lists which keep all the candidates obtainable from one single 2-exchange within the current candidate.
	 */
	NeighbourList neighbours;

	/**
	 * Constructor for the class Candidate
	 * @param parent The arrayList that serves as the starting point for generating the initial candidate.
	 */
	public Candidate(ArrayList<Coordinate> parent){
		super(parent);
		parent = null;
		intersectionCount = -1;
		neighbours = null;
	}

	/**
	 * Constructor for a child of the class Candidate.
	 * @param parent The candidate which we want our new candidate to be a son of.
	 */
	public Candidate(Candidate parent){
		super(parent);
		this.parent = parent;
		intersectionCount = -1;
		neighbours = null;

//		System.out.println("Candidate by (Candidate)");
	}

	/**
	 * Constructor for a Candidate which takes the selected method as an input.
	 * @param parent The list of coordinates to build the Candidate.
	 * @param generator The choice of method (1 for random, 2 for nearest neighbour).
	 */
	public Candidate(ArrayList<Coordinate> parent, byte generator){
		super(parent.size());
		this.parent = new Candidate(parent);

		ArrayList<Coordinate> base; 
		Coordinate cur = null;
		base = new ArrayList<>(parent);
		switch(generator){
			case 1:		// generate by Random Permutation
				while (base.size() > 0) {
					int i = new Random().nextInt(base.size());
					this.add(base.remove(i));
				}
				break;
			case 2:		// generate by Nearest Neighbour
				cur = base.remove(0);
			case 3:
				if(cur ==null) cur = base.remove( (new Random()).nextInt(this.size()) );
				while (cur != null) {
					this.add(cur);
					cur = findNearest(cur, base);
				}
				break;
			default:		// preferably throw Exception
				intersectionCount = -1;
				return;
		}

		intersectionCount = -1;
//		intersected = new IntersectionList(this);
		neighbours = null;
	}

	/**
	 * Guarantees that the candidate did not add or delete any node.
	 * @return true if the candidate has as many points as its parent.
	 */
	public boolean checkIntegrity(){
		return this.size() == parent.size();
	}

	/**
	 * Generates a random permutation of the points in an array.
	 *
	 * @param base   The array containing the points we wish to operate on.
	 * @return The Candidate containing the points in a random order.
	 */
	public static Candidate randomPermutation(ArrayList<Coordinate> base) {
		return new Candidate(base, (byte)1);
	}

	/**
	 * Insert into an ArrayList the neighbours sorted by who is nearest to
	 * the starting point.
	 *
	 * @param base   The array containing the points we wish to operate on.
	 * @return The Candidate containing the points sorted by the Nearest Neighbour technique.
	 */
	public static Candidate nearestNeighbour(ArrayList<Coordinate> base) {
		return new Candidate(base, (byte)2);
	}

	/**
	 * Analyses all non-visited neighbours and finds the nearest.
	 *
	 * @param cur  The current coordinate we are measuring distances from.
	 * @param base The ArrayList containing its neighbours.
	 * @return the nearest coordinate.
	 */
	private static Coordinate findNearest(Coordinate cur, ArrayList<Coordinate> base) {
		if (base.size() == 0)
			return null;

		int toBeRemoved = 0;
		int distance = Integer.MAX_VALUE;

		for (int i = 0; i < base.size(); i++) {
			Coordinate next = base.get(i);
			int nextDist = euclidianDistance(cur, next);
			if (nextDist < distance) {
				distance = nextDist;
				toBeRemoved = i;
			}
		}

		return base.remove(toBeRemoved);
	}

	/**
	 * Find the square of the euclidian distance between two points.
	 *
	 * @param x1 X coordinate of the first point.
	 * @param y1 Y coordinate of the first point.
	 * @param x2 X coordinate of the second point.
	 * @param y2 Y coordinate of the second point.
	 * @return the square of the euclidian distance.
	 */
	private static int euclidianDistance(int x1, int y1, int x2, int y2) {
		return (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
	}

	/**
	 * Find the square of the euclidian distance between two coordinates
	 * @param a The first coordinate
	 * @param b The second coordinate
	 * @return the square of the euclidian distance.
	 */
	public static int euclidianDistance(Coordinate a, Coordinate b) {
		return euclidianDistance(a.getX(), a.getY(), b.getX(), b.getY());
	}

	/*
	private void exchange(Coordinate j, Coordinate k) {
		int ij = this.indexOf(j), ik = this.indexOf(k);
		this.add(ij, k);
		this.add(ik, j);
	}
	*/

	/**
	 *
	 * @param none
	 * @return the preimeter of the path given by this Candidate.
	 */
	public int getPerimeter(){
		int curPerimeter = 0;
		for(int j = 0; j < this.size(); j++) {
			if(j < this.size()-1)
				curPerimeter += Candidate.euclidianDistance(this.get(j), this.get(j+1));
			else 
				curPerimeter += Candidate.euclidianDistance(this.get(j), this.get(0));
		}
		return curPerimeter;
	}

	/**
	 * Counts how many lines cross in the present candidate.
	 * @return an integer with the number of intersections.
	 */
	public int getIntersectionCount(){
		if(neighbours == null)
			neighbours = new NeighbourList(this);
		if(intersectionCount == -1)
			intersectionCount = neighbours.size();
		return intersectionCount;
	}
		
	/**
	 * Checks the perimeter of every neighbour candidate and finds the smallest.
	 * @return the candidate with the smallest perimeter.
	 */
	public Candidate improveBestFirst() {
		if(neighbours == null)
			neighbours = new NeighbourList(this);
		return neighbours.getSmallestPerimeter();
	}

	/**
	 * Chooses the first candidate in the list of neighbours.
	 * @return the candidate ate index 0 in the neighbour list.
	 */
	public Candidate improveFirst(){
		if(neighbours == null)
			neighbours = new NeighbourList(this);
		return neighbours.get(0);
	}

	/**
	 * Counts the number of conflicts in every candidate in the neighbour list.
	 * @return the neighbour which has the less number of intersections.
	 */
	public Candidate improveLessConflict() {
		if(neighbours == null)
			neighbours = new NeighbourList(this);
		return neighbours.getLessIntersections();
	}

	
	/**
	 * Gets a random neighbour.
	 * @return a random neighbour.
	 */
	public Candidate improveRandom() {
		if(neighbours == null)
			neighbours = new NeighbourList(this);
		return neighbours.get( new Random().nextInt(neighbours.size()) );
	}

	/**
	 * Prints the candidate itself using printArrayList.
	 */
	public void printItself(){
		System.out.print("Itself: ");
		Main.printArrayList(this);
	}

	/**
	 * Prints every candidate in the neighbourList of the current candidate. 
	 */
	public void printNeighbours() {
		if(neighbours == null)
			neighbours = new NeighbourList(this);
		System.out.print("Original order: ");
		Main.printArrayList(this);
		System.out.println("Number of intersections: " + this.getIntersectionCount());
		for(ArrayList<Coordinate> list : neighbours) {
			Main.printArrayList(list);
		}
	}
}
