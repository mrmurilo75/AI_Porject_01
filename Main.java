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
     * Insert into the answer ArrayList the neighbours sorted by who is nearest to the starting point.
     * @param base The array containing the points we wish to operate on.
     * @param answer The array in which we wish to put the result in.
     */
    private static void nearestNeighbour(ArrayList<Coordinate> base, ArrayList<Coordinate> answer) {
     
        Coordinate cur = base.remove(0);
        while(cur != null) {
            answer.add(cur);
            cur = findNearest(cur, base);
        }
        return; 
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
            double tmp = euclidianDistance(curX, curY, nextX, nextY);
            if(tmp<distance) {
                distance = tmp;
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
        return Math.pow((x1-x2), 2) + Math.pow((y1-y2), 2);
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

            ArrayList<Coordinate> base = cloneList(list);
            ArrayList<Coordinate> result = new ArrayList<>();

            System.out.println("Please enter the number corresponding to the function you desire.");
            System.out.println("0 - Exit the program.");
            System.out.println("1 - Random permutation");
            System.out.println("2 - Nearest Neighbour");
            // TODO
            // Add the functions here as we create them;
            int choice = stdin.nextInt();
            switch (choice) {
            case 0:
                System.out.println("Exiting...");
                stdin.close();
                return;
            case 1:
                randomPermutation(base, result);
                break;
            case 2:
                nearestNeighbour(base, result);
            default:
                break;
            }

            printArrayList(result);
        }

    }
}