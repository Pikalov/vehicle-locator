package com.digifico.vehiclelocator.persistence.entities;

import com.vividsolutions.jts.geom.Point;

import javax.persistence.*;


// Index has been created manually in db with USING GIST instead of not working annotation-driven index
@Entity
@Table(name = "VEHICLE_LOCATION"/*, indexes = {
        @Index(name = "LOCATION_IDX_0", columnList = "location")
}*/)
public class VehicleLocationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "location", columnDefinition = "Geometry(Point,0)")
    private Point location;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }
}


