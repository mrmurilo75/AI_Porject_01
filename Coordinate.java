
class Coordinate {
    int x;
    int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if(this==o) return true;
        if(o==null) return false;
        if(getClass()!=o.getClass()) return false;
        Coordinate c = (Coordinate) o;
        return (this.x==c.x && this.y==c.y);
    }
}