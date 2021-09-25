public class BeatenCheckException extends OtherGameException {
    public void printExceptionMessage(){
        System.out.println("Beaten check can't be deleted, or was calculated incorrectly");
    }
}
