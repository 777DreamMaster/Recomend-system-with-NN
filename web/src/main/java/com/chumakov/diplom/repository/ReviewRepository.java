package com.chumakov.diplom.repository;

import com.chumakov.diplom.model.Product;
import com.chumakov.diplom.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByProduct(Product product);

}
