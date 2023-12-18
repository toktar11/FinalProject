package kz.iitu.lab2.controller;

import kz.iitu.lab2.dtos.DriverLicenseResponse;
import kz.iitu.lab2.entity.DriverLicense;
import kz.iitu.lab2.service.DataManagementService;
import kz.iitu.lab2.dtos.DriverLicenseDTO;
import kz.iitu.lab2.service.DriverLicenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/licenses")
@RequiredArgsConstructor
public class DriverLicenseController {
    private final DriverLicenseService driverLicenseService;
    private final DataManagementService dataManagementService;

    @GetMapping("/{licenseId}")
    public ResponseEntity<DriverLicenseResponse> getDriverLicenseById(@PathVariable Long licenseId, @RequestHeader("languageCode") String languageCode) {
        DriverLicenseResponse license = driverLicenseService.findById(licenseId, languageCode).orElseThrow();
        return ResponseEntity.ok(license);
    }

    @GetMapping("/all")
    public ResponseEntity<List<DriverLicenseResponse>> getAllViolations(
                                                                @RequestHeader("languageCode") String languageCode,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "4") int size,
                                                                @RequestParam(defaultValue = "id") String sortBy,
                                                                @RequestParam(defaultValue = "desc") String order) {
        List<DriverLicenseResponse> driverLicenses = driverLicenseService.findAll(page, size, sortBy, order,languageCode);
        return ResponseEntity.ok(driverLicenses);
    }

    @PostMapping("/{accountId}")
    public ResponseEntity<String> addDriverLicense(@RequestBody DriverLicense license, @PathVariable Long accountId) {
        dataManagementService.addDriverLicense(license, accountId);
        return ResponseEntity.ok("Driver license added successfully");
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<DriverLicense> getDriverLicenseByAccountId(@PathVariable Long accountId) {
        try {
            DriverLicense license = driverLicenseService.findByAccountId(accountId);
            return ResponseEntity.ok(license);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{licenseId}")
    public ResponseEntity<String> deleteDriverLicense(@PathVariable Long licenseId) {
        driverLicenseService.deleteById(licenseId);
        return ResponseEntity.ok("Driver license deleted successfully");
    }
}

