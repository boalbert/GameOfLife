import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class GameOfLife {

    private final List<Cell> aliveCells = new ArrayList<>();

    public boolean setAlive(int row, int col) {
        if(aliveCells.contains(new Cell(true, row, col)))
            return false;
        return aliveCells.add(new Cell(true, row, col));
    }

    public List<Cell> getAliveCells() {
        return List.copyOf(aliveCells);
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
