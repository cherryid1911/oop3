package DomainEntities;

public abstract class Enemy extends Unit {

    // _____Fields_____
    protected final int experienceValue;
    protected MessageCallback messageCallback;


    //_____Constructor_____
    public Enemy(String name, char tileChar, int healthPool, int attack, int defense, int experienceValue) {
        super(name, tileChar, new Position(0, 0), healthPool, attack, defense);
        this.experienceValue = experienceValue;
    }


    // _____Methods_____
    public void initialize(MessageCallback callback) {
        this.messageCallback = callback;
    }

    @Override
    public int getExperienceValue() {
        return experienceValue;
    }

    @Override
    public abstract String description();

    @Override
    public abstract void onGameTick();

    public abstract Direction onEnemyTurn(Player player);


    // _____Visitor_Pattern_____
    @Override
    public abstract void accept(Unit other);

    @Override
    public abstract void visit(Player player);

    @Override
    public void visit(Monster monster) {
        // Do nothing.
    }

    @Override
    public void visit(Trap trap){
        // Do nothing.
    }
}