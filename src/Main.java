import java.util.Scanner;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.Random;

/**
 * The program asks the user for inputs to choose a way of generating, correcting and returning a simple polygon (Hamilton cycle with no intersections)
 * from a collection of randomly generated points in a 2D space.
 * 
 * @author Felipe Valverde
 * @author Murilo Rosa
 */
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
	static void printArrayList(ArrayList<Coordinate> result) {
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

	/**
	 * Generates text to the user and waits for a response, to determine the methods which will be used to achieve the final goal.
	 * @param args A String array containing command line arguments (not used).
	 */
	public static void main(String[] args) {
		stdin = new Scanner(System.in);

		while (true) {

			System.out.println("Please enter the number of points to be generated: ");
			int n = stdin.nextInt();
			if(n==0) {
				System.out.println("Exiting...");
				return;
			}
			list = new ArrayList<>(n);

			System.out.println("Please enter the boundary to generate the points: ");
			int m = stdin.nextInt();
			if(m==0) {
				System.out.println("Exiting...");
				return;
			}

			if(n>(4*m*m)) continue;		// restart loop

			for (int i = 0; i < n; i++) {

				int x = new Random().nextInt(2 * m - 1) - m;
				int y = new Random().nextInt(2 * m - 1) - m;

				Coordinate coor = new Coordinate(x, y, i);
				if (list.contains(coor))
					i--; 		//  n/(2*m)^2 chance
				else
					list.add(coor);
			}

			for (Coordinate c : list) {
				System.out.println(c.printName() + " " + c.toString());
			}

			int choice = -1;


			System.out.println("Please enter the number corresponding to the function you desire.");
			System.out.println("1 - Random permutation");
			System.out.println("2 - Nearest Neighbour");
			System.out.println("0 - Exit the program.");

			choice = stdin.nextInt();

			if(choice == 0){
				System.out.println("Exiting...");
				stdin.close();
				return;
			}

			Candidate result = new Candidate(list, (byte)choice);

			if(!result.checkIntegrity()) {
				System.out.println("Invalid input. Try again.");
				continue;	// restart loop
			}

//			result.printNeighbours();

			if(result.getIntersectionCount() != 0) {
				System.out.println("Please enter the number corresponding to the function you desire.");
				System.out.println("1 - Best-improvement First");
				System.out.println("2 - First-improvement");
				System.out.println("3 - Less-conflicts");
				System.out.println("4 - Anyone");
				System.out.println("0 - Exit the program.");

				choice = stdin.nextInt();
			}

			while(result.getIntersectionCount() != 0) {

				switch (choice) {
					case 1:
						result = result.improveBestFirst();
						break;
					case 2:
						result = result.improveFirst();
						break;
					case 3:
						result = result.improveLessConflict();
						break;
					case 4:
						result = result.improveRandom();
						break;
					default:
						System.out.println("Invalid input. Try again.");
						continue;		// restart loop
				}
				System.out.print("Current solution: ");
				printArrayList(result);
			}
			System.out.print("Found the simple polygon: ");
			printArrayList(result);
			System.out.println(" ----------------------------  ");
		}

	}
}
