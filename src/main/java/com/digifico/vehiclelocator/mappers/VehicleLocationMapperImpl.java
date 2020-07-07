package com.digifico.vehiclelocator.mappers;

import com.digifico.vehiclelocator.dto.PointDto;
import com.digifico.vehiclelocator.dto.VehicleLocationDto;
import com.digifico.vehiclelocator.persistence.entities.VehicleLocationEntity;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import org.springframework.stereotype.Component;

@Component
public class VehicleLocationMapperImpl implements VehicleLocationMapper {

    private static final int POINT_DIMENSION = 2;

    @Override
    public VehicleLocationEntity toEntity(VehicleLocationDto vehicleLocationDto) {
        VehicleLocationEntity vehicleLocationEntity = null;
        if (vehicleLocationDto != null) {
            vehicleLocationEntity = new VehicleLocationEntity();
            vehicleLocationEntity.setId(vehicleLocationDto.getId());
            if (vehicleLocationDto.getLocation() != null
                    && vehicleLocationDto.getLocation().getCoordinates() != null
                    && vehicleLocationDto.getLocation().getCoordinates().length == POINT_DIMENSION) {
                Coordinate coordinate = new Coordinate(vehicleLocationDto.getLocation().getCoordinates()[0], vehicleLocationDto.getLocation().getCoordinates()[1]);
                Point point = new GeometryFactory().createPoint(coordinate);
                vehicleLocationEntity.setLocation(point);
            }
        }
        return vehicleLocationEntity;
    }

    @Override
    public VehicleLocationDto toDto(VehicleLocationEntity vehicleLocationEntity) {
        VehicleLocationDto vehicleLocationDto = null;
        if (vehicleLocationEntity != null) {
            vehicleLocationDto = new VehicleLocationDto();
            vehicleLocationDto.setId(vehicleLocationEntity.getId());
            if (vehicleLocationEntity.getLocation() != null) {
                PointDto pointDto = new PointDto(vehicleLocationEntity.getLocation().getX(), vehicleLocationEntity.getLocation().getY());
                vehicleLocationDto.setLocation(pointDto);
            }
        }
        return vehicleLocationDto;
    }
}
