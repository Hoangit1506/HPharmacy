package com.project.HPharmacy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
//    @Autowired
//    private ProductService productService;
//
//    @GetMapping
//    public String adminHome(Model model) {
//        model.addAttribute("title", "Admin Profile");
//        return "admin/profile";
//    }

    // Danh sách sản phẩm
//    @GetMapping("/products")
//    public String listProducts(@RequestParam(defaultValue = "1") int page, Model model) {
//        model.addAttribute("products", productService.getPaginatedProducts(page));
//        return "admin/products/list";
//    }

//    @GetMapping("/products")
//    public String listProducts(
//            @RequestParam(defaultValue = "1") int page,
//            @RequestParam(defaultValue = "10") int size,
//            @RequestParam(required = false) String keyword,
//            @RequestParam(required = false) Long productTypeId,
//            Model model) {
//
//        Page<Product> productPage;
//
//        if (keyword != null && !keyword.isEmpty()) {
//            productPage = productService.searchProducts(keyword, page, size, sort);
//        } else if (productTypeId != null) {
//            productPage = productService.filterProductsByType(productTypeId, page, size, sort);
//        } else {
//            productPage = productService.getPaginatedProducts(page, size, sort);
//        }
//
//        model.addAttribute("products", productPage.getContent());
//        model.addAttribute("currentPage", page);
//        model.addAttribute("totalPages", productPage.getTotalPages());
//        model.addAttribute("keyword", keyword);
//        model.addAttribute("productTypeId", productTypeId);
//
//        // Kiểm tra danh sách trống
//        if (productPage.isEmpty()) {
//            model.addAttribute("message", "Không tìm thấy sản phẩm nào.");
//        }
//
//        return "admin/products/list";
//    }
//
//    // Thêm sản phẩm mới (GET)
//    @GetMapping("/add")
//    public String addProductForm(Model model) {
//        model.addAttribute("product", new Product());
//        return "admin/products/add";
//    }
//
//    // Thêm sản phẩm mới (POST)
//    @PostMapping("/add")
//    public String addProduct(@ModelAttribute @Valid Product product, BindingResult bindingResult, Model model) {
//        if (bindingResult.hasErrors()) {
//            return "admin/products/add";
//        }
//        try {
//            productService.addProduct(product);
//            return "redirect:/admin/products";
//        } catch (IllegalStateException e) {
//            model.addAttribute("error", e.getMessage());
//            return "admin/products/add";
//        }
//    }
//
//    // Chỉnh sửa sản phẩm (GET)
//    @GetMapping("/edit/{id}")
//    public String editProductForm(@PathVariable Long id, Model model) {
//        Product product = productService.getById(id);
//        if (product == null) {
//            return "redirect:/admin/products?error=notfound";
//        }
//        model.addAttribute("product", product);
//        return "admin/products/edit";
//    }
//
//    // Chỉnh sửa sản phẩm (POST)
//    @PostMapping("/edit/{id}")
//    public String editProduct(@PathVariable Long id, @ModelAttribute @Valid Product product, BindingResult bindingResult, Model model) {
//        if (bindingResult.hasErrors()) {
//            return "admin/products/edit";
//        }
//        try {
//            productService.updateProduct(id, product);
//            return "redirect:/admin/products";
//        } catch (IllegalArgumentException e) {
//            model.addAttribute("error", e.getMessage());
//            return "admin/products/edit";
//        }
//    }
//
//    // Xóa sản phẩm
//    @GetMapping("/delete/{id}")
//    public String deleteProduct(@PathVariable Long id) {
//        try {
//            productService.delete(id);
//            return "redirect:/admin/products";
//        } catch (IllegalArgumentException e) {
//            return "redirect:/admin/products?error=" + e.getMessage();
//        }
//    }
}


//// Chỉnh sửa sản phẩm (POST)
//@PostMapping("/edit/{id}")
//public String editProduct(@PathVariable Long id, @ModelAttribute Product product) {
//  Product existingProduct = productService.getById(id);
//  if (existingProduct == null) {
//      return "redirect:/admin/products?error=notfound";
//  }
//
//  // Cập nhật các trường
//  existingProduct.setName(product.getName());
//  existingProduct.setPrice(product.getPrice());
//  existingProduct.setDescription(product.getDescription());
//  existingProduct.setQuantity(product.getQuantity());
//  existingProduct.setImagePath(product.getImagePath());
//  existingProduct.setProductType(product.getProductType());
//
//  // Lưu sản phẩm
//  productService.save(existingProduct);
//  return "redirect:/admin/products";
//}