import domain.RideDetails;
import domain.User;
import service.RideSharingService;
import service.RideSharingServiceImpl;
import utils.Utils;

import java.io.File;
import java.util.Scanner;

/**
 * This application takes input from "input.txt" present in same package and output is shown on console.
 *
 * Assumptions :
 *      Request in "input.txt" is in correct format (each request has proper variables).
 *      For a vehicle, vehicle No is unique and hence a ride can be determined by vehicle no.
 *      Name of user will be unique and hence will uniquely determine the user.
 */
public class RideSharingApplication {
    public static void main(String[] arg) throws Exception {
        System.out.println("Ride Sharing application started");
        File file = new File("src/main/java/input.txt");
        Scanner scanner = new Scanner(file);

        final RideSharingService rideSharingService = new RideSharingServiceImpl();

        while(scanner.hasNextLine()) {
            String requestType = scanner.nextLine().trim();
            try {
                switch (requestType) {
                    case "add_user":
                        /**
                         * Request is in following format in "input.txt"
                         *
                         * add_user
                         * userName Gender Age
                         * numberOfVehicles
                         * numberOfVehicles lines having details for each vehicle in following format
                         * vehicleOwner vehicleName vehicleNo
                         *
                         */
                        final User user = Utils.getUserFromAddUserRequest(scanner);
                        rideSharingService.addUser(user);
                        System.out.println(String.format("%s added succesfully", user.getName()));
                        break;
                    case "offer_ride":
                        /**
                         * Request for this requestType is in following format in "input.txt"
                         *
                         * offer_ride
                         * vehicleOwner source destination vehicleName vehicleNo availableSeats
                         *
                         */
                        final String rideInfo = scanner.nextLine().trim();
                        final RideDetails rideDetails = Utils.getRideDetailsFromOfferRideRequest(rideInfo);
                        rideSharingService.offerRide(rideDetails);
                        System.out.println(String.format("Ride successfully offered for vehicleNo %s", rideDetails.getVehicleNo()));
                        break;
                    case "select_ride":
                        /**
                         * Request for this requestType is in following format in "input.txt"
                         *
                         * select_ride
                         * riderName source destination requestedNumberOfSeats selectionStrategy
                         *
                         */
                        final String selectRideInfo = scanner.nextLine().trim();
                        final String[] selectRideDetails = selectRideInfo.split(" ");
                        final String riderName = selectRideDetails[0].trim();
                        final String source = selectRideDetails[1].trim();
                        final String destination = selectRideDetails[2].trim();
                        final Integer requestedNumberOfSeats = Integer.parseInt(selectRideDetails[3].trim());
                        // selectionStrategy will take value as "Most_Vacant" if most vacant ride is preferred otherwise
                        // name of preferred vehicle (like baleno, polo etc.)
                        final String selectionStrategy = selectRideDetails[4];

                        final String vehicleNoSelected =
                                rideSharingService.selectRide(riderName, source, destination,requestedNumberOfSeats, selectionStrategy);
                        if (vehicleNoSelected != null) {
                            System.out.println(String.format("Ride on vehicleNo %s successfully selected", vehicleNoSelected));
                        } else {
                            System.out.println("No ride found");
                        }
                        break;
                    case "end_ride":
                        final String vehicleNo = scanner.nextLine().trim();
                        rideSharingService.endRide(vehicleNo);
                        System.out.println(String.format("Ride on vehicle number %s ended successfully", vehicleNo));
                        break;
                    case "print_ride_stats":
                        final String rideStats = rideSharingService.printRideStats();
                        System.out.println(rideStats);
                }
            } catch (Exception e) {
                System.out.println(String.format("Exception occured : %s", e.getMessage()));
            }
        }

        System.out.println("Ride Sharing application ended");
    }
}
