import java.util.ArrayList;
import java.util.List;

public class GameOfLife {
    private final List<Cell> aliveCells = new ArrayList<>();

    public boolean setAlive(int row, int col) {
        if(aliveCells.contains(new Cell(1, row, col)))
            return false;
        return aliveCells.add(new Cell(1, row, col));
    }

    public List<Cell> getAliveCells(){
        return List.copyOf(aliveCells);
    }
}
