import java.util.ArrayList;
import java.util.List;

public class Parser {
    public List<String> parse(String line, List<Move> moves) {
        States state = States.START;
        List<String> positions = new ArrayList<>();
        StringBuilder str = new StringBuilder();
        Move move = new Move();

        for (char c : line.toCharArray()){
            switch (state){
                case START -> {
                    state = States.GET_NUMBER;
                    str.append(c);
                }
                case GET_NUMBER -> {
                    state = States.SKIP_SPACE;
                    str.append(c);
                }
                case SKIP_SPACE -> {
                    if (c == ' '){
                        state = States.START;
                        positions.add(str.toString());
                        if (!move.getMoving().isEmpty()) {
                            move.getMoving().add(str.toString());
//                            System.out.println(move.getMoving());
                            moves.add(move);
                            move = new Move();
                        }
                        str.delete(0, str.length());
                    }
                    if (c == ':'){
                        move.getMoving().add(str.toString());
                        state = States.START;
                        str.delete(0, str.length());
                        move.setBeatFlag(true);
                    }
                    if (c == '-'){
                        move.getMoving().add(str.toString());
                        state = States.START;
                        str.delete(0, str.length());
                    }
                }
            }
        }

        return positions;
    }
}
