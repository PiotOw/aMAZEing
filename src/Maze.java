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

    private void divide(int xStart, int xEnd, int yStart, int yEnd) {
        int spaceWidth = xEnd - xStart;
        int spaceHeight = yEnd - yStart;
        boolean verticalWall = false;
        int wallPlacement;
        int doorPlacement;
        Random random = new Random();
        if (spaceWidth > 2 && spaceHeight > 2) {
            if (spaceWidth == spaceHeight) {
                verticalWall = random.nextBoolean();
            } else if (spaceWidth > spaceHeight) {
                verticalWall = true;
            }
            wallPlacement = random.nextInt(((verticalWall ? spaceWidth : spaceHeight) / 2) - 1) * 2 + 2;
            doorPlacement = random.nextInt((verticalWall ? spaceHeight : spaceWidth) / 2) * 2 + 1;
            for (int i = (verticalWall ? yStart : xStart); i < (verticalWall ? yEnd : xEnd); i++) {
                setWall((verticalWall ? i : yStart + wallPlacement), (verticalWall ? xStart + wallPlacement : i));
            }
            setPath(yStart + (verticalWall ? doorPlacement : wallPlacement), xStart + (verticalWall ? wallPlacement : doorPlacement));
            divide(xStart, (verticalWall ? xStart + wallPlacement : xEnd), yStart, (verticalWall ? yEnd : yStart + wallPlacement));
            divide(xStart + (verticalWall ? wallPlacement : 0), xEnd, yStart + (verticalWall ? 0 : wallPlacement), yEnd);

//            DUPLICATED?!
            /*
            if (verticalWall) {
                wallPlacement = random.nextInt((spaceWidth / 2) - 1) * 2 + 2;
                doorPlacement = random.nextInt((spaceHeight / 2)) * 2 + 1;
                for (int i = yStart; i < yEnd; i++) {
                    setWall(i, xStart + wallPlacement);
                }
                setPath(yStart + doorPlacement, xStart + wallPlacement);
                divide(xStart, xStart + wallPlacement, yStart, yEnd);

                divide(xStart + wallPlacement, xEnd, yStart, yEnd);
            }
            else {
                wallPlacement = random.nextInt((spaceHeight / 2) - 1) * 2 + 2;
                doorPlacement = random.nextInt((spaceWidth / 2)) * 2 + 1;
                for (int i = xStart; i < xEnd; i++) {
                    setWall(yStart + wallPlacement, i);
                }
                setPath(yStart + wallPlacement, xStart + doorPlacement);
                divide(xStart, xEnd, yStart, yStart + wallPlacement);

                divide(xStart, xEnd, yStart + wallPlacement, yEnd);
            }
            */
        }
    }

    public void generateEmptyMaze(int width, int height) {
        this.width = width;
        this.height = height;
        maze = new MazeElement[height * 2 + 1][width * 2 + 1];
        for (int i = 0; i < this.maze.length; i++) {
            for (int j = 0; j < this.maze[0].length; j++) {
                if (i == 0 || i == this.maze.length - 1 || j == 0 || j == this.maze[0].length - 1) {
                    setWall(i, j);
                } else {
                    setPath(i, j);
                }
            }
        }
        makeEntranceExit();
        divide(0, width * 2, 0, height * 2);
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
