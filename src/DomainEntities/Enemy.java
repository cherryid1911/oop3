package DomainEntities;

public abstract class Enemy extends Unit {
    // _____Fields_____
    protected final int experienceValue;
    protected MessageCallback messageCallback;


    //_____Constructor_____
    public Enemy(String name, char tileChar, Position position, int healthPool, int attack, int defense, int experienceValue) {
        super(name, tileChar, position, healthPool, attack, defense);
        this.experienceValue = experienceValue;
    }


    // _____Methods_____
    public int getExperienceValue() {
        return experienceValue;
    }

    public abstract void onEnemyTurn(Player player);

    @Override
    public abstract String description();

    @Override
    public abstract void accept(Unit other);

    @Override
    public abstract void onGameTick();
}