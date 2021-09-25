public class InvalidMoveException extends GameException{
    @Override
    public void printExceptionMessage(){
        System.out.println("invalid move");
    }
}
