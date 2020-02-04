package pw.react.backend.reactbackend.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import pw.react.backend.reactbackend.utils.JsonDateDeserializer;
import pw.react.backend.reactbackend.utils.JsonDateSerializer;

import javax.persistence.*;
import java.time.LocalDateTime;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BookingDTO {

    private long id;
    private int parkingId;
    private int parkingSpotId;
    private int placeNumber;
    private int userId;
    private int paidAmount;
    private boolean active;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private LocalDateTime bookDate;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private LocalDateTime startDate;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private LocalDateTime endDate;

    public BookingDTO(Booking booking) {
        this.id = booking.getId();
        this.parkingId = booking.getParkingFromBooking().getParkingId();
        this.parkingSpotId = booking.getParkingSpotFromBooking().getParkingSpotId();
        this.placeNumber = booking.getParkingSpotFromBooking().getPlaceNumber();
        this.userId = booking.getUserId();
        this.paidAmount = booking.getPaidAmount();
        this.active = booking.getActive();
        this.bookDate = booking.getBookDate();
        this.startDate = booking.getStartDate();
        this.endDate = booking.getEndDate();
    }

    public int getParkingId() {return parkingId;}
    public int getParkingSpotId() {return parkingSpotId; }
    public LocalDateTime getStartDate() {
        return startDate;
    }
    public LocalDateTime getEndDate() {
        return endDate;
    }
    public LocalDateTime getBookDate() {return bookDate;}

    public Long getId() {
        return id;
    }
    public int getUserId() {return userId;}
    public int getPaidAmount() {return paidAmount;}
    public int getPlaceNumber() {return placeNumber;}
    public boolean getActive() {return active;}


}
