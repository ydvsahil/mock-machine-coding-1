package domain;

public class RideDetails {
    private String source;
    private String destination;
    private Integer availableSeats;
    private String vehicleOwner;
    private String vehicleName;
    private String vehicleNo;
    private RideStatus rideStatus;
    private String riderName;

    public RideDetails(
            String source,
            String destination,
            Integer availableSeats,
            String vehicleOwner,
            String vehicleName,
            String vehicleNo) {
        this.source = source;
        this.destination = destination;
        this.availableSeats = availableSeats;
        this.vehicleOwner = vehicleOwner;
        this.vehicleName = vehicleName;
        this.vehicleNo = vehicleNo;
        // For a new ride, ride status would be 'pending'
        this.rideStatus = RideStatus.PENDING;
    }

    public void setRideStatus(RideStatus rideStatus) {
        this.rideStatus = rideStatus;
    }

    public void setRiderName(String riderName) {
        this.riderName = riderName;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public String getVehicleOwner() {
        return vehicleOwner;
    }

    public String getRiderName() {
        return riderName;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public RideStatus getRideStatus() {
        return rideStatus;
    }
}
