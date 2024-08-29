package com.alexlasota.online_shop_product_service.controller;

import com.alexlasota.online_shop_product_service.dto.ProductAttributeDTO;
import com.alexlasota.online_shop_product_service.dto.ProductDTO;
import com.alexlasota.online_shop_product_service.exception.ProductNotFoundException;
import com.alexlasota.online_shop_product_service.mapper.ProductMapper;
import com.alexlasota.online_shop_product_service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @Autowired
    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductDTOWithFinalPrice(id));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<ProductDTO>> getProductsByType(@PathVariable String type) {
        return ResponseEntity.ok(productService.getProductsByType(type));
    }

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.saveProduct(productDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.updateProduct(id, productDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/attributes")
    public ResponseEntity<ProductDTO> addAttributeToProduct(@PathVariable Long id, @RequestBody ProductAttributeDTO attributeDTO) {
        return ResponseEntity.ok(productService.addAttributeToProduct(id, attributeDTO));
    }

    @DeleteMapping("/{productId}/attributes/{attributeId}")
    public ResponseEntity<ProductDTO> removeAttributeFromProduct(@PathVariable Long productId, @PathVariable Long attributeId) {
        return ResponseEntity.ok(productService.removeAttributeFromProduct(productId, attributeId));
    }

    @GetMapping("/configurations/{type}")
    public ResponseEntity<Map<String, Set<String>>> getProductConfigurations(@PathVariable String type) {
        return ResponseEntity.ok(productService.getProductConfigurations(type));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handleProductNotFoundException(ProductNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }
}