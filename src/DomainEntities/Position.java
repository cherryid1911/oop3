package DomainEntities;

public class Position {

    // _____Fields_____
    private final int x;
    private final int y;


    //_____Constructor_____
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }


    // _____Methods_____
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double distance(Position other) {
        int dx = this.x - other.x;
        int dy = this.y - other.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public Position offset(int dx, int dy) {
        return new Position(this.x + dx, this.y + dy);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position)) return false;
        Position p = (Position) o;
        return this.x == p.x && this.y == p.y;
    }

    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
