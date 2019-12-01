package Core.BFSAlgorithm;

import java.util.ArrayList;

public class GraphElement {
    private int id;
    private GraphElement source;
    private ArrayList<GraphElement> neighbours;
    private boolean isExit;
    private boolean isEntrance;

    public int getId() {
        return id;
    }

    public boolean isExit() {
        return isExit;
    }

    public boolean isEntrance() {
        return isEntrance;
    }

    public void addNeighbour(GraphElement newNeighbour) {
        this.neighbours.add(newNeighbour);
    }

    public GraphElement getNeighbour(int index) {
        return this.neighbours.get(index);
    }

    public int getNeighboursAmount() {
        return this.neighbours.size();
    }

    public void setExit() {
        this.isExit = true;
    }

    public void setEntrance() {
        this.isEntrance = true;
    }

    public GraphElement setSource(GraphElement source) {
        this.source = source;
        for (int i = 0; i < this.neighbours.size(); i++) {
            if (source == this.neighbours.get(i)) {
                this.neighbours.remove(i);
            }
        }
        return this;
    }

    public GraphElement getSource() {
        return source;
    }

    public GraphElement(int n) {
        id = n;
        this.neighbours = new ArrayList<>();
    }
}
