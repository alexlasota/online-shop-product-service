package com.alexlasota.online_shop_product_service.service.impl;

import com.alexlasota.online_shop_product_service.model.Product;
import com.alexlasota.online_shop_product_service.model.Smartphone;
import com.alexlasota.online_shop_product_service.repository.ProductRepository;
import com.alexlasota.online_shop_product_service.repository.SmartphoneRepository;
import com.alexlasota.online_shop_product_service.service.SmartphoneService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SmartphoneServiceImpl implements SmartphoneService {

    private final SmartphoneRepository smartphoneRepository;
    private final ProductRepository productRepository;

    @Autowired
    public SmartphoneServiceImpl(SmartphoneRepository smartphoneRepository, ProductRepository productRepository) {
        this.smartphoneRepository = smartphoneRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    @Override
    public Smartphone createSmartphone(Smartphone smartphone) {
        Product product = smartphone.getProduct();
        product.setType(Product.ProductType.SMARTPHONE);
        Product savedProduct = productRepository.save(product);
        smartphone.setProduct(savedProduct);
        return smartphoneRepository.save(smartphone);
    }

    @Override
    public Smartphone getSmartphoneById(Long id) {
        return smartphoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Smartphone not found"));
    }

    @Override
    public List<Smartphone> getAllSmartphones() {
        return smartphoneRepository.findAll();
    }

    @Transactional
    @Override
    public Smartphone updateSmartphone(Long id, Smartphone smartphone) {
        Smartphone existingSmartphone = getSmartphoneById(id);
        existingSmartphone.setColor(smartphone.getColor());
        existingSmartphone.setBatteryCapacityMah(smartphone.getBatteryCapacityMah());
        existingSmartphone.getProduct().setName(smartphone.getProduct().getName());
        existingSmartphone.getProduct().setPrice(smartphone.getProduct().getPrice());
        existingSmartphone.getProduct().setType(Product.ProductType.SMARTPHONE);
        return smartphoneRepository.save(existingSmartphone);
    }

    @Transactional
    @Override
    public void deleteSmartphone(Long id) {
        Smartphone smartphone = getSmartphoneById(id);
        Product product = smartphone.getProduct();
        product.setType(Product.ProductType.ELECTRONICS);
        productRepository.save(product);
        smartphoneRepository.delete(smartphone);
    }
    @Override
    public Map<String, List<String>> getSmartphoneConfigurations() {
        Map<String, List<String>> configurations = new HashMap<>();
        configurations.put("colors", Arrays.asList("Black", "White", "Red", "Blue"));
        configurations.put("batteryCapacities", Arrays.asList("3000mAh", "4000mAh", "5000mAh"));
        return configurations;
    }
}