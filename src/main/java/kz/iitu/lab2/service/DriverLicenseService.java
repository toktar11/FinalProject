package kz.iitu.lab2.service;

import kz.iitu.lab2.dtos.DriverLicenseDTO;
import kz.iitu.lab2.dtos.DriverLicenseResponse;
import kz.iitu.lab2.entity.DriverLicense;
import kz.iitu.lab2.entity.DriverLicenseTranslation;
import kz.iitu.lab2.entity.User;
import kz.iitu.lab2.repository.DriverLicenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DriverLicenseService {

    private final DriverLicenseRepository driverLicenseRepository;

    private final UserService userService;

    public Optional<DriverLicenseResponse> findById(Long id, String languageCode) {
        Optional<DriverLicense> driverLicense = driverLicenseRepository.findById(id);

        return driverLicense.map(driver -> {
            DriverLicenseResponse driverLicenseResponse = new DriverLicenseResponse();
            driverLicenseResponse.setId(driver.getId());
            driverLicenseResponse.setNumber(driver.getNumber());
            driverLicenseResponse.setLicenseDetails(driver.getLicenseDetails());
            driverLicenseResponse.setIssueDate(driver.getIssueDate());
            driverLicenseResponse.setValidUntil(driver.getValidUntil());
            driverLicenseResponse.setUserId(driver.getUser() != null ? driver.getUser().getId() : null);

            if (driver.getDriverLicenseTranslations() != null && !driver.getDriverLicenseTranslations().isEmpty()) {
                DriverLicenseTranslation selectedTranslation = driver.getDriverLicenseTranslations().stream()
                        .filter(translation -> languageCode.equals(translation.getLanguageCode()))
                        .findFirst()
                        .orElse(null);

                if (selectedTranslation != null) {
                    DriverLicenseDTO translationDTO = new DriverLicenseDTO();
                    translationDTO.setId(selectedTranslation.getId());
                    translationDTO.setTranslatedCategoriesValue(selectedTranslation.getTranslatedCategoriesValue());

                    driverLicenseResponse.setDriverLicenseTranslation(Collections.singletonList(translationDTO).get(0).getTranslatedCategoriesValue());
                }
            }

            return driverLicenseResponse;
        });
    }


    public List<DriverLicenseResponse> findAll(int page, int size, String sortBy, String order, String languageCode) {
        Sort sort = order.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<DriverLicense> driverLicenses = driverLicenseRepository.findAll(pageable);

        return driverLicenses.stream()
                .map(driverLicense -> convertToDTO(driverLicense, languageCode))
                .collect(Collectors.toList());
    }

    public DriverLicense save(DriverLicense driverLicense) {
        return driverLicenseRepository.save(driverLicense);
    }

    public void deleteById(Long id) {
        driverLicenseRepository.deleteById(id);
    }

    public DriverLicense findByAccountId(Long accountId) {
        User user = userService.findById(accountId);
        if (user == null) {
            throw new IllegalArgumentException("Account not found");
        }
        return driverLicenseRepository.findByUser(user);
    }

    private DriverLicenseResponse convertToDTO(DriverLicense driverLicense, String languageCode) {
        DriverLicenseResponse driverLicenseResponse = new DriverLicenseResponse();
        driverLicenseResponse.setId(driverLicense.getId());

        if (driverLicense.getDriverLicenseTranslations() != null && !driverLicense.getDriverLicenseTranslations().isEmpty()) {
            DriverLicenseTranslation driverLicenseTranslation = driverLicense.getDriverLicenseTranslations().stream()
                    .filter(translation -> languageCode.equals(translation.getLanguageCode()))
                    .findFirst()
                    .orElse(null);

            if (driverLicenseTranslation != null) {
                driverLicenseResponse.setDriverLicenseTranslation(driverLicenseTranslation.getTranslatedCategoriesValue());
                driverLicenseResponse.setNumber(driverLicense.getNumber());
                driverLicenseResponse.setLicenseDetails(driverLicense.getLicenseDetails());
                driverLicenseResponse.setIssueDate(driverLicense.getIssueDate());
                driverLicenseResponse.setValidUntil(driverLicense.getValidUntil());
            }
        }

        return driverLicenseResponse;
    }
}