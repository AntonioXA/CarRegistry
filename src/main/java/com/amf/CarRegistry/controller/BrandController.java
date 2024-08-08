package com.amf.CarRegistry.controller;

import com.amf.CarRegistry.controller.dto.BrandDTO;
import com.amf.CarRegistry.controller.mapper.BrandMapper;
import com.amf.CarRegistry.repository.entity.Brand;
import com.amf.CarRegistry.service.BrandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/api/brands")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @Autowired
    private BrandMapper brandMapper;

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getBrand(@PathVariable Integer id) {
        log.info("Retrieving Brand info for ID: " + id);
        Brand brand = brandService.getBrandById(id);
        if (brand == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(brandMapper.toDto(brand), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllBrands() {
        CompletableFuture<List<Brand>> brandsFuture = brandService.getAllBrands();
        List<Brand> brands;
        try {
            brands = brandsFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error retrieving brands", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        List<BrandDTO> brandDTOs = brands.stream().map(brandMapper::toDto).collect(Collectors.toList());
        return new ResponseEntity<>(brandDTOs, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addBrand(@RequestBody BrandDTO brandDTO) {
        log.info("Adding a new Brand");
        try {
            Brand brand = brandMapper.toDomain(brandDTO);
            brandService.addBrand(brand);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error adding brand", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<?> updateBrand(@PathVariable Integer id, @RequestBody BrandDTO brandDTO) {
        log.info("Updating Brand info for ID: " + id);
        try {
            Brand brand = brandMapper.toDomain(brandDTO);
            Brand updatedBrand = brandService.updateBrand(id, brand);
            if (updatedBrand == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(brandMapper.toDto(updatedBrand), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error updating brand", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBrand(@PathVariable Integer id) {
        log.info("Deleting Brand with ID: " + id);
        boolean isDeleted = brandService.deleteBrand(id);
        if (!isDeleted) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}