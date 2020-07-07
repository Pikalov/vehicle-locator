package com.digifico.vehiclelocator.mappers;

import com.digifico.vehiclelocator.dto.VehicleLocationDto;
import com.digifico.vehiclelocator.persistence.entities.VehicleLocationEntity;

public interface VehicleLocationMapper {

    VehicleLocationEntity toEntity(VehicleLocationDto vehicleLocationDto);

    VehicleLocationDto toDto(VehicleLocationEntity vehicleLocationEntity);

}
