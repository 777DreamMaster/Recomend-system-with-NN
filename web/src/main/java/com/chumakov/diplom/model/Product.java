package com.chumakov.diplom.model;

import com.chumakov.diplom.dto.Property;
import com.chumakov.diplom.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.OptionalDouble;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @Column(name = "slug", nullable = false)
    private String urlName;
    private Boolean isAvailable;
    @Column(name = "short_name")
    private String shortName;
    private String description;
    private Integer price;
    @Column(name = "properties")
    private String properties;
    @Column(name = "image_url")
    private String imageUrl;
    private transient double avgRating;
    private transient double avgPredRating;
    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private Set<Review> reviews;

    public List<Property> jsonProperties() throws JsonProcessingException {
        return ProductService.jsonProperties(properties);
    }

    public double getAvgRating() {
        OptionalDouble avg =
                reviews.stream()
                        .filter(x -> x.getRating() != null)
                        .mapToDouble(Review::getRating)
                        .average();
        BigDecimal result = BigDecimal.valueOf(avg.isPresent() ? avg.getAsDouble() : 0);

        result = result.setScale(2, RoundingMode.UP);
        return result.doubleValue();
    }

    public double getAvgPredRating() {
        OptionalDouble avgPred =
                reviews.stream()
                        .filter(x -> x.getPredictedRating() != null)
                        .mapToDouble(Review::getPredictedRating)
                        .average();
        BigDecimal result = BigDecimal.valueOf(
                                    avgPred.isPresent()
                                        ? avgPred.getAsDouble()
                                        : 0);

        result = result.setScale(2, RoundingMode.UP);
        return result.doubleValue();
    }

}
