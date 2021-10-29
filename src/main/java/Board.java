public class Board {
    private int[][] tiles;

    public Board(int rows, int columns) {
        this.tiles = new int[rows][columns];
    }

    public boolean isAlive(int row, int col) {
        return tiles[row][col] == 1;
    }
}
