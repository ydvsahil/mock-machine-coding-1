package exception;

/**
 * Exception handling when ride is found (e.g in case of ending ride).
 */
public class RideNotFoundException extends RideSharingBaseException{
    public static final Integer HTTP_CODE = 400;

    public RideNotFoundException(String errorMessage) {
        super(errorMessage, HTTP_CODE);
    }
}
