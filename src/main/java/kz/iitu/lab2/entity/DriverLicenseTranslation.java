package kz.iitu.lab2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverLicenseTranslation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "driver_license_id")
    private DriverLicense driverLicense;

    @Column(name = "language_code")
    private String languageCode;

    @Column(name = "translated_categories_value")
    private String translatedCategoriesValue;
}
