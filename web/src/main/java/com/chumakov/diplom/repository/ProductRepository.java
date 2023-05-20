package com.chumakov.diplom.repository;

import com.chumakov.diplom.model.Category;
import com.chumakov.diplom.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByCategoryAndPriceAfter(Category category, Integer price);

    List<Product> findAllByCategoryAndPriceBetween(Category category, Integer price, Integer price2);

}
