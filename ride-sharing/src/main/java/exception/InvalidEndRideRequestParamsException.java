package exception;

/**
 * Exception handling when request params are invalid in case of end ride.
 */
public class InvalidEndRideRequestParamsException extends RideSharingBaseException {
    public static final Integer HTTP_CODE = 400;

    public InvalidEndRideRequestParamsException(String errorMessage) {
        super(errorMessage, HTTP_CODE);
    }
}
