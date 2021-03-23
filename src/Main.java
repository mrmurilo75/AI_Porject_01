import java.util.Scanner;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.Random;


public class Main {
    static Scanner stdin;
    static ArrayList<Coordinate> list;

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
        if(result == null){
		System.out.println("result is null");
		return;
	}
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

        int choice = -1;

        while (true) {

	    if(choice == 0){
		System.out.println("Exiting...");
		stdin.close();
		return;
	    }

            System.out.println("Please enter the number corresponding to the function you desire.");
            System.out.println("1 - Random permutation");
            System.out.println("2 - Nearest Neighbour");
            System.out.println("0 - Exit the program.");
            // TODO
            // Add the functions here as we create them;
	
	    choice = stdin.nextInt();

            ArrayList<Coordinate> base = new ArrayList<>(list);	// a shallow copy (its ok bc we dont change the elements themselves)
            Candidate result = null;

	    switch (choice) {
		    case 1:
			result = new Candidate(Candidate.randomPermutation(base));
			break;
		    case 2:
			result = new Candidate(Candidate.nearestNeighbour(base));
			break;
		    default:
			System.out.println("Invalid input. Try again.");
			continue;		// not sure if this will work
            }

	    if(UIfindIntersect())
		    findIntersect(result);

            System.out.println("Please enter the number corresponding to the function you desire.");
            System.out.println("1 - Best-improvement First");
            System.out.println("2 - First-improvement");
            System.out.println("3 - Less-conflicts");
            System.out.println("4 - Anyone");
            System.out.println("0 - Exit the program.");
            // TODO
            // Add the functions here as we create them;
        
            choice = stdin.nextInt();

            printArrayList(result);
        }

    }
}
