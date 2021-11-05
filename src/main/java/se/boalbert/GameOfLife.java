package se.boalbert;

import org.apache.commons.cli.ParseException;

import java.util.Random;

public record GameOfLife() {
    public static void main(String[] args) {

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
            }
        } else {
            standardGrid.randomStartBoard(new Random());
            printBoard(20, standardGrid);
        }
    }

    private static void printErrorMessage(ParseException e) {
        System.out.println(e.getMessage());
        System.out.println("run with -h (--help) for more info");
        System.exit(0);
    }

    private static void printBoard(int numberOfGenerations, Grid grid) {
        for (int i = 0; i < numberOfGenerations; i++) {
            System.out.print("\033\143");
            grid.printGrid();
            grid.goToNextGeneration();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }
}
