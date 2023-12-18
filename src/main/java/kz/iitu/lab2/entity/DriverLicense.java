package kz.iitu.lab2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverLicense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;

    @Column(name = "license_details")
    private String licenseDetails;

    private Date issueDate;
    private Date validUntil;

    @OneToMany(mappedBy = "driverLicense", cascade = CascadeType.ALL)
    private List<DriverLicenseTranslation> driverLicenseTranslations;

    @OneToOne
    private User user;
}