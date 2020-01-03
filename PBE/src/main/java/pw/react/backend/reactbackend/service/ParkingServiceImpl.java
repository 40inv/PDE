package pw.react.backend.reactbackend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pw.react.backend.reactbackend.dao.ParkingRepository;
import pw.react.backend.reactbackend.model.Parking;

@Service
class ParkingServiceImpl implements ParkingService {
    private final Logger logger = LoggerFactory.getLogger(ParkingServiceImpl.class);

    private ParkingRepository repository;

    ParkingServiceImpl() { /*Needed only for initializing spy in unit tests*/}

    @Autowired
    ParkingServiceImpl(ParkingRepository repository) {
        this.repository = repository;
    }

    @Override
    public Parking updateParking(Long id, Parking updatedParking) {
        Parking result = Parking.EMPTY;
        if (repository.existsById(id)) {
            updatedParking.setId(id);
            result = repository.save(updatedParking);
            logger.info("Parking with id {} updated.", id);
        }
        return result;
    }

    @Override
    public boolean deleteParking(Long id) {
        boolean result = false;
        if (repository.existsById(id)) {
            repository.deleteById(id);
            logger.info("Parking with id {} deleted.", id);
            result = true;
        }
        return result;
    }
}
