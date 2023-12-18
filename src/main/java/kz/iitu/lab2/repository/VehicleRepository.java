package kz.iitu.lab2.repository;

import kz.iitu.lab2.entity.Vehicle;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle,Long> {
    List<Vehicle> findByBrand(String brand, Pageable pageable);

    List<Vehicle> findByBrandAndModel(String brand, String model, Pageable pageable);

    List<Vehicle> findByBrandAndModelAndYear(String brand, String model, int year, Pageable pageable);

}
