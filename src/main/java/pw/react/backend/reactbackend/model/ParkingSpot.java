package pw.react.backend.reactbackend.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;


import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "ParkingSpot")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ParkingSpot implements Serializable {

    public static ParkingSpot EMPTY = new ParkingSpot();



    @Id
    @Column(name="parkingSpotId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "parkingId", nullable = false)
    private Parking parkingId;
    @Column(name = "placeNumber")
    private int placeNumber;

    public int getParkingSpotId() {return (int)id;}
}