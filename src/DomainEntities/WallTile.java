package DomainEntities;

public class WallTile extends Tile {

    //_____Constructor_____
    public WallTile(Position position) {
        super('#', position);
    }


    // _____Methods_____
    @Override
    public void accept(Unit unit) {
        unit.visit(this);
    }

    @Override
    public String toString() {
        return "#";
    }
}