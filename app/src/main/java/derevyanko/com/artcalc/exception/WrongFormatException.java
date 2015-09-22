package derevyanko.com.artcalc.exception;

/**
 * Created by anton on 9/22/15.
 */
public final class WrongFormatException extends RuntimeException {

    private final String errorCause;

    public WrongFormatException(String errorMessage) {
        this.errorCause = errorMessage;
    }

    public String getErrorCause() {
        return errorCause;
    }
}
