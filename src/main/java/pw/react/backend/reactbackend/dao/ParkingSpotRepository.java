package pw.react.backend.reactbackend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pw.react.backend.reactbackend.model.ParkingSpot;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long> {
}
