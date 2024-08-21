package com.alexlasota.online_shop_product_service.service;

import com.alexlasota.online_shop_product_service.model.Product;

import java.util.List;

public interface ProductService {

    Product createProduct(Product product);

    Product getProductById(Long id);

    List<Product> getAllProducts();

    List<Product> getProductsByType(Product.ProductType type);

    Product updateProduct(Long id, Product product);

    void deleteProduct(Long id);
}