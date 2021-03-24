import java.util.ArrayList;

class NeighbourList extends ArrayList<Candidate>{

	public NeighbourList(Candidate candidate){
		super();

		ArrayList<Coordinate> base = new ArrayList<>(candidate);
		base.add(base.get(0));
		Coordinate a, b, c, d;

		for(int i = 0; i < base.size()-3; i++) {
			a = base.get(i);
			b = base.get(i+1);
			for(int j = i+2; j < base.size()-1; j++) {
				if(i == 0 && j == base.size()-2) continue;
				c = base.get(j);
				d = base.get(j+1);
				if(segmentsIntersect(a, b, c, d)) {
					Candidate next = twoExchange(candidate, b, c);
					if( !next.equals(candidate.parent) )
						this.add(next);
				}
			}
		}

	}

	public Candidate getSmallestPerimeter() {
		int size = Integer.MAX_VALUE;
		Candidate answer = null;
		for(int i = 0; i < this.size(); i++) {
			Candidate current = this.get(i);
			int curPerimeter = 0;
			for(int j = 0; j < current.size(); j++) {
				if(j < this.get(i).size()-1 )
					curPerimeter += Candidate.euclidianDistance(current.get(j), current.get(j+1));
				else 
					curPerimeter += Candidate.euclidianDistance(current.get(j), current.get(0));
			}
			if(curPerimeter < size){
				size = curPerimeter;
				answer = current;
			}
		}
		return answer;
	}

	public Candidate getLessIntersections() {
		int intersections = Integer.MAX_VALUE;
		Candidate answer = null;
		for(int i=0; i < this.size(); i++){
			int conflicts = this.get(i).getIntersectionCount();
			if(conflicts < intersections) {
				intersections = conflicts;
				answer = this.get(i);
			}
		}
		return answer;
	}

	private Candidate twoExchange(Candidate candidate, Coordinate b, Coordinate c) {
		Candidate answer = new Candidate(candidate);
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
