package pw.react.backend.reactbackend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pw.react.backend.reactbackend.config.JwtTokenUtil;
import pw.react.backend.reactbackend.dao.AdminRepository;
import pw.react.backend.reactbackend.model.*;
import pw.react.backend.reactbackend.service.AdminService;
import pw.react.backend.reactbackend.service.JwtUserDetailsService;

import javax.validation.Valid;
import java.util.Collection;



@CrossOrigin(origins = { "http://localhost:3000" })
@RestController
@RequestMapping(path = "/admin")
public class AdminController {

    private AdminRepository applicationUserRepository;
    private AdminService AdminService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    public AdminController(AdminRepository repository, AdminService BookingService) {
        this.applicationUserRepository = repository;
        this.AdminService = AdminService;
    }

    @PostMapping(path = "")
    public ResponseEntity<?> createAdmin(@RequestHeader HttpHeaders headers, @Valid @RequestBody Admin adm) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(adm.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @GetMapping(path = "") // For testing only and will be deleted
    public ResponseEntity<Collection<Admin>> getAllAdmins(@RequestHeader HttpHeaders headers) {
            return ResponseEntity.ok(applicationUserRepository.findAll());
    }


    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

}