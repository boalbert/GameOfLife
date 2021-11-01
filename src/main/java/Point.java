public record Point(int row, int col) {
    public boolean isInside(Grid grid) {
        return !(row() < 0 || row() >= grid.numberOfRows() || col() < 0 || col() >= grid.numberOfColumns());
    }
}
