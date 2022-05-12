package exception;

public class RideSharingBaseException extends RuntimeException {
    private String errorMessage = "Something went wrong";
    private Integer statusCode = 500;

    public RideSharingBaseException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public RideSharingBaseException(String errorMessage, Integer statusCode) {
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.statusCode = statusCode;
    }
}
