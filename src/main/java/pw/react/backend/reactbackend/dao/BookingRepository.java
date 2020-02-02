package pw.react.backend.reactbackend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pw.react.backend.reactbackend.model.Booking;
import pw.react.backend.reactbackend.model.Parking;
import pw.react.backend.reactbackend.model.ParkingSpot;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query(value = "SELECT booking FROM Booking booking where booking.active = :activeStatus")
    List<Booking> findBookingsByActivity(@Param("activeStatus") boolean activeStatus);

    @Query("SELECT COUNT(booking) FROM Booking booking " +
            "where (booking.startDate <= :toDate and booking.endDate >= :fromDate) " +
            "and booking.parkingSpotId = :parkingSpotId")
   int checkOverlappingDates(@Param("fromDate") LocalDateTime fromDate,
                                   @Param("toDate") LocalDateTime toDate,
                                        @Param("parkingSpotId") ParkingSpot parkingSpotId);

}