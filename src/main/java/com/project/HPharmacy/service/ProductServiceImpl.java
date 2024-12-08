package com.project.HPharmacy.service;

import com.project.HPharmacy.entity.Product;
import com.project.HPharmacy.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    // Lấy danh sách sản phẩm có phân trang và sắp xếp
    @Override
    public Page<Product> getPaginatedProducts(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(sort).ascending());
        return productRepository.findAll(pageable);
    }

    // Tìm kiếm sản phẩm theo tên
    @Override
    public Page<Product> searchProducts(String keyword, int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(sort).ascending());
        return productRepository.findByNameContaining(keyword, pageable);
    }

    // Lọc sản phẩm theo loại
    @Override
    public Page<Product> filterProductsByType(Long productTypeId, int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(sort).ascending());
        return productRepository.findByProductTypeId(productTypeId, pageable);
    }

    @Override
    public Page<Product> getProducts(String keyword, Long productTypeId, int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(sort).ascending());

        if (keyword != null && !keyword.isEmpty()) {
            return productRepository.findByNameContaining(keyword, pageable);
        } else if (productTypeId != null) {
            return productRepository.findByProductTypeId(productTypeId, pageable);
        } else {
            return productRepository.findAll(pageable);
        }
    }

    @Override
    public Page<Product> getFilteredProducts(String keyword, Long productTypeId, Double minPrice, Double maxPrice, String sort, int page, int size) {
        Pageable pageable;
        if (sort != null) {
            switch (sort) {
                case "name_asc":
                    pageable = PageRequest.of(page - 1, size, Sort.by("name").ascending());
                    break;
                case "name_desc":
                    pageable = PageRequest.of(page - 1, size, Sort.by("name").descending());
                    break;
                case "price_asc":
                    pageable = PageRequest.of(page - 1, size, Sort.by("price").ascending());
                    break;
                case "price_desc":
                    pageable = PageRequest.of(page - 1, size, Sort.by("price").descending());
                    break;
                case "quantity_asc":
                    pageable = PageRequest.of(page - 1, size, Sort.by("quantity").ascending());
                    break;
                case "quantity_desc":
                    pageable = PageRequest.of(page - 1, size, Sort.by("quantity").descending());
                    break;
                default:
                    pageable = PageRequest.of(page - 1, size);
            }
        } else {
            pageable = PageRequest.of(page - 1, size);
        }

        return productRepository.findProductsWithFilters(keyword, productTypeId, minPrice, maxPrice, pageable);
    }


    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + id));
    }

    @Override
    public Product getById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public void addProduct(Product product) {
        if (productRepository.findByName(product.getName()).isPresent()) {
            throw new IllegalStateException("Product with this name already exists.");
        }
        productRepository.save(product);
    }

    @Override
    public void updateProduct(Long id, Product product) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + id));

        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setQuantity(product.getQuantity());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setImagePath(product.getImagePath());
        existingProduct.setProductType(product.getProductType());

        productRepository.save(existingProduct);
    }

    @Override
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("Product not found with ID: " + id);
        }
        productRepository.deleteById(id);
    }

}
