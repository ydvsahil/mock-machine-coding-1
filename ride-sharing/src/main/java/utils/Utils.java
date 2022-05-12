package utils;

import domain.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Ride sharing utility class
 *
 */
public class Utils {
    /**
     * Helper function to get RideDetails object from request params.
     *
     * @param rideInfo
     * @return
     */
    public static RideDetails getRideDetailsFromOfferRideRequest(final String rideInfo) {
        final String[] rideParams = rideInfo.split(" ");

        // exepecting ride details of length 6 in "input.txt" file.
        if (rideParams.length != 6) {
            return null;
        }

        final String vehicleOwner = rideParams[0].trim();
        final String source = rideParams[1].trim();
        final String destination = rideParams[2].trim();
        final String vehicleName = rideParams[3].trim();
        final String vehicleNo = rideParams[4].trim();
        final Integer availableSeats = Integer.parseInt(rideParams[5].trim());

        return new RideDetails(source, destination, availableSeats, vehicleOwner , vehicleName, vehicleNo);
    }

    /**
     * Helper function to take request params from "input.txt" and convert it into java User object.
     *
     * @param scanner
     * @return
     */
    public static User getUserFromAddUserRequest(final Scanner scanner) {
        if (scanner == null) {
            return null;
        }

        String userDetails = scanner.nextLine().trim();
        String[] userParams = userDetails.split(" ");

        // Expecting user params of length 3 in request ("input.txt").
        if (userParams.length != 3) {
            return null;
        }

        final Gender gender = Gender.MALE.getGenderId().equalsIgnoreCase(userParams[1]) ? Gender.MALE : Gender.FEMALE;
        final User user = new User(userParams[0], gender, Integer.parseInt(userParams[2]));

        final int noOfVehicles = Integer.parseInt(scanner.nextLine().trim());
        if (noOfVehicles != 0) {
            final List<Vehicle> vehicleList = new ArrayList<>();
            for (int i = 0; i < noOfVehicles; i++) {
                final String vehicleInfo = scanner.nextLine().trim();
                String[] vehicleParams = vehicleInfo.split(" ");
                if (vehicleParams.length != 3) {
                    continue;
                }
                final Vehicle vehicle = new Vehicle(vehicleParams[1], vehicleParams[2]);
                vehicleList.add(vehicle);
            }
            user.setVehicleList(vehicleList);
        }

        return user;
    }

    /**
     * Utility function to check whether user params are valid or not.
     *
     * @param user
     * @return
     */
    public static boolean isUserParamsValid(final User user) {
        // for sake of simplicity only checking null condition,
        // in actual application we should do empty string check etc.
        return !(user == null
                || user.getGender() == null
                || user.getAge() == null
                || user.getName() == null);
    }

    /**
     * Helper function to check whether ride details params are valid in case of select ride.
     *
     * @param riderName
     * @param source
     * @param destination
     * @param seats
     * @param selectionStrategy
     * @return
     */
    public static boolean isSelectRideDetailsParamsValid(
            final String riderName,
            final String source,
            final String destination,
            final Integer seats,
            final String selectionStrategy) {
        // for sake of simplicity only checking null condition,
        // in actual application we should do empty string check etc.
        return !(riderName == null
                || source == null
                || destination == null
                || seats == null
                || seats <= 0
                || seats > 2
                || selectionStrategy == null);
    }

    /**
     * Helper function to check whether offer ride details are valid or not.
     *
     * @param rideDetails
     * @return
     */
    public static boolean isOfferRideDetailsParamsValid(final RideDetails rideDetails) {
        // for sake of simplicity only checking null condition,
        // in actual application we should do empty string check etc.
        return !(rideDetails == null
                || rideDetails.getSource() == null
                || rideDetails.getDestination() == null
                || rideDetails.getAvailableSeats() == null
                || rideDetails.getVehicleNo() == null);
    }

    /**
     * Helper function to get active ride details list.
     *
     * @param source
     * @param destination
     * @param seats
     * @return
     */
    public static List<RideDetails> getActiveRideDetailsList(
            final List<RideDetails> allActiveRideDetailsList,
            final String source,
            final String destination,
            final Integer seats) {
        if (allActiveRideDetailsList == null || seats == null) {
            return null;
        }

        return allActiveRideDetailsList
                .stream()
                .filter(Objects::nonNull)
                .filter((rideDetails) -> rideDetails.getSource().equalsIgnoreCase(source)
                        && rideDetails.getDestination().equalsIgnoreCase(destination)
                        && rideDetails.getAvailableSeats() >= seats
                        && RideStatus.OFFERED.equals(rideDetails.getRideStatus()))
                .collect(Collectors.toList());
    }

    /**
     * Helper function to convert offeredRideDetails List into
     * {source -> {destination, List<RideDetails>}};
     *
     * @param allActiveRideDetailList
     * @return
     */
    public static Map<String, List<RideDetails>> getSourceToDestinationOfferedRideDetailsMap(
            final List<RideDetails> allActiveRideDetailList, final Integer seats) {
        if (allActiveRideDetailList == null
                || allActiveRideDetailList.size() == 0
                || seats == null) {
            return null;
        }

        return allActiveRideDetailList
                .stream()
                .filter(Objects::nonNull)
                .filter(rideDetails ->
                        RideStatus.OFFERED.equals(rideDetails.getRideStatus())
                                && rideDetails.getAvailableSeats() != null
                                && rideDetails.getAvailableSeats() >= seats)
                .collect(
                        Collectors.groupingBy(
                                RideDetails::getSource, Collectors.toList()));
    }

    /**
     * Recursion DFS function to check whether there is indirect path from source to destination
     * and path gets stored in stack desiredRideDetailStack.
     *
     * @param sourceToDestinationRideDetailsMap
     * @param visitedMap
     * @param source
     * @param destination
     * @param desiredRideDetailStack
     * @return
     */
    public static boolean dfsToFindPathFromSourceToDestination(
            final Map<String, List<RideDetails>> sourceToDestinationRideDetailsMap,
            final Map<String, Boolean> visitedMap,
            final String source,
            final String destination,
            final Stack<RideDetails> desiredRideDetailStack) {
        if (sourceToDestinationRideDetailsMap == null
                || source == null
                || destination == null
                || visitedMap == null
                || desiredRideDetailStack == null) {
            return false;
        }

        // source comes equal to destination and hence exiting.
        if (source.equalsIgnoreCase(destination)) {
            return true;
        }

        // Putting an entry in visited map for visited place.
        visitedMap.put(source, Boolean.TRUE);

        // If there are no ride from source, returning false.
        if (sourceToDestinationRideDetailsMap.get(source) == null) {
            return false;
        }

        for(RideDetails rideDetail: sourceToDestinationRideDetailsMap.get(source)) {
            // Not visiting one node more than once
            if (visitedMap.containsKey(rideDetail.getDestination())) {
                continue;
            }

            desiredRideDetailStack.push(rideDetail);
            if (dfsToFindPathFromSourceToDestination(sourceToDestinationRideDetailsMap,
                            visitedMap,
                            rideDetail.getDestination(),
                            destination,
                            desiredRideDetailStack)) {
                return true;
            }
            desiredRideDetailStack.pop();
        }

        return false;
    }
}
