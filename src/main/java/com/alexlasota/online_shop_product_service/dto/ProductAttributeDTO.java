package com.alexlasota.online_shop_product_service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductAttributeDTO {
    private Long id;
    private String attributeName;
    private String attributeValue;
}