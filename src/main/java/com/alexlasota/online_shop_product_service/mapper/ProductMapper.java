package com.alexlasota.online_shop_product_service.mapper;

import com.alexlasota.online_shop_product_service.dto.ProductAttributeDTO;
import com.alexlasota.online_shop_product_service.dto.ProductDTO;
import com.alexlasota.online_shop_product_service.model.Product;
import com.alexlasota.online_shop_product_service.model.ProductAttribute;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDTO toDTO(Product product);

    Product toEntity(ProductDTO dto);

    ProductAttributeDTO toAttributeDTO(ProductAttribute attribute);

    ProductAttribute toAttributeEntity(ProductAttributeDTO dto);
}

