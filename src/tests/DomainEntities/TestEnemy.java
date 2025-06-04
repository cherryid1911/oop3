package DomainEntities;

public class TestEnemy extends Enemy {

    public TestEnemy(String name, char tileChar, Position position, int healthPool, int attack, int defense, int experienceValue) {
        super(name, tileChar, position, healthPool, attack, defense, experienceValue);
    }

    @Override
    public Direction decideMoveDirection(Player player) {
        return Direction.STAY;
    }

    @Override
    public void onEnemyTurn(Player player) {}

    @Override
    public String description() {
        return "TestEnemy";
    }

    @Override
    public void onGameTick() {}

    @Override
    public void accept(Unit other) {}

    @Override
    public void visit(Player player) {}
}
