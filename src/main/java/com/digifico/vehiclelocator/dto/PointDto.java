package com.digifico.vehiclelocator.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PointDto {

    @Pattern(regexp = "Point", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Type should be \"Point\".")
    private String type;
    @Size(min = 2, max = 2, message = "Point should be 2-dimensional.")
    private Double[] coordinates;

    public PointDto() {
    }

    public PointDto(String type, Double[] coordinates) {
        this.type = type;
        this.coordinates = coordinates;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Double[] coordinates) {
        this.coordinates = coordinates;
    }
}
