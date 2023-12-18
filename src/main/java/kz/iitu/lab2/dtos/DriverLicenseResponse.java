package kz.iitu.lab2.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
public class DriverLicenseResponse {
    private Long id;
    private String number;
    private String licenseDetails;
    private Date issueDate;
    private Date validUntil;
    private String driverLicenseTranslation;
    private Long userId;
}
