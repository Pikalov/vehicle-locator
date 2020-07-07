package com.digifico.vehiclelocator.services;

import com.digifico.vehiclelocator.dto.VehicleLocationDto;
import com.digifico.vehiclelocator.exceptions.InvalidSearchAreaPolygonException;
import com.digifico.vehiclelocator.exceptions.NoSuchVehicleException;
import com.digifico.vehiclelocator.mappers.VehicleLocationMapper;
import com.digifico.vehiclelocator.persistence.entities.VehicleLocationEntity;
import com.digifico.vehiclelocator.persistence.repositories.VehicleLocationRepository;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.geojson.GeoJsonReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleLocationServiceImpl implements VehicleLocationService {

    private VehicleLocationRepository vehicleLocationRepository;
    private VehicleLocationMapper vehicleLocationMapper;

    @Autowired
    public VehicleLocationServiceImpl(VehicleLocationRepository vehicleLocationRepository, VehicleLocationMapper vehicleLocationMapper) {
        this.vehicleLocationRepository = vehicleLocationRepository;
        this.vehicleLocationMapper = vehicleLocationMapper;
    }

    @Override
    public VehicleLocationDto getVehicleLocation(Long id) throws NoSuchVehicleException {
        return vehicleLocationRepository.findById(id)
                .map(vehicleLocationMapper::toDto)
                .orElseThrow(NoSuchVehicleException::new);
    }

    @Override
    public VehicleLocationDto createVehicleLocation(VehicleLocationDto vehicleLocationDto) {
        VehicleLocationEntity vehicleLocationEntity = vehicleLocationRepository.save(vehicleLocationMapper.toEntity(vehicleLocationDto));
        return vehicleLocationMapper.toDto(vehicleLocationEntity);
    }

    @Override
    public VehicleLocationDto updateVehicleLocation(VehicleLocationDto vehicleLocationDto) throws NoSuchVehicleException {
        VehicleLocationEntity vehicleLocationEntity = vehicleLocationRepository.findById(vehicleLocationDto.getId())
                .orElseThrow(NoSuchVehicleException::new);
        vehicleLocationEntity.setLocation(vehicleLocationMapper.toEntity(vehicleLocationDto).getLocation());
        vehicleLocationEntity = vehicleLocationRepository.save(vehicleLocationEntity);
        return vehicleLocationMapper.toDto(vehicleLocationEntity);
    }

    @Override
    public List<VehicleLocationDto> findVehiclesInside(String geoJsonSearchAreaPolygon) throws InvalidSearchAreaPolygonException {
        try {
            Polygon polygon = (Polygon) new GeoJsonReader().read(geoJsonSearchAreaPolygon);
            polygon.setSRID(0);
            return vehicleLocationRepository.findAllInside(polygon).stream()
                    .map(vehicleLocationMapper::toDto)
                    .collect(Collectors.toList());
        } catch (ParseException | ClassCastException | NoSuchMethodError e) {
            throw new InvalidSearchAreaPolygonException();
        }
    }
}
