package com.project.HPharmacy.service;

import com.project.HPharmacy.entity.ProductType;
import com.project.HPharmacy.repository.ProductTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductTypeServiceImpl implements ProductTypeService{
    @Autowired
    private ProductTypeRepository productTypeRepository;

    @Override
    public List<ProductType> getAllProductTypes() {
        return productTypeRepository.findAll(); // Truy vấn tất cả loại sản phẩm
    }
}
