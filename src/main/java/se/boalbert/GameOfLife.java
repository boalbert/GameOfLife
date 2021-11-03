package se.boalbert;

import org.apache.commons.cli.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public record GameOfLife() {

    public static void main(String[] args) throws ParseException {
        Options options = setupCommandLineOptions();
        CommandLineParser commandLineParser = new DefaultParser();
        CommandLine cmd = commandLineParser.parse(options, args);

        int generations = 20;
        Grid defaultGrid = new Grid(20, 20);


        if (cmd.hasOption("g")) {
            System.out.println("-g --generations entered");
            generations = parseGenerations(cmd);
        }

        if (cmd.hasOption("s")) {
            System.out.println("-s --size entered");
            parseSize(cmd);
            defaultGrid = parseSize(cmd);
        }

        if (cmd.hasOption("r")) {
            System.out.println("-r chosen, random start board");
            defaultGrid.randomStartBoard(new Random());
        }

        if (cmd.hasOption("p")) {
            String argPoints = cmd.getOptionValue("p");
            var splitPoints = argPoints.split(",");
            List<Point> insertThesePoints = parsePoints(splitPoints);
            insertThesePoints.forEach(defaultGrid::insertLivingCell);

        }

        if (cmd.hasOption("h")) {
            HelpFormatter helpFormatter = new HelpFormatter();
            helpFormatter.printHelp("game of life", "defualt values: grid 20x20, random startingcells, 20 generations", options, "footer", false);
        }

        printGenerations(generations, defaultGrid);

    }

    private static Grid parseSize(CommandLine cmd) {

        String sizeString = cmd.getOptionValue("s");
        var size = sizeString.split("\\.");

        int rows = parseSizeInput(size, 0);
        int columns = parseSizeInput(size, 1);

        return new Grid(rows, columns);
    }

    private static int parseGenerations(CommandLine cmd) {
        String generationString = cmd.getOptionValue("g");
        System.out.println("-g chosen, specified count of generations: " + generationString);

        return Integer.parseInt(generationString);

    }


    private static List<Point> parsePoints(String[] split) {
        List<Point> insertThesePoints = new ArrayList<>();
        for (String s : split) {
            var rowAndCol = s.split("\\.");
            int pointAtRow = Integer.parseInt(rowAndCol[0]);
            int pointAtCol = Integer.parseInt(rowAndCol[1]);
            Point point = new Point(pointAtRow, pointAtCol);
            insertThesePoints.add(point);
        }
        return insertThesePoints;
    }

    static int parseSizeInput(String[] split, int i) {
        int rows;
        rows = Integer.parseInt(split[i]);
        return rows;
    }


    static Options setupCommandLineOptions() {
        Options options = new Options();
        Option generation = Option.builder("g")
                .argName("number")
                .longOpt("generation")
                .hasArg()
                .desc("number of generations to simulate")
                .build();
        options.addOption(generation);
        options.addOption("r", "random", false, "run with random start values");
        options.addOption("h", "help", false, "print help");
        options.addOption("s", "size", true, "set size of grid via 'rows,columns', separated by .");
        options.addOption("p", "point", true, "insert point at coordinare, separated by ., example: -p 20.20,12.12,11.11");
        return options;
    }

    static Grid returnGridWithDefaultValues() {
        int defaultRows = 20;
        int defaultColumns = 20;

        Grid defaultGrid = new Grid(defaultRows, defaultColumns);
        defaultGrid.randomStartBoard(new Random());

        return defaultGrid;
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
                Thread.currentThread().interrupt();
            }
        }
    }
}
