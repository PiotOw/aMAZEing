package BFSAlgorithm;

import MazeElements.Entrance;
import MazeElements.Exit;
import MazeElements.MazeElement;
import MazeElements.Path;

import java.util.ArrayList;

public class Graph {
    private ArrayList<GraphElement> elementsList;

    public Graph() {
        this.elementsList = new ArrayList<>();
    }

    private int mazeIndexToElementId(int x, int y, int width) {
        return (x - 1) / 2 + (y - 1) / 2 * width;
    }

    public GraphElement getElement(int index) {
        return this.elementsList.get(index);
    }

    private void checkConnections(int x, int y, GraphElement graphElement, MazeElement[][] maze) {
        int columns = maze[0].length / 2;
        if (maze[y - 1][x] instanceof Path) {
            graphElement.addNeighbour(this.elementsList.get(mazeIndexToElementId(x, y - 2, columns)));
        }
        if (maze[y][x + 1] instanceof Path) {
            graphElement.addNeighbour(this.elementsList.get(mazeIndexToElementId(x + 2, y, columns)));
        }
        if (maze[y + 1][x] instanceof Path) {
            graphElement.addNeighbour(this.elementsList.get(mazeIndexToElementId(x, y + 2, columns)));
        }
        if (maze[y][x - 1] instanceof Path) {
            graphElement.addNeighbour(this.elementsList.get(mazeIndexToElementId(x - 2, y, columns)));
        }
    }

    private void findEntranceExit(MazeElement[][] maze) {
        int width = maze[0].length;
        int height = maze.length;
        int columns = width / 2;
        for (int i = 1; i < width; i += 2) {
            if (maze[0][i] instanceof Entrance) {
                this.elementsList.get(mazeIndexToElementId(i, 1, columns)).setEntrance();
            } else if (maze[0][i] instanceof Exit) {
                this.elementsList.get(mazeIndexToElementId(i, 1, columns)).setExit();
            }
            if (maze[height - 1][i] instanceof Entrance) {
                this.elementsList.get(mazeIndexToElementId(i, height - 2, columns)).setEntrance();
            } else if (maze[height - 1][i] instanceof Exit) {
                this.elementsList.get(mazeIndexToElementId(i, height - 2, columns)).setExit();
            }
        }
        for (int i = 1; i < height; i += 2) {
            if (maze[i][0] instanceof Entrance) {
                this.elementsList.get(mazeIndexToElementId(1, i, columns)).setEntrance();
            } else if (maze[i][0] instanceof Exit) {
                this.elementsList.get(mazeIndexToElementId(1, i, columns)).setExit();
            }
            if (maze[i][width - 1] instanceof Entrance) {
                this.elementsList.get(mazeIndexToElementId(width - 2, i, columns)).setEntrance();
            } else if (maze[i][width - 1] instanceof Exit) {
                this.elementsList.get(mazeIndexToElementId(width - 2, i, columns)).setExit();
            }
        }
    }

    public void mazeToGraph(MazeElement[][] maze) {
        int width = maze[0].length;
        int height = maze.length;
        int columns = width / 2;
        GraphElement currentElement;
        for (int i = 1; i < height; i += 2) {
            for (int j = 1; j < width; j += 2) {
                currentElement = new GraphElement(mazeIndexToElementId(j, i, columns));
                this.elementsList.add(currentElement);
            }
        }
        for (int i = 1; i < height; i += 2) {
            for (int j = 1; j < width; j += 2) {
                checkConnections(j, i, elementsList.get(mazeIndexToElementId(j, i, columns)), maze);
            }
        }
        findEntranceExit(maze);

    }
}
