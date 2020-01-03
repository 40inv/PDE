package pw.react.backend.reactbackend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pw.react.backend.reactbackend.dao.AdminRepository;

@Service
public class AdminServiceImpl implements AdminService {

    private final Logger logger = LoggerFactory.getLogger(ParkingServiceImpl.class);

    private AdminRepository repository;

}
