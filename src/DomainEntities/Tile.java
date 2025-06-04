package DomainEntities;

public abstract class Tile {

    // _____Fields_____
    protected char tileChar;
    protected Position position;


    //_____Constructor_____
    public Tile(char tile, Position position) {
        this.tileChar = tile;
        this.position = position;
    }


    // _____Methods_____
    public char getTileChar() {
        return tileChar;
    }

    public Position getPosition() {
        return position;
    }

    public abstract void accept(Unit unit);

    public abstract String toString();

}
