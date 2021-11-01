public class GameOfLife {
    private final Grid grid;

    public GameOfLife(int rows, int columns) {
        this.grid = new Grid(rows, columns);
    }

    public Grid grid() {
        return grid;
    }
}
