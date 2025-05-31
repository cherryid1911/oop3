public class Tile {
    private char Tile;
    private int[] Position;

    Tile(char t, int[] coor){
        if (coor.length!=2){
            throw new RuntimeException();
        }
        this.Tile=t;
        this.Position =coor;
    }

    public int[] getPosition(){return Position;}

    public double Distance(Tile t) {
        int[] otherPos = t.getPosition();
        double dx = otherPos[0] - Position[0];
        double dy = otherPos[1] - Position[1];
        return Math.sqrt(dx * dx + dy * dy);
    }



}
