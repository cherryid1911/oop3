package DomainEntities;

public class Boss extends Monster implements HeroicUnit {

    // _____Fields_____
    private int abilityFrequency;
    private int combatTicks;


    //_____Constructors_____
    public Boss(String name, char tileChar, int healthPool, int attack, int defense,
                int experienceValue, int visionRange, int abilityFrequency) {
        super(name, tileChar, healthPool, attack, defense, experienceValue, visionRange);
        this.abilityFrequency = abilityFrequency;
        this.combatTicks = 0;
    }

    public Boss(Boss other, Position position) {
        super(other.getName(), other.tileChar, other.getHealthPool(),
                other.getAttack(), other.getDefense(), other.getExperienceValue(), other.getVisionRange());
        this.abilityFrequency = other.abilityFrequency;
        this.combatTicks = 0;
        this.position = position;
    }


    // _____Methods_____
    @Override
    public Direction onEnemyTurn(Player player) {
        double distance = position.distance(player.getPosition());

        if (distance <= visionRange) {
            if (combatTicks >= abilityFrequency) {
                castAbility(player);
                combatTicks = 0;
                return Direction.STAY;
            }
            else {
                combatTicks++;
                return moveTowards(player);
            }
        }
        else {
            combatTicks = 0;
            return randomMove();
        }
    }

    @Override
    public void castAbility(Player player) {
        int def = player.rollDefense();
        int dmg = Math.max(0, attack - def);
        player.takeDamage(dmg);
        messageCallback.send(String.format("%s cast Shoebodybop on %s for %d damage.",
                name, player.getName(), dmg));

        if (player.isDead()) {
            messageCallback.send(player.getName() + " died.");
        }
    }
}
