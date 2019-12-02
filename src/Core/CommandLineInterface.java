package Core;

import Core.BFSAlgorithm.BFSAlgorithm;
import Core.MazeIO.FileReader;
import Core.MazeIO.FileWriter;
import Core.TremauxAlgorithm.TremauxAlgorithm;

import java.util.Scanner;

public class CommandLineInterface {
    private Scanner scanner = new Scanner(System.in);
    private int width;
    private int height;
    private Maze maze = new Maze();

    private void printCutoff(int n) {
        for (int i = 0; i < n; i++) {
            System.out.print("*");
        }
        System.out.println();
    }

    private void printBlankSpace(int n) {
        for (int i = 0; i < n; i++) {
            System.out.println();
        }
    }

    private void printTitle(String string) {
        printCutoff(string.length() + 4);
        System.out.println("* " + string + " *");
        printCutoff(string.length() + 4);
        printBlankSpace(2);
    }

    private void printMainMenu() {
        printTitle("Welcome to aMAZEing!");
        System.out.println("Choose what do you want to do and type number according to instruction:");
        printBlankSpace(1);
        System.out.println("1: Generate new maze");
        System.out.println("2: Load maze from file");
    }

    private void widthInput() {
        System.out.println("What width should the maze be?");
        printBlankSpace(7);
        this.width = this.scanner.nextInt();
    }

    private void heightInput() {
        System.out.println("What height should the maze be?");
        printBlankSpace(7);
        this.height = this.scanner.nextInt();
    }

    private void printMaze() {
        printTitle("This is Your maze");
        this.maze.printMaze();
    }

    private void mazeSaverMenu() {
        printTitle("Maze Saver");
        FileWriter fileWriter = new FileWriter();
        System.out.println("Input absolute path to file You want Your maze to be saved to");
        printBlankSpace(7);
        String absolutePath = scanner.next();
        fileWriter.saveToFile(absolutePath, this.maze);
    }

    private void mazeGenerationMenu() {
        printTitle("Maze Generation");
        widthInput();
        printTitle("Maze Generation");
        heightInput();
        this.maze.generateMaze(this.width, this.height);
        printBlankSpace(2);
        System.out.println("Do You want to save it to File?");
        System.out.println("1: Yes");
        System.out.println("2: No");
        printBlankSpace(7);
        if (inputOnly1or2() == 1) {
            mazeSaverMenu();
        }
    }

    private void mazeLoadingMenu() {
        printTitle("Maze Loader");
        FileReader fileReader = new FileReader();
        System.out.println("Input absolute path to source file");
        printBlankSpace(7);
        String absolutePath = this.scanner.next();
        fileReader.readFile(absolutePath, maze);
    }

    private int inputOnly1or2() {
        int choice = 0;
        int tried = 0;
        while (choice != 1 && choice != 2) {
            if (tried == 1) {
                System.out.println("Wrong input! Choose your option again:");
            }
            choice = scanner.nextInt();
            tried = 1;
        }
        return choice;
    }

    private void solveMenu() {
        printTitle("Let's Solve!");
        System.out.println("What algorithm should I solve Your maze with?");
        System.out.println("1: BFS Algorithm");
        System.out.println("2: Trémaux's algorithm");
        printBlankSpace(1);
        printExample();
        printBlankSpace(1);
        int choice;
        choice = inputOnly1or2();
        if (choice == 1) {
            BFSAlgorithm bfsAlgorithm = new BFSAlgorithm();
            bfsAlgorithm.solve(maze.getMaze());
        } else if (choice == 2) {
            TremauxAlgorithm tremauxAlgorithm = new TremauxAlgorithm();
            tremauxAlgorithm.solve(maze.getMaze());
        }
        printBlankSpace(3);
        System.out.println("Do you want to solve the same maze using second algorithm?");
        System.out.println("1: Yes!");
        System.out.println("2: No, Goodbye! :(");
        printBlankSpace(2);
        if (inputOnly1or2() == 1) {
            if (choice == 2) {
                BFSAlgorithm bfsAlgorithm = new BFSAlgorithm();
                bfsAlgorithm.solve(maze.getMaze());
            } else if (choice == 1) {
                TremauxAlgorithm tremauxAlgorithm = new TremauxAlgorithm();
                tremauxAlgorithm.solve(maze.getMaze());
            }
        }
    }

    private void typeAnythingToContinue() {
        printBlankSpace(2);
        System.out.println("Type anything if You want to proceed to solving");
        printBlankSpace(2);
        this.scanner.next();
    }

    private void printExample() {
        System.out.println("Rooms will be numbered like this:");
        System.out.println("-------------");
        System.out.println("| 0 | 1 | 2 |");
        System.out.println("-------------");
        System.out.println("| 3 | 4 | 5 |");
        System.out.println("-------------");
        System.out.println("| 6 | 7 | 9 |");
        System.out.println("-------------");
    }

    private void mainMenu() {
        printMainMenu();
        int choice;
        choice = inputOnly1or2();
        if (choice == 1) {
            printBlankSpace(7);
            mazeGenerationMenu();
        } else if (choice == 2) {
            printBlankSpace(7);
            mazeLoadingMenu();
        }
    }

    public void start() {
        mainMenu();
        printMaze();
        typeAnythingToContinue();
        solveMenu();
    }
}
