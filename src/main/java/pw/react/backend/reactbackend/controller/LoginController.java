package pw.react.backend.reactbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pw.react.backend.reactbackend.config.JwtTokenUtil;
import pw.react.backend.reactbackend.dao.AdminRepository;
import pw.react.backend.reactbackend.model.Admin;
import pw.react.backend.reactbackend.model.JwtResponse;
import pw.react.backend.reactbackend.service.AdminService;
import pw.react.backend.reactbackend.service.JwtUserDetailsService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Collections;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/login")
public class LoginController {

    private AdminRepository applicationUserRepository;
    private pw.react.backend.reactbackend.service.AdminService AdminService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    public LoginController(AdminRepository repository, AdminService AdminService) {
        this.applicationUserRepository = repository;
        this.AdminService = AdminService;
    }

    @PostMapping(path = "")
    public ResponseEntity<?> createAdmin(@RequestHeader HttpHeaders headers, @Valid @RequestBody Admin adm) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(adm.getUsername());
        if (userDetails.getPassword().equals(adm.getPassword()))
        {
            String token = jwtTokenUtil.generateToken(userDetails);
            return ResponseEntity.ok(new JwtResponse(token));
        }
        else
            return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
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
