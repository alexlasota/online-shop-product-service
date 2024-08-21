package com.alexlasota.online_shop_product_service.service.impl;

import com.alexlasota.online_shop_product_service.exception.ProductNotFoundException;
import com.alexlasota.online_shop_product_service.model.Product;
import com.alexlasota.online_shop_product_service.repository.ProductRepository;
import com.alexlasota.online_shop_product_service.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product createProduct(Product product) {
        if (product.getType() == null) {
            product.setType(Product.ProductType.ELECTRONICS);
        }
        return productRepository.save(product);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByType(Product.ProductType type) {
        return productRepository.findByType(type);
    }

    @Transactional
    @Override
    public Product updateProduct(Long id, Product product) {
        Product existingProduct = getProductById(id);
        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());

        if (existingProduct.getType() != Product.ProductType.COMPUTER
                && existingProduct.getType() != Product.ProductType.SMARTPHONE) {
            existingProduct.setType(product.getType());
        }
        return productRepository.save(existingProduct);
    }

    @Transactional
    @Override
    public void deleteProduct(Long id) {
        Product product = getProductById(id);
        if (product.getType() == Product.ProductType.COMPUTER
                || product.getType() == Product.ProductType.SMARTPHONE) {
            throw new IllegalStateException("Cannot delete a product of type Computer or Smartphone directly");
        }
        productRepository.delete(product);
    }
}