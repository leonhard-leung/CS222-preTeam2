package util.exception;

public class AccountAlreadyLoggedIn extends Exception{
    public AccountAlreadyLoggedIn() {
        super();
    }

    public AccountAlreadyLoggedIn(String message) {
        super(message);
    }
}
