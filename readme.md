# Game of Life CLI

- Game of Life simulation with CLI interface.
- Build the .jar and run with parameters below.
- Note: Uses Java 16 language features.

### Instructions

```bash
usage: game of life
runs a simulation of Conways Game of Life with specified parameters
 -g,--generation <number>   number of generations to simulate
 -h,--help                  print help
 -p,--point <n.n>           insert point at coordinate, separate points
                            with comma. ex: -p 20.20,12.12,11.11
 -r,--random                run with random start values
 -s,--size <n.n>            set size of grid as Rows.Columns, ex: 30.40
default values: 20 generations, 40x50 grid, random starting board
```

**Example**

- Create a grid with 50 rows, 40 columns. Random starting board. Simulate 30 generations.

```bash
java -jar build/libs/GameOfLife-1.0-uber.jar -s 50.40 -g 30 -r
```

- Use standard grid (40x50), set 5 points manually, run for 10 generations.

```bash
java -jar build/libs/GameOfLife-1.0-uber.jar -p 19.4,19.5,20.4,20.5,20.6 -g 10
```

- Run a simulation with default parameters

```bash
java -jar build/libs/GameOfLife-1.0-uber.jar
```

### Build

```
$ ./gradlew uberJar
```

### Dependencies

[Apache Commons CLI 1.5.0](https://commons.apache.org/proper/commons-cli/)