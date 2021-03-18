import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class Main {
    static ArrayList<Coordinate> list;

    public static void main(String[] args) {
        Scanner stdin = new Scanner(System.in);

        System.out.println("Please enter the number of points to be generated.");
        int n = stdin.nextInt();
        list = new ArrayList<>();

        System.out.println("Please enter the boundary to generate the points");
        int m = stdin.nextInt();

        for(int i = 0; i < n; i++) {
            int x = new Random().nextInt(2*m-1) - m;
            int y = new Random().nextInt(2*m-1) - m;

            Coordinate coor = new Coordinate(x, y);
            if(list.contains(coor)) i--;  // Not very efficient yeah but since it's TSP I'm hoping inputs are gonna be small.
            else list.add(coor);
        }

        for(Coordinate c : list)
            System.out.println(c.toString());
        
    }
}