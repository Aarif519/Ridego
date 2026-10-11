package ridego.repository;

import java.util.List;
import ridego.entity.RideStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import ridego.entity.Ride;

public interface RideRepository extends JpaRepository<Ride, Long> {

    List<Ride> findByUserId(Long userId);

    List<Ride> findByDriverId(Long driverId);

List<Ride> findByStatus(RideStatus status);
}
