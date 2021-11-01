record Cell(boolean alive) {
    @Override
    public String toString() {
        if (alive) return "⏣";
        return " ";
    }
}