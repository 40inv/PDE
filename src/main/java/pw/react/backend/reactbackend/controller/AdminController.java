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
import pw.react.backend.reactbackend.model.*;
import pw.react.backend.reactbackend.service.AdminService;
import pw.react.backend.reactbackend.service.JwtUserDetailsService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.joining;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/admin")
public class AdminController {

    private AdminRepository applicationUserRepository;
    private AdminService AdminService;

    @Autowired
    public AdminController(AdminRepository repository, AdminService AdminService) {
        this.applicationUserRepository = repository;
        this.AdminService = AdminService;
    }


    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(path = "")
    public ResponseEntity<Collection<Admin>> getAllAdminsActivity(@RequestHeader HttpHeaders headers,
                                                          @RequestParam(required = false) String filter) {
        if(filter == null)
            return ResponseEntity.ok(applicationUserRepository.findAll());
        else {
            if (filter.equals("active"))
                return ResponseEntity.ok(applicationUserRepository.findAdminsByActivity(true));
            else if (filter.equals("inactive"))
                return ResponseEntity.ok(applicationUserRepository.findAdminsByActivity(false));
        }
        return ResponseEntity.badRequest().body(Collections.emptyList());
    }



}
