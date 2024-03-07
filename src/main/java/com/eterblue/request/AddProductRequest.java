package com.eterblue.request;


import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@ApiModel("增加商品传入的数据")
public class AddProductRequest {

    @Range(min = 1,message = "商品id最小为1")
    private Long id;

    @Range(min = 1,message = "分类id最小为1")
    private Long categoryId;

    @NotNull(message = "商品名字不为空")
    private String name;

    @NotNull(message = "商品价格不为空")
    private BigDecimal price;

    @URL
    private String image;
}
