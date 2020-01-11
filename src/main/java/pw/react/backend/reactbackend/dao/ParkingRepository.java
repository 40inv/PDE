package pw.react.backend.reactbackend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pw.react.backend.reactbackend.model.Admin;
import pw.react.backend.reactbackend.model.Parking;

import java.util.List;

public interface ParkingRepository extends JpaRepository<Parking, Long> {
    @Query(value = "SELECT parking FROM Parking parking where parking.active = :activeStatus")
    List<Parking> findParkingsByActivity(@Param("activeStatus") boolean activeStatus);
}
