package DomainEntities;

public class EmptyTile extends Tile {
    public EmptyTile(Position position) {
        super('.', position);
    }


    @Override
    public void accept(Unit unit) {
        // פשוט מעדכנים את המיקום של היחידה
        unit.setPosition(this.position);
    }

    @Override
    public String toString() {
        return ".";
    }
}