package com.alexlasota.online_shop_product_service.service;

import com.alexlasota.online_shop_product_service.model.Smartphone;

import java.util.List;
import java.util.Map;

public interface SmartphoneService {

    Smartphone createSmartphone(Smartphone smartphone);
    Smartphone getSmartphoneById(Long id);
    List<Smartphone> getAllSmartphones();
    Smartphone updateSmartphone(Long id, Smartphone smartphone);
    void deleteSmartphone(Long id);

    Map<String, List<String>> getSmartphoneConfigurations();
}