package kz.iitu.lab2.controller;

import kz.iitu.lab2.dtos.TrafficViolationDTO;
import kz.iitu.lab2.entity.TrafficViolation;
import kz.iitu.lab2.entity.User;
import kz.iitu.lab2.entity.Vehicle;
import kz.iitu.lab2.service.TrafficViolationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/traffic-violations")
@RequiredArgsConstructor
public class TrafficViolationController {
    private final TrafficViolationService violationService;

    @GetMapping("/vehicle/{vehicleId}")
    public ResponseEntity<List<TrafficViolation>> getViolationsByVehicleId(@PathVariable Long vehicleId) {
        List<TrafficViolation> violations = violationService.getViolationsByVehicleId(vehicleId);
        return ResponseEntity.ok(violations);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addViolation(@RequestBody TrafficViolationDTO request) {
        violationService.addViolation(request);
        return ResponseEntity.ok("Violation added successfully");
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TrafficViolation>> getViolationsByUserId(@PathVariable Long userId) {
        List<TrafficViolation> violations = violationService.getViolationsByUserId(userId);
        return ResponseEntity.ok(violations);
    }

    @GetMapping("/type/{violationType}")
    public ResponseEntity<List<TrafficViolation>> getViolationsByType(@PathVariable String violationType) {
        List<TrafficViolation> violations = violationService.getViolationsByType(violationType);
        return ResponseEntity.ok(violations);
    }

    @GetMapping("/location/{location}")
    public ResponseEntity<List<TrafficViolation>> getViolationsByLocation(@PathVariable String location) {
        List<TrafficViolation> violations = violationService.getViolationsByLocation(location);
        return ResponseEntity.ok(violations);
    }

}