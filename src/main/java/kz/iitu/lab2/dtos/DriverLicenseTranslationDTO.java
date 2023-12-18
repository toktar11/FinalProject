package kz.iitu.lab2.dtos;

import lombok.Data;

@Data
public class DriverLicenseTranslationDTO {
    private String languageCode;
    private String translatedCategoriesValue;
}
