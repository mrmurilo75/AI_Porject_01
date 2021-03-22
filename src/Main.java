import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.TreeMap;
import java.util.Random;
import java.util.Map;
import java.util.Map.Entry;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;

public class Main {
    static Scanner stdin;
    static ArrayList<Coordinate> list;

    /**
     * Generates a random permutation of the points in an array.
     * 
     * @param base   The array containing the points we wish to operate on.
     * @param answer The array in which we wish to put the result in.
     */
    private static ArrayList<Coordinate> randomPermutation(ArrayList<Coordinate> base) {
	ArrayList<Coordinate> answer = new ArrayList<>();
        int size = list.size();
        while (size != 0) {
            int i = new Random().nextInt(size--);
            answer.add(base.remove(i));
        }
	return answer;
    }

    /**
     * Insert into the answer ArrayList the neighbours sorted by who is nearest to the starting point.
     * @param base The array containing the points we wish to operate on.
     * @param answer The array in which we wish to put the result in.
     */
    private static ArrayList<Coordinate> nearestNeighbour(ArrayList<Coordinate> base) {
	ArrayList<Coordinate> answer = new ArrayList<>();
     
        Coordinate cur = base.remove(0);
        while(cur != null) {
            answer.add(cur);
            cur = findNearest(cur, base);
        }
        return answer; 
    }

    /**
     * Analyses all non-visited neighbours and finds the nearest.
     * @param cur The current coordinate we are measuring distances from.
     * @param base The ArrayList containing its neighbours.
     * @return the nearest coordinate.
     */
    private static Coordinate findNearest(Coordinate cur, ArrayList<Coordinate> base) {
        if(base.size()==0) return null;

        int toBeRemoved = 0;
        double distance = Double.MAX_VALUE;
        double curX = (double) cur.getX();
        double curY = (double) cur.getY();

        for(int i = 0; i < base.size(); i++) {
            double nextX = base.get(i).getX();
            double nextY = base.get(i).getY();
            double nextDist = euclidianDistance(curX, curY, nextX, nextY);
            if(nextDist<distance) {
                distance = nextDist;
                toBeRemoved = i;
            }
        }

        return base.remove(toBeRemoved);
    }

    /**
     * Find the square of the euclidian distance between two points.
     * @param x1 X coordinate of the first point.
     * @param y1 Y coordinate of the first point.
     * @param x2 X coordinate of the second point.
     * @param y2 Y coordinate of the second point.
     * @return the square of the euclidian distance.
     */
    private static double euclidianDistance(double x1, double y1, double x2, double y2) {
        return (x1-x2)*(x1-x2) + (y1-y2)*(y1-y2);
    }

    /*
     *	
     */
    public static TreeMap< Map.Entry<Coordinate, Coordinate>, Map.Entry<Coordinate, Coordinate> > findIntersect( ArrayList<Coordinate> base){
	    TreeMap<Map.Entry<Coordinate, Coordinate>, Map.Entry<Coordinate, Coordinate>> answer = new TreeMap<>();

	    if (base.size()<=3) return answer;

	    base.add( base.get(0) );		// put first at the end to loop
	    for(int i=0; i+1 < base.size(); i++){
		    Map.Entry<Coordinate, Coordinate> seg1 = new AbstractMap.SimpleEntry<> (base.get(i), base.get(i+1));
		    for(int j=i+1; j+1 < base.size(); j++){
		    Map.Entry<Coordinate, Coordinate> seg2 = new AbstractMap.SimpleEntry<Coordinate, Coordinate>(base.get(j), base.get(j+1));
			    if(segmentsIntersect(seg1, seg2))
				    answer.put(seg1, seg2);
		    }
	    }

	    return answer;
    }

    private static boolean segmentsIntersect(Map.Entry<Coordinate, Coordinate> seg1, Map.Entry<Coordinate, Coordinate> seg2){
	    int d1, d2, d3, d4;
	    d1 = dot(seg1.getKey(), seg1.getValue(), seg2.getKey());
	    d2 = dot(seg1.getKey(), seg1.getValue(), seg2.getValue());
	    d3 = dot(seg2.getKey(), seg2.getValue(), seg1.getKey());
	    d4 = dot(seg2.getKey(), seg2.getValue(), seg1.getValue());
	    if( d1*d2 < 0 && d3*d4 < 0 ) return true;
	    if( d1 == 0 && isInBox(seg1.getKey(), seg1.getValue(), seg2.getKey()) ) return true;
	    if( d2 == 0 && isInBox(seg1.getKey(), seg1.getValue(), seg2.getValue()) ) return true;
	    if( d3 == 0 && isInBox(seg2.getKey(), seg2.getValue(), seg1.getKey()) ) return true;
	    if( d4 == 0 && isInBox(seg2.getKey(), seg2.getValue(), seg1.getValue()) ) return true;
	    return false;
    }

