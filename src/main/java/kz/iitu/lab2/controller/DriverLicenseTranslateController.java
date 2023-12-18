package kz.iitu.lab2.controller;

import kz.iitu.lab2.dtos.DriverLicenseTranslationDTO;
import kz.iitu.lab2.service.DriverLicenseTranslationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/translate/license")
@RequiredArgsConstructor
public class DriverLicenseTranslateController {
    private final DriverLicenseTranslationService driverLicenseTranslationService;

    @PostMapping("/{licenseId}")
    public ResponseEntity<String> addTranslation(
            @PathVariable Long licenseId,
            @RequestBody DriverLicenseTranslationDTO translationDTO
    ) {
        try {
            driverLicenseTranslationService.addTranslation(licenseId, translationDTO);
            return ResponseEntity.ok("Translation added successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to add translation");
        }
    }

    @GetMapping("/{licenseId}")
    public ResponseEntity<List<DriverLicenseTranslationDTO>> getTranslations(@PathVariable Long licenseId) {
        List<DriverLicenseTranslationDTO> translations = driverLicenseTranslationService.getTranslationsByLicenseId(licenseId);
        return ResponseEntity.ok(translations);
    }

    @GetMapping("/{licenseId}/{languageCode}")
    public ResponseEntity<DriverLicenseTranslationDTO> getTranslationByLanguage(
            @PathVariable Long licenseId,
            @PathVariable String languageCode
    ) {
        DriverLicenseTranslationDTO translation = driverLicenseTranslationService.getTranslationByLanguage(licenseId, languageCode);
        if (translation != null) {
            return ResponseEntity.ok(translation);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{translationId}")
    public ResponseEntity<String> updateTranslation(
            @PathVariable Long translationId,
            @RequestBody DriverLicenseTranslationDTO translationDTO
    ) {
        try {
            driverLicenseTranslationService.updateTranslation(translationId, translationDTO);
            return ResponseEntity.ok("Translation updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to update translation");
        }
    }

    @DeleteMapping("/{translationId}")
    public ResponseEntity<String> deleteTranslation(@PathVariable Long translationId) {
        try {
            driverLicenseTranslationService.deleteTranslation(translationId);
            return ResponseEntity.ok("Translation deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to delete translation");
        }
    }
}
