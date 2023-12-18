package kz.iitu.lab2.dtos;

import lombok.Data;

@Data
public class VehicleDTO {
    private Long id;
    private String brand;
    private String model;
    private int year;
    private String registrationNumber;
}
