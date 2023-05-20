package com.chumakov.diplom.service;

import com.chumakov.diplom.model.Product;

import java.util.Comparator;

public class AveragePredictedRatingComparator implements Comparator<Product> {

    @Override
    public int compare(Product o1, Product o2) {
        if (o1.getAvgPredRating() == o2.getAvgPredRating()) {
            return o2.getReviews().size() - o1.getReviews().size();
        }
        return o2.getAvgPredRating() > o1.getAvgPredRating() ? 1 : -1;
    }
}
