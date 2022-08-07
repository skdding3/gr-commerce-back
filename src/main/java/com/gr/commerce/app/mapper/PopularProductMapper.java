package com.gr.commerce.app.mapper;

import com.gr.commerce.app.model.PopularProductDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PopularProductMapper {
    List<PopularProductDto> loadPopularProductList();
}
