package com.project.HPharmacy.controller;

import com.project.HPharmacy.entity.Product;
import com.project.HPharmacy.repository.UserEntityRepository;
import com.project.HPharmacy.service.ProductService;
import com.project.HPharmacy.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductTypeService productTypeService;

    @Autowired
    private UserEntityRepository userEntityRepository;


    @GetMapping
    public ModelAndView shop(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long productTypeId,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(defaultValue = "name_asc") String sort,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "9") int size
    ) {
        ModelAndView modelAndView = new ModelAndView("shop");
        modelAndView.addObject("productTypes", productTypeService.getAllProductTypes());
        Page<Product> products = productService.getFilteredProducts(keyword, productTypeId, minPrice, maxPrice, sort, page, size);
        modelAndView.addObject("products", products);
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", products.getTotalPages());
        modelAndView.addObject("sort", sort);
        modelAndView.addObject("keyword", keyword);
        modelAndView.addObject("productTypeId", productTypeId);
        modelAndView.addObject("minPrice", minPrice);
        modelAndView.addObject("maxPrice", maxPrice);
        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView viewProductDetail(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("product-detail");
        Product product = productService.getProductById(id);
        if (product == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
        modelAndView.addObject("product", product);
        return modelAndView;
    }


    @GetMapping("/management")
    public ModelAndView productsManagement(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long productTypeId,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(defaultValue = "name_asc") String sort,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        ModelAndView modelAndView = new ModelAndView("products/management");
        modelAndView.addObject("productTypes", productTypeService.getAllProductTypes());
        Page<Product> products = productService.getFilteredProducts(keyword, productTypeId, minPrice, maxPrice, sort, page, size);
        modelAndView.addObject("products", products);
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", products.getTotalPages());
        modelAndView.addObject("sort", sort);
        modelAndView.addObject("keyword", keyword);
        modelAndView.addObject("productTypeId", productTypeId);
        modelAndView.addObject("minPrice", minPrice);
        modelAndView.addObject("maxPrice", maxPrice);
        return modelAndView;
    }

    @GetMapping({"/add", "/edit/{id}"})
    public String showProductForm(@PathVariable(required = false) Long id, Model model) {
        Product product = (id != null)
                ? productService.getById(id)
                : new Product();

        if (id != null && product == null) {
            throw new IllegalArgumentException("Product not found with ID: " + id);
        }

        model.addAttribute("product", product);
        model.addAttribute("isEdit", id != null); // Đánh dấu trạng thái (Thêm hoặc Chỉnh sửa)
        model.addAttribute("productTypes", productTypeService.getAllProductTypes());
        return "products/add-edit-product";
    }

    @PostMapping("/save")
    public String saveProduct(
            @RequestParam(required = false) Long id,
            Product product,
            @RequestParam("file") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, fileName);
            Files.copy(file.getInputStream(), filePath);
            product.setImagePath("/uploads/images/products/" + fileName);
        } else if (id != null) {
            Product existingProduct = productService.getById(id);
            if (existingProduct != null) {
                product.setImagePath(existingProduct.getImagePath());
            }
        }

        if (id != null) {
            productService.updateProduct(id, product);
        } else {
            productService.addProduct(product);
        }

        return "redirect:/products/management";
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            productService.delete(id);
            redirectAttributes.addFlashAttribute("successMessage", "Xóa sản phẩm thành công!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/products/management";
    }

}
