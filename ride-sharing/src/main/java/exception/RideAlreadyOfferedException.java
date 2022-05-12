package exception;

/**
 * Exception handling when a vehicle is already offered and then trying to offer again.
 */
public class RideAlreadyOfferedException extends RideSharingBaseException{
    public static final Integer HTTP_CODE = 400;

    public RideAlreadyOfferedException(String errorMessage) {
        super(errorMessage, HTTP_CODE);
    }
}