    private static boolean isInBox(Coordinate p1, Coordinate p2, Coordinate p3) {
	    return (Math.min(p1.getX(), p2.getX()) <= p3.getX() && p3.getX() <= Math.max(p1.getX(), p2.getX())) 
		    && (Math.min(p1.getY(), p2.getY()) <= p3.getY() && p3.getY()<= Math.max(p1.getY(), p2.getY()));
    }

    private static int dot(Coordinate p1, Coordinate p2, Coordinate p3) {
	    return (p3.subtract(p1)).dotProduct(p1.subtract(p2));
    }

    /*
     *
     */
    public static boolean UIfindIntersect(){
	    System.out.println("find Intersect? (yes = 1)");
	    if(stdin.nextInt() == 1) return true;
	    return false;
    }

    /**
     * Clones an arraylist of coordinates and its contents.
     * 
     * @param base The ArrayList to be cloned.
     * @return A new ArrayList with the same elements as the parameter.
     */
    private static ArrayList<Coordinate> cloneArrayList(ArrayList<Coordinate> base) {
        ArrayList<Coordinate> clonedList = new ArrayList<>(base.size());
        for (Coordinate c : base) {
            clonedList.add(c.clone());
        }
        return clonedList;
    }

    /**
     * Clones an arrayList to a TreeSet.
     * 
     * @param base The ArrayList to be transformed.
     * @return A TreeSet with the same elements as the parameter.
     */
    private static TreeSet<Coordinate> cloneToTreeSet(ArrayList<Coordinate> base) {
        TreeSet<Coordinate> clonedSet = new TreeSet<>();
        for(Coordinate c : base) {
            clonedSet.add(c.clone());
        }
        return clonedSet;
    }

    /**
     * Prints an ArrayList of coordinates using their names.
     * @param result The ArrayList to be printed.
     */
    private static void printArrayList(ArrayList<Coordinate> result) {
        String ans = "[";
        for (int i = 0; i < result.size(); i++) {
            ans += result.get(i).printName();
            if (i != result.size() - 1)
                ans += ", ";
        }
        ans += "]";
        System.out.println(ans);
    }

    public static void main(String[] args) {
        stdin = new Scanner(System.in);

        System.out.println("Please enter the number of points to be generated: ");
        int n = stdin.nextInt();
        list = new ArrayList<>(n);

        System.out.println("Please enter the boundary to generate the points: ");
        int m = stdin.nextInt();

	if(n>(4*m*m)) return;

        for (int i = 0; i < n; i++) {
            int x = new Random().nextInt(2 * m - 1) - m;
            int y = new Random().nextInt(2 * m - 1) - m;

            Coordinate coor = new Coordinate(x, y, (char) ('A' + i));
            if (list.contains(coor))
                i--; 		//  n/(2*m)^2 chance
	    else
                list.add(coor);
        }

        for (Coordinate c : list) {
            System.out.println(c.printName() + " " + c.toString());
        }

        while (true) {

            System.out.println("Please enter the number corresponding to the function you desire.");
            System.out.println("1 - Random permutation");
            System.out.println("2 - Nearest Neighbour");
            System.out.println("0 - Exit the program.");
            // TODO
            // Add the functions here as we create them;

	    int choice = stdin.nextInt();

            ArrayList<Coordinate> base = new ArrayList<>(list);	// a shallow copy (its ok bc we dont change the elements themselves)
            ArrayList<Coordinate> result = new ArrayList<>();

	    switch (choice) {
		    case 1:
			result = randomPermutation(base);
			if(UIfindIntersect())
				findIntersect(result);
			break;
		    case 2:
			result = nearestNeighbour(base);
			if(UIfindIntersect())
				findIntersect(result);
			break;
		    case 0:
			System.out.println("Exiting...");
			stdin.close();
			return;
            }

            printArrayList(result);
        }

    }
}
