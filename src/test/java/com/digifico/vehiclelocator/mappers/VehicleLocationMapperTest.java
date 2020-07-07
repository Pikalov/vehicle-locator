package com.digifico.vehiclelocator.mappers;

import com.digifico.vehiclelocator.dto.PointDto;
import com.digifico.vehiclelocator.dto.VehicleLocationDto;
import com.digifico.vehiclelocator.persistence.entities.VehicleLocationEntity;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class VehicleLocationMapperTest {

    private static final Long TEST_VEHICLE_LOCATION_ID = 100L;
    private static final Double TEST_VEHICLE_LOCATION_COORD_X = 1.0d;
    private static final Double TEST_VEHICLE_LOCATION_COORD_Y = 2.0d;

    private VehicleLocationMapper vehicleLocationMapper = new VehicleLocationMapperImpl();

    @Test
    public void testValidDtoToEntityMapping() {
        VehicleLocationDto vehicleLocationDto = createVehicleLocationDto();
        VehicleLocationEntity vehicleLocationEntity = vehicleLocationMapper.toEntity(vehicleLocationDto);
        assertEquals(vehicleLocationDto.getId(), vehicleLocationEntity.getId());
        assertEquals(vehicleLocationDto.getLocation().getType(), vehicleLocationEntity.getLocation().getGeometryType());
        assertEquals(vehicleLocationDto.getLocation().getCoordinates()[0], Double.valueOf(vehicleLocationEntity.getLocation().getX()));
        assertEquals(vehicleLocationDto.getLocation().getCoordinates()[1], Double.valueOf(vehicleLocationEntity.getLocation().getY()));
    }

    @Test
    public void testValidEntityToDtoMapping() {
        VehicleLocationEntity vehicleLocationEntity = createVehicleLocationEntity();
        VehicleLocationDto vehicleLocationDto = vehicleLocationMapper.toDto(vehicleLocationEntity);
        assertEquals(vehicleLocationEntity.getId(), vehicleLocationDto.getId());
        assertEquals(vehicleLocationEntity.getLocation().getGeometryType(), vehicleLocationDto.getLocation().getType());
        assertEquals(Double.valueOf(vehicleLocationEntity.getLocation().getX()), vehicleLocationDto.getLocation().getCoordinates()[0]);
        assertEquals(Double.valueOf(vehicleLocationEntity.getLocation().getY()), vehicleLocationDto.getLocation().getCoordinates()[1]);
    }

    @Test
    public void testNullDtoToEntityMapping() {
        VehicleLocationEntity vehicleLocationEntity = vehicleLocationMapper.toEntity(null);
        assertNull(vehicleLocationEntity);
    }

    @Test
    public void testNullEntityToDtoMapping() {
        VehicleLocationDto vehicleLocationDto = vehicleLocationMapper.toDto(null);
        assertNull(vehicleLocationDto);
    }

    @Test
    public void testNotSavedDtoToEntityMapping() {
        VehicleLocationDto vehicleLocationDto = createVehicleLocationDto();
        vehicleLocationDto.setId(null);
        VehicleLocationEntity vehicleLocationEntity = vehicleLocationMapper.toEntity(vehicleLocationDto);
        assertNull(vehicleLocationEntity.getId());
        assertEquals(vehicleLocationDto.getLocation().getType(), vehicleLocationEntity.getLocation().getGeometryType());
        assertEquals(vehicleLocationDto.getLocation().getCoordinates()[0], Double.valueOf(vehicleLocationEntity.getLocation().getX()));
        assertEquals(vehicleLocationDto.getLocation().getCoordinates()[1], Double.valueOf(vehicleLocationEntity.getLocation().getY()));
    }

    @Test
    public void testInvalidDtoToEntityMapping() {
        VehicleLocationDto vehicleLocationDto = createVehicleLocationDto();
        vehicleLocationDto.setLocation(null);
        VehicleLocationEntity vehicleLocationEntity = vehicleLocationMapper.toEntity(vehicleLocationDto);
        assertEquals(vehicleLocationDto.getId(), vehicleLocationEntity.getId());
        assertNull(vehicleLocationEntity.getLocation());
    }

    @Test
    public void testInvalidEntityToDtoMapping() {
        VehicleLocationEntity vehicleLocationEntity = createVehicleLocationEntity();
        vehicleLocationEntity.setLocation(null);
        VehicleLocationDto vehicleLocationDto = vehicleLocationMapper.toDto(vehicleLocationEntity);
        assertEquals(vehicleLocationEntity.getId(), vehicleLocationDto.getId());
        assertNull(vehicleLocationDto.getLocation());
    }

    private VehicleLocationDto createVehicleLocationDto() {
        VehicleLocationDto vehicleLocationDto = new VehicleLocationDto();
        vehicleLocationDto.setId(TEST_VEHICLE_LOCATION_ID);
        PointDto pointDto = new PointDto();
        pointDto.setType("Point");
        pointDto.setCoordinates(new Double[]{TEST_VEHICLE_LOCATION_COORD_X, TEST_VEHICLE_LOCATION_COORD_Y});
        vehicleLocationDto.setLocation(pointDto);
        return vehicleLocationDto;
    }

    private VehicleLocationEntity createVehicleLocationEntity() {
        VehicleLocationEntity vehicleLocationEntity = new VehicleLocationEntity();
        vehicleLocationEntity.setId(TEST_VEHICLE_LOCATION_ID);
        Coordinate coordinate = new Coordinate(TEST_VEHICLE_LOCATION_COORD_X, TEST_VEHICLE_LOCATION_COORD_Y);
        Point point = new GeometryFactory().createPoint(coordinate);
        vehicleLocationEntity.setLocation(point);
        return vehicleLocationEntity;
    }
}
