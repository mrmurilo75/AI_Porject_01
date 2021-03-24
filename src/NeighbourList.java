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
					candidate.intersections++;
					this.add(twoExchange(candidate, b, c));
				}
			}
		}

	}

	public int findSmallestPerimeter() {
		int size = Integer.MAX_VALUE;
		int answer = -1;
		for(int i = 0; i < this.size(); i++) {
			int tmp = 0;
			ArrayList<Coordinate> current = this.get(i);
			for(int j = 0; j < current.size(); j++) {
				if(j<this.get(i).size()-1) tmp += Candidate.euclidianDistance(current.get(j), current.get(j+1));
				else tmp += Candidate.euclidianDistance(current.get(j), current.get(0));
			}
			if(tmp<size){
				size = tmp;
				answer = i;
			}
		}
		return answer;
	}

	public int findLessIntersections() {
		int intersections = Integer.MAX_VALUE;
		int answer = -1;
		for(int count = 0; count < this.size(); count++) {
			ArrayList<Coordinate> temporaryList = this.get(count);
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
						conflicts++;
					}
				}
			}
			if(conflicts<intersections) {
				intersections = conflicts;
				answer = count;
			}
			temporaryList.remove(temporaryList.size()-1);
		}
		return answer;
	}

	private Candidate twoExchange(Candidate candidate, Coordinate b, Coordinate c) {
		Candidate answer = new Candidate(candidate);
		int cIndex = answer.indexOf(c);
		int bIndex = answer.indexOf(b);

		candidate.set(bIndex, c);
		candidate.set(cIndex, b);

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
