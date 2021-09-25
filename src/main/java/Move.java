import java.util.ArrayList;
import java.util.List;

public class Move {
    private ArrayList<String> moving;
    private boolean beatFlag;
    private boolean color; // 0 - mean white color, otherwise - black
    private boolean damkaFlag;

    Move(){
        moving = new ArrayList<>();
        beatFlag = false;
        color = false;
        damkaFlag = false;
    }

    public ArrayList<String> getMoving() {
        return moving;
    }

    public boolean isBeatFlag() {
        return beatFlag;
    }

    public boolean isColor() {
        return color;
    }

    public void setBeatFlag(boolean beatFlag) {
        this.beatFlag = beatFlag;
    }

    public void setColor(boolean color) {
        this.color = color;
    }

    public void setMoving(ArrayList<String> moving) {
        this.moving = moving;
    }
}
