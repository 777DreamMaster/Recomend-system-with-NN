package com.chumakov.diplom.controllers;

import com.chumakov.diplom.model.Product;
import com.chumakov.diplom.model.Review;
import com.chumakov.diplom.repository.ReviewRepository;
import com.chumakov.diplom.service.ProductService;
import com.chumakov.diplom.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ReviewRepository reviewRepository;

    @GetMapping("/{id}")
    public String getCategoryPage(@PathVariable(value = "id") Product product, Model model) {
        model.addAttribute("title", product.getShortName());
        model.addAttribute("product", product);
        model.addAttribute("link", ProductService.createLink(product));
        return "product";
    }

    @PostMapping("/{id}")
    public String saveReview(@PathVariable(value = "id") Product product,
                             @RequestParam(value = "pros", required = false, defaultValue = "") String pros,
                             @RequestParam(value = "cons", required = false, defaultValue = "") String cons,
                             @RequestParam(value = "comment", required = false, defaultValue = "") String comment,
                             Model model) throws IOException, InterruptedException {
        int prediction = Integer.parseInt(ReviewService.getPrediction(pros, cons, comment));
        Review review = Review.builder()
                .product(product)
                .pros(pros)
                .cons(cons)
                .comment(comment)
                .predictedRating(prediction)
                .build();
        System.out.println(review);
        reviewRepository.save(review);

        model.addAttribute("title", product.getShortName());
        model.addAttribute("product", product);
        model.addAttribute("link", ProductService.createLink(product));
        return "product";
    }
}
