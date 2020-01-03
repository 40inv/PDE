package pw.react.backend.reactbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "Admin")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Admin {

    public static Admin EMPTY = new Admin();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "adminId")
    private long id;
    @Column(name = "username", nullable = false,unique = true)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "modification", nullable = false)
    private Boolean modification;

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return String.valueOf(password);
    }

}
