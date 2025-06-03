package DomainEntities;

import java.util.Random;

public class Monster extends Enemy {
    // _____Fields_____
    private final int visionRange;
    private final Random random = new Random();


    //_____Constructor_____
    public Monster(String name, char tileChar, Position position, int healthPool, int attack, int defense, int experienceValue, int visionRange) {
        super(name, tileChar, position, healthPool, attack, defense, experienceValue);
        this.visionRange = visionRange;
    }


    // _____Methods_____
    public int getVisionRange() {
        return visionRange;
    }

    @Override
    public void onEnemyTurn(Player player) {
        double distance = position.distance(player.getPosition());

        if (distance < visionRange) {
            int dx = player.getPosition().getX() - position.getX();
            int dy = player.getPosition().getY() - position.getY();

            if (Math.abs(dx) > Math.abs(dy)) {
                if (dx > 0)
                    moveRight();
                else
                    moveLeft();
            } else {
                if (dy > 0)
                    moveDown();
                else
                    moveUp();
            }
        } else {
            performRandomMove();
        }
    }

    private void moveUp() {
        // TBD: implement via game engine or board logic
    }

    private void moveDown() {
        // TBD
    }

    private void moveLeft() {
        // TBD
    }

    private void moveRight() {
        // TBD
    }

    private void performRandomMove() {
        int r = random.nextInt(5); // 0: up, 1: down, 2: left, 3: right, 4: stay
        switch (r) {
            case 0 -> moveUp();
            case 1 -> moveDown();
            case 2 -> moveLeft();
            case 3 -> moveRight();
            default -> {} // stay in place
        }
    }

    @Override
    public void onGameTick() {
        // For monster, tick = no special update
    }

    @Override
    public String description() {
        return String.format("Monster %s\tHP: %d/%d\tATK: %d\tDEF: %d\tEXP: %d\tVision: %d",
                name, currentHealth, healthPool, attack, defense, experienceValue, visionRange);
    }

    @Override
    public void accept(Unit other) {
        // Visitor pattern
    }


}
