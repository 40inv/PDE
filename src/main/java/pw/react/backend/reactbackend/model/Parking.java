package pw.react.backend.reactbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Parking")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Parking implements Serializable {

    public static Parking EMPTY = new Parking();

    @Id
    @Column(name="parkingId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "city", nullable = false)
    private String city;
    @Column(name = "zip")
    private String zip;
    @Column(name = "address", nullable = false)
    private String address;
    @Column(name = "price", nullable = false)
    private int price;
    @Column(name = "description")
    private String description;
    @Column(name = "nspots", nullable = false)
    private int nspots;
    @Column(name = "is247", nullable = false)
    private boolean is247;
    @Column(name = "active", nullable = false)
    private boolean active;

    public int getParkingId() {return Math.toIntExact(id);}

}
