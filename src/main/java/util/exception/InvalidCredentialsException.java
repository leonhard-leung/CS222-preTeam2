package util.exception;

public class InvalidCredentialsException extends Exception{
    public InvalidCredentialsException() {
        super();
    }

    public InvalidCredentialsException(String message) {
        super(message);
    }
}
