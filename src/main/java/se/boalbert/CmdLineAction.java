package se.boalbert;

import org.apache.commons.cli.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CmdLineAction {
    private static final int STANDARD_GENERATIONS = 20;
    private static final CommandLineParser commandLineParser = new DefaultParser();
    private static final Options options = setupCommandLineOptions();

    private final CommandLine cmd;
    private Grid modifiedGrid;

    public CmdLineAction(String[] args, Grid modifiedGrid) throws ParseException {
        this.cmd = commandLineParser.parse(options, args);
        this.modifiedGrid = modifiedGrid;
    }

    private static Options setupCommandLineOptions() {
        Options options = new Options();

        options.addOption("r", "random", false, "run with random start values");
        options.addOption("h", "help", false, "print help");

        Option size = Option.builder("s").argName("n.n").longOpt("size").hasArg().desc("set size of grid as Rows.Columns, ex: 30.40")
                .build();
        options.addOption(size);

        Option generation = Option.builder("g").argName("number").longOpt("generation").hasArg().desc("number of generations to simulate")
                .build();
        options.addOption(generation);

        Option point = Option.builder("p").argName("n.n").longOpt("point").hasArg().desc("insert point at coordinate, separate points with comma. ex: -p 20.20,12.12,11.11")
                .build();
        options.addOption(point);
        return options;

    }

    public int setGenerations() {
        if (cmd.hasOption("g")) {
            String generationString = cmd.getOptionValue("g");
            return parseNumberOfGenerations(generationString);
        }
        return STANDARD_GENERATIONS;
    }

    public boolean helpArgumentSupplied() {
        return cmd.hasOption("h");
    }

    public Grid setGridProperties() {
        if (cmd.hasOption("s")) {
            String sizeString = cmd.getOptionValue("s");
            modifiedGrid = parseGridSize(sizeString);
        }

        if (cmd.hasOption("p")) {
            String argPoints = cmd.getOptionValue("p");
            var splitPoints = argPoints.split(",");
            List<Point> insertThesePoints = parseGridPoints(splitPoints);
            insertThesePoints.forEach(modifiedGrid::insertLivingCell);
        }

        if (cmd.hasOption("r")) {
            modifiedGrid = modifiedGrid.randomGrid(new Random());
        }

        return modifiedGrid;
    }

    public void printHelpText() {
        HelpFormatter helpFormatter = new HelpFormatter();

        String usage = "game of life";
        String header = "runs a simulation of Conways Game of Life with specified parameters";
        String footer = "default values: 20 generations, 40x50 grid, random starting board";

        helpFormatter.printHelp(usage, header, options, footer, false);
    }

    private List<Point> parseGridPoints(String[] split) {
        List<Point> suppliedPoints = new ArrayList<>();
        for (String s : split) {
            var rowAndCol = s.split("\\.");
            int pointAtRow = Integer.parseInt(rowAndCol[0]);
            int pointAtCol = Integer.parseInt(rowAndCol[1]);
            Point point = new Point(pointAtRow, pointAtCol);
            suppliedPoints.add(point);
        }
        return suppliedPoints;
    }

    private Grid parseGridSize(String sizeString) {
        var sizeArgument = sizeString.split("\\.");


        int rows = Integer.parseInt(sizeArgument[0].trim());
        int columns = Integer.parseInt(sizeArgument[1]);

        return new Grid(rows, columns);
    }

    private int parseNumberOfGenerations(String generationValue) {
        return Integer.parseInt(generationValue);
    }
}
