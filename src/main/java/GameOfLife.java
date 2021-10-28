import java.util.ArrayList;
import java.util.List;

public class GameOfLife {
    private final List<Cell> aliveCells = new ArrayList<>();

    public boolean setAlive(int row, int col) {
        if(aliveCells.contains(new Cell(row, col)))
            return false;
        return aliveCells.add(new Cell(row, col));
    }

    public List<Cell> getAliveCells(){
        return List.copyOf(aliveCells);
    }
}
