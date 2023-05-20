package com.chumakov.diplom.service;


import com.chumakov.diplom.dto.Property;
import com.chumakov.diplom.model.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class ProductService {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static List<Property> jsonProperties(String json) throws JsonProcessingException {
        String clear = json.replaceAll("\"", "ё");
        clear = clear.replaceAll("'", "\"");
        List<Property> properties = objectMapper.readValue(clear, new TypeReference<>() {});
        properties.forEach(x -> {
            String t = x.getName();
            x.setName(t.substring(0, 1).toUpperCase() + t.substring(1));
            x.setValue(x.getValue().replaceAll("ё", "\""));
        });
        return properties;
    }

    public static String createLink(Product product) {
        return String.format("https://www.citilink.ru/product/%s-%s/",
                product.getUrlName(),
                product.getId());
    }
}
