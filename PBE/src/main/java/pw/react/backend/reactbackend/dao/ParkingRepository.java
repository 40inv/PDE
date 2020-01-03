package pw.react.backend.reactbackend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pw.react.backend.reactbackend.model.Parking;

public interface ParkingRepository extends JpaRepository<Parking, Long> {
}
