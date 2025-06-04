package DomainEntities;

import java.util.List;

public class Hunter extends Player {

    // _____Fields_____
    private final int range;
    private int arrowsCount;
    private int ticksCount;


    //_____Constructor_____
    public Hunter(String name, int healthPool, int attack, int defense, int range) {
        super(name, healthPool, attack, defense);
        this.range = range;
        this.arrowsCount = 10;
        this.ticksCount = 0;
    }


    // _____Methods_____
    @Override
    public void castAbility(Player player) {
        if (arrowsCount <= 0) {
            messageCallback.send(getName() + " tried to shoot, but has no arrows!");
            return;
        }

        Enemy closest = null;
        double minDist = Double.MAX_VALUE;
        for (Enemy e : getEnemiesInRange(range)) {
            double dist = position.distance(e.getPosition());
            if (dist < minDist) {
                minDist = dist;
                closest = e;
            }
        }

        if (closest != null) {
            arrowsCount--;
            int def = closest.rollDefense();
            int dmg = Math.max(0, attack - def);
            closest.takeDamage(dmg);
            messageCallback.send(getName() + " shot " + closest.getName() + " for " + dmg + " damage.");

            if (closest.isDead()) {
                messageCallback.send(closest.getName() + " died.");
                gainExperience(closest.getExperienceValue());
            }
        } else {
            messageCallback.send(getName() + " found no enemies in range.");
        }
    }

    @Override
    public void onGameTick() {
        if (ticksCount == 10) {
            arrowsCount += level;
            ticksCount = 0;
        } else {
            ticksCount++;
        }
    }

    @Override
    protected void levelUp() {
        super.levelUp();
        arrowsCount += 10 * level;
        attack += 2 * level;
        defense += level;
    }

    @Override
    public String description() {
        return String.format("Hunter %s\tLevel %d\tXP: %d\tHP: %d/%d\tATK: %d\tDEF: %d\tArrows: %d\tRange: %d",
                name, level, experience, currentHealth, healthPool, attack, defense, arrowsCount, range);
    }
}