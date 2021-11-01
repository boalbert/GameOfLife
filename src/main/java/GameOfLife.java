public class GameOfLife {
    private final Grid grid;

    public GameOfLife(int rows, int columns) {
        this.grid = new Grid(rows, columns);
    }

    public Grid grid() {
        return grid;
    }
    /*
    Ideas:
    - When program is started ask user what size of grid it is
       - Don't create the grid just yet
    - Then ask what tiles has alive cells
        - Check so that the placement is not outside the gridsize
    - Only create the grid when printing out to console
     */
}
