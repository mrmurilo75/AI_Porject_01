import java.util.ArrayList;
import java.util.Random;

class Candidate extends ArrayList<Coordinate>{
    ArrayList<Coordinate> list;

    public Candidate(ArrayList<Coordinate> result){
	    super(result);
    }

    /**
     * Generates a random permutation of the points in an array.
     * 
     * @param base   The array containing the points we wish to operate on.
     * @param answer The array in which we wish to put the result in.
     */
    public static ArrayList<Coordinate> randomPermutation(ArrayList<Coordinate> base) {
	ArrayList<Coordinate> answer = new ArrayList<>();
        int size = base.size();
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
    public static ArrayList<Coordinate> nearestNeighbour(ArrayList<Coordinate> base) {
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
}