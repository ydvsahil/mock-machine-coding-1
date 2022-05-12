package exception;

/**
 * Exception handling when requested user is invalid.
 */
public class InvalidAddUserRequestException extends RideSharingBaseException {
    public static final Integer HTTP_CODE = 400;

    public InvalidAddUserRequestException(String errorMessage) {
        super(errorMessage, HTTP_CODE);
    }
}
