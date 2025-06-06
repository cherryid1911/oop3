package GameLogicLayer;

import DomainEntities.*;

public class Board {

    // _____Fields_____
    private final int rows;
    private final int cols;
    private final Tile[][] tiles;


    //_____Constructor_____
    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.tiles = new Tile[rows][cols];
    }


    // _____Getters_____
    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }


    // _____Methods_____
    public Tile getTile(Position position) {
        return tiles[position.getY()][position.getX()];
    }

    public void setTile(Position position, Tile tile) {
        tiles[position.getY()][position.getX()] = tile;
    }

}