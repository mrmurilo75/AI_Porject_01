import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.Random;

public class Main {
    static ArrayList<Coordinate> list;

    // Maybe we should put the classes in a different file? I don't know...
    /**
     * Generates a random permutation of the points in an array.
     * 
     * @param base   The array containing the points we wish to operate on.
     * @param answer The array in which we wish to put the result in.
     */
    private static void randomPermutation(ArrayList<Coordinate> base, ArrayList<Coordinate> answer) {
        int size = list.size();
        while (size != 0) {
            int i = new Random().nextInt(size--);
            answer.add(base.remove(i));
        }
    }

    /**
     * Clones an arraylist of coordinates and its contents.
     * 
     * @param base The ArrayList to be cloned.
     * @return A new ArrayList with the same elements as the parameter.
     */
    private static ArrayList<Coordinate> cloneList(ArrayList<Coordinate> base) {
        ArrayList<Coordinate> clonedList = new ArrayList<>(base.size());
        for (Coordinate c : base) {
            clonedList.add(new Coordinate(c));
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
            clonedSet.add(new Coordinate(c));
        }
        return clonedSet;
    }

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
        Scanner stdin = new Scanner(System.in);

        System.out.println("Please enter the number of points to be generated.");
        int n = stdin.nextInt();
        list = new ArrayList<>();

        System.out.println("Please enter the boundary to generate the points.");
        int m = stdin.nextInt();

        for (int i = 0; i < n; i++) {
            int x = new Random().nextInt(2 * m - 1) - m;
            int y = new Random().nextInt(2 * m - 1) - m;

            Coordinate coor = new Coordinate(x, y, (char) ('A' + i));
            if (list.contains(coor))
                i--; // Not very efficient yeah but since it's TSP I'm hoping inputs are gonna be
                     // small.
            else
                list.add(coor);
        }

        for (Coordinate c : list) {
            System.out.println(c.printName() + " " + c.toString());
        }

        while (true) {
            System.out.println("Please enter the number corresponding to the function you desire.");
            System.out.println("0 - Exit the program.");
            System.out.println("1 - Random permutation");
            // TODO
            // Add the functions here as we create them;
            int choice = stdin.nextInt();
            switch (choice) {
            case 0:
                System.out.println("Exiting...");
                stdin.close();
                return;
            case 1:
                ArrayList<Coordinate> base = cloneList(list);
                ArrayList<Coordinate> result = new ArrayList<>();
                randomPermutation(base, result);
                printArrayList(result);
                break;
            default:
                break;
            }
        }

    }
}