package ridego.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ridego.entity.Ride;
import ridego.service.RideService;

@RestController
public class RideController {

    private final RideService rideService;

    public RideController(RideService rideService) {
        this.rideService = rideService;
    }

    @Operation(
        summary = "Create Ride",
        description = "Creates a new ride"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Ride created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid ride data")
    })
    @PostMapping("/api/rides")
public ResponseEntity<Ride> createRide(
        @Valid @RequestBody Ride ride) {

    Ride savedRide = rideService.saveRide(ride);

    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(savedRide);
}

    @Operation(
        summary = "Request Ride",
        description = "Creates a ride request and automatically calculates the fare"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Ride requested successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid ride request")
    })
    @PostMapping("/api/rides/request")
public ResponseEntity<Ride> requestRide(
        @Valid @RequestBody Ride ride) {

    Ride requestedRide = rideService.requestRide(ride);

    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(requestedRide);
}

    @Operation(
        summary = "Accept Ride",
        description = "Allows an available driver to accept a requested ride"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Ride accepted successfully"),
        @ApiResponse(responseCode = "400", description = "Ride cannot be accepted"),
        @ApiResponse(responseCode = "404", description = "Ride or driver not found")
    })
    @PostMapping("/api/rides/{rideId}/accept")
    public Ride acceptRide(
            @PathVariable Long rideId,
            @RequestParam Long driverId) {

        return rideService.acceptRide(rideId, driverId);
    }

    @Operation(
        summary = "Start Ride",
        description = "Starts an accepted ride"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Ride started successfully"),
        @ApiResponse(responseCode = "400", description = "Ride must be accepted before starting")
    })
    @PostMapping("/api/rides/{rideId}/start")
    public Ride startRide(@PathVariable Long rideId) {
        return rideService.startRide(rideId);
    }

    @Operation(
        summary = "Complete Ride",
        description = "Completes a ride that is currently in progress"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Ride completed successfully"),
        @ApiResponse(responseCode = "400", description = "Ride must be started before completion")
    })
    @PostMapping("/api/rides/{rideId}/complete")
    public Ride completeRide(@PathVariable Long rideId) {
        return rideService.completeRide(rideId);
    }

    @Operation(
        summary = "Cancel Ride",
        description = "Cancels a ride before it has started"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Ride cancelled successfully"),
        @ApiResponse(responseCode = "400", description = "Ride cannot be cancelled after starting")
    })
    @PostMapping("/api/rides/{rideId}/cancel")
    public Ride cancelRide(@PathVariable Long rideId) {
        return rideService.cancelRide(rideId);
    }

    @Operation(
        summary = "Get All Rides",
        description = "Returns all rides"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Rides retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Authentication required")
    })
    @GetMapping("/api/rides")
    public List<Ride> getAllRides() {
        return rideService.getAllRides();
    }

    @Operation(
        summary = "Get Ride By ID",
        description = "Returns a ride by its unique ID"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Ride found successfully"),
        @ApiResponse(responseCode = "404", description = "Ride not found")
    })
    @GetMapping("/api/rides/{id}")
    public Ride getRideById(@PathVariable Long id) {
        return rideService.getRideById(id);
    }

    @Operation(
        summary = "Calculate Fare",
        description = "Calculates ride fare based on distance in kilometers"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Fare calculated successfully")
    })
    @GetMapping("/api/rides/fare")
    public double calculateFare(@RequestParam double distanceKm) {
        return rideService.calculateFare(distanceKm);
    }

    @Operation(
        summary = "Get User Ride History",
        description = "Returns all rides associated with a specific user"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User ride history retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Authentication required")
    })
    @GetMapping("/api/rides/user/{userId}")
    public List<Ride> getRidesByUser(@PathVariable Long userId) {
        return rideService.getRidesByUser(userId);
    }

    @Operation(
        summary = "Get Driver Ride History",
        description = "Returns all rides associated with a specific driver"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Driver ride history retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Authentication required")
    })
    @GetMapping("/api/rides/driver/{driverId}")
    public List<Ride> getRidesByDriver(@PathVariable Long driverId) {
        return rideService.getRidesByDriver(driverId);
    }

    @Operation(
        summary = "Get Available Rides",
        description = "Returns all ride requests that are waiting for a driver"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Available rides retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Authentication required")
    })
    @GetMapping("/api/rides/available")
    public List<Ride> getAvailableRides() {
        return rideService.getAvailableRides();
    }

    @Operation(
        summary = "Delete Ride",
        description = "Deletes a ride by its unique ID"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Ride deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Ride not found"),
        @ApiResponse(responseCode = "401", description = "Authentication required")
    })
    @DeleteMapping("/api/rides/{id}")
public ResponseEntity<Map<String, String>> deleteRide(
        @PathVariable Long id) {

    rideService.deleteRide(id);

    Map<String, String> response = new HashMap<>();
    response.put("message", "Ride deleted successfully");

    return ResponseEntity.ok(response);
}
    @Operation(
        summary = "Update Ride",
        description = "Updates an existing ride"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Ride updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid ride data"),
        @ApiResponse(responseCode = "404", description = "Ride not found"),
        @ApiResponse(responseCode = "401", description = "Authentication required")
    })
    @PutMapping("/api/rides/{id}")
    public Ride updateRide(
            @PathVariable Long id,
            @RequestBody Ride ride) {

        return rideService.updateRide(id, ride);
    }
}