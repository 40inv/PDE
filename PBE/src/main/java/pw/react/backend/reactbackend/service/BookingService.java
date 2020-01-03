package pw.react.backend.reactbackend.service;

import pw.react.backend.reactbackend.model.Booking;

public interface BookingService {
    Booking updateBooking(Long id, Booking updatedBooking);
    boolean deleteBooking(Long id);
}
