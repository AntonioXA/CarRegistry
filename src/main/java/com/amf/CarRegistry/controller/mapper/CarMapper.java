package com.amf.CarRegistry.controller.mapper;

import com.amf.CarRegistry.controller.dto.CarDTO;
import com.amf.CarRegistry.repository.entity.Brand;
import com.amf.CarRegistry.repository.entity.Car;
import org.springframework.stereotype.Component;

@Component
public class CarMapper {

    public CarDTO toDto(Car car) {
        CarDTO carDTO = new CarDTO();
        carDTO.setId(car.getId());
        carDTO.setBrandId(car.getBrand().getId());
        carDTO.setBrandName(car.getBrand().getName());
        carDTO.setModel(car.getModel());
        carDTO.setMileage(car.getMileage());
        carDTO.setPrice(car.getPrice());
        carDTO.setYear(car.getYear());
        carDTO.setDescription(car.getDescription());
        carDTO.setColour(car.getColour());
        carDTO.setFuelType(car.getFuelType());
        carDTO.setNumDoors(car.getNumDoors());
        return carDTO;
    }

    public Car toDomain(CarDTO carDTO, Brand brand) {
        Car car = new Car();
        car.setId(carDTO.getId());
        car.setBrand(brand);
        car.setModel(carDTO.getModel());
        car.setMileage(carDTO.getMileage());
        car.setPrice(carDTO.getPrice());
        car.setYear(carDTO.getYear());
        car.setDescription(carDTO.getDescription());
        car.setColour(carDTO.getColour());
        car.setFuelType(carDTO.getFuelType());
        car.setNumDoors(carDTO.getNumDoors());
        return car;
    }
}
