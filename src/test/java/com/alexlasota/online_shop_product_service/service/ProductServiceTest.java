package com.alexlasota.online_shop_product_service.service;

import com.alexlasota.online_shop_product_service.dto.ProductDTO;
import com.alexlasota.online_shop_product_service.mapper.ProductMapper;
import com.alexlasota.online_shop_product_service.model.Product;
import com.alexlasota.online_shop_product_service.model.ProductAttribute;
import com.alexlasota.online_shop_product_service.repository.ProductRepository;
import com.alexlasota.online_shop_product_service.repository.ProductAttributeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private ProductAttributeRepository productAttributeRepository;

    @MockBean
    private ProductMapper productMapper;

    @Test
    void getAllProducts_ReturnsListOfProducts() {
        List<Product> products = Arrays.asList(new Product(), new Product());
        List<ProductDTO> productDTOs = Arrays.asList(new ProductDTO(), new ProductDTO());

        when(productRepository.findAll()).thenReturn(products);
        when(productMapper.toDTO(any(Product.class))).thenReturn(new ProductDTO());

        List<ProductDTO> result = productService.getAllProducts();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(productRepository).findAll();
        verify(productMapper, times(2)).toDTO(any(Product.class));
    }

    @Test
    void getProductById_ExistingProduct_ReturnsProduct() {
        Product product = new Product();
        product.setId(1L);
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productMapper.toDTO(product)).thenReturn(productDTO);

        Optional<ProductDTO> result = productService.getProductById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void getProductById_NonExistingProduct_ReturnsEmpty() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<ProductDTO> result = productService.getProductById(1L);

        assertFalse(result.isPresent());
    }

    @Test
    void saveProduct_ValidProduct_ReturnsSavedProduct() {
        ProductDTO inputDTO = new ProductDTO();
        inputDTO.setName("Test Product");
        inputDTO.setPrice(new BigDecimal("10.00"));

        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setPrice(new BigDecimal("10.00"));

        ProductDTO outputDTO = new ProductDTO();
        outputDTO.setId(1L);
        outputDTO.setName("Test Product");
        outputDTO.setPrice(new BigDecimal("10.00"));

        when(productMapper.toEntity(inputDTO)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toDTO(product)).thenReturn(outputDTO);

        ProductDTO result = productService.saveProduct(inputDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Product", result.getName());
        assertEquals(new BigDecimal("10.00"), result.getPrice());
    }

    @Test
    void getProductConfigurations_ReturnsConfigurations() {
        Product product1 = new Product();
        product1.setType("Laptop");
        ProductAttribute attr1 = new ProductAttribute();
        attr1.setAttributeName("RAM");
        attr1.setAttributeValue("8GB");
        product1.setAttributes(new HashSet<>(Collections.singletonList(attr1)));

        Product product2 = new Product();
        product2.setType("Laptop");
        ProductAttribute attr2 = new ProductAttribute();
        attr2.setAttributeName("RAM");
        attr2.setAttributeValue("16GB");
        product2.setAttributes(new HashSet<>(Collections.singletonList(attr2)));

        when(productRepository.findByType("Laptop")).thenReturn(Arrays.asList(product1, product2));

        Map<String, Set<String>> result = productService.getProductConfigurations("Laptop");

        assertNotNull(result);
        assertTrue(result.containsKey("RAM"));
        assertEquals(2, result.get("RAM").size());
        assertTrue(result.get("RAM").contains("8GB"));
        assertTrue(result.get("RAM").contains("16GB"));
    }
}