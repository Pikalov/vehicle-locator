package com.digifico.vehiclelocator.services;

import com.digifico.vehiclelocator.dto.PointDto;
import com.digifico.vehiclelocator.dto.VehicleLocationDto;
import com.digifico.vehiclelocator.exceptions.InvalidSearchAreaPolygonException;
import com.digifico.vehiclelocator.exceptions.NoSuchVehicleException;
import com.digifico.vehiclelocator.mappers.VehicleLocationMapper;
import com.digifico.vehiclelocator.mappers.VehicleLocationMapperImpl;
import com.digifico.vehiclelocator.persistence.entities.VehicleLocationEntity;
import com.digifico.vehiclelocator.persistence.repositories.VehicleLocationRepository;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.geojson.GeoJsonReader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class VehicleLocationServiceTest {

    private static final Long TEST_VEHICLE_LOCATION_ID = 100L;
    private static final Double TEST_VEHICLE_LOCATION_COORD_X = 1.0d;
    private static final Double TEST_VEHICLE_LOCATION_COORD_Y = 2.0d;
    private static final String TEST_GEO_JSON_SEARCH_AREA =
            "{\n" +
            "    \"type\": \"Polygon\",\n" +
            "    \"coordinates\": [\n" +
            "        [\n" +
            "            [0.0, 0.0], [10.0, 0.0], [10.0, 10.0], [0.0, 10.0], [0.0, 0.0]\n" +
            "        ]\n" +
            "    ]\n" +
            "}";

    @Mock
    private VehicleLocationRepository vehicleLocationRepository;
    private VehicleLocationMapper vehicleLocationMapper = new VehicleLocationMapperImpl();
    private VehicleLocationService vehicleLocationService;

    @Before
    public void init() {
        vehicleLocationService = new VehicleLocationServiceImpl(vehicleLocationRepository, vehicleLocationMapper);
    }

    @Test
    public void testGetVehicleLocation() throws NoSuchVehicleException {
        VehicleLocationEntity vehicleLocationEntity = createVehicleLocationEntity();
        when(vehicleLocationRepository.findById(TEST_VEHICLE_LOCATION_ID)).thenReturn(Optional.of(vehicleLocationEntity));

        VehicleLocationDto vehicleLocationDto = vehicleLocationService.getVehicleLocation(TEST_VEHICLE_LOCATION_ID);

        assertEquals(vehicleLocationEntity.getId(), vehicleLocationDto.getId());
        assertEquals(vehicleLocationEntity.getLocation().getGeometryType(), vehicleLocationDto.getLocation().getType());
        assertEquals(Double.valueOf(vehicleLocationEntity.getLocation().getX()), vehicleLocationDto.getLocation().getCoordinates()[0]);
        assertEquals(Double.valueOf(vehicleLocationEntity.getLocation().getY()), vehicleLocationDto.getLocation().getCoordinates()[1]);
    }

    @Test
    public void testCreateVehicleLocation() {
        VehicleLocationEntity vehicleLocationEntity = createVehicleLocationEntity();
        VehicleLocationDto vehicleLocationDto = createVehicleLocationDto();
        vehicleLocationDto.setId(null);
        when(vehicleLocationRepository.save(any())).thenReturn(vehicleLocationEntity);

        vehicleLocationDto = vehicleLocationService.createVehicleLocation(vehicleLocationDto);

        assertEquals(vehicleLocationEntity.getId(), vehicleLocationDto.getId());
        assertEquals(vehicleLocationEntity.getLocation().getGeometryType(), vehicleLocationDto.getLocation().getType());
        assertEquals(Double.valueOf(vehicleLocationEntity.getLocation().getX()), vehicleLocationDto.getLocation().getCoordinates()[0]);
        assertEquals(Double.valueOf(vehicleLocationEntity.getLocation().getY()), vehicleLocationDto.getLocation().getCoordinates()[1]);
    }

    @Test
    public void testUpdateVehicleLocation() throws NoSuchVehicleException {
        VehicleLocationEntity vehicleLocationEntity = createVehicleLocationEntity();
        vehicleLocationEntity.setLocation(new GeometryFactory()
                .createPoint(new Coordinate(TEST_VEHICLE_LOCATION_COORD_X + 1, TEST_VEHICLE_LOCATION_COORD_Y + 1)));
        VehicleLocationDto vehicleLocationDto = createVehicleLocationDto();
        vehicleLocationDto.getLocation().setCoordinates(new Double[]{TEST_VEHICLE_LOCATION_COORD_X + 1, TEST_VEHICLE_LOCATION_COORD_Y + 1});
        when(vehicleLocationRepository.findById(TEST_VEHICLE_LOCATION_ID)).thenReturn(Optional.of(vehicleLocationEntity));
        when(vehicleLocationRepository.save(eq(vehicleLocationEntity))).thenReturn(vehicleLocationEntity);

        vehicleLocationService.updateVehicleLocation((vehicleLocationDto));

        assertEquals(vehicleLocationEntity.getId(), vehicleLocationDto.getId());
        assertEquals(vehicleLocationEntity.getLocation().getGeometryType(), vehicleLocationDto.getLocation().getType());
        assertEquals(Double.valueOf(vehicleLocationEntity.getLocation().getX()), vehicleLocationDto.getLocation().getCoordinates()[0]);
        assertEquals(Double.valueOf(vehicleLocationEntity.getLocation().getY()), vehicleLocationDto.getLocation().getCoordinates()[1]);
    }

    @Test
    public void testFindVehiclesInside() throws InvalidSearchAreaPolygonException, ParseException {
        List<VehicleLocationEntity> vehicleLocationEntities = new ArrayList<>();
        vehicleLocationEntities.add(createVehicleLocationEntity());
        vehicleLocationEntities.add(createVehicleLocationEntity());
        when(vehicleLocationRepository.findAllInside((Polygon) new GeoJsonReader().read(TEST_GEO_JSON_SEARCH_AREA)))
                .thenReturn(vehicleLocationEntities);

        List<VehicleLocationDto> vehicleLocationDtos = vehicleLocationService.findVehiclesInside(TEST_GEO_JSON_SEARCH_AREA);

        assertEquals(vehicleLocationEntities.size(), vehicleLocationDtos.size());
    }

    private VehicleLocationDto createVehicleLocationDto() {
        VehicleLocationDto vehicleLocationDto = new VehicleLocationDto();
        vehicleLocationDto.setId(TEST_VEHICLE_LOCATION_ID);
        PointDto pointDto = new PointDto(TEST_VEHICLE_LOCATION_COORD_X, TEST_VEHICLE_LOCATION_COORD_Y);
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
