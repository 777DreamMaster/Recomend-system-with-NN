package com.chumakov.diplom.service;

import com.chumakov.diplom.model.Product;

import java.util.Comparator;

public class AverageRatingComparator implements Comparator<Product> {

    @Override
    public int compare(Product o1, Product o2) {
        if (o1.getAvgRating() == o2.getAvgRating()) {
            return o2.getReviews().size() - o1.getReviews().size();
        }
        return o2.getAvgRating() > o1.getAvgRating() ? 1 : -1;
    }
}
