package Core.MazeIO;

import Core.Maze;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class FileWriter {

    public void saveToFile(String path, Maze maze) {
        try {
            PrintWriter printWriter = new PrintWriter(path);
            int width = maze.getWidth() * 2 + 1;
            int height = maze.getHeight() * 2 + 1;
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    printWriter.print(maze.getMazeElement(j, i).getSymbol());
                }
                printWriter.println();
            }
            printWriter.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found! :(");
        }
    }
}
