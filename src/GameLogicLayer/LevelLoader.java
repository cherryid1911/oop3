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
        private final List<Enemy> enemies;
        private final Path path;
        private Position playerPos;


        //_____Constructor_____
        public LoadedLevel(Board board, List<Enemy> enemies, Path path, Position playerPos) {
            this.board = board;
            this.enemies = enemies;
            this.path = path;
            this.playerPos = playerPos;
        }


        // _____Getters_____
        public Board getBoard() {
            return board;
        }

        public List<Enemy> getEnemies() {
            return enemies;
        }

        public Path getPath() {
            return path;
        }

        public Position getPlayerPos() { return playerPos; }
    }


    // _____Methods_____
    public static LoadedLevel loadLevel(Path filePath, Player player) throws IOException {
        List<String> lines = Files.readAllLines(filePath);
        int rows = lines.size();
        int cols = lines.getFirst().length();

        Board board = new Board(rows, cols);
        List<Enemy> enemies = new ArrayList<>();
        Position playerPos = new Position(0, 0);

        for (int y = 0; y < rows; y++) {
            String line = lines.get(y);
            for (int x = 0; x < cols; x++) {
                char c = line.charAt(x);
                Position pos = new Position(x, y);

                switch (c) {
                    case '.' -> board.setTile(pos, new EmptyTile(pos));
                    case '#' -> board.setTile(pos, new WallTile(pos));
                    case '@' -> {
                        board.setTile(pos, player);
                        playerPos = pos;
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

        return new LoadedLevel(board, enemies, filePath, playerPos);
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