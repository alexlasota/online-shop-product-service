package com.alexlasota.online_shop_product_service.service;

import com.alexlasota.online_shop_product_service.model.Computer;

import java.util.List;
import java.util.Map;

public interface ComputerService {
    Computer createComputer(Computer computer);

    Computer getComputerById(Long id);

    List<Computer> getAllComputers();

    Computer updateComputer(Long id, Computer computer);

    void deleteComputer(Long id);

    Map<String, List<String>> getComputerConfigurations();
}