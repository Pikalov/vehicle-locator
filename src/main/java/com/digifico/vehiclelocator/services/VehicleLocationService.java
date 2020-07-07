package com.digifico.vehiclelocator.services;

import com.digifico.vehiclelocator.dto.VehicleLocationDto;
import com.digifico.vehiclelocator.exceptions.InvalidSearchAreaPolygonException;
import com.digifico.vehiclelocator.exceptions.NoSuchVehicleException;

import java.util.List;

public interface VehicleLocationService {

    VehicleLocationDto getVehicleLocation(Long id) throws NoSuchVehicleException;

    VehicleLocationDto createVehicleLocation(VehicleLocationDto vehicleLocationDto);

    VehicleLocationDto updateVehicleLocation(VehicleLocationDto vehicleLocationDTO) throws NoSuchVehicleException;

    List<VehicleLocationDto> findVehiclesInside(String geoJsonSearchArea) throws InvalidSearchAreaPolygonException;
}
