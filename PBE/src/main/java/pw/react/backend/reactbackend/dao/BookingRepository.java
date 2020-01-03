package pw.react.backend.reactbackend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pw.react.backend.reactbackend.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}