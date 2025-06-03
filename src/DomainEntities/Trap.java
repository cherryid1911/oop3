package DomainEntities;

public class Trap extends Enemy {
    // _____Fields_____
    private final int visibilityTime;
    private final int invisibilityTime;
    private int ticksCount;
    private boolean visible;


    //_____Constructor_____
    public Trap(String name, char tileChar, Position position, int healthPool, int attack, int defense, int experienceValue, int visibilityTime, int invisibilityTime) {
        super(name, tileChar, position, healthPool, attack, defense, experienceValue);
        this.visibilityTime = visibilityTime;
        this.invisibilityTime = invisibilityTime;
        this.ticksCount = 0;
        this.visible = true;
    }


    // _____Methods_____
    @Override
    public void onGameTick() {
        ticksCount++;

        if (ticksCount == visibilityTime + invisibilityTime) {
            ticksCount = 0;
        }

        visible = ticksCount < visibilityTime;
    }

    @Override
    public void onEnemyTurn(Player player) {
        double distance = position.distance(player.getPosition());
        if (distance < 2) {
            int attackRoll = this.rollAttack();
            int defenseRoll = player.rollDefense();
            int damage = Math.max(0, attackRoll - defenseRoll);
            player.takeDamage(damage);
            messageCallback.send(String.format("%s attacked %s for %d damage!", name, player.getName(), damage));
            if (player.isDead()) {
                messageCallback.send(player.getName() + " died!");
            }
        }
    }

    @Override
    public String description() {
        return String.format("Trap %s\tHP: %d/%d\tATK: %d\tDEF: %d\tEXP: %d\tVisible: %s",
                name, currentHealth, healthPool, attack, defense, experienceValue, visible);
    }

    @Override
    public void accept(Unit other) {
        // Visitor pattern
    }

    @Override
    public String toString() {
        return visible ? String.valueOf(tileChar) : ".";
    }
}