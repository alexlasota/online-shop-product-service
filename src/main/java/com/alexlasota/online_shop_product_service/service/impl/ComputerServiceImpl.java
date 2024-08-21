package com.alexlasota.online_shop_product_service.service.impl;

import com.alexlasota.online_shop_product_service.model.Computer;
import com.alexlasota.online_shop_product_service.model.Product;
import com.alexlasota.online_shop_product_service.repository.ComputerRepository;
import com.alexlasota.online_shop_product_service.service.ComputerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ComputerServiceImpl implements ComputerService {

    private final ComputerRepository computerRepository;

    @Autowired
    public ComputerServiceImpl(ComputerRepository computerRepository) {
        this.computerRepository = computerRepository;
    }

    @Override
    public Computer createComputer(Computer computer) {
        return computerRepository.save(computer);
    }

    @Override
    public Computer getComputerById(Long id) {
        return computerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Computer not found"));
    }

    @Override
    public List<Computer> getAllComputers() {
        return computerRepository.findAll();
    }

    @Override
    public Computer updateComputer(Long id, Computer computer) {
        Computer existingComputer = getComputerById(id);
        existingComputer.setProcessor(computer.getProcessor());
        existingComputer.setRamGB(computer.getRamGB());
        existingComputer.getProduct().setName(computer.getProduct().getName());
        existingComputer.getProduct().setPrice(computer.getProduct().getPrice());
        existingComputer.getProduct().setType(Product.ProductType.COMPUTER);
        return computerRepository.save(existingComputer);
    }

    @Override
    public void deleteComputer(Long id) {
        computerRepository.deleteById(id);
    }

    @Override
    public Map<String, List<String>> getComputerConfigurations() {
        Map<String, List<String>> configurations = new HashMap<>();
        configurations.put("processors", Arrays.asList("Intel i5", "Intel i7", "AMD Ryzen 5", "AMD Ryzen 7"));
        configurations.put("ramOptions", Arrays.asList("8GB", "16GB", "32GB", "64GB"));
        return configurations;
    }
}