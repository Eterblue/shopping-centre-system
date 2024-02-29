package com.eterblue.request;


import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel("增加商品传入的数据")
public class AddProductRequest {

    private Long id;

    private Long categoryId;

    private String name;

    private BigDecimal price;

    private String image;
}
