package ch.feomathar.adventofcode;


public class ProgramError extends RuntimeException{
    public ProgramError() {
        super();
    }

    public ProgramError(String message) {
        super(message);
    }

    public ProgramError(String message, Throwable cause) {
        super(message, cause);
    }

    public ProgramError(Throwable cause) {
        super(cause);
    }

    protected ProgramError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
