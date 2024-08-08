package com.amf.CarRegistry.service;


import java.util.List;
import com.amf.CarRegistry.repository.BrandRepository;
import com.amf.CarRegistry.repository.entity.Brand;
import com.amf.CarRegistry.service.impl.BrandServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BrandServiceImplTest {

    @Mock
    private BrandRepository brandRepository;

    @InjectMocks
    private BrandServiceImpl brandService;

    @Test
    public void testGetBrandById() {
        Brand brand = new Brand();
        brand.setId(1);
        when(brandRepository.findById(1)).thenReturn(Optional.of(brand));

        Brand foundBrand = brandService.getBrandById(1);
        assertNotNull(foundBrand);
        assertEquals(1, foundBrand.getId());
    }

    @Test
    public void testGetBrandById_NotFound() {
        when(brandRepository.findById(1)).thenReturn(Optional.empty());

        Brand foundBrand = brandService.getBrandById(1);
        assertNull(foundBrand);
    }

    @Test
    public void testAddBrand() {
        Brand brand = new Brand();
        brand.setName("Test Brand");
        when(brandRepository.save(any(Brand.class))).thenReturn(brand);

        Brand savedBrand = brandService.addBrand(brand);
        assertNotNull(savedBrand);
        assertEquals("Test Brand", savedBrand.getName());
    }

    @Test
    public void testUpdateBrand() {
        Brand brand = new Brand();
        brand.setId(1);
        brand.setName("Updated Brand");

        when(brandRepository.existsById(1)).thenReturn(true);
        when(brandRepository.save(any(Brand.class))).thenReturn(brand);

        Brand updatedBrand = brandService.updateBrand(1, brand);
        assertNotNull(updatedBrand);
        assertEquals(1, updatedBrand.getId());
        assertEquals("Updated Brand", updatedBrand.getName());
    }

    @Test
    public void testDeleteBrand() {
        when(brandRepository.existsById(1)).thenReturn(true);

        boolean isDeleted = brandService.deleteBrand(1);
        assertTrue(isDeleted);
        verify(brandRepository, times(1)).deleteById(1);
    }

    @Test
    public void testGetAllBrands() throws Exception {
        Brand brand1 = new Brand();
        brand1.setId(1);
        brand1.setName("Brand 1");

        Brand brand2 = new Brand();
        brand2.setId(2);
        brand2.setName("Brand 2");

        List<Brand> brands = Arrays.asList(brand1, brand2);
        when(brandRepository.findAll()).thenReturn(brands);

        CompletableFuture<List<Brand>> futureBrands = brandService.getAllBrands();
        List<Brand> result = futureBrands.get();
        assertNotNull(result);
        assertEquals(2, result.size());
    }
}