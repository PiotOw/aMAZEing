import MazeElements.*;

public class Maze {
    private MazeElement[][] maze;

    private void setEntrance(int x, int y) {
        this.maze[x][y] = new Entrance();
    }

    private void setExit(int x, int y) {
        this.maze[x][y] = new Exit();
    }

    private void setWall(int x, int y) {
        this.maze[x][y] = new Wall();
    }

    private void setPath(int x, int y) {
        this.maze[x][y] = new Path();
    }

    public void generateEmptyMaze(int width, int height) {
        maze = new MazeElement[width][height];
        for(int i = 0; i < this.maze.length; i++) {
            for (int j = 0; j < this.maze[0].length; j++) {
                setPath(i, j);
            }
        }
        for(int w = 0; w < this.maze.length; w++) {
            setWall(w, 0);
            setWall(w, height * 2);
        }
        for (int i = 0; i < this.maze[0].length; i++) {
            setWall(0, i);
            setWall(width * 2, i);
        }
    }

    public void printMaze() {
        for(int i = 0; i < this.maze.length; i++) {
            for (int j = 0; j < this.maze[0].length; j++) {
                System.out.print(this.maze[i][j].getSymbol());
            }
            System.out.println();
        }
    }
}
