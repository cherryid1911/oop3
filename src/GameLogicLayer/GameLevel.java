package GameLogicLayer;

import DomainEntities.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameLevel {

    // _____Fields_____
    private final Board board;
    private final Player player;
    private final List<Enemy> enemies;
    private final MessageCallback messageCallback;


    //_____Constructor_____
    public GameLevel(Board board, Player player, List<Enemy> enemies, MessageCallback messageCallback) {
        this.board = board;
        this.player = player;
        this.enemies = new ArrayList<>(enemies);
        this.messageCallback = messageCallback;
        this.player.initialize(messageCallback);
        for (Enemy e : enemies) {
            e.initialize(messageCallback);
        }
    }


    // _____Methods_____
    public void gameTick(char input) {
        Position newPos = null;

        switch (input) {
            case 'w' -> newPos = player.getPosition().offset(0, -1);
            case 's' -> newPos = player.getPosition().offset(0, 1);
            case 'a' -> newPos = player.getPosition().offset(-1, 0);
            case 'd' -> newPos = player.getPosition().offset(1, 0);
            case 'e' -> player.castAbility();
            case 'q' -> messageCallback.send("Player chose to wait.");
            default -> messageCallback.send("Invalid input: " + input);
        }

        if (newPos != null && isInsideBoard(newPos)) {
            Tile tile = board.getTile(newPos);
            tile.accept(player);  // Visitor pattern
        }

        player.onGameTick();

        Iterator<Enemy> it = enemies.iterator();
        while (it.hasNext()) {
            Enemy enemy = it.next();
            enemy.onGameTick();
            if (!enemy.isDead()) {
                Direction dir = enemy.decideMoveDirection(player);
                Position newPos2 = getNextPosition(enemy.getPosition(), dir);
                if (newPos2 != null && isInsideBoard(newPos2)) {
                    board.getTile(newPos2).accept(enemy);
                }
            }
            else {
                board.setTile(enemy.getPosition(), new EmptyTile(enemy.getPosition()));
                messageCallback.send(enemy.getName() + " was slain!");
                it.remove();
            }
        }
    }

    private boolean isInsideBoard(Position pos) {
        return pos.getX() >= 0 && pos.getX() < board.getCols()
                && pos.getY() >= 0 && pos.getY() < board.getRows();
    }

    private Position getNextPosition(Position pos, Direction dir) {
        return switch (dir) {
            case UP -> pos.offset(0, -1);
            case DOWN -> pos.offset(0, 1);
            case LEFT -> pos.offset(-1, 0);
            case RIGHT -> pos.offset(1, 0);
            case STAY -> null;
        };
    }

    public boolean isLevelComplete() {
        return enemies.isEmpty();
    }

    public String render() {
        StringBuilder sb = new StringBuilder();

        for (int y = 0; y < board.getRows(); y++) {
            for (int x = 0; x < board.getCols(); x++) {
                Position pos = new Position(x, y);
                Tile tile = board.getTile(pos);
                sb.append(tile.getTileChar());
            }
            sb.append('\n');
        }

        return sb.toString();
    }

    public Board getBoard() {
        return board;
    }

    public Player getPlayer() {
        return player;
    }
}