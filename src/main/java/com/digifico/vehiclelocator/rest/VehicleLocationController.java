package com.digifico.vehiclelocator.rest;

import com.digifico.vehiclelocator.dto.VehicleLocationDto;
import com.digifico.vehiclelocator.exceptions.InvalidSearchAreaPolygonException;
import com.digifico.vehiclelocator.exceptions.NoSuchVehicleException;
import com.digifico.vehiclelocator.services.VehicleLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/vehicle-locator")
public class VehicleLocationController {

    private VehicleLocationService vehicleLocationService;

    @Autowired
    public VehicleLocationController(VehicleLocationService vehicleLocationService) {
        this.vehicleLocationService = vehicleLocationService;
    }

    @GetMapping(
            value = "/vehicle-location/{id}",
            produces = "application/json"
    )
    @ResponseBody
    public ResponseEntity getVehicleLocation(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(vehicleLocationService.getVehicleLocation(id));
        } catch (NoSuchVehicleException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(
            value = "/vehicle-location",
            consumes = "application/json",
            produces = "application/json"
    )
    @ResponseBody
    public ResponseEntity createVehicleLocation(@RequestBody @Valid VehicleLocationDto vehicleLocationDto) {
        vehicleLocationDto = vehicleLocationService.createVehicleLocation(vehicleLocationDto);
        return ResponseEntity.created(URI.create("/vehicle-location/" + vehicleLocationDto.getId())).body(vehicleLocationDto);
    }

    @PutMapping(
            value = "/vehicle-location",
            consumes = "application/json",
            produces = "application/json"
    )
    @ResponseBody
    public ResponseEntity updateVehicleLocation(@RequestBody @Valid VehicleLocationDto vehicleLocationDto) {
        try {
            if (vehicleLocationDto.getId() == null) {
                throw new NoSuchVehicleException();
            }
            vehicleLocationService.updateVehicleLocation(vehicleLocationDto);
            return ResponseEntity.ok().build();
        } catch (NoSuchVehicleException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(
        value = "/search-inside-polygon",
        consumes = "application/json",
        produces = "application/json"
    )
    @ResponseBody
    public ResponseEntity searchVehiclesInside(@RequestBody String geoJsonSearchAreaPolygon) {
        try {
            return ResponseEntity.ok(vehicleLocationService.findVehiclesInside(geoJsonSearchAreaPolygon));
        } catch (InvalidSearchAreaPolygonException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
