public class BusyCellException extends GameException {
    @Override
    public void printExceptionMessage() {
        System.out.println("busy cell");
    }
}
