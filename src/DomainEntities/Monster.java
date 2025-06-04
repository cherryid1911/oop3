package DomainEntities;

import java.util.Random;

public class Monster extends Enemy {

    // _____Fields_____
    protected final int visionRange;
    private final Random random = new Random();


    //_____Constructors_____
    public Monster(String name, char tileChar, int healthPool, int attack,
                   int defense, int experienceValue, int visionRange) {
        super(name, tileChar, healthPool, attack, defense, experienceValue);
        this.visionRange = visionRange;
    }

    public Monster(Monster other, Position position) {
        super(other.getName(), other.tileChar, other.getHealthPool(),
                other.getAttack(), other.getDefense(), other.getExperienceValue());
        this.visionRange = other.visionRange;
        this.position = position;
    }


    // _____Methods_____
    public int getVisionRange() {
        return visionRange;
    }

    @Override
    public Direction onEnemyTurn(Player player) {
        double distance = position.distance(player.getPosition());

        if (distance < visionRange) {
            return moveTowards(player);
        }
        else {
            return randomMove();
        }
    }

    protected Direction randomMove() {
        int r = random.nextInt(5);
        return switch (r) {
            case 0 -> Direction.UP;
            case 1 -> Direction.DOWN;
            case 2 -> Direction.LEFT;
            case 3 -> Direction.RIGHT;
            default -> Direction.STAY;
        };
    }

    protected Direction moveTowards(Player player) {
        int dx = player.getPosition().getX() - position.getX();
        int dy = player.getPosition().getY() - position.getY();

        if (Math.abs(dx) > Math.abs(dy)) {
            return dx > 0 ? Direction.RIGHT : Direction.LEFT;
        }
        else {
            return dy > 0 ? Direction.DOWN : Direction.UP;
        }
    }

    @Override
    public String description() {
        return String.format("Monster %s\tHP: %d/%d\tATK: %d\tDEF: %d\tEXP: %d\tVision: %d",
                name, currentHealth, healthPool, attack, defense, experienceValue, visionRange);
    }

    @Override
    public void onGameTick() {
        // For monster, Do nothing.
    }


    // _____Visitor_Pattern_____
    @Override
    public void accept(Unit other) {
        other.visit(this);
    }

    @Override
    public void visit(Player player) {
        int attackRoll = this.rollAttack();
        int defenseRoll = player.rollDefense();
        int damage = Math.max(0, attackRoll - defenseRoll);
        player.takeDamage(damage);
        messageCallback.send(name + " attacked " + player.getName() + " for " + damage + " damage.");
        if (player.isDead()) {
            messageCallback.send(player.getName() + " died!");
        }
    }

}
