package kz.iitu.lab2.repository;

import kz.iitu.lab2.entity.DriverLicenseTranslation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriverLicenseTranslationRepository extends JpaRepository<DriverLicenseTranslation,Long> {
    List<DriverLicenseTranslation> findByDriverLicenseId(Long licenseId);

    DriverLicenseTranslation findByDriverLicenseIdAndLanguageCode(Long licenseId, String languageCode);
}
