package pw.react.backend.reactbackend.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import pw.react.backend.reactbackend.utils.JsonDateDeserializer;
import pw.react.backend.reactbackend.utils.JsonDateSerializer;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "Booking")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Booking implements Serializable {

    public static Booking EMPTY = new Booking();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "parkingId", nullable = false)
    private Parking parkingId;
    @ManyToOne
    @JoinColumn(name = "parkingSpotId", nullable = false)
    private ParkingSpot parkingSpotId;
    @Column(name = "userId", nullable = false)
    private int userId;

    @Column(name = "bookDate", nullable = false)
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private LocalDateTime bookDate;

    @Column(name = "paidAmount", nullable = false)
    private int paidAmount;

    @Column(name = "startDate", nullable = false)
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private LocalDateTime startDate;
    @Column(name = "endDate", nullable = false)
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private LocalDateTime endDate;
    @Column(name = "active")
    private boolean active;

    public Parking getParkingFromBooking() {return parkingId;}
    public ParkingSpot getParkingSpotFromBooking() {return parkingSpotId; }
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
    public boolean getActive() {return active;}

}