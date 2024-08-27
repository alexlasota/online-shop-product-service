package com.alexlasota.online_shop_product_service.controller;

import com.alexlasota.online_shop_product_service.dto.ProductDTO;
import com.alexlasota.online_shop_product_service.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @Test
    void getAllProducts_ReturnsListOfProducts() throws Exception {
        List<ProductDTO> products = new ArrayList<>();

        ProductDTO product1 = new ProductDTO();
        product1.setId(1L);
        product1.setName("Product 1");
        product1.setPrice(new BigDecimal("10.00"));
        product1.setType("Electronics");
        product1.setAttributes(new HashSet<>());
        products.add(product1);

        ProductDTO product2 = new ProductDTO();
        product2.setId(2L);
        product2.setName("Product 2");
        product2.setPrice(new BigDecimal("20.00"));
        product2.setType("Electronics");
        product2.setAttributes(new HashSet<>());
        products.add(product2);

        when(productService.getAllProducts()).thenReturn(products);

        mockMvc.perform(get("/api/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Product 1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Product 2"));
    }

    @Test
    void getProductById_ExistingProduct_ReturnsProduct() throws Exception {
        ProductDTO product = new ProductDTO();
        product.setId(1L);
        product.setName("Product 1");
        product.setPrice(new BigDecimal("10.00"));
        product.setType("Electronics");
        product.setAttributes(new HashSet<>());

        when(productService.getProductById(1L)).thenReturn(Optional.of(product));

        mockMvc.perform(get("/api/products/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Product 1"));
    }

    @Test
    void getProductById_NonExistingProduct_ReturnsNotFound() throws Exception {
        when(productService.getProductById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/products/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("Product not found with id: 1"));
    }

    @Test
    void createProduct_ValidProduct_ReturnsCreatedProduct() throws Exception {
        ProductDTO inputProduct = new ProductDTO();
        inputProduct.setName("New Product");
        inputProduct.setPrice(new BigDecimal("15.00"));
        inputProduct.setType("Electronics");
        inputProduct.setAttributes(new HashSet<>());

        ProductDTO createdProduct = new ProductDTO();
        createdProduct.setId(1L);
        createdProduct.setName("New Product");
        createdProduct.setPrice(new BigDecimal("15.00"));
        createdProduct.setType("Electronics");
        createdProduct.setAttributes(new HashSet<>());

        when(productService.saveProduct(any(ProductDTO.class))).thenReturn(createdProduct);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputProduct)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("New Product"));
    }

    @Test
    void getProductConfigurations_ReturnsConfigurations() throws Exception {
        Map<String, Set<String>> configurations = new HashMap<>();
        configurations.put("RAM", new HashSet<>(Arrays.asList("8GB", "16GB")));
        configurations.put("CPU", new HashSet<>(Arrays.asList("i5", "i7")));

        when(productService.getProductConfigurations("Laptop")).thenReturn(configurations);

        mockMvc.perform(get("/api/products/configurations/{type}", "Laptop")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.RAM").isArray())
                .andExpect(jsonPath("$.RAM[0]").value("8GB"))
                .andExpect(jsonPath("$.RAM[1]").value("16GB"))
                .andExpect(jsonPath("$.CPU").isArray())
                .andExpect(jsonPath("$.CPU[0]").value("i5"))
                .andExpect(jsonPath("$.CPU[1]").value("i7"));
    }
}