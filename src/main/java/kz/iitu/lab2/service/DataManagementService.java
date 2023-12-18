package kz.iitu.lab2.service;

import kz.iitu.lab2.entity.DriverLicense;
import kz.iitu.lab2.entity.User;
import kz.iitu.lab2.entity.Vehicle;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DataManagementService {
    private final DriverLicenseService driverLicenseService;
    private final UserService userService;

    public void addDriverLicense(DriverLicense driverLicense, Long accountId) {
        User user = userService.findById(accountId);
        if (user.getDriverLicense() != null) {
            throw new IllegalStateException("User already has a driver's license");
        } else {
            driverLicense.setUser(user);
            driverLicenseService.save(driverLicense);
        }
    }
}

