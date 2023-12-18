package kz.iitu.lab2.service;

import kz.iitu.lab2.dtos.VehicleDTO;
import kz.iitu.lab2.dtos.VehicleRequestDTO;
import kz.iitu.lab2.entity.DriverLicense;
import kz.iitu.lab2.entity.User;
import kz.iitu.lab2.entity.Vehicle;
import kz.iitu.lab2.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final UserService userService;

    @Autowired
    public VehicleService(VehicleRepository vehicleRepository, UserService userService) {
        this.vehicleRepository = vehicleRepository;
        this.userService = userService;
    }

    public List<VehicleDTO> findAll(int page, int size, String sortBy, String order) {
        Sort.Direction sortDirection = Sort.Direction.fromString(order);
        Sort sort = Sort.by(sortDirection, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Vehicle> vehiclePage = vehicleRepository.findAll(pageable);
        return vehiclePage.getContent().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<VehicleDTO> search(String brand, String model, Integer year, int page, int size, String sortBy, String order) {
        Sort.Direction sortDirection = Sort.Direction.fromString(order);
        Sort sort = Sort.by(sortDirection, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        List<Vehicle> vehicles;

        if (brand != null && model != null && year != null) {
            vehicles = vehicleRepository.findByBrandAndModelAndYear(brand, model, year, pageable);
        } else if (brand != null && model != null) {
            vehicles = vehicleRepository.findByBrandAndModel(brand, model, pageable);
        } else if (brand != null) {
            vehicles = vehicleRepository.findByBrand(brand, pageable);
        } else {
            vehicles = vehicleRepository.findAll(pageable).getContent();
        }

        return vehicles.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Преобразование сущности Vehicle в DTO
    private VehicleDTO convertToDTO(Vehicle vehicle) {
        VehicleDTO vehicleDTO = new VehicleDTO();
        vehicleDTO.setId(vehicle.getId());
        vehicleDTO.setBrand(vehicle.getBrand());
        vehicleDTO.setModel(vehicle.getModel());
        vehicleDTO.setYear(vehicle.getYear());
        vehicleDTO.setRegistrationNumber(vehicle.getRegistrationNumber());
        // ... (добавление других полей при необходимости)

        return vehicleDTO;
    }
    public VehicleDTO findById(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id).orElse(null);
        return vehicle != null ? convertToDTO(vehicle) : null;
    }

    public void addVehicle(VehicleRequestDTO vehicleRequestDTO, Long accountId) {
        User user = userService.findById(accountId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        Vehicle vehicle = convertToEntity(vehicleRequestDTO);
        vehicle.setUser(user);

        vehicleRepository.save(vehicle);
    }

    public void deleteById(Long id) {
        vehicleRepository.deleteById(id);
    }

    private Vehicle convertToEntity(VehicleRequestDTO vehicleRequestDTO) {
        Vehicle vehicle = new Vehicle();
        vehicle.setBrand(vehicleRequestDTO.getBrand());
        vehicle.setModel(vehicleRequestDTO.getModel());
        vehicle.setYear(vehicleRequestDTO.getYear());
        vehicle.setRegistrationNumber(vehicleRequestDTO.getRegistrationNumber());

        return vehicle;
    }
}
