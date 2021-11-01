import java.util.List;

public record Point(int row, int col) {
    public boolean isInside(int maxRows, int maxColumns) {
        return !(row() < 0 || row() >= maxRows || col() < 0 || col() >= maxColumns);
    }

    public List<Point> neighbours(Point point) {
        return List.of(
                new Point(point.row() - 1, point.col() - 1),
                new Point(point.row() - 1, point.col()),
                new Point(point.row() - 1, point.col() + 1),

                new Point(point.row(), point.col() - 1),
                new Point(point.row(), point.col() + 1),

                new Point(point.row() + 1, point.col() - 1),
                new Point(point.row() + 1, point.col()),
                new Point(point.row() + 1, point.col() + 1)
        );
    }

    public List<Point> neighboursInsideGrid(Point point, int maxRows, int maxColumns) {
        return neighbours(point).stream()
                .filter(p -> p.isInside(maxRows, maxColumns))
                .toList();
    }
}
