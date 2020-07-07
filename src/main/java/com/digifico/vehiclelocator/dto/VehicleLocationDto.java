package com.digifico.vehiclelocator.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class VehicleLocationDto {

    private Long id;
    @NotNull
    @Valid
    private PointDto location;

    public VehicleLocationDto() {
    }

    public VehicleLocationDto(Long id, PointDto location) {
        this.id = id;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PointDto getLocation() {
        return location;
    }

    public void setLocation(PointDto location) {
        this.location = location;
    }
}
