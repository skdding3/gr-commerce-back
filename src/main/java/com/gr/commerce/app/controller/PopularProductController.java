package com.gr.commerce.app.controller;

import com.gr.commerce.app.mapper.PopularProductMapper;
import com.gr.commerce.app.service.PopularProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/main")
public class PopularProductController {

    private final PopularProductService popularProductService;

    @GetMapping("/popularProduct")
    @ResponseBody
    public List<?> LoadPopularProduct(){
        return popularProductService.loadPopularProductList();
    }
}
