package pw.react.backend.reactbackend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pw.react.backend.reactbackend.dao.ParkingRepository;
import pw.react.backend.reactbackend.model.Parking;
import pw.react.backend.reactbackend.service.ParkingService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.joining;
@CrossOrigin(origins = { "http://localhost:3000"})
@RestController
@RequestMapping(path = "/Parking")
public class ParkingController {

    private final Logger logger = LoggerFactory.getLogger(ParkingController.class);

    private ParkingRepository repository;
    private ParkingService ParkingService;

    @Autowired
    public ParkingController(ParkingRepository repository, ParkingService ParkingService) {
        this.repository = repository;
        this.ParkingService = ParkingService;
    }

    @PostMapping(path = "")
    public ResponseEntity<String> createParkings(@RequestHeader HttpHeaders headers, @Valid @RequestBody List<Parking> parkings) {
            List<Parking> result = repository.saveAll(parkings);
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

    @GetMapping(path = "/{ParkingId}")
    public ResponseEntity<Parking> getParking(@RequestHeader HttpHeaders headers,
                                              @PathVariable Long ParkingId) {
            return ResponseEntity.ok(repository.findById(ParkingId).orElseGet(() -> Parking.EMPTY));
    }

    @GetMapping(path = "")
    public ResponseEntity<Collection<Parking>> getAllParkings(@RequestHeader HttpHeaders headers) {
        logHeaders(headers);
        return ResponseEntity.ok(repository.findAll());
    }

    @PutMapping(path = "/{ParkingId}")
    public ResponseEntity<Parking> updateParking(@RequestHeader HttpHeaders headers,
                                                 @PathVariable Long ParkingId,
                                                 @RequestBody Parking updatedParking) {
        logHeaders(headers);
        Parking result;
            result = ParkingService.updateParking(ParkingId, updatedParking);
            if (Parking.EMPTY.equals(result)) {
                return ResponseEntity.badRequest().body(updatedParking);
            }
            return ResponseEntity.ok(result);
    }

    @DeleteMapping(path = "/{ParkingId}")
    public ResponseEntity<String> deleteParking(@RequestHeader HttpHeaders headers, @PathVariable Long ParkingId) {
        logHeaders(headers);
            boolean deleted = ParkingService.deleteParking(ParkingId);
            if (!deleted) {
                return ResponseEntity.badRequest().body(String.format("Parking with id %s does not exists.", ParkingId));
            }
            return ResponseEntity.ok(String.format("Parking with id %s deleted.", ParkingId));
    }


}
