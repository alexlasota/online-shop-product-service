package com.alexlasota.online_shop_product_service.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "computers")
@Data
@NoArgsConstructor
public class Computer {

    @Id
    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column
    private String processor;

    @Column
    private int ramGB;

    public Computer(Product product, String processor, int ramGB) {
        this.product = product;
        this.processor = processor;
        this.ramGB = ramGB;
    }
}