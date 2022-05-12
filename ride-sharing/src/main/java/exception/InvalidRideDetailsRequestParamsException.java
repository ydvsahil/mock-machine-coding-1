package exception;

/**
 * Exception handling when ride details params is invalid (like in case of offerRide).
 */
public class InvalidRideDetailsRequestParamsException extends RideSharingBaseException {
    public static final Integer HTTP_CODE = 400;

    public InvalidRideDetailsRequestParamsException(String errorMessage) {
        super(errorMessage, HTTP_CODE);
    }
}
