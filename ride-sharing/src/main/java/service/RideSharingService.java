package service;

import domain.RideDetails;
import domain.User;

public interface RideSharingService {
    void addUser(User user) throws Exception;
    void offerRide(RideDetails rideDetails);
    String selectRide(String riderName, String source, String destination, Integer seats, String selectionStrategy);
    void endRide(String vehicleNo);
    String printRideStats();
}
