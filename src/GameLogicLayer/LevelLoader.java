package GameLogicLayer;

import DomainEntities.*;
import Utils.UnitFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class LevelLoader {

    // _____NestedClass_____
    public static class LoadedLevel {

        // _____Fields_____
        private final Board board;
        private final Player player;
        private final List<Enemy> enemies;
        private final Path path;


        //_____Constructor_____
        public LoadedLevel(Board board, Player player, List<Enemy> enemies, Path path) {
            this.board = board;
            this.player = player;
            this.enemies = enemies;
            this.path = path;
        }


        // _____Getters_____
        public Board getBoard() {
            return board;
        }

        public Player getPlayer() {
            return player;
        }

        public List<Enemy> getEnemies() {
            return enemies;
        }

        public Path getPath() {
            return path;
        }
    }


    // _____Methods_____
    public static LoadedLevel loadLevel(Path filePath, Player player) throws IOException {
        List<String> lines = Files.readAllLines(filePath);
        int rows = lines.size();
        int cols = lines.get(0).length();

        Board board = new Board(rows, cols);
        List<Enemy> enemies = new ArrayList<>();

        for (int y = 0; y < rows; y++) {
            String line = lines.get(y);
            for (int x = 0; x < cols; x++) {
                char c = line.charAt(x);
                Position pos = new Position(x, y);

                switch (c) {
                    case '.' -> board.setTile(pos, new EmptyTile(pos));
                    case '#' -> board.setTile(pos, new WallTile(pos));
                    case '@' -> {
                        player.setPosition(pos);
                        board.setTile(pos, player);
                    }
                    default -> {
                        Enemy enemy = createEnemyFromChar(c, pos);
                        if (enemy != null) {
                            enemies.add(enemy);
                            board.setTile(pos, enemy);
                        } else {
                            board.setTile(pos, new EmptyTile(pos)); // fallback
                        }
                    }
                }
            }
        }

        return new LoadedLevel(board, player, enemies, filePath);
    }

    protected static Enemy createEnemyFromChar(char c, Position pos) {
        Monster monster = UnitFactory.getMonsterByTile(c);
        if (monster != null) {
            return new Monster(monster, pos);
        }

        Trap trap = UnitFactory.getTrapByTile(c);
        if (trap != null) {
            return new Trap(trap, pos);
        }

        Boss boss = UnitFactory.getBossByTile(c);
        if (boss != null) {
            return new Boss(boss, pos);
        }

        return null;
    }
}