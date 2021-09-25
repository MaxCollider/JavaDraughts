public class OutOfBoardBoundsExeption extends OtherGameException {
    @Override
    public void printExceptionMessage() {
        System.out.println("Check was moved out of board");
        System.exit(0);
    }
}
