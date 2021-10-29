public class Board {
    private Cell[][] tiles;

    public Board(int rows, int columns) {
        this.tiles = new Cell[rows][columns];
    }

    public boolean isAlive(int row, int col) {
        return tiles[row][col].alive;
    }

}
