package kz.iitu.lab2.service;

import kz.iitu.lab2.dtos.DriverLicenseTranslationDTO;
import kz.iitu.lab2.entity.DriverLicense;
import kz.iitu.lab2.entity.DriverLicenseTranslation;
import kz.iitu.lab2.repository.DriverLicenseRepository;
import kz.iitu.lab2.repository.DriverLicenseTranslationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DriverLicenseTranslationService {
    private final DriverLicenseTranslationRepository translationRepository;
    private final DriverLicenseRepository driverLicenseRepository;

    public void addTranslation(Long licenseId, DriverLicenseTranslationDTO translationDTO) {
        Optional<DriverLicense> optionalLicense = driverLicenseRepository.findById(licenseId);
        if (optionalLicense.isPresent()) {
            DriverLicenseTranslation translation = new DriverLicenseTranslation();
            translation.setDriverLicense(optionalLicense.get()); // Привязка к существующей лицензии
            translation.setLanguageCode(translationDTO.getLanguageCode());
            translation.setTranslatedCategoriesValue(translationDTO.getTranslatedCategoriesValue());

            translationRepository.save(translation);
        } else {
            // Обработка случая, если лицензия не найдена
            throw new IllegalArgumentException("License not found with id: " + licenseId);
        }
    }


    public List<DriverLicenseTranslationDTO> getTranslationsByLicenseId(Long licenseId) {
        List<DriverLicenseTranslation> translations = translationRepository.findByDriverLicenseId(licenseId);
        return translations.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public DriverLicenseTranslationDTO getTranslationByLanguage(Long licenseId, String languageCode) {
        DriverLicenseTranslation translation = translationRepository.findByDriverLicenseIdAndLanguageCode(licenseId, languageCode);
        return mapToDTO(translation);
    }

    public void updateTranslation(Long translationId, DriverLicenseTranslationDTO translationDTO) {
        Optional<DriverLicenseTranslation> optionalTranslation = translationRepository.findById(translationId);
        if (optionalTranslation.isPresent()) {
            DriverLicenseTranslation translation = optionalTranslation.get();
            translation.setLanguageCode(translationDTO.getLanguageCode());
            translation.setTranslatedCategoriesValue(translationDTO.getTranslatedCategoriesValue());

            translationRepository.save(translation);
        } else {
            // Обработка случая, если перевод не найден
            throw new IllegalArgumentException("Translation not found with id: " + translationId);
        }
    }

    public void deleteTranslation(Long translationId) {
        translationRepository.deleteById(translationId);
    }

    private DriverLicenseTranslationDTO mapToDTO(DriverLicenseTranslation translation) {
        if (translation != null) {
            DriverLicenseTranslationDTO dto = new DriverLicenseTranslationDTO();
            dto.setLanguageCode(translation.getLanguageCode());
            dto.setTranslatedCategoriesValue(translation.getTranslatedCategoriesValue());
            return dto;
        }
        return null;
    }
}
