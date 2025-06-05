package DomainEntities;

public class EmptyTile extends Tile {

    //_____Constructor_____
    public EmptyTile(Position position) {
        super('.', position);
    }


    // _____Methods_____
    @Override
    public void accept(Unit unit) {
        unit.visit(this);
    }

    @Override
    public String toString() {
        return ".";
    }

}