package DomainEntities;

public class WallTile extends Tile {
    public WallTile(Position position) {
        super('#', position);
    }

    @Override
    public void accept(Unit unit) {
        // לא עושים כלום – אי אפשר לעבור קיר
    }

    @Override
    public String toString() {
        return "#";
    }
}