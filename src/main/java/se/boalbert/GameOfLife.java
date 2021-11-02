package se.boalbert;

import java.util.Random;
import java.util.Scanner;

public record GameOfLife() {
    final static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to Game of Life");
        menu();
    }

    static void menu() {
        String greeting = """
                - Press (1) to start the game.
                - Press (2) to show the rules.""";
        System.out.println(greeting);
        startMenu();

    }

    static void rules() {
        String rules = """
                Rules:
                1. Any live cell with fewer than two live neighbours dies, as if caused by underpopulation.
                2. Any live cell with more than three live neighbours dies, as if by overcrowding.
                3. Any live cell with two or three live neighbours lives on to the next generation.
                4. Any dead cell with exactly three live neighbours becomes a live cell.""";
        System.out.println(rules);
        startMenu();
    }

    static void startMenu() {
        int userInput = scanner.nextInt();
        switch (userInput) {
            case 1 -> enterSizeOfGrid();
            case 2 -> rules();
        }
    }

    private static void enterSizeOfGrid() {
        System.out.println("Enter size of grid.");
        System.out.print("Rows: ");
        final int rows = scanner.nextInt();

        System.out.print("Columns: ");
        final int columns = scanner.nextInt();

        selectRandomOrManualStartingBoard(new Grid(rows, columns));
    }

    private static void selectRandomOrManualStartingBoard(Grid grid) {
        System.out.println("(1) Generate random board");
        System.out.println("(2) Manual input");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1 -> randomBoard(grid);
            case 2 -> manualInput(grid);
        }
    }

    private static void manualInput(Grid grid) {

        String continueInput = "Y";

        while (continueInput.equalsIgnoreCase("Y")) {
            insertPoint(grid);
            System.out.print("Continue? (Y/N): ");
            continueInput = scanner.next();
        }
        enterNumberOfGenerations(grid);
    }

    private static void enterNumberOfGenerations(Grid grid) {
        System.out.print("Enter number of generations to simulate: ");
        int numberOfGenerations = scanner.nextInt();
        printGenerations(numberOfGenerations, grid);
    }

    private static void insertPoint(Grid grid) {
        System.out.print("Row: ");
        int row = scanner.nextInt();
        System.out.print("Col: ");
        int col = scanner.nextInt();

        var chosenPoint = new Point(row, col);

        if (chosenPoint.isInside(grid.numberOfRows(), grid.numberOfColumns())) {
            grid.insertLivingCell(chosenPoint);
        } else {
            System.out.println("Error: You can't insert points outside grid.");
        }
    }

    private static void randomBoard(Grid grid) {
        grid.randomStartBoard(new Random());
        enterNumberOfGenerations(grid);
    }

    private static void printGenerations(int numberOfGenerations, Grid grid) {
        for (int i = 0; i < numberOfGenerations; i++) {
            System.out.print("\033\143");

            grid.printGrid('⏣');
            grid.goToNextGeneration();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Simulation over.");
    }
}
