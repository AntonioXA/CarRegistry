package com.amf.CarRegistry.service;

import com.amf.CarRegistry.repository.entity.Brand;

import java.util.List;
import java.util.concurrent.CompletableFuture;


public interface BrandService {
    Brand getBrandById(Integer id);
    Brand addBrand(Brand brand);
    Brand updateBrand(Integer id, Brand brand);
    boolean deleteBrand(Integer id);
    CompletableFuture<List<Brand>> getAllBrands();
}


