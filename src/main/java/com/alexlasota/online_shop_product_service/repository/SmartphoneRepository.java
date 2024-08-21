package com.alexlasota.online_shop_product_service.repository;

import com.alexlasota.online_shop_product_service.model.Smartphone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmartphoneRepository extends JpaRepository<Smartphone, Long> {
}