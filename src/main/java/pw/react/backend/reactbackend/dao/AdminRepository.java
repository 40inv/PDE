package pw.react.backend.reactbackend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pw.react.backend.reactbackend.model.Admin;

import java.util.Collection;
import java.util.List;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByUsername(String username);

    @Query(value = "SELECT adm FROM Admin adm where adm.active = :activeStatus")
    List<Admin> findAdminsByActivity(@Param("activeStatus") boolean activeStatus);
}