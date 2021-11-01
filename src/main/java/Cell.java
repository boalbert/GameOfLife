public class Cell {
    private boolean alive;

    public Cell() {
    }

    public Cell(boolean alive) {
        this.alive = alive;
    }

    public boolean alive() {
        return alive;
    }

    public void setDead() {
        this.alive = false;
    }
}