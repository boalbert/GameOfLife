package se.boalbert;

import org.apache.commons.cli.ParseException;

import java.util.Arrays;
import java.util.Random;

public record GameOfLife() {
    static final int STANDARD_GENERATIONS_20 = 20;

    public static void main(String[] args) {
        System.out.println(Arrays.toString(args));
        Grid standardGrid = new Grid(40, 50);

        if (args.length > 0) {
            try {
                var cmdLineAction = new CmdLineAction(args, standardGrid);

                if (cmdLineAction.helpArgumentSupplied()) {
                    cmdLineAction.printHelpText();
                } else {
                    standardGrid = cmdLineAction.setGridProperties();
                    int numberOfGenerations = cmdLineAction.setGenerations();

                    printBoard(numberOfGenerations, standardGrid);
                }
            } catch (ParseException e) {
                printErrorMessage(e);
                System.exit(0);
            }
        } else {
            standardGrid = standardGrid.randomGrid(new Random());
            printBoard(STANDARD_GENERATIONS_20, standardGrid);
        }
    }

    private static void printErrorMessage(ParseException e) {
        System.out.println(e.getMessage());
        System.out.println("run with -h (--help) for more info");
    }

    private static void printBoard(int numberOfGenerations, Grid grid) {
        for (int i = 0; i < numberOfGenerations; i++) {
            System.out.print("\033\143");
            grid.printGrid();

            grid = grid.goToNextGeneration();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }
}
