package ridego.controller;
import java.util.HashMap;
import java.util.Map;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ridego.entity.Driver;
import ridego.service.DriverService;

@RestController
public class DriverController {

    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }
@Operation(
    summary = "Create Driver",
    description = "Creates a new driver"
)
@ApiResponses({
    @ApiResponse(responseCode = "201", description = "Driver created successfully"),
    @ApiResponse(responseCode = "400", description = "Invalid driver data")
})
    @PostMapping("/api/drivers")
public ResponseEntity<Driver> createDriver(
        @Valid @RequestBody Driver driver) {

    Driver savedDriver = driverService.saveDriver(driver);

    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(savedDriver);
}
    @Operation(
    summary = "Get All Drivers",
    description = "Returns a list of all registered drivers"
)
@ApiResponses({
    @ApiResponse(responseCode = "200", description = "Drivers retrieved successfully"),
    @ApiResponse(responseCode = "401", description = "Authentication required")
})

    @GetMapping("/api/drivers")
    public List<Driver> getAllDrivers() {
        return driverService.getAllDrivers();
    }
@Operation(
    summary = "Get Driver By ID",
    description = "Returns a driver by their unique ID"
)
@ApiResponses({
    @ApiResponse(responseCode = "200", description = "Driver found successfully"),
    @ApiResponse(responseCode = "404", description = "Driver not found"),
    @ApiResponse(responseCode = "401", description = "Authentication required")
})
    @GetMapping("/api/drivers/{id}")
    public Driver getDriverById(@PathVariable Long id) {
        return driverService.getDriverById(id);
    }
    @Operation(
    summary = "Delete Driver",
    description = "Deletes a driver by their unique ID"
)
@ApiResponses({
    @ApiResponse(responseCode = "200", description = "Driver deleted successfully"),
    @ApiResponse(responseCode = "404", description = "Driver not found"),
    @ApiResponse(responseCode = "401", description = "Authentication required")
})

    @DeleteMapping("/api/drivers/{id}")
public ResponseEntity<Map<String, String>> deleteDriver(
        @PathVariable Long id) {

    driverService.deleteDriver(id);

    Map<String, String> response = new HashMap<>();
    response.put("message", "Driver deleted successfully");

    return ResponseEntity.ok(response);
}
    @Operation(
    summary = "Update Driver Availability",
    description = "Updates whether a driver is available for accepting rides"
)
@ApiResponses({
    @ApiResponse(responseCode = "200", description = "Driver availability updated successfully"),
    @ApiResponse(responseCode = "404", description = "Driver not found"),
    @ApiResponse(responseCode = "401", description = "Authentication required")
})
    @PutMapping("/api/drivers/{id}/availability")
public Driver updateAvailability(
        @PathVariable Long id,
        @RequestParam boolean available) {

    return driverService.updateAvailability(id, available);
}
}