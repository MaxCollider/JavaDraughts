import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    final static int DIFF_FOR_LETTERS = 96;


    public static void main(String[] args) {
        List<String> WhitePos = new ArrayList<>();
        List<String> BlackPos = new ArrayList<>();
        List<Move> moves = new ArrayList<>();

        try {
            InputData(WhitePos, BlackPos, moves);
            int i = 0;
            System.out.println(WhitePos);
            System.out.println(BlackPos);
            for (Move move : moves) {
                ParseMoves(WhitePos, BlackPos, move, i % 2);
                i++;
            }
        } catch (GameException e) {
            e.printExceptionMessage();
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> RightAnswerWhite = new ArrayList<>();
        List<String> RightAnswerBlack = new ArrayList<>();

        Parser parser = new Parser();
        Path path = Paths.get("/home/maxim/IdeaProjects/Shashki/src/main/resources/answer.txt");

        try(BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            RightAnswerWhite.addAll(parser.parse(br.readLine() + "\n", moves));
            RightAnswerBlack.addAll(parser.parse(br.readLine() + "\n", moves));
        } catch (IOException e){
            e.printStackTrace();
        }

        Collections.sort(RightAnswerBlack);
        Collections.sort(RightAnswerWhite);
        Collections.sort(WhitePos);
        Collections.sort(BlackPos);

        if (WhitePos.equals(RightAnswerWhite) && BlackPos.equals(RightAnswerBlack)){
            System.out.println("SOSATb");
        } else {
            System.out.println("-------------------------");
            System.out.println(WhitePos);
            System.out.println(RightAnswerWhite);
            System.out.println("------------------------");
            System.out.println(BlackPos);
            System.out.println(RightAnswerBlack);
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
            WhitePos.addAll(parser.parse(bufferedReader.readLine() + "\n", moves));
            BlackPos.addAll(parser.parse(bufferedReader.readLine() + "\n", moves));

            while ((CurrentLine = bufferedReader.readLine()) != null) {
                parser.parse(CurrentLine + " ", moves);
            }
        }
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

            if (temp.toCharArray()[0] > 'h' || temp.toCharArray()[0] < 'a'
                    || Character.getNumericValue(temp.toCharArray()[1]) > 8
                    || Character.getNumericValue(temp.toCharArray()[1]) < 1) {

                throw new GameErrorException();
            }
            endPosition[0] = FromLettersToInteger(temp.toCharArray()[0]);
            endPosition[1] = temp.toCharArray()[1] - '0';

            CheckWhiteCellMove(endPosition[0], endPosition[1]);
            CheckOccupy(WhitePos, BlackPos, temp);

            if (!move.isBeatFlag()) {
                if (color == 0){
                    if (!WhitePos.remove(startCell)){
                        System.out.println("LOOL");
                        System.out.println(move.getMoving());
                        System.out.println(startCell);
                    }
                    WhitePos.add(temp);
                } else {
                    if (!BlackPos.remove(startCell)){
                        System.out.println("Lool2");
                        System.out.println(move.getMoving());
                        System.out.println(startCell);
                    }
                    BlackPos.add(temp);
                }
            } else {
                if (Math.abs(endPosition[0] - startPosition[0]) != 2 || Math.abs(endPosition[1] - startPosition[1]) != 2) {
                    throw new InvalidMoveException();
                }
                String beaten = FromCoordToMoveString((endPosition[0] + startPosition[0])/2, (endPosition[1] + startPosition[1])/2);
                if (color == 0) {
                    if (!BlackPos.remove(beaten)){
                        System.out.println("suck");
                        System.out.println(move.getMoving());
                    }
                    WhitePos.remove(startCell);
                    WhitePos.add(temp);
                } else {
                    if (!WhitePos.remove(beaten)) System.out.println("suck2");
                    BlackPos.remove(startCell);
                    BlackPos.add(temp);
                }

            }
            startPosition = endPosition;
            startCell = temp;

        }
    }

    private static String FromCoordToMoveString(int x0, int y0) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Character.toChars(x0 + DIFF_FOR_LETTERS));
        stringBuilder.append(y0);
        return stringBuilder.toString();

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
