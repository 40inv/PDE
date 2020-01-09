package pw.react.backend.reactbackend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pw.react.backend.reactbackend.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByUsername(String username);
}