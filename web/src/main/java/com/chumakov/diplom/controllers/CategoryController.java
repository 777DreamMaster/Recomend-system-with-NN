package com.chumakov.diplom.controllers;

import com.chumakov.diplom.model.Category;
import com.chumakov.diplom.model.Product;
import com.chumakov.diplom.repository.CategoryRepository;
import com.chumakov.diplom.repository.ProductRepository;
import com.chumakov.diplom.service.AveragePredictedRatingComparator;
import com.chumakov.diplom.service.AverageRatingComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ProductRepository productRepository;

    Comparator<Product> ratingComparator = new AverageRatingComparator();
    Comparator<Product> predRatingComparator = new AveragePredictedRatingComparator();

    @GetMapping()
    public String getListPage(Model model) {
        List<String> images = new ArrayList<>(List.of(
                "https://cdn.citilink.ru/CVk15QRxW7eHBdN2KR-iYQeyhzSoZrZQo4Ea4PwE1Ts/resizing_type:fit/gravity:sm/width:400/height:400/plain/items/1373055_v01_b.jpg",
                "https://cdn.citilink.ru/qtoTbCml5hlzZ7eC_ya0JQW2rQVpABlrXMRv6wihKs0/resizing_type:fit/gravity:sm/width:400/height:400/plain/items/1454156_v01_b.jpg",
                "https://cdn.citilink.ru/4vQCzrNQrtj-TPQeU1b5PFc6mtuvfMB8s4iMUsuEYCI/resizing_type:fit/gravity:sm/width:400/height:400/plain/items/1530201_v01_b.jpg",
                "https://cdn.citilink.ru/dCAApaXwVSj_2SyXMLnxOYvNoTLzSrbp2rkgfXam31k/resizing_type:fit/gravity:sm/width:400/height:400/plain/items/1795723_v01_b.jpg",
                "https://cdn.citilink.ru/y9Sshea__q6tck8ktCVOWDsdA3nioEPmpI0znAFjtkc/resizing_type:fit/gravity:sm/width:400/height:400/plain/items/1836492_v01_b.jpg"
        ));
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("images", images);

        return "categories";
    }

    @GetMapping("/{id}")
    public String getCategoryPage(@PathVariable(value = "id") Category category,
                                  @RequestParam(required = false, defaultValue = "1") Integer from,
                                  @RequestParam(required = false, defaultValue = "100000") Integer to,
                                  Model model) {
        model.addAttribute("title", "Страница категорий");
        model.addAttribute("category", category);
        model.addAttribute("from", from);
        model.addAttribute("to", to);

        List<Product> products = productRepository.findAllByCategoryAndPriceBetween(category, from, to);
        model.addAttribute("products", products);

        return "products";
    }

    @GetMapping("/{id}/filtered")
    public String filterProducts(@PathVariable(value = "id") Category category,
                               @RequestParam(required = false) String type,
                               @RequestParam(required = false, defaultValue = "1") Integer from,
                               @RequestParam(required = false, defaultValue = "100000") Integer to,
                               Model model) {
        model.addAttribute("title", category.getAltName());
        model.addAttribute("category", category);
        model.addAttribute("from", from);
        model.addAttribute("to", to);
        model.addAttribute("type", type);

        List<Product> products = productRepository.findAllByCategoryAndPriceBetween(category, from, to);
        if (type.equals("predicted")) products.sort(predRatingComparator);
        if (type.equals("simple")) products.sort(ratingComparator);

        model.addAttribute("products", products);
        return "products";
    }

}
