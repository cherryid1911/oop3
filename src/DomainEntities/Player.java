package DomainEntities;

import java.util.List;

public abstract class Player extends Unit implements HeroicUnit {

    // _____Fields_____
    protected int experience;
    protected int level;
    protected MessageCallback messageCallback;
    protected List<Enemy> enemies;


    //_____Constructor_____
    public Player(String name, int healthPool, int attack, int defense) {
        super(name, '@', new Position(0, 0), healthPool, attack, defense);
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

    public void setMessageCallback(MessageCallback callback) {
        this.messageCallback = callback;
    }

    public void initialize(MessageCallback callback) {
        this.messageCallback = callback;
    }

    public void engage(Enemy enemy) {
        int atk = rollAttack();
        int def = enemy.rollDefense();
        int dmg = Math.max(0, atk - def);
        messageCallback.send(getName() + " engaged " + enemy.getName() + " for " + dmg + " damage.");
        enemy.takeDamage(dmg);

        if (enemy.isDead()) {
            gainExperience(enemy.getExperienceValue());
            setPosition(enemy.position);
        }
    }

    public void deadChar(){
        tileChar = 'X';
    }

    public void setEnemies(List<Enemy> enemies) {
        this.enemies = enemies;
    }

    protected List<Enemy> getEnemiesInRange(int range) {
        return enemies.stream()
                .filter(e -> !e.isDead())
                .filter(e -> position.distance(e.getPosition()) <= range)
                .toList();
    }

    public abstract void onGameTick();
    public abstract void castAbility(Player player);
    public abstract String description();


    // _____Visitor_Pattern_____
    public void accept(Unit other){
        other.visit(this);
    }

    public void visit(Player player) {
        // Impossible.
    }

    public void visit(Monster monster){
        engage(monster);
    }

    public void visit(Trap trap){
        engage(trap);
    }
}
