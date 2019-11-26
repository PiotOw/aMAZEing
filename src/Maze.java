import MazeElements.*;

import java.util.Random;

public class Maze {
    private int width;

    private int height;

    private MazeElement[][] maze;

    private void setEntrance(int y, int x) {
        this.maze[y][x] = new Entrance();
    }

    private void setExit(int y, int x) {
        this.maze[y][x] = new Exit();
    }

    private void setWall(int y, int x) {
        this.maze[y][x] = new Wall();
    }

    private void setPath(int y, int x) {
        this.maze[y][x] = new Path();
    }

    private int calculatePathIndex(int x) {
        return x * 2 + 1;
    }

    private int calculateWallIndex(int x) {
        return x * 2;
    }

    private void makeEntranceExit() {
        Random random = new Random();
        int range;
        int entranceExactPosition;
        int exitExactPosition;
        int entrancePosition = random.nextInt(4);
        if (entrancePosition % 2 == 0) {
            range = this.width;
        } else {
            range = this.height;
        }
        entranceExactPosition = calculatePathIndex(random.nextInt(range));
        exitExactPosition = calculatePathIndex(random.nextInt(range));
        if (entrancePosition == 0) {
            setEntrance(0, entranceExactPosition);
            setExit(calculateWallIndex(height), exitExactPosition);
        } else if (entrancePosition == 1) {
            setEntrance(entranceExactPosition, calculateWallIndex(width));
            setExit(exitExactPosition, 0);
        } else if (entrancePosition == 2) {
            setEntrance(calculateWallIndex(height), entranceExactPosition);
            setExit(0, exitExactPosition);
        } else {
            setEntrance(entranceExactPosition, 0);
            setExit(exitExactPosition, calculateWallIndex(width));
        }
    }

    public void generateEmptyMaze(int width, int height) {
        this.width = width;
        this.height = height;
        maze = new MazeElement[height * 2 + 1][width * 2 + 1];
        for (int i = 0; i < this.maze.length; i++) {
            for (int j = 0; j < this.maze[0].length; j++) {
                if(i == 0 || i == this.maze.length - 1 || j == 0 || j == this.maze[0].length - 1) {
                    setWall(i, j);
                } else {
                    setPath(i, j);
                }
            }
        }
        makeEntranceExit();
    }

    public void printMaze() {
        for (int i = 0; i < this.maze.length; i++) {
            for (int j = 0; j < this.maze[0].length; j++) {
                System.out.print(this.maze[i][j].getSymbol());
            }
            System.out.println();
        }
    }
}
