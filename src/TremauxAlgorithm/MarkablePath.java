package TremauxAlgorithm;

import MazeElements.Path;

public class MarkablePath extends Path {
    private boolean isAnswer;
    private boolean isCorridor;
    private int[] directions = {-1, -1, -1, -1};

    public int[] getDirections() {
        return this.directions;
    }

    public void setIsCorridor(boolean isCorridor) {
        this.isCorridor = isCorridor;
    }

    public void setIsAnswer(boolean isAnswer) {
        this.isAnswer = isAnswer;
    }

    public boolean isCorridor() {
        return this.isCorridor;
    }

    @Override
    public String getSymbol() {
        if (isAnswer) {
            return "X";
        } else {
            return "0";
        }
    }
}
