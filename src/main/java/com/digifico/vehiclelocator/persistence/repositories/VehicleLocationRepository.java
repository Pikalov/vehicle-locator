package com.digifico.vehiclelocator.persistence.repositories;

import com.digifico.vehiclelocator.persistence.entities.VehicleLocationEntity;
import com.vividsolutions.jts.geom.Polygon;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VehicleLocationRepository extends CrudRepository<VehicleLocationEntity, Long> {

    @Query(value = "SELECT vle FROM VehicleLocationEntity vle WHERE contains(:searchAreaPolygon, vle.location) = TRUE ")
    List<VehicleLocationEntity> findAllInside(Polygon searchAreaPolygon);

}
