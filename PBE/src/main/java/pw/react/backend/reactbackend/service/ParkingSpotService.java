package pw.react.backend.reactbackend.service;

import pw.react.backend.reactbackend.model.ParkingSpot;

public interface ParkingSpotService {
    ParkingSpot updateParkingSpot(Long parkingSpotId, ParkingSpot updateParkingSpot);
    boolean deleteParkingSpot(Long parkingSpotId);
}
