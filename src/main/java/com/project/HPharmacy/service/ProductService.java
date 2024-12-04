package com.project.HPharmacy.service;

import com.project.HPharmacy.entity.Product;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface ProductService {
    Optional<Product> findById(Long id);

//    Page<Product> getPaginatedProducts(int page);

    Page<Product> getPaginatedProducts(int page, int size, String sort);

    Page<Product> searchProducts(String keyword, int page, int size, String sort);

    Page<Product> filterProductsByType(Long productTypeId, int page, int size, String sort);

    Page<Product> getProducts(String keyword, Long productTypeId, int page, int size, String sort);

    Page<Product> getFilteredProducts(String keyword, Long productTypeId, Double minPrice, Double maxPrice, String sort, int page, int size);

    Product getProductById(Long id);

    Product getById(Long id);

    void addProduct(Product product);

    void updateProduct(Long id, Product product);

    void delete(Long id);

}

