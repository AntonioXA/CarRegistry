package com.amf.CarRegistry.controller.mapper;

import com.amf.CarRegistry.controller.dto.BrandDTO;
import com.amf.CarRegistry.repository.entity.Brand;
import org.springframework.stereotype.Component;

@Component
public class BrandMapper {

    public BrandDTO toDto(Brand brand) {
        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setId(brand.getId());
        brandDTO.setName(brand.getName());
        brandDTO.setWarranty(brand.getWarranty());
        brandDTO.setCountry(brand.getCountry());
        return brandDTO;
    }

    public Brand toDomain(BrandDTO brandDTO) {
        Brand brand = new Brand();
        brand.setId(brandDTO.getId());
        brand.setName(brandDTO.getName());
        brand.setWarranty(brandDTO.getWarranty());
        brand.setCountry(brandDTO.getCountry());
        return brand;
    }
}
