package com.amf.CarRegistry.service;

import com.amf.CarRegistry.repository.entity.Car;

import java.util.List;
import java.util.concurrent.CompletableFuture;



public interface CarService {
    Car getCarById(Integer id);
    Car addCar(Car car);
    Car updateCar(Integer id, Car car);
    boolean deleteCar(Integer id);
    CompletableFuture<List<Car>> getAllCars();
}
