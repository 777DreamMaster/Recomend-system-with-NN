package com.chumakov.diplom.model;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "review")
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Getter
@Setter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String pros;
    private String cons;
    private String comment;
    private Integer rating;
    @Column(name = "predicted_rating")
    private Integer predictedRating;

    @ManyToOne(fetch = FetchType.EAGER)
    private Product product;
}
