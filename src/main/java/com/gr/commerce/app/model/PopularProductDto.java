package com.gr.commerce.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PopularProductDto {
    private int id;
    private String itemId;
    private String itemNm;
    private String img;
    private String cat;
}
