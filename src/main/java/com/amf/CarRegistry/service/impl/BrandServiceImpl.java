package com.amf.CarRegistry.service.impl;

import com.amf.CarRegistry.repository.BrandRepository;
import com.amf.CarRegistry.repository.entity.Brand;
import com.amf.CarRegistry.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;

import java.util.List;
import java.util.Optional;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandRepository brandRepository;

    @Override
    public Brand getBrandById(Integer id) {
        return brandRepository.findById(id).orElse(null);
    }

    @Override
    public Brand addBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    @Override
    public Brand updateBrand(Integer id, Brand brand) {
        if (brandRepository.existsById(id)) {
            brand.setId(id);
            return brandRepository.save(brand);
        }
        return null;
    }

    @Override
    public boolean deleteBrand(Integer id) {
        if (brandRepository.existsById(id)) {
            brandRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Async
    @Override
    public CompletableFuture<List<Brand>> getAllBrands() {
        return CompletableFuture.completedFuture(brandRepository.findAll());
    }
}