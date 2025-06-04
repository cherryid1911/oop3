package DomainEntities;

public class TestUnit extends Unit {

    public TestUnit(String name, char tileChar, Position position, int healthPool, int attack, int defense) {
        super(name, tileChar, position, healthPool, attack, defense);
    }

    @Override public void onGameTick() {}

    @Override public String description() {
        return "TestUnit";
    }

    @Override public void accept(Unit other) {}

    @Override public void visit(Player player) {}

    @Override public void visit(Monster monster) {}

    @Override public void visit(Trap trap) {}

    @Override public void visit(WallTile wall) {}

    @Override public void visit(EmptyTile empty) {}
}
