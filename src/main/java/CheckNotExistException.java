public class CheckNotExistException extends OtherGameException {
    @Override
    public void printExceptionMessage() {
        System.out.println("Impossible remove not existent check");
    }
}
