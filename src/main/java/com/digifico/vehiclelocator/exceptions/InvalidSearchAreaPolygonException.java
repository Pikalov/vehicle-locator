package com.digifico.vehiclelocator.exceptions;

public class InvalidSearchAreaPolygonException extends Exception {

    @Override
    public String getMessage() {
        return "Search area should be defined as GeoJson Polygon.";
    }

}
