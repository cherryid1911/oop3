package UI;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java -jar Assignment3.jar <levels-directory>");
            System.exit(1); // or throw exception
        }

        String levelsDirectory = args[0];
        GameEngine engine = new GameEngine(levelsDirectory);
        engine.run();
    }
}
