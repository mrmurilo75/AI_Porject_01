
class Coordinate implements Comparable<Coordinate>{
    private int x;
    private int y;
    private char c;
    private double distance;

    /**
     * Default constructor.
     * @param x X coordinate.
     * @param y Y coordinate.
     * @param c Letter to represent the point.
     */
    public Coordinate(int x, int y, char c) {
        this.x = x;
        this.y = y;
        this.c = c;
    }

    /**
     * Constructor for cloning elements.
     * @param coor The coordinate from which we get our variables.
     */
    public Coordinate(Coordinate coor) {
        this.x = coor.x;
        this.y = coor.y;
        this.c = coor.c;
        this.distance = Double.MAX_VALUE/2;
    }

    @Override
    public boolean equals(Object o) {
        if(this==o) return true;
        if(o==null) return false;
        if(getClass()!=o.getClass()) return false;
        Coordinate c = (Coordinate) o;
        return (this.x==c.x && this.y==c.y);
    }

    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }

    /**
     * 
     * @return the letter that represents the point.
     */
    public String printName() {
        return Character.toString(c);
    }

    @Override
    public int compareTo(Coordinate c) {
        if(this.distance > c.distance) return +1;
        if(this.distance < c.distance) return -1;
        if(this.c > c.c) return +1;
        if(this.c < c.c) return -1;
        return 0;
    }
}