package DomainEntities;

public class Trap extends Enemy {

    // _____Fields_____
    private final int visibilityTime;
    private final int invisibilityTime;
    private int ticksCount;
    private boolean visible;


    //_____Constructors_____
    public Trap(String name, char tileChar, int healthPool,
                int attack, int defense, int experienceValue, int visibilityTime, int invisibilityTime) {
        super(name, tileChar, healthPool, attack, defense, experienceValue);
        this.visibilityTime = visibilityTime;
        this.invisibilityTime = invisibilityTime;
        this.ticksCount = 0;
        this.visible = true;
    }

    public Trap(Trap other, Position position) {
        super(other.getName(), other.tileChar, other.getHealthPool(), other.getAttack(), other.getDefense(), other.getExperienceValue());
        this.visibilityTime = other.visibilityTime;
        this.invisibilityTime = other.invisibilityTime;
        this.ticksCount = other.ticksCount;
        this.visible = other.visible;
        this.position = position;
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
    public Direction onEnemyTurn(Player player) {
        double distance = position.distance(player.getPosition());
        if (distance < 2) {
            int attackRoll = this.rollAttack();
            int defenseRoll = player.rollDefense();
            int damage = Math.max(0, attackRoll - defenseRoll);
            player.takeDamage(damage);
            messageCallback.send(String.format("%s attacked %s for %d damage!", name, player.getName(), damage));
            if (player.isDead())
                messageCallback.send(player.getName() + " died!");
        }
        return Direction.STAY;
    }

    @Override
    public String description() {
        return String.format("Trap %s\tHP: %d/%d\tATK: %d\tDEF: %d\tEXP: %d\tVisible: %s",
                name, currentHealth, healthPool, attack, defense, experienceValue, visible);
    }

    @Override
    public String toString() {
        return visible ? String.valueOf(tileChar) : ".";
    }


    // _____Visitor_Pattern_____
    @Override
    public void accept(Unit other) {
        other.visit(this);
    }

    @Override
    public void visit(Player player) {
        // Impossible.
    }


}