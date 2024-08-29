package com.alexlasota.online_shop_product_service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private BigDecimal basePrice;
    private BigDecimal finalPrice;
    private String type;
    private Set<ProductAttributeDTO> attributes = new HashSet<>();
}