package com.gr.commerce.app.service;

import com.gr.commerce.app.mapper.PopularProductMapper;
import com.gr.commerce.app.model.PopularProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PopularProductService {
    private final PopularProductMapper mapper;
    public List<PopularProductDto> loadPopularProductList(){
        ArrayList popularProductList = (ArrayList) mapper.loadPopularProductList();
        return popularProductList;
    }
}
