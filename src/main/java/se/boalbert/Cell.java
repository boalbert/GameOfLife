package se.boalbert;

record Cell(boolean alive) {

    @Override
    public String toString() {
        return alive ? "*" : " ";
    }
}