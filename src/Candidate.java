import java.util.ArrayList;
import java.util.Random;

class Candidate extends ArrayList<Coordinate> {
	ArrayList<Coordinate> parent;
	public int intersections;
//	IntersectionList intersected;
	NeighbourList neighbours;

	public Candidate(Candidate parent){
		super(parent);
		this.parent = parent.parent;
		intersections = 0;
		neighbours = new NeighbourList(this);
	}

	public Candidate(ArrayList<Coordinate> parent, byte generator){
		super(parent.size());
		this.parent = parent;

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
				parent = null;
				intersections = -1;
				return;
		}

		intersections = 0;
//		intersected = new IntersectionList(this);
		neighbours = new NeighbourList(this);
	}

	public boolean checkIntegrity(){
		if(parent == null)
			return false;
		return true;
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

	private void exchange(Coordinate j, Coordinate k) {
		int ij = this.indexOf(j), ik = this.indexOf(k);
		this.add(ij, k);
		this.add(ik, j);
	}

	/*
	 *
	 *
	public void improveBestFirst() {
	}

	/*
	 *
	 *
	public void improveFirst(){
		Pair<Pair<Coordinate>> cur = intersected.remove(0);
		this.exchange(cur.getKey().getValue(), cur.getValue().getKey());
		while(intersected.size() > 0)
			this.improveFirst();
	}

	/*
	 *
	 *
	public void improveLessConflict() {
	}

	/*
	 *
	 *
	public void improve() {
	}

	*/

	/*
	 *
	 */
	public void printNeighbours() {
		 System.out.print("Original order: ");
		 Main.printArrayList(this);
		 System.out.println("Number of intersections: " + this.intersections);
		 for(ArrayList<Coordinate> list : neighbours) {
			 Main.printArrayList(list);
		 }
	}
}
