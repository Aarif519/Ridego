package ridego.service;

import java.util.List;

import org.springframework.stereotype.Service;
import ridego.exception.DriverNotFoundException;
import ridego.entity.Driver;
import ridego.entity.Ride;
import ridego.entity.RideStatus;
import ridego.exception.RideCancellationException;
import ridego.exception.RideNotFoundException;
import ridego.exception.RideStatusException;
import ridego.repository.DriverRepository;
import ridego.repository.RideRepository;

@Service
public class RideService {

    private final DriverRepository driverRepository;
    private final RideRepository rideRepository;

    public RideService(RideRepository rideRepository,
                       DriverRepository driverRepository) {
        this.rideRepository = rideRepository;
        this.driverRepository = driverRepository;
    }

    // Create Ride
    public Ride saveRide(Ride ride) {
        return rideRepository.save(ride);
    }

    // Get All Rides
    public List<Ride> getAllRides() {
        return rideRepository.findAll();
    }

    // Get Ride By ID
    public Ride getRideById(Long id) {
        return rideRepository.findById(id)
                .orElseThrow(() ->
                        new RideNotFoundException(
                                "Ride not found with id: " + id
                        )
                );
    }

    // Get Rides By User
    public List<Ride> getRidesByUser(Long userId) {
        return rideRepository.findByUserId(userId);
    }

    // Get Rides By Driver
    public List<Ride> getRidesByDriver(Long driverId) {
        return rideRepository.findByDriverId(driverId);
    }

    // Get Available Rides
    public List<Ride> getAvailableRides() {
        return rideRepository.findByStatus(RideStatus.REQUESTED);
    }

    // Delete Ride
    public void deleteRide(Long id) {

    Ride ride = rideRepository.findById(id)
            .orElseThrow(() ->
                    new RideNotFoundException(
                            "Ride not found with id: " + id
                    )
            );

    rideRepository.delete(ride);
}

    // Update Ride
    public Ride updateRide(Long id, Ride updatedRide) {

        Ride existingRide = rideRepository.findById(id)
                .orElseThrow(() ->
                        new RideNotFoundException(
                                "Ride not found with id: " + id
                        )
                );

        existingRide.setUserId(updatedRide.getUserId());
        existingRide.setDriverId(updatedRide.getDriverId());
        existingRide.setPickupLocation(updatedRide.getPickupLocation());
        existingRide.setDropLocation(updatedRide.getDropLocation());
        existingRide.setFare(updatedRide.getFare());
        existingRide.setDistanceKm(updatedRide.getDistanceKm());
        existingRide.setStatus(updatedRide.getStatus());

        return rideRepository.save(existingRide);
    }

    // Request Ride
    public Ride requestRide(Ride ride) {

        double calculatedFare = calculateFare(
                ride.getDistanceKm()
        );

        ride.setFare(calculatedFare);
        ride.setStatus(RideStatus.REQUESTED);

        return rideRepository.save(ride);
    }

    // Calculate Fare
    public double calculateFare(double distanceKm) {

        double baseFare = 50;
        double perKmRate = 10;

        return baseFare + (distanceKm * perKmRate);
    }

    // Accept Ride
    public Ride acceptRide(Long rideId, Long driverId) {

        Ride ride = rideRepository.findById(rideId)
        .orElseThrow(() ->
                new RideNotFoundException(
                        "Ride not found with id: " + rideId
                )
        );

        if (ride.getStatus() != RideStatus.REQUESTED) {
    throw new RideStatusException(
            "Ride is not available for acceptance"
    );
}

        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() ->
                        new DriverNotFoundException(
                                "Driver not found with id: " + driverId
                        )
                );

        if (!driver.isAvailable()) {
            throw new RideStatusException(
                    "Driver is not available"
            );
        }

        ride.setDriverId(driverId);
        ride.setStatus(RideStatus.ACCEPTED);

        driver.setAvailable(false);
        driverRepository.save(driver);

        return rideRepository.save(ride);
    }

    

    // Start Ride
public Ride startRide(Long rideId) {

    Ride ride = rideRepository.findById(rideId)
            .orElseThrow(() ->
                    new RideNotFoundException(
                            "Ride not found with id: " + rideId
                    )
            );

    if (ride.getStatus() != RideStatus.ACCEPTED) {
        throw new RideStatusException(
                "Ride must be ACCEPTED before it can be started"
        );
    }

    ride.setStatus(RideStatus.STARTED);

    return rideRepository.save(ride);
}


        

    // Complete Ride
    // Complete Ride
public Ride completeRide(Long rideId) {

    Ride ride = rideRepository.findById(rideId)
            .orElseThrow(() ->
                    new RideNotFoundException(
                            "Ride not found with id: " + rideId
                    )
            );

    if (ride.getStatus() != RideStatus.STARTED) {
        throw new RideStatusException(
                "Ride must be STARTED before it can be completed"
        );
    }

    ride.setStatus(RideStatus.COMPLETED);

    // Driver ko available karo
    Long driverId = ride.getDriverId();

    if (driverId != null) {

        Driver driver = driverRepository.findById(driverId)
                .orElse(null);

        if (driver != null) {
            driver.setAvailable(true);
            driverRepository.save(driver);
        }
    }

    return rideRepository.save(ride);
}

    
    // Cancel Ride
public Ride cancelRide(Long rideId) {

    Ride ride = rideRepository.findById(rideId)
            .orElseThrow(() ->
                    new RideNotFoundException(
                            "Ride not found with id: " + rideId
                    )
            );

    if (ride.getStatus() == RideStatus.STARTED ||
        ride.getStatus() == RideStatus.COMPLETED) {

        throw new RideCancellationException(
                "Ride cannot be cancelled after it has started"
        );
    }

    ride.setStatus(RideStatus.CANCELLED);

    return rideRepository.save(ride);
}}