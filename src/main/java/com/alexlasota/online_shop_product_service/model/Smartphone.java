package com.alexlasota.online_shop_product_service.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "smartphones")
@Data
@NoArgsConstructor
public class Smartphone {

    @Id
    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column
    private String color;

    @Column
    private int batteryCapacityMah;

    public Smartphone(Product product, String color, int batteryCapacityMah) {
        this.product = product;
        this.color = color;
        this.batteryCapacityMah = batteryCapacityMah;
    }
}