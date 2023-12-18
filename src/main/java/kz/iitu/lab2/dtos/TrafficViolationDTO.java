package kz.iitu.lab2.dtos;

import lombok.Data;

@Data
public class TrafficViolationDTO  {
    private String type;
    private String location;
    private Long vehicleId;
    private Long userId;
}
