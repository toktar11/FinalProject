package kz.iitu.lab2.controller;

import kz.iitu.lab2.dtos.VehicleDTO;
import kz.iitu.lab2.dtos.VehicleRequestDTO;
import kz.iitu.lab2.entity.Vehicle;
import kz.iitu.lab2.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {
    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/all")
    public ResponseEntity<List<VehicleDTO>> getAllVehicles(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "4") int size,
                                                           @RequestParam(defaultValue = "id") String sortBy,
                                                           @RequestParam(defaultValue = "desc") String order) {
        List<VehicleDTO> vehicles = vehicleService.findAll(page, size, sortBy, order);
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/search")
    public ResponseEntity<List<VehicleDTO>> searchVehicles(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) Integer year,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String order) {

        List<VehicleDTO> vehicles = vehicleService.search(brand, model, year, page, size, sortBy, order);
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/{vehicleId}")
    public ResponseEntity<VehicleDTO> getVehicleById(@PathVariable Long vehicleId) {
        VehicleDTO vehicle = vehicleService.findById(vehicleId);
        return vehicle != null ? ResponseEntity.ok(vehicle) : ResponseEntity.notFound().build();
    }

    @PostMapping("/{accountId}")
    public ResponseEntity<String> addVehicle(@RequestBody VehicleRequestDTO vehicleRequestDTO, @PathVariable Long accountId) {
        vehicleService.addVehicle(vehicleRequestDTO, accountId);
        return ResponseEntity.ok("Vehicle added successfully");
    }

    @DeleteMapping("/{vehicleId}")
    public ResponseEntity<String> deleteVehicle(@PathVariable Long vehicleId) {
        vehicleService.deleteById(vehicleId);
        return ResponseEntity.ok("Vehicle deleted successfully");
    }
}
