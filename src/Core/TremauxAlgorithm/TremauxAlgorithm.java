package Core.TremauxAlgorithm;

import Core.MazeElements.*;

import java.util.ArrayList;
import java.util.Random;

public class TremauxAlgorithm {
    private MazeElement[][] markableMaze;
    private int[] startingPosition;
    private int[] exitPosition;
    private int[] currentPosition;
    private ArrayList<Integer> correctPath;
    private int width;
    private int height;
    private int lastMove;

    private MarkablePath currentMarkablePath() {
        return ((MarkablePath) markableMaze[currentPosition[1]][currentPosition[0]]);
    }

    private void makeMazeMarkable(MazeElement[][] maze) {
        this.width = maze[0].length;
        this.height = maze.length;
        this.markableMaze = new MazeElement[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (maze[i][j] instanceof Wall) {
                    markableMaze[i][j] = new Wall();
                } else if (maze[i][j] instanceof Entrance) {
                    markableMaze[i][j] = new Entrance();
                    setStartingPosition(j, i);
                } else if (maze[i][j] instanceof Exit) {
                    markableMaze[i][j] = new Exit();
                    setExitPosition(j, i);
                } else if (maze[i][j] instanceof Path) {
                    markableMaze[i][j] = new MarkablePath();
                }
            }
        }
        ((MarkablePath) markableMaze[exitPosition[1]][exitPosition[0]]).setIsCorridor(false);
        ((MarkablePath) markableMaze[exitPosition[1]][exitPosition[0]]).setIsExit(true);
        for (int i = 1; i < height; i += 2) {
            for (int j = 1; j < width; j += 2) {
                checkPossibleDirections(markableMaze[i][j], j, i);
            }
        }
    }

    private int[] calculateXYOfGates(int x, int y) {
        if (x == 0) {
            x++;
        } else if (x == width - 1) {
            x--;
        } else if (y == 0) {
            y++;
        } else if (y == height - 1) {
            y--;
        }
        int[] result = {x, y};
        return result;
    }

    private void setExitPosition(int x, int y) {
        exitPosition = new int[2];
        exitPosition[0] = calculateXYOfGates(x, y)[0];
        exitPosition[1] = calculateXYOfGates(x, y)[1];
    }

    private void setStartingPosition(int x, int y) {
        startingPosition = new int[2];
        startingPosition[0] = calculateXYOfGates(x, y)[0];
        startingPosition[1] = calculateXYOfGates(x, y)[1];
    }

    private void checkPossibleDirections(MazeElement mazeElement, int x, int y) {
        if (mazeElement instanceof MarkablePath) {
            if (markableMaze[y - 1][x] instanceof MarkablePath) {
                ((MarkablePath) mazeElement).getDirections()[0] = 0;
            }
            if (markableMaze[y][x + 1] instanceof MarkablePath) {
                ((MarkablePath) mazeElement).getDirections()[1] = 0;
            }
            if (markableMaze[y + 1][x] instanceof MarkablePath) {
                ((MarkablePath) mazeElement).getDirections()[2] = 0;
            }
            if (markableMaze[y][x - 1] instanceof MarkablePath) {
                ((MarkablePath) mazeElement).getDirections()[3] = 0;
            }
            if ((((MarkablePath) mazeElement).getDirections()[0] == 0 && ((MarkablePath) mazeElement).getDirections()[1] == -1 &&
                    ((MarkablePath) mazeElement).getDirections()[2] == 0 && ((MarkablePath) mazeElement).getDirections()[3] == -1) ||
                    (((MarkablePath) mazeElement).getDirections()[0] == -1 && ((MarkablePath) mazeElement).getDirections()[1] == 0 &&
                            ((MarkablePath) mazeElement).getDirections()[2] == -1 && ((MarkablePath) mazeElement).getDirections()[3] == 0) &&
                            (x != startingPosition[0] && y != startingPosition[1])) {
                ((MarkablePath) mazeElement).setIsCorridor(true);
            }
        }
    }

    private int directionOppositeToGiven(int direction) {
        switch (direction) {
            case 0:
                return 2;
            case 1:
                return 3;
            case 2:
                return 0;
            case 3:
                return 1;
        }
        return -1;
    }

    private void chooseDirection() {
        ArrayList<Integer> notSeenDirections = new ArrayList<>();
        ArrayList<Integer> seenOnceDirections = new ArrayList<>();
        int notSeen = 0;
        int seenOnce = 0;
        if (currentMarkablePath().isCorridor()) {
            move(lastMove);
            if (currentMarkablePath().isExit()) {
                return;
            }
        }
        for (int i = 0; i < 4; i++) {
            if (currentMarkablePath().getDirections()[i] == 0) {
                notSeen++;
                notSeenDirections.add(i);
            } else if (currentMarkablePath().getDirections()[i] == 1) {
                seenOnce++;
                seenOnceDirections.add(i);
            }
        }
        if (notSeen == 1) {
            move(notSeenDirections.get(0));
        } else if (notSeen > 1) {
            Random random = new Random();
            move(notSeenDirections.get(random.nextInt(notSeen)));
        } else {
            if (currentMarkablePath().getDirections()[directionOppositeToGiven(lastMove)] != 2) {
                move(directionOppositeToGiven(lastMove));
            } else {
                Random random = new Random();
                move(seenOnceDirections.get(random.nextInt(seenOnce)));
            }

        }
    }

    private void move(int direction) {
        currentMarkablePath().getDirections()[direction]++;
        if (direction == 0) {
            currentPosition[1] -= 2;
            currentMarkablePath().getDirections()[2]++;
        } else if (direction == 1) {
            currentPosition[0] += 2;
            currentMarkablePath().getDirections()[3]++;
        } else if (direction == 2) {
            currentPosition[1] += 2;
            currentMarkablePath().getDirections()[0]++;
        } else if (direction == 3) {
            currentPosition[0] -= 2;
            currentMarkablePath().getDirections()[1]++;
        }
        lastMove = direction;
    }

    private boolean checkIfEntrance() {
        if (currentPosition[0] == startingPosition[0] && currentPosition[1] == startingPosition[1]) {
            return true;
        }
        return false;
    }

    private int currentRoomNumber() {
        return (currentPosition[0] - 1) / 2 + (currentPosition[1] - 1) / 2 * (width - 1) / 2;
    }

    private void markCorrectPath() {
        this.correctPath = new ArrayList<>();
        while (!checkIfEntrance()) {
            currentMarkablePath().setIsAnswer(true);
            this.correctPath.add(currentRoomNumber());
            for (int i = 0; i < 4; i++) {
                if (currentMarkablePath().getDirections()[i] == 1) {
                    move(i);
                    break;
                }
            }
        }
        this.correctPath.add(currentRoomNumber());
        currentMarkablePath().setIsAnswer(true);
    }

    private void printCorrectPath() {
        for (int i = this.correctPath.size() - 1; i >= 0; i--) {
            System.out.print(this.correctPath.get(i));
            if (i != 0) {
                System.out.print(" -> ");
            }
        }
        System.out.println();
    }

    public void solve(MazeElement[][] maze) {
        makeMazeMarkable(maze);
        currentPosition = startingPosition.clone();
        while (!currentMarkablePath().isExit()) {
            chooseDirection();
        }
        markCorrectPath();
        printCorrectPath();
    }
}
