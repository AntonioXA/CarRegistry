package com.amf.CarRegistry.service.impl;

import com.amf.CarRegistry.repository.CarRepository;
import com.amf.CarRegistry.repository.entity.Car;
import com.amf.CarRegistry.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private CarRepository carRepository;

    @Override
    public Car getCarById(Integer id) {
        return carRepository.findById(id).orElse(null);
    }

    @Override
    public Car addCar(Car car) {
        return carRepository.save(car);
    }

    @Override
    public Car updateCar(Integer id, Car car) {
        if (carRepository.existsById(id)) {
            car.setId(id);
            return carRepository.save(car);
        }
        return null;
    }

    @Override
    public boolean deleteCar(Integer id) {
        if (carRepository.existsById(id)) {
            carRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Async
    @Override
    public CompletableFuture<List<Car>> getAllCars() {
        return CompletableFuture.completedFuture(carRepository.findAll());
    }
}
