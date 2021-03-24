import java.util.ArrayList;
import java.util.Random;

class Candidate extends ArrayList<Coordinate> {
	Candidate parent;
	private int intersectionCount;
//	IntersectionList intersected;
	NeighbourList neighbours;

	public Candidate(ArrayList<Coordinate> parent){
		super(parent);
		parent = null;
		intersectionCount = -1;
		neighbours = null;
	}

	public Candidate(Candidate parent){
		super(parent);
		this.parent = parent;
		intersectionCount = -1;
		neighbours = null;

//		System.out.println("Candidate by (Candidate)");
	}

	public Candidate(ArrayList<Coordinate> parent, byte generator){
		super(parent.size());
		this.parent = new Candidate(parent);

		ArrayList<Coordinate> base; 
		switch(generator){
			case 1:		// generate by Random Permutation
				base = new  ArrayList<>(parent);
				while (base.size() > 0) {
					int i = new Random().nextInt(base.size());
					this.add(base.remove(i));
				}
				break;
			case 2:		// generate by Nearest Neighbour
				base = new  ArrayList<>(parent);
				Coordinate cur = base.remove(0);
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

	public boolean checkIntegrity(){
		return this.size() == parent.size();
	}

	/**
	 * Generates a random permutation of the points in an array.
	 *
	 * @param base   The array containing the points we wish to operate on.
	 * @param answer The array in which we wish to put the result in.
	 */
	public static Candidate randomPermutation(ArrayList<Coordinate> base) {
		return new Candidate(base, (byte)1);
	}

	/**
	 * Insert into the answer ArrayList the neighbours sorted by who is nearest to
	 * the starting point.
	 *
	 * @param base   The array containing the points we wish to operate on.
	 * @param answer The array in which we wish to put the result in.
	 */
	public static Candidate nearestNeighbour(ArrayList<Coordinate> base) {
		return new Candidate(base, (byte)1);
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

	/*
	 *
	 */
	public int getIntersectionCount(){
		if(neighbours == null)
			neighbours = new NeighbourList(this);
		if(intersectionCount == -1)
			intersectionCount = neighbours.size();
		return intersectionCount;
	}
		
	/*
	 *
	 */
	public Candidate improveBestFirst() {
		if(neighbours == null)
			neighbours = new NeighbourList(this);
		return neighbours.getSmallestPerimeter();
	}

	/*
	 *
	 */
	public Candidate improveFirst(){
		if(neighbours == null)
			neighbours = new NeighbourList(this);
		return neighbours.get(0);
	}

	/*
	 *
	 */
	public Candidate improveLessConflict() {
		if(neighbours == null)
			neighbours = new NeighbourList(this);
		return neighbours.getLessIntersections();
	}

	/*
	 *
	 */
	public Candidate improveRandom() {
		if(neighbours == null)
			neighbours = new NeighbourList(this);
		return neighbours.get( new Random().nextInt(neighbours.size()) );
	}

	public void printItself(){
		System.out.print("Itself: ");
		Main.printArrayList(this);
	}

	/*
	 *
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
