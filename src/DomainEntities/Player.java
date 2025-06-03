package DomainEntities;

public abstract class Player extends Unit implements HeroicUnit {
    // _____Fields_____
    protected int experience;
    protected int level;
    protected MessageCallback messageCallback;


    //_____Constructor_____
    public Player(String name, char tileChar, Position position, int healthPool, int attack, int defense) {
        super(name, tileChar, position, healthPool, attack, defense);
        this.level = 1;
        this.experience = 0;
    }


    // _____Getters_____
    public int getExperience() {
        return experience;
    }

    public int getLevel() {
        return level;
    }


    // _____Methods_____
    public void gainExperience(int xp) {
        experience += xp;
        while (checkLevelUp()) {
            experience -= 50 * level;
            level++;
            levelUp();
        }
    }

    protected void levelUp(){

        healthPool += 10 * level;
        currentHealth = healthPool;
        attack += 4 * level;
        defense += level;
    }

    private boolean checkLevelUp(){
        return experience >= 50 * level;
    }


    protected void denySpecialAbility() throws Exception{
        throw new Exception("You don't have enough resources" +
                "to case special ability!");
    }

    public void setMessageCallback(MessageCallback callback) {
        this.messageCallback = callback;
    }

    public void initialize(MessageCallback callback) {
        this.messageCallback = callback;
    }


    public abstract void onGameTick();
    public abstract void castAbility();
    public abstract String description();
    public abstract void accept(Unit other);

}
