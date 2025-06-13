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
        this.player.setEnemies(this.enemies);
        for (Enemy e : enemies) {
            e.initialize(messageCallback);
        }
    }

    // _____Methods_____
    public void gameTick(char input) {
        Position destPos = null;
        Position oldPos = player.getPosition();

        switch (input) {
            case 'w' -> destPos = oldPos.offset(0, -1);
            case 's' -> destPos = oldPos.offset(0, 1);
            case 'a' -> destPos = oldPos.offset(-1, 0);
            case 'd' -> destPos = oldPos.offset(1, 0);
            case 'e' -> player.castAbility(player);
            case 'q' -> messageCallback.send("Player chose to wait.");
            default -> messageCallback.send("Invalid input: " + input);
        }

        if (destPos != null && isInsideBoard(destPos)) {
            Tile destTile = board.getTile(destPos);
            destTile.accept(player);
            Position newPos = player.getPosition();
            if (!newPos.equals(oldPos)) {
                board.setTile(oldPos, new EmptyTile(oldPos));
                board.setTile(newPos, player);
            }
        }
        player.onGameTick();

        Iterator<Enemy> it = enemies.iterator();
        while (it.hasNext()) {
            Enemy enemy = it.next();
            if (!enemy.isDead()) {
                Position oldPos2 = enemy.getPosition();
                enemy.onGameTick();
                Direction dir = enemy.onEnemyTurn(player);
                Position destPos2 = getNextPosition(enemy.getPosition(), dir);
                if (destPos2 != null && isInsideBoard(destPos2)) {
                    Tile destTile = board.getTile(destPos2);
                    destTile.accept(enemy);
                    Position newPos2 = enemy.getPosition();
                    if (!enemy.isDead() && !newPos2.equals(oldPos2)) {
                        board.setTile(oldPos2, new EmptyTile(oldPos2));
                        board.setTile(newPos2, enemy);
                    }
                }
            }
            else {
                // board.setTile(enemy.getPosition(), player);
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

    public String display() {
        StringBuilder sb = new StringBuilder();

        for (int y = 0; y < board.getRows(); y++) {
            for (int x = 0; x < board.getCols(); x++) {
                Position pos = new Position(x, y);
                Tile tile = board.getTile(pos);
                sb.append(tile.toString());
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