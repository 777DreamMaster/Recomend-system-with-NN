package com.chumakov.diplom.controllers;

import com.chumakov.diplom.service.ReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
@RequestMapping("/review")
public class ReviewController {

    @GetMapping()
    public String getCheckReview(@RequestParam(value = "pros", required = false) String pros,
                                 @RequestParam(value = "cons", required = false) String cons,
                                 @RequestParam(value = "comment", required = false) String comment,
                                 Model model) throws IOException, InterruptedException {
        if (StringUtils.hasText(pros) || StringUtils.hasText(cons) || StringUtils.hasText(comment)) {
            model.addAttribute("pros", pros);
            model.addAttribute("cons", cons);
            model.addAttribute("comment", comment);
            model.addAttribute("isHidden", false);

            String pred = ReviewService.getPrediction(pros, cons, comment);
            model.addAttribute("predicted", Integer.valueOf(pred));
        } else {
            model.addAttribute("isHidden", true);
            model.addAttribute("predicted", 0);
        }
        model.addAttribute("title", "Узнайте оценку отзыва");
        return "review";
    }

}
