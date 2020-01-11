package pw.react.backend.reactbackend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pw.react.backend.reactbackend.dao.ParkingSpotRepository;
import pw.react.backend.reactbackend.model.ParkingSpot;

@Service
public class ParkingSpotServiceImpl implements ParkingSpotService {
    private final Logger logger = LoggerFactory.getLogger(ParkingServiceImpl.class);

    private ParkingSpotRepository repository;

    ParkingSpotServiceImpl() { }

    @Autowired
    ParkingSpotServiceImpl(ParkingSpotRepository repository) {
        this.repository = repository;
    }

    @Override
    public ParkingSpot updateParkingSpot(Long id, ParkingSpot updatedParkingSpot) {
        ParkingSpot result = ParkingSpot.EMPTY;
        if (repository.existsById(id)) {
            updatedParkingSpot.setId(id);
            result = repository.save(updatedParkingSpot);
            logger.info("ParkingSpot with id {} updated.", id);
        }
        return result;
    }

    @Override
    public boolean deleteParkingSpot(Long id) {
        boolean result = false;
        if (repository.existsById(id)) {
            repository.deleteById(id);
            logger.info("ParkingSpot with id {} deleted.", id);
            result = true;
        }
        return result;
    }

}
