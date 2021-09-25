import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    final static int DIFF_FOR_LETTERS = 96;


    public static void main(String[] args) {
        List<String> WhitePos = new ArrayList<>();
        List<String> BlackPos = new ArrayList<>();
        List<Move> moves = new ArrayList<>();

        try {
            InputData(WhitePos, BlackPos, moves);
            System.out.println(WhitePos);
            int i = 0;
            for (Move move : moves) {
                i++;
                ParseMoves(WhitePos, BlackPos, move, i % 2);
            }
        } catch (GameException e) {
            e.printExceptionMessage();
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void InputData(List<String> WhitePos, List<String> BlackPos, List<Move> moves) throws IOException{

        Path path;
        Parser parser = new Parser();
        if (!Files.exists(Paths.get("/home/maxim/IdeaProjects/Shashki/src/main/resources/input.txt"))){
            path = Files.createFile(Paths.get("/home/maxim/IdeaProjects/Shashki/src/main/resources/input.txt"));
        }
        path = Paths.get("/home/maxim/IdeaProjects/Shashki/src/main/resources/input.txt");

        try (BufferedReader bufferedReader = Files.newBufferedReader(path, StandardCharsets.UTF_8)){
            String CurrentLine;
            WhitePos = parser.parse(bufferedReader.readLine(), moves);
            BlackPos = parser.parse(bufferedReader.readLine(), moves);
            System.out.println(WhitePos);
            while ((CurrentLine = bufferedReader.readLine()) != null) {
                parser.parse(CurrentLine + " ", moves);
            }
        }
        System.out.println(WhitePos);
    }

    private static void ParseMoves(List<String> WhitePos, List<String> BlackPos, Move move, int color) throws GameException{
        String s;
        String startCell = move.getMoving().get(0);
        int[] startPosition = new int[2];
        int[] endPosition = new int[2];

        startPosition[0] = FromLettersToInteger(move.getMoving().get(0).toCharArray()[0]);
        startPosition[1] = move.getMoving().get(0).toCharArray()[1] - '0';

        for (int i = 1; i < move.getMoving().size(); i++) {

            String temp = move.getMoving().get(i);

            if (temp.toCharArray()[0] > 'h' || temp.toCharArray()[0] < 'a') {
                System.out.println(temp.toCharArray()[0]);
                throw new GameErrorException();
            }
            endPosition[0] = FromLettersToInteger(temp.toCharArray()[0]);
            endPosition[1] = temp.toCharArray()[1] - '0';

            CheckWhiteCellMove(endPosition[0], endPosition[1]);
            CheckOccupy(WhitePos, BlackPos, temp);

            if (!move.isBeatFlag()) {
                if (color == 1){
                    if (!WhitePos.remove(startCell)) System.out.println("LOOL");
                    WhitePos.add(temp);
                }else {
                    BlackPos.remove(startCell);
                    BlackPos.add(temp);
                }

            }

            startPosition = endPosition;
            startCell = temp;
        }
    }

    private static void CheckOccupy(List<String> WhitePosition, List<String> BlackPosition, String position) throws InvalidMoveException {
        if (WhitePosition.contains(position) || BlackPosition.contains(position)) {
            throw new InvalidMoveException();
        }
    }

    private static int FromLettersToInteger(char letter) {
        return letter - DIFF_FOR_LETTERS;
    }

    private static void CheckWhiteCellMove(int posX, int posY) throws WhiteCellException{
        if ((posX + posY) % 2 == 1) {
            throw new WhiteCellException();
        }
    }

//    private static void


}
