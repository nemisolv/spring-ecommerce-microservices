package net.nemisolv.productservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@SuperBuilder

public class Product extends IdBaseEntity {
    private String name;
    private String sku; // Mã sản phẩm
    @Column(columnDefinition = "TEXT")
    private String description;
    private BigDecimal price;
    private String unit;
    private String mainImgUrl;
    private Integer quantity;
    private Float averageRating;
    private String videoUrl;
    private boolean active; // status

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductImage> images;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductVariant> variants;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = true) // Optional
    private Brand brand;


    @Column(columnDefinition = "TEXT")
    private String reviewStory;

}
