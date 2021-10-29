public class Cell {
    private boolean alive = false;

    public Cell() {
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