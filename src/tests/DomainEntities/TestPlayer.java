package DomainEntities;

public class TestPlayer extends Player {

    public TestPlayer(String name, Position position, int healthPool, int attack, int defense) {
        super(name, position, healthPool, attack, defense);
    }

    @Override
    public void onGameTick() {}

    @Override
    public void castAbility() {}

    @Override
    public String description() {
        return "TestPlayer";
    }
}
