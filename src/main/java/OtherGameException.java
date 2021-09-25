public class OtherGameException extends GameException {
    @Override
    public void printExceptionMessage() {
        System.out.println("Other error");
    }
}
