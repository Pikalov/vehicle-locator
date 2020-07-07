package com.digifico.vehiclelocator.exceptions;

public class NoSuchVehicleException extends Exception {

    @Override
    public String getMessage() {
        return "No such vehicle has been found.";
    }

}
