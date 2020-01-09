package pw.react.backend.reactbackend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pw.react.backend.reactbackend.dao.BookingRepository;
import pw.react.backend.reactbackend.model.Booking;
import pw.react.backend.reactbackend.service.BookingService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.joining;
@CrossOrigin(origins = { "http://localhost:3000" })
@RestController
@RequestMapping(path = "/Booking")
public class BookingController {

    private final Logger logger = LoggerFactory.getLogger(BookingController.class);

    private BookingRepository repository;
    private BookingService BookingService;

    @Autowired
    public BookingController(BookingRepository repository, BookingService BookingService) {
        this.repository = repository;
        this.BookingService = BookingService;
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping(path = "")
    public ResponseEntity<String> createBookings(@RequestHeader HttpHeaders headers, @Valid @RequestBody List<Booking> bookings) {
        logHeaders(headers);
            List<Booking> result = repository.saveAll(bookings);
            return ResponseEntity.ok(result.stream().map(c -> String.valueOf(c.getId())).collect(joining(",")));
    }

    private void logHeaders(@RequestHeader HttpHeaders headers) {
        logger.info("Controller request headers {}",
                headers.entrySet()
                        .stream()
                        .map(entry -> String.format("%s->[%s]", entry.getKey(), String.join(",", entry.getValue())))
                        .collect(joining(","))
        );
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping(path = "/{BookingId}")
    public ResponseEntity<Booking> getBooking(@RequestHeader HttpHeaders headers,
                                                  @PathVariable Long BookingId) {
        logHeaders(headers);
            return ResponseEntity.ok(repository.findById(BookingId).orElseGet(() -> Booking.EMPTY));
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping(path = "")
    public ResponseEntity<Collection<Booking>> getAllBookings(@RequestHeader HttpHeaders headers) {
        logHeaders(headers);
            return ResponseEntity.ok(repository.findAll());
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping(path = "/{BookingId}")
    public ResponseEntity<Booking> updateBooking(@RequestHeader HttpHeaders headers,
                                                     @PathVariable Long BookingId,
                                                     @RequestBody Booking updatedParkingSpot) {
        logHeaders(headers);
        Booking result;
            result = BookingService.updateBooking(BookingId, updatedParkingSpot);
            if (Booking.EMPTY.equals(result)) {
                return ResponseEntity.badRequest().body(updatedParkingSpot);
            }
            return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping(path = "/{BookingId}")
    public ResponseEntity<String> deleteBooking(@RequestHeader HttpHeaders headers, @PathVariable Long BookingId) {
        logHeaders(headers);
            boolean deleted = BookingService.deleteBooking(BookingId);
            if (!deleted) {
                return ResponseEntity.badRequest().body(String.format("Booking with id %s does not exists.", BookingId));
            }
            return ResponseEntity.ok(String.format("Parking with id %s deleted.", BookingId));

    }

}
