package kz.iitu.lab2.repository;

import kz.iitu.lab2.entity.TrafficViolation;
import kz.iitu.lab2.entity.User;
import kz.iitu.lab2.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrafficViolationRepository extends JpaRepository<TrafficViolation, Long> {

    List<TrafficViolation> findByVehicle(Vehicle vehicle);

    List<TrafficViolation> findByUser(User user);

    List<TrafficViolation> findByViolationType(String violationType);

    List<TrafficViolation> findByLocation(String location);
}