package Core.MazeIO;

import Core.Maze;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileReader {
    private File file;
    private Scanner scanner;

    public void readFile(String path, Maze maze) {
        ArrayList<String> lines = new ArrayList<>();
        int height = 1;
        this.file = new File(path);
        try {
            this.scanner = new Scanner(this.file);
            lines.add(scanner.nextLine());
            int width = lines.get(0).length();
            while (scanner.hasNextLine()) {
                lines.add(this.scanner.nextLine());
                height++;
            }
            maze.generateEmptyMaze((width - 1) / 2, (height - 1) / 2);
            for (int i = 0; i < lines.size(); i++) {
                for (int j = 0; j < width; j++) {
                    switch (lines.get(i).charAt(j)) {
                        case '+':
                            maze.setWall(i, j);
                            break;
                        case '0':
                            maze.setPath(i, j);
                            break;
                        case '#':
                            maze.setEntrance(i, j);
                            break;
                        case '*':
                            maze.setExit(i, j);
                            break;
                    }
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found! :(");
        }
    }
}
