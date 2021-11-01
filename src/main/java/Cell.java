public class Cell {
    private boolean alive;

    public Cell() {
    }

    public Cell(boolean alive) {
        this.alive = alive;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive() {
        this.alive = true;
    }

    public void setDead() {
        this.alive = false;
    }
}