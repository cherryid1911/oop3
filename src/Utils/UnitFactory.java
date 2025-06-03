package Utils;

import DomainEntities.*;
import java.util.List;

public class UnitFactory {

    // _____Fields_____
    public static final List<Player> players = List.of(
            new Warrior("Jon Snow", new Position(0,0), 300, 30, 4, 3),
            new Warrior("The Hound", new Position(0, 0), 400, 20, 6, 5),
            new Mage("Melisandre", new Position(0, 0), 100, 5, 1, 300, 30, 15, 5, 6),
            new Mage("Thoros of Myr", new Position(0, 0), 250, 25, 4, 150, 20, 20, 3, 4),
            new Rogue("Arya Stark", new Position(0, 0), 150, 40, 2, 20),
            new Rogue("Bronn", new Position(0, 0), 250, 35, 3, 50)
    );

    public static final List<Monster> monsters = List.of(
            new Monster("Lannister Soldier", 's', new Position(0, 0), 80, 8, 3, 25, 3),
            new Monster("Lannister Knight", 'k', new Position(0, 0), 200, 14, 8, 50, 4),
            new Monster("Queen’s Guard", 'q', new Position(0, 0), 400, 20, 15, 100, 5),
            new Monster("Wright", 'z', new Position(0, 0), 600, 30, 15, 100, 3),
            new Monster("Bear-Wright", 'b', new Position(0, 0), 1000, 75, 30, 250, 4),
            new Monster("Giant-Wright", 'g', new Position(0, 0), 1500, 100, 40, 500, 5),
            new Monster("White Walker", 'w', new Position(0, 0), 2000, 150, 50, 1000, 6),
            new Monster("The Mountain", 'M', new Position(0, 0), 1000, 60, 25, 500, 6),
            new Monster("Queen Cersei", 'C', new Position(0, 0), 100, 10, 10, 1000, 1),
            new Monster("Night’s King", 'K', new Position(0, 0), 5000, 300, 150, 5000, 8)
    );

    public static final List<Trap> traps = List.of(
            new Trap("Bonus Trap", 'B', new Position(0, 0), 1, 1, 1, 250, 1, 5),
            new Trap("Queen’s Trap", 'Q', new Position(0, 0), 250, 50, 10, 100, 3, 7),
            new Trap("Death Trap", 'D', new Position(0, 0), 500, 100, 20, 250, 1, 10)
    );


    // _____Methods_____
    public static Monster getMonsterByTile(char c) {
        return monsters.stream()
                .filter(m -> m.getTileChar() == c)
                .findFirst()
                .orElse(null);
    }

    public static Trap getTrapByTile(char c) {
        return traps.stream()
                .filter(t -> t.getTileChar() == c)
                .findFirst()
                .orElse(null);
    }

    public static List<Player> getDefaultPlayers() {
        return players;
    }

}
