package se.boalbert;

import org.apache.commons.cli.*;

import java.util.ArrayList;
import java.util.List;

public record GameOfLife() {

    public static void main(String[] args) throws ParseException {
        CommandLineParser commandLineParser = new DefaultParser();

        CommandLineHelper commandLineHelper = new CommandLineHelper(commandLineParser, args);

        commandLineHelper.parseArgs();

        /*try {

        } catch (ParseException e) {
            System.out.println(e.getMessage());
            helpText(options);
            System.exit(0);
        }*/

        /*int generations = 20;
        Grid defaultGrid = new Grid(20, 20);

        if (cmd.hasOption("g")) {
            System.out.println("-g --generations entered");
            generations = parseGenerationArg(cmd);
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
            List<Point> insertThesePoints = parsePointsArg(splitPoints);
            insertThesePoints.forEach(defaultGrid::insertLivingCell);

        }

        if (cmd.hasOption("h")) {
            helpText(options);
        }

        if (!cmd.hasOption("h"))
            printGenerations(generations, defaultGrid);*/

    }

    private static void helpText(Options options) {
        HelpFormatter helpFormatter = new HelpFormatter();

        String usage = "game of life";
        String header = "runs a simulation of Conways Game of Life with specified parameters";
        String footer = "default values: 20 generations, 20x20 grid, random starting board";

        helpFormatter.printHelp(usage, header, options, footer, false);
    }

    private static Grid parseSize(CommandLine cmd) {

        String sizeString = cmd.getOptionValue("s");
        var size = sizeString.split("\\.");

        int rows = parseSizeArg(size, 0);
        int columns = parseSizeArg(size, 1);

        return new Grid(rows, columns);
    }

    private static int parseGenerationArg(CommandLine cmd) {
        String generationString = cmd.getOptionValue("g");
        System.out.println("-g chosen, specified count of generations: " + generationString);

        return Integer.parseInt(generationString);

    }

    private static List<Point> parsePointsArg(String[] split) {
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

    private static int parseSizeArg(String[] split, int i) {
        int rows;
        rows = Integer.parseInt(split[i]);
        return rows;
    }

    private static Options setupCommandLineOptions() {
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
