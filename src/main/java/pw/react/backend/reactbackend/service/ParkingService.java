package pw.react.backend.reactbackend.service;

import pw.react.backend.reactbackend.model.Parking;

public interface ParkingService {
    Parking updateParking(Long parkingId, Parking updatedParking);
    boolean deleteParking(Long ParkingId);
}
