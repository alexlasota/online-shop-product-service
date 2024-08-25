package com.alexlasota.online_shop_product_service.service;

import com.alexlasota.online_shop_product_service.dto.ProductAttributeDTO;
import com.alexlasota.online_shop_product_service.dto.ProductDTO;
import com.alexlasota.online_shop_product_service.mapper.ProductMapper;
import com.alexlasota.online_shop_product_service.model.Product;
import com.alexlasota.online_shop_product_service.model.ProductAttribute;
import com.alexlasota.online_shop_product_service.repository.ProductAttributeRepository;
import com.alexlasota.online_shop_product_service.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductAttributeRepository productAttributeRepository;
    private final ProductMapper productMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductAttributeRepository productAttributeRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productAttributeRepository = productAttributeRepository;
        this.productMapper = productMapper;
    }

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<ProductDTO> getProductById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toDTO);
    }

    public List<ProductDTO> getProductsByType(String type) {
        return productRepository.findByType(type).stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductDTO saveProduct(ProductDTO productDTO) {
        Product product = productMapper.toEntity(productDTO);
        Product savedProduct = productRepository.save(product);
        return productMapper.toDTO(savedProduct);
    }

    @Transactional
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setName(productDTO.getName());
                    product.setPrice(productDTO.getPrice());
                    product.setType(productDTO.getType());
                    product.setAttributes(productDTO.getAttributes().stream()
                            .map(productMapper::toAttributeEntity)
                            .collect(Collectors.toSet()));
                    return productMapper.toDTO(productRepository.save(product));
                })
                .orElseThrow(() -> new RuntimeException("Product not found with id " + id));
    }


    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Transactional
    public ProductDTO addAttributeToProduct(Long productId, ProductAttributeDTO attributeDTO) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id " + productId));
        ProductAttribute attribute = productMapper.toAttributeEntity(attributeDTO);
        product.getAttributes().add(attribute);
        attribute.getProducts().add(product);
        return productMapper.toDTO(productRepository.save(product));
    }

    @Transactional
    public ProductDTO removeAttributeFromProduct(Long productId, Long attributeId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id " + productId));
        product.setAttributes(product.getAttributes().stream()
                .filter(attr -> !attr.getId().equals(attributeId))
                .collect(Collectors.toSet()));
        return productMapper.toDTO(productRepository.save(product));
    }

    public Map<String, Set<String>> getProductConfigurations(String type) {
        List<Product> products = productRepository.findByType(type);
        Map<String, Set<String>> configurations = new HashMap<>();

        for (Product product : products) {
            for (ProductAttribute attribute : product.getAttributes()) {
                configurations
                        .computeIfAbsent(attribute.getAttributeName(), k -> new HashSet<>())
                        .add(attribute.getAttributeValue());
            }
        }

        return configurations;
    }
}