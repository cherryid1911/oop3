package DomainEntities;

public interface Visitor {
    void visit(WallTile wall);
    void visit(EmptyTile empty);
    void visit(Monster monster);
    void visit(Trap trap);
    void visit(Player player);
}