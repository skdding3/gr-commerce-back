package com.gr.commerce.app.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PopularProductDto {
    private int id;
    private String itemId;
    private String itemNm;
    private String img;
    private String cat;
}
