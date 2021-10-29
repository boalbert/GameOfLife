import java.util.ArrayList;
import java.util.List;
public class Cell {
    boolean alive;
    int row;
    int col;

    public Cell(boolean alive, int row, int col) {
        this.alive = alive;
        this.row = row;
        this.col = col;
    }

    // Equals only checks row/col combination
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cell cell = (Cell) o;

        if (row != cell.row) return false;
        return col == cell.col;
    }

    @Override
    public int hashCode() {
        int result = row;
        result = 31 * result + col;
        return result;
    }
}
