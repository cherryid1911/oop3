package UI;

import DomainEntities.*;
import GameLogicLayer.LevelLoader;
import GameLogicLayer.LevelLoader.LoadedLevel;
import GameLogicLayer.GameLevel;
import Utils.UnitFactory;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class GameEngine implements MessageCallback {

    // _____Fields_____
    private final Scanner scanner = new Scanner(System.in);
    private final List<LoadedLevel> levels = new ArrayList<>();
    private Player player;
    private List<Player> playerOptions;
    private final String levelsDirectory;

    public GameEngine(String levelsDirectory) {
        this.levelsDirectory = levelsDirectory;
    }

    public GameEngine() {
        this("levels");
    }

    // _____Methods_____
    @Override
    public void send(String message) {
        System.out.println(message);
    }

    public void run() {
        choosePlayer();
        loadLevels();


        for (LoadedLevel loadedLevel : levels) {
            GameLevel gameLevel = new GameLevel(loadedLevel.getBoard(), player, loadedLevel.getEnemies(), this);
            player.setPosition(loadedLevel.getPlayerPos());

            while (!player.isDead() && !gameLevel.isLevelComplete()) {
                System.out.println(gameLevel.display());
                System.out.println(player.description());
                System.out.print("Enter command (w/a/s/d/e/q): ");
                String input = scanner.nextLine();
                if (!input.isEmpty()) {
                    gameLevel.gameTick(input.charAt(0));
                }
            }

            if (player.isDead()) {
                player.deadChar();
                System.out.println(gameLevel.display());
                System.out.println("You died. Game over.");
                return;
            }
        }

        System.out.println("Congratulations! You finished all levels.");
    }

    private void choosePlayer() {
        playerOptions = UnitFactory.getDefaultPlayers();

        System.out.println("Choose a player:");
        for (int i = 0; i < playerOptions.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, playerOptions.get(i).description());
        }

        int choice = -1;
        while (choice < 1 || choice > playerOptions.size()) {
            System.out.print("Enter number: ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException ignored) {}
        }

        player = playerOptions.get(choice - 1);
    }

    private void loadLevels() {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(levelsDirectory), "*.txt")) {
            for (Path path : stream) {
                LoadedLevel level = LevelLoader.loadLevel(path, player);
                levels.add(level);
            }
            levels.sort(Comparator.comparing(lvl -> lvl.getPath().getFileName().toString()));
        } catch (IOException e) {
            System.out.println("Failed to load levels: " + e.getMessage());
            System.exit(1);
        }
    }

}