package kz.iitu.lab2.repository;

import kz.iitu.lab2.entity.DriverLicense;
import kz.iitu.lab2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverLicenseRepository extends JpaRepository<DriverLicense,Long> {
    DriverLicense findByUser(User user);
}
