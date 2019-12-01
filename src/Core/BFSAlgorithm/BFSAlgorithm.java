package Core.BFSAlgorithm;


import Core.MazeElements.MazeElement;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BFSAlgorithm {
    private Queue<GraphElement> queue = new LinkedList<>();


    public void solve(MazeElement[][] maze) {
        Graph graph = new Graph();
        graph.mazeToGraph(maze);
        ArrayList<Integer> answer = new ArrayList<>();
        boolean solved = false;
        GraphElement exitElement = null;
        GraphElement currentElement;
        GraphElement removed;
        queue.add(graph.getEntranceElement());
        while (!solved) {
            removed = queue.remove();
            for (int i = 0; i < removed.getNeighboursAmount(); i++) {
                currentElement = removed.getNeighbour(i);
                currentElement.setSource(removed);
                queue.add(currentElement);
                if (currentElement.isExit()) {
                    solved = true;
                    exitElement = currentElement;
                    break;
                }
            }
        }
        currentElement = exitElement;
        while (!currentElement.isEntrance()) {
            answer.add(currentElement.getId());
            currentElement = currentElement.getSource();
        }
        answer.add(currentElement.getId());
        for (int i = answer.size() - 1; i >= 0; i--) {
            System.out.print(answer.get(i));
            if (i != 0) {
                System.out.print(" -> ");
            }
        }
        System.out.println();
    }
}
