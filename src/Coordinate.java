class Coordinate implements Comparable<Coordinate>{
    private int x;
    private int y;
    private char c;
    private double distance; // Note: I don't think we even need this.

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
	this.distance = Double.POSITIVE_INFINITY;
    }

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
        this.c = '\0';
	this.distance = Double.POSITIVE_INFINITY;
    }

    /**
     * Clones this element.
     * @param none
     * @return A new Coordinate clone of this element
     */
    public Coordinate clone() {
	    return new Coordinate(this.x, this.y, this.c);
    }

    public Coordinate subtract(Coordinate that) {
	    return new Coordinate(this.x - that.getX(), this.y - that.getY());
    }

    public int dotProduct(Coordinate that) {
	    return (this.x * that.getX()) + (this.y - that.getY());
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
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
