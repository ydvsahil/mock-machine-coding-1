package exception;

/**
 * Exception handling when request params are invalid in case of selectRide.
 */
public class InvalidSelectRideRequestParamsException extends RideSharingBaseException{
    public static final Integer HTTP_CODE = 400;

    public InvalidSelectRideRequestParamsException(String errorMessage) {
        super(errorMessage, HTTP_CODE);
    }
}
