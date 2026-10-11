package ridego.service;

import java.util.List;

import org.springframework.stereotype.Service;
import ridego.exception.DriverNotFoundException;
import ridego.entity.Driver;
import ridego.repository.DriverRepository;

@Service
public class DriverService {

    private final DriverRepository driverRepository;

    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    public Driver saveDriver(Driver driver) {
        return driverRepository.save(driver);
    }

    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }

    public Driver getDriverById(Long id) {

    return driverRepository.findById(id)
            .orElseThrow(() ->
                new DriverNotFoundException(
                    "Driver not found with id: " + id
                )
            );
}

    public void deleteDriver(Long id) {

    Driver driver = driverRepository.findById(id)
            .orElseThrow(() ->
                    new DriverNotFoundException(
                            "Driver not found with id: " + id
                    )
            );

    driverRepository.delete(driver);
}
    public Driver updateAvailability(Long id, boolean available) {

    Driver driver = driverRepository.findById(id)
            .orElseThrow(() ->
                    new DriverNotFoundException(
                            "Driver not found with id: " + id
                    )
            );

    driver.setAvailable(available);

    return driverRepository.save(driver);
}}
    