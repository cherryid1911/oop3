package DomainEntities;

public abstract class Unit extends Tile {

    // _____Fields_____
    protected String name;
    protected int healthPool;
    protected int currentHealth;
    protected int attack;
    protected int defense;


    //_____Constructor_____
    public Unit(String name, char tileChar, Position position, int healthPool, int attack, int defense) {
        super(tileChar, position);
        this.name = name;
        this.healthPool = healthPool;
        this.currentHealth = healthPool;
        this.attack = attack;
        this.defense = defense;
    }


    // _____Getters_____
    public String getName() {
        return name;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public int getHealthPool() {
        return healthPool;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }


    // _____Methods_____
    public boolean isDead() {
        return currentHealth <= 0;
    }

    public int rollAttack() {
        return (int)(Math.random() * (attack + 1));
    }

    public int rollDefense() {
        return (int)(Math.random() * (defense + 1));
    }

    public void takeDamage(int damage) {
        currentHealth = Math.max(0, currentHealth - damage);
    }

    public abstract void onGameTick();

    @Override
    public String toString() {
        return String.valueOf(tileChar);
    }

    public void setPosition(Position newPos) {
        this.position = newPos;
    }

    public abstract String description();


    // _____Visitor_Pattern_____
    public abstract void accept(Unit other);

    public void visit(WallTile wall) {
        // Do nothing
    }

    public void visit(EmptyTile empty) {
        setPosition(empty.getPosition());
    }

    public abstract void visit(Player player);

    public abstract void visit(Monster monster);

    public abstract void visit(Trap trap);
}
