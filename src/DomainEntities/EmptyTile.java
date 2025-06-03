package DomainEntities;

public class EmptyTile extends Tile {
    public EmptyTile(Position position) {
        super('.', position);
    }


    @Override
    public void accept(Unit unit) {
        unit.visit(this);
    }

    @Override
    public String toString() {
        return ".";
    }
}