package kz.iitu.lab2.service;

import kz.iitu.lab2.dtos.TrafficViolationDTO;
import kz.iitu.lab2.entity.TrafficViolation;
import kz.iitu.lab2.entity.User;
import kz.iitu.lab2.entity.Vehicle;
import kz.iitu.lab2.repository.TrafficViolationRepository;
import kz.iitu.lab2.repository.UserRepository;
import kz.iitu.lab2.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrafficViolationService {
    private final TrafficViolationRepository violationRepository;
    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;

    public List<TrafficViolation> getViolationsByVehicleId(Long vehicleId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElse(null);
        if (vehicle != null) {
            return violationRepository.findByVehicle(vehicle);
        }
        return Collections.emptyList();
    }

    public List<TrafficViolation> getViolationsByUserId(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            return violationRepository.findByUser(user);
        }
        return Collections.emptyList();
    }

    public List<TrafficViolation> getViolationsByType(String violationType) {
        return violationRepository.findByViolationType(violationType);
    }

    public List<TrafficViolation> getViolationsByLocation(String location) {
        return violationRepository.findByLocation(location);
    }

    public void addViolation(TrafficViolationDTO request) {
            Vehicle vehicle = vehicleRepository.findById(request.getVehicleId())
                    .orElseThrow(() -> new EntityNotFoundException("Vehicle not found"));

            User user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found"));

            TrafficViolation violation = new TrafficViolation();
            violation.setViolationType(request.getType());
            violation.setLocation(request.getLocation());
            violation.setVehicle(vehicle);
            violation.setUser(user);

            violationRepository.save(violation);

    }
}

