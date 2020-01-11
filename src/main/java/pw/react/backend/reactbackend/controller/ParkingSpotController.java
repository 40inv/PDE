package pw.react.backend.reactbackend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import pw.react.backend.reactbackend.dao.ParkingSpotRepository;
import pw.react.backend.reactbackend.model.ParkingSpot;
import pw.react.backend.reactbackend.service.ParkingSpotService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.joining;
@CrossOrigin(origins = { "http://localhost:3000"})
@RestController
@RequestMapping(path = "/ParkingSpot")
public class ParkingSpotController {

    private final Logger logger = LoggerFactory.getLogger(ParkingSpotController.class);

    private ParkingSpotRepository repository;
    private pw.react.backend.reactbackend.service.ParkingSpotService ParkingSpotService;

    @Autowired
    public ParkingSpotController(ParkingSpotRepository repository, ParkingSpotService ParkingSpotService) {
        this.repository = repository;
        this.ParkingSpotService = ParkingSpotService;
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping(path = "")
    public ResponseEntity<String> createParkingSpot(@RequestHeader HttpHeaders headers, @Valid @RequestBody List<ParkingSpot> parkingspots) {
            List<ParkingSpot> result = repository.saveAll(parkingspots);
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
    @GetMapping(path = "/{ParkingSpotId}")
    public ResponseEntity<ParkingSpot> getParkingSpot(@RequestHeader HttpHeaders headers,
                                              @PathVariable Long ParkingSpotId) {
        logHeaders(headers);

        return ResponseEntity.ok(repository.findById(ParkingSpotId).orElseGet(() -> ParkingSpot.EMPTY));

    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping(path = "")
    public ResponseEntity<MappingJacksonValue> getAllParkingSpots(@RequestHeader HttpHeaders headers)
    {
        logHeaders(headers);
        MappingJacksonValue wrapper = new MappingJacksonValue(repository.findAll());

            return ResponseEntity.ok(wrapper);

    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping(path = "/{ParkingSpotId}")
    public ResponseEntity<ParkingSpot> updateParkingSpot(@RequestHeader HttpHeaders headers,
                                                 @PathVariable Long ParkingSpotId,
                                                 @RequestBody ParkingSpot updatedParkingSpot) {
        ParkingSpot result;
            result = ParkingSpotService.updateParkingSpot(ParkingSpotId, updatedParkingSpot);
            if (ParkingSpot.EMPTY.equals(result)) {
                return ResponseEntity.badRequest().body(updatedParkingSpot);
            }
            return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping(path = "/{ParkingSpotId}")
    public ResponseEntity<String> deleteParkingSpot(@RequestHeader HttpHeaders headers, @PathVariable Long parkingSpotId) {
        logHeaders(headers);
            boolean deleted = ParkingSpotService.deleteParkingSpot(parkingSpotId);
            if (!deleted) {
                return ResponseEntity.badRequest().body(String.format("Parking spot with id %s does not exists.", parkingSpotId));
            }
            return ResponseEntity.ok(String.format("Parking spot with id %s deleted.", parkingSpotId));
    }

}