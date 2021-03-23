import java.util.ArrayList;
import java.util.TreeMap;

class NeighbourList extends ArrayList<ArrayList<Coordinate>>{

    public NeighbourList(){ super(); }

    public NeighbourList(Candidate candidate){
	    super();

		ArrayList<Coordinate> temporaryList = new ArrayList<Coordinate>(candidate.list);
		temporaryList.add(temporaryList.get(0));
		int conflicts = 0;
		Coordinate a, b, c, d;

		for(int i = 0; i < temporaryList.size()-3; i++) {
			a = temporaryList.get(i);
			b = temporaryList.get(i+1);
			for(int j = i+2; j < temporaryList.size()-1; j++) {
				if(i == 0 && j == temporaryList.size()-2) continue;
				c = temporaryList.get(j);
				d = temporaryList.get(j+1);
				if(segmentsIntersect(a, b, c, d)) {
					candidate.intersections++;
					this.add(twoExchange(candidate, a, b, c, d));
				}
			}
		}

    }


	private ArrayList<Coordinate> twoExchange(Candidate candidate, Coordinate a, Coordinate b, Coordinate c, Coordinate d) {
		ArrayList<Coordinate> answer = new ArrayList<>(candidate.list);
		int cIndex = answer.indexOf(c);
		int bIndex = answer.indexOf(b);

		answer.set(bIndex, c);
		answer.set(cIndex, b);

		return answer;
	}

    private static boolean segmentsIntersect(Coordinate a, Coordinate b, Coordinate c, Coordinate d){
	    int d1, d2, d3, d4;
	    d1 = dot(a, b, c);
	    d2 = dot(a, b, d);
	    d3 = dot(c, d, a);
	    d4 = dot(c, d, b);
	    if( d1*d2 < 0 && d3*d4 < 0 ) return true;
	    if( d1 == 0 && isInBox(a, b, c) ) return true;
	    if( d2 == 0 && isInBox(a, b, d) ) return true;
	    if( d3 == 0 && isInBox(c, d, a) ) return true;
	    if( d4 == 0 && isInBox(c, d, b) ) return true;
	    return false;
    }

    private static boolean isInBox(Coordinate p1, Coordinate p2, Coordinate p3) {
	    return (Math.min(p1.getX(), p2.getX()) <= p3.getX() && p3.getX() <= Math.max(p1.getX(), p2.getX())) 
		    && (Math.min(p1.getY(), p2.getY()) <= p3.getY() && p3.getY()<= Math.max(p1.getY(), p2.getY()));
    }

    private static int dot(Coordinate p1, Coordinate p2, Coordinate p3) {
	    return (p3.subtract(p1)).dotProduct(p2.subtract(p1));
    }


}
